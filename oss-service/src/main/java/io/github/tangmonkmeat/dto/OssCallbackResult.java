package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * Description: 回调接口中返回的数据对象，封装了上传文件的信息。
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 下午7:34
 */
public class OssCallbackResult implements Serializable {

    /**
     * 文件名称 / 文件的url
     */
    private String filename;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 文件的mimeType
     */
    private String mimeType;

    /**
     * 图片文件的宽
     */
    private String width;

    /**
     * 图片文件的高
     */
    private String height;

    @Override
    public String toString() {
        return "OssCallbackResult{" +
                "filename='" + filename + '\'' +
                ", size='" + size + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
