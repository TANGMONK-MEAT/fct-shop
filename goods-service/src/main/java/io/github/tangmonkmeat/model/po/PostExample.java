package io.github.tangmonkmeat.model.po;

import io.github.tangmonkmeat.model.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午4:34
 */
public class PostExample extends Goods implements Serializable {

    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
