package io.github.tangmonkmeat.Enum;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午9:08
 */
public enum  MqMsgType {

    USER_REGISTER(0,"用户注册消息"),
    ES_PRODUCT_SAVE(1,"es 保存商品信息"),
    ES_PRODUCT_DEL(2,"es 删除商品信息"),
    ;

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    MqMsgType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public boolean hasType(int type){
        MqMsgType[] values = MqMsgType.values();
        for (MqMsgType v : values){
            if (v.getType() == type){
                return true;
            }
        }
        return false;
    }
}
