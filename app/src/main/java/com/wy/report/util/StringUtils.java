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

import android.os.SystemClock;
import android.text.TextUtils;

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
}
