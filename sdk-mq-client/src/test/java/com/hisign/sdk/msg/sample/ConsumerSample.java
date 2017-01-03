package com.hisign.sdk.msg.sample;

import java.io.IOException;
import java.nio.ByteBuffer;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Properties;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import kafka.consumer.Consumer;  
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;  
import kafka.message.Message;  
import kafka.message.MessageAndMetadata;  
  
public class ConsumerSample {  
  
    public static void main(String[] args) {  
                // specify some consumer properties  
        Properties props = new Properties();  
        String kafkaZkConnect = "172.16.0.114:52181/kafka";
        props.put("zookeeper.connect", kafkaZkConnect);
        props.put("zookeeper.session.timeout.ms", "5000");
        props.put("zookeeper.connection.timeout.ms", "10000");
        props.put("zookeeper.sync.time.ms", "200000");
        props.put("auto.commit.enable", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("rebalance.max.retries", "10");
        props.put("rebalance.backoff.ms", "2000");
        props.put("group.id", "test_group");  
  
                // Create the connection to the cluster  
        ConsumerConfig consumerConfig = new ConsumerConfig(props);  
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);  
  
                // create 4 partitions of the stream for topic “test-topic”, to allow 4 threads to consume  
        HashMap<String, Integer> map = new HashMap<String, Integer>();  
        map.put("flume", 4);  
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams =  
                consumerConnector.createMessageStreams(map);  
        final List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get("flume");  
  
                // create list of 4 threads to consume from each of the partitions   
        ExecutorService executor = Executors.newFixedThreadPool(4);  
  
        System.out.println("begin to get message");
                // consume the messages in the threads  
        for (final KafkaStream<byte[], byte[]> stream : streams) {  
            executor.submit(new Runnable() {  
                public void run() {  
                	ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    while (it.hasNext()){
                        byte [] dataBytes = it.next().message();
                        String tmp = new String(dataBytes);  
                        System.out.println("message content: " + tmp);  
                    }  
                }  
            });  
        }  
  
        
        try {
			System.in.read();
		} catch (IOException e) {
		}
    }  
}  
