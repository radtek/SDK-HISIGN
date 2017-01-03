package com.hisign.sdk.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 消息处理回调接口缺省实现类
 * @author lnj
 *
 */
public class MessageHandlerAdapter implements MessageHandler {
    static Logger log = LoggerFactory.getLogger(MessageHandlerAdapter.class);
    Message message = null;

    /* (non-Javadoc)
     * @see com.ultrapower.msg.MessageHandler#init()
     */
    public void init() {
        
    }

    public void start() {
        
    }

    /* (non-Javadoc)
     * @see com.ultrapower.msg.MessageHandler#onMessage(com.ultrapower.msg.Message)
     */
    @Override
	public void onMessage(Message msg) {
        log.debug("收到了:--)\t" + msg);        
    }

    /* (non-Javadoc)
     * @see com.ultrapower.msg.MessageHandler#stop()
     */
    public void stop() {
        
    }

    /* (non-Javadoc)
     * @see com.ultrapower.msg.MessageHandler#close()
     */
    public void close() {
        
    }

    /*
     * @see com.ultrapower.ultranms.msg.MessageHandler#getMessage()
     */
    public Message getMessage() {
        return message;
    }

    /*
     * @see com.ultrapower.ultranms.msg.MessageHandler#setMessage(com.ultrapower.ultranms.msg.Message)
     */
    public void setMessage(Message message) {
        this.message = message;
    }
}
