
package com.hisign.sdk.msg;



/**
 * 消息处理回调接口
 * @author lnj
 */
public interface MessageHandler{
    /**
     * 消息处理回调方法
     * @param msg 消息对象
     */
    void onMessage(Message msg);
    
}
