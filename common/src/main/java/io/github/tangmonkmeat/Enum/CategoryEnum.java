package io.github.tangmonkmeat.Enum;

/**
 * Description: 分类级别
 *
 * @author zwl
 * @date 2021/7/22 下午2:32
 * @version 1.0
 */
public enum CategoryEnum {

    ONE(0),
    TWO(1)
    ;
    private int parentId;

    CategoryEnum(int parentId){
        this.parentId = parentId;
    }

    public int getParentId(){
        return this.parentId;
    }
}
