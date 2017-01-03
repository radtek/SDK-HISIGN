package com.hisign.sdk.msg;

import java.io.Serializable;
import java.util.Date;

import com.hisign.sdk.common.util.InetAddressUtil;



/**
 * 消息类
 * @author lnj
 *
 */
public class Message<T> implements Serializable {
    
	private static final long serialVersionUID = 1L;

	/*** 消息系列号自增计数器， 达到Long.MAX_VALUE复位为1 */
    public static  long MESSAGENO_COUNT = 1;
    
    /**消息系列号 */
    private long messageNo = 0;

    /** 消息来源，一般记录消息发送端ip */
    private String src = null;

    /** 消息Topic */
    private String topic = null;

    /** 消息类型 */
    private long type = -1;

    /** 消息附加信息 */
    private String addtional = null;

    /** 消息用户自定义对象 */
    private T userObject = null;
    
    private String userObjectClass = null;

	/** 消息内容是否压缩 true:压缩 false不压缩 */
    private boolean isCompress = false;
    
    

    public Message() {
        this(null, -1, null);
    }
    
    public Message(String topic,long type) {
        this(topic, type, null);
    }
    
    public Message(String topic,long type,T userObject){
    	this.topic = topic;
    	this.type = type;
    	this.userObject = userObject;
    	src = InetAddressUtil.getLocalIp();
        initMessageNo();
    }
    
    private void initMessageNo(){
        if (MESSAGENO_COUNT >= Long.MAX_VALUE) {
            MESSAGENO_COUNT = 1;
        }
        messageNo = MESSAGENO_COUNT++;
    }

    /**
     * @return
     */
    public String getAddtional() {
        return addtional;
    }

    /**
     * @param addtional
     */
    public void setAddtional(String addtional) {
        this.addtional = addtional;
    }


    public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

    /**
     * @return
     */
    public long getMessageNo() {
        return messageNo;
    }

	/**
     * @param messageNo
     */
    public void setMessageNo(long messageNo) {
        this.messageNo = messageNo;
    }

    /**
     * @return
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return
     */
    public long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(long type) {
        this.type = type;
    }


    /**
     * @return
     */
    public T getUserObject() {
        return userObject;
    }

    /**
     * @param userObject
     */
    public void setUserObject(T userObject) {
        this.userObject = userObject;
        if(this.userObject != null){
        	this.userObjectClass = userObject.getClass().getName();
        }
    }
    
    public boolean isCompress() {
        return isCompress;
    }

    public void setCompress(boolean isCompress) {
        this.isCompress = isCompress;
    }
    
    public String getUserObjectClass() {
		return userObjectClass;
	}
  
	public void setUserObjectClass(String userObjectClass) {
		this.userObjectClass = userObjectClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [messageNo=");
		builder.append(messageNo);
		builder.append(", src=");
		builder.append(src);
		builder.append(", topic=");
		builder.append(topic);
		builder.append(", type=");
		builder.append(type);
		builder.append(", addtional=");
		builder.append(addtional);
		builder.append(", userObject=");
		builder.append(userObject);
		builder.append(", userObjectClass=");
		builder.append(userObjectClass);
		builder.append(", isCompress=");
		builder.append(isCompress);
		builder.append("]");
		return builder.toString();
	}
	
}
