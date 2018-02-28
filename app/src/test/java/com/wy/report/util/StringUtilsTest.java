package com.wy.report.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
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
 * @date 2017-11-26 21:35
 */public class StringUtilsTest {

    @Test
    public void hidePhoneNumber() throws Exception {
        String phoneNumber = "13900001234";
        assertEquals("139****1234", StringUtils.hidePhoneNumber(phoneNumber));
    }

    /**
     * @throws Exception
     */
    @Test
    public void formatTime() throws Exception {

        // 当1分钟以内显示：刚刚（0:00:00~0:00:59）
        long time = System.currentTimeMillis() - 1000;
        assertEquals("刚刚", StringUtils.formatTime(time));

        //当1小时以内显示具体几分钟前：13分钟前（0:13:00~0:13:59）
        time = System.currentTimeMillis() - 1000;
        time -= 60 * 1000;
        assertEquals("1分钟前", StringUtils.formatTime(time));
        time -= 60 * 1000;
        assertEquals("2分钟前", StringUtils.formatTime(time));
        time -= 60 * 1000 * 10;
        assertEquals("12分钟前", StringUtils.formatTime(time));

        //当24小时以内显示具体几小时前：1小时前（1:00:00~1:59:59）
        time = System.currentTimeMillis() - 1000;
        time -= 60 * 60 * 1000;
        assertEquals("1小时前", StringUtils.formatTime(time));
        time -= 60 * 60 * 1000;
        assertEquals("2小时前", StringUtils.formatTime(time));
        time -= 60 * 60 * 1000 * 10;
        assertEquals("12小时前", StringUtils.formatTime(time));

        //当超过24小时就显示昨天：昨天（24:00:00~47:59:59）
        time = System.currentTimeMillis() - 1000;
        time -= 60 * 60 * 1000 * 24;
        assertEquals("昨天", StringUtils.formatTime(time));
        time -= 60 * 60 * 1000 * 24 - 2000;
        assertEquals("昨天", StringUtils.formatTime(time));

        //当超过48小时就显示几天前：2天前（48:00:00~71:59:59）
        time = System.currentTimeMillis() - 1000;
        time -= 60 * 60 * 1000 * 48;
        assertEquals("2天前", StringUtils.formatTime(time));
        time = System.currentTimeMillis() - 1000;
        time -= 60 * 60 * 1000 * 72;
        assertEquals("3天前", StringUtils.formatTime(time));
    }

    @Test
    public void testStringLenght()
    {
        assertEquals(3, "3天前".length());
        assertEquals(3, "abc".length());
    }

}