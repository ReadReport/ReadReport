package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-5 上午9:35
 * @description: ReadReport
 */
public class AskMode {

    @JSONField(name = "talklists")
    private List<AskItemMode> mAskItemModes;
    @JSONField(name = "count")
    private int count;

    public List<AskItemMode> getAskItemModes() {
        return mAskItemModes;
    }

    public void setAskItemModes(List<AskItemMode> askItemModes) {
        mAskItemModes = askItemModes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
