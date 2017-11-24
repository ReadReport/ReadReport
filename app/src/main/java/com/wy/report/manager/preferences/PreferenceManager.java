package com.wy.report.manager.preferences;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cantalou.android.util.Log;
import com.cantalou.android.util.ReflectUtil;
import com.wy.report.ReportApplication;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author cantalou
 * @date 2017年11月23日 11:30
 */
@SuppressWarnings("unchecked")
public class PreferenceManager {

    private static final Class[] SUPPORTED_TYPE = new Class[]{boolean.class, Boolean.class, int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, String.class};

    private SharedPreferences preferences;

    private Field mapField;

    private static class InstanceHolder {
        static final PreferenceManager instance = new PreferenceManager();
    }

    private PreferenceManager() {
        preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(ReportApplication.getGlobalContext());
    }

    public static final PreferenceManager getInstance() {
        return InstanceHolder.instance;
    }

    public void setValue(String key, Object value) {

        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Param key can not be null or blank");
        }

        if (value == null) {
            throw new NullPointerException("Param value can not be null");
        }

        SharedPreferences.Editor editor = preferences.edit();

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else {
            editor.putString(key, JSON.toJSONString(value));
        }
        editor.apply();
    }


    public <T> T getValue(String key, Class<?> type, Object defaultValue) {

        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Param key can not be null or blank");
        }

        if (mapField == null) {
            mapField = ReflectUtil.findField(preferences.getClass(), "mMap");
            mapField.setAccessible(true);
        }

        Map<String, ?> all = null;
        try {
            all = (Map<String, ?>) mapField.get(preferences);
        } catch (Exception e) {
            Log.w("Can not find field mMap in {}", preferences.getClass());
        }
        if (all == null) {
            all = preferences.getAll();
        }

        Object value = all.get(key);
        if (value == null) {
            return (T) defaultValue;
        }

        if (type != null && value instanceof String) {
            return (T) JSON.parseObject((String) value, type);
        }

        Class<?> valueClass = value.getClass();
        for (Class<?> supported : SUPPORTED_TYPE) {
            if (supported == valueClass) {
                return (T) value;
            }
        }

        throw new RuntimeException("Unknown key " + key + ",value " + value);
    }

    public <T> T getValue(String key, Object defaultValue) {
        return (T) getValue(key, null, defaultValue);
    }

    public <T> T getValue(String key) {
        return (T) getValue(key, null, null);
    }

}
