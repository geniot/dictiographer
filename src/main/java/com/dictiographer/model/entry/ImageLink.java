package com.dictiographer.model.entry;

/**
 * User: Vitaly Sazanovich
 * Date: 12/28/12
 * Time: 7:52 PM
 */
public class ImageLink extends EntryLink {
    private int xoffset = 0;
    private int yoffset = 0;


    public int getXoffset() {
        return xoffset<0?0:xoffset;
    }

    public void setXoffset(int xoffset) {
        this.xoffset = xoffset<0?0:xoffset;
    }

    public int getYoffset() {
        return yoffset<0?0:yoffset;
    }

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset<0?0:yoffset;
    }

}
