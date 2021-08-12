package io.github.tangmonkmeat.constant;

/**
 * Description: 消息类型
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/26 下午10:23
 */
public class MessageType {
    /** 系统消息 */
    public static final int SYS_MESSAGE = 0;

    /** 用户发送的消息 */
    public static final int USER_MESSAGE = 1;

    /** 单方建立对话,在被建立者中不展示 */
    public static final int ESTABLISH_CHAT = 2;

    /** 第一次发送消息,需把聊天设置为双方可见*/
    public static final int FIRST_CHAT = 3;

    /** 未读消息数,用于更新badge */
    public static final int UNREAD_NUM = 4;
}
