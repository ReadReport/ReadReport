package com.wy.report.util;/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author cantalou
 * @date 2017-11-26 21:26
 */

import android.text.TextUtils;
import android.widget.TextView;

public class StringUtils {

    /**
     * 隐藏手机号码<br/>
     * 将第4位开始到第7位替换为*号
     *
     * @param phoneNumber
     * @return
     */
    public static String hidePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 4) {
            return phoneNumber;
        }
        char[] value = phoneNumber.toCharArray();
        for (int i = 3; i < 7 && i < value.length; i++) {
            value[i] = '*';
        }
        return new String(value);
    }

    public static String formatTime(long time) {

        long currentTime = System.currentTimeMillis();

        if (currentTime < time) {
            return "刚刚";
        }

        long interval = (currentTime - time) / 1000;

        //当1分钟以内显示：刚刚（0:00:00~0:00:59）
        if (interval < 60) {
            return "刚刚";
        }

        //当1小时以内显示具体几分钟前：13分钟前
        if (interval < 3600) {
            return interval / 60 + "分钟前";
        }

        //当24小时以内显示具体几小时前：1小时前
        if (interval < 3600 * 24) {
            return interval / 3600 + "小时前";
        }

        //当超过24小时就显示昨天：昨天（24:00:00~47:59:59）
        if (interval < 3600 * 24 * 2) {
            return "昨天";
        }

        //当超过48小时就显示几天前：2天前（48:00:00~71:59:59）
        return interval / (3600 * 24) + "天前";
    }

    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters.</p>
     * <p>
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case sensitive.</p>
     * <p>
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1 the first CharSequence, may be {@code null}
     * @param cs2 the second CharSequence, may be {@code null}
     * @return {@code true} if the String are equal (case-sensitive), or both {@code null}
     * @see Object#equals(Object)
     * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     */
    public static boolean equals(final String cs1, final String cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        return cs1.equals(cs2);
    }

    /**
     * Checks if a CharSequence is whitespace, empty ("") or null.<br>
     * StringUtils.isBlank(null)      = true<br>
     * StringUtils.isBlank("")        = true<br>
     * StringUtils.isBlank(" ")       = true<br>
     * StringUtils.isBlank("bob")     = false<br>
     * StringUtils.isBlank("  bob  ") = false<br>
     *
     * @param str the String to check, may be null
     * @return if the String is null, empty or whitespace
     * @author LinZhiWei
     * @date 2015年5月6日 下午1:55:43
     */
    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || "null".equals(str)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a CharSequence is whitespace, empty ("") or null.<br>
     * StringUtils.isBlank(null)      = true<br>
     * StringUtils.isBlank("")        = true<br>
     * StringUtils.isBlank(" ")       = true<br>
     * StringUtils.isBlank("bob")     = false<br>
     * StringUtils.isBlank("  bob  ") = false<br>
     *
     * @param tv the TextView to check, may be null
     * @return if the EditText is null, empty or whitespace
     * @author LinZhiWei
     * @date 2015年5月6日 下午1:55:43
     */
    public static boolean isBlank(TextView tv) {
        return tv == null || isBlank(tv.getText());
    }

    /**
     * Checks if a CharSequence is  not whitespace, not empty ("") ,not null.<br>
     *
     * @param str the String to check, may be null
     * @return trur if the String is not null, not empty , not whitespace
     * @author LinZhiWei
     * @date 2015年5月6日 下午1:55:43
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * Checks if a EditText is  not whitespace, not empty ("") ,not null.<br>
     *
     * @param str the String to check, may be null
     * @return trur if the String is not null, not empty , not whitespace
     * @author LinZhiWei
     * @date 2015年5月6日 下午1:55:43
     */
    public static boolean isNotBlank(TextView str) {
        return str != null && !isBlank(str.getText());
    }


    /**
     * 性别转换为文字
     * @param sex
     * @return
     */
    public static String getSex2Show(int sex) {
        return getSex2Show(String.valueOf(sex));
    }

    /**
     * 文字转换为性别
     * @param sex
     * @return
     */
    public static int getSex2UploadInt(String sex) {
        if (sex.equals("男")) {
            return 1;
        } else if (sex.equals("女")) {
            return 2;
        }
        return 1;
    }

    /**
     * 性别转换为文字
     * @param sex
     * @return
     */
    public static String getSex2Show(String sex) {
        if (sex.equals("1")) {
            return "男";
        } else if (sex.equals("2")) {
            return "女";
        }
        return "男";
    }

    /**
     * 文字转换为性别
     * @param sex
     * @return
     */
    public static String getSex2Upload(String sex) {
        return String.valueOf(getSex2UploadInt(sex));
    }
}
