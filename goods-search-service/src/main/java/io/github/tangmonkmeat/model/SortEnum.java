package io.github.tangmonkmeat.model;

/**
 * Description: 商品条件查询枚举
 *
 * @author zwl
 * @date 2021/7/18 下午7:15
 * @version 1.0
 */
public enum SortEnum {

    NEW_TO_OLD(0,"从新到旧"),
    PRICE_DESC(1,"价格从低到高"),
    PRICE_ASC(2,"价格从高到低")
    ;
    int type;
    String desc;

    SortEnum(int type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public int getType(){
        return type;
    }

    public String getDesc(){
        return desc;
    }

    public static SortEnum getSort(int type){
        SortEnum[] values = SortEnum.values();
        for (SortEnum v : values){
            if (v.getType() == type) {
                return v;
            }
        }
        return null;
    }
}
