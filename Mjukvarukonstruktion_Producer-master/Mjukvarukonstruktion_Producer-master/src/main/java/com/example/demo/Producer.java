package com.example.demo;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//class for a producer that can produce messages onto a kafka stream.
public class Producer {

    private static KafkaProducer<String, String> producer;

    //Configure the producer with the ip and port of the cluster to produce to. 
    public static void InitProducer(String ip, String port){
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "HelloProducer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ip+":"+port);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    //Send a message onto the kafka stream. topicName is the topic the message will be produced to. 
    public static void sendMessage(String topicName,String key, String message){
        try {
            producer.send(new ProducerRecord<>(topicName, key, message));
        } catch (KafkaException e) {
            System.out.println("Exception occurred in method: sendMessage.\n" + e.getMessage());
            System.exit(-1);
        }
    }

}
