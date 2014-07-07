package com.dictiographer.model.entry;

/**
 * User: Vitaly Sazanovich
 * Date: 12/27/12
 * Time: 12:50 AM
 */
public class EntryImage {
    private byte[] image;
    private String ext;
    private long checksum;
    private ImageLink[] imageLinks;

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public ImageLink[] getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLink[] imageLinks) {
        this.imageLinks = imageLinks;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
