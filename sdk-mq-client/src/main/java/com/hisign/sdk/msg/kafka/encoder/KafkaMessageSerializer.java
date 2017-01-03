package com.hisign.sdk.msg.kafka.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.serializer.ISerializer;
import com.hisign.sdk.common.util.serializer.SerializerFactory;
import com.hisign.sdk.msg.Message;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * @Title:
 *  Kafka消息序列化封装
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2016年5月26日  下午4:01:12
 * @version 1.0
 */
public class KafkaMessageSerializer implements Encoder<Message> {
    
    private static Logger log = LoggerFactory.getLogger(KafkaMessageSerializer.class);
    
    //序列化器
    private ISerializer serializer = null;
    
    public KafkaMessageSerializer(VerifiableProperties props){
        serializer = SerializerFactory.createSerializer();
    }
    
    public KafkaMessageSerializer(){
        serializer = SerializerFactory.createSerializer();
    }

    @Override
    public byte[] toBytes(Message msg) {
        byte[] dataBytes = this.serializer.serialize(msg);
        return dataBytes;
    }

}

