package io.manasobi.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.manasobi.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by twjang on 15. 10. 7.
 */
@Slf4j
public class KafkaMessageWorker implements Runnable {

    private String topic;

    private KafkaProducer<String, JsonNode> producer;

    private int page;

    private int size;

    private String datesetDate = "2015-10-28";

    private int msgMaxRows;

    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    KafkaMessageWorker(String topic, int page, int size, boolean decreaseIndex, String datasetDir, int msgMaxRows) {

        this.topic = topic;

        this.page = (decreaseIndex) ? --page * size : page * size;
        this.size = size;

        this.datesetDate = datasetDir;
        this.msgMaxRows = msgMaxRows;

        producer = ProducerFactory.getInstance();
    }

    @Override
    public void run() {

        DataSetReader reader = new DataSetReader();

        List<Point> messageList = reader.read(datesetDate, size);


        List<List<ProducerRecord<String, JsonNode>>> sendMsgList = Lists.newArrayList();
        List<ProducerRecord<String, JsonNode>> sendMsgUnitList = Lists.newArrayList();

        int index = 0;

        ObjectMapper objectMapper = new ObjectMapper();

        for (Point message : messageList) {

            index++;

            JsonNode jsonNode = objectMapper.convertValue(message, JsonNode.class);

            sendMsgUnitList.add(new ProducerRecord<String, JsonNode>(topic, generateKey(), jsonNode));

            if (index % msgMaxRows == 0) {

                sendMsgList.add(sendMsgUnitList);
                sendMsgUnitList = Lists.newArrayList();

                continue;
            }

            if (index == messageList.size()) {
                sendMsgList.add(sendMsgUnitList);
            }
        }

        Future<RecordMetadata> future;

        sendMsgList.forEach(msgList -> {
                msgList.forEach(r -> {
                    producer.send(r);
                });
                    /*Future<RecordMetadata> future = producer.send(msgList.get(0));

                    if (future.isDone()) {
                        producer.close();
                    }*/
        }



                //msgList.forEach(r -> producer.send(r))
        );



        /*sendMsgList.forEach(msgList ->
                msgList.forEach(r -> producer.send(r))
        );*/

        log.debug("Dataset 레코드 총계: {}", numberFormat.format(messageList.size()));

        //producer.close();

    }

    /*private ImpressionLog convertMapToMessage(Map resultMap) {

        ImpressionLog message = new ImpressionLog();

        message.setId(String.valueOf(resultMap.get("id")));
        message.setAsset((Long) resultMap.get("asset"));
        message.setCampaign((Long) resultMap.get("campaign"));
        message.setCpv((Integer) resultMap.get("cpv"));
        message.setCueOwner((Integer) resultMap.get("cueOwner"));
        message.setDevice((Long) resultMap.get("device"));
        message.setImpressionTime((Date) resultMap.get("impressionTime"));
        message.setError((Boolean) resultMap.get("isError"));
        message.setPlayTime((Integer) resultMap.get("playTime"));
        message.setProgramProvider((Integer) resultMap.get("programProvider"));
        message.setRegion1((Integer) resultMap.get("region1"));
        message.setRegion2((Integer) resultMap.get("region2"));
        message.setRegion3((Integer) resultMap.get("region3"));
        message.setRegion4((Integer) resultMap.get("region4"));
        message.setServiceOperator((Integer) resultMap.get("serviceOperator"));
        message.setVtr((Float) resultMap.get("vtr"));
        message.setZipCode((Integer) resultMap.get("zipCode"));

        *//*message.setId(String.valueOf(resultMap.get("id")));
        message.setAsset((Long) resultMap.get("asset"));
        message.setCampaign((Long) resultMap.get("campaign"));
        message.setCpv((Integer) resultMap.get("cpv"));
        message.setCueOwner((Boolean) resultMap.get("cue_owner") ? 1 : 0);
        message.setDevice((Long) resultMap.get("device"));
        message.setImpressionTime((Date) resultMap.get("impression_time"));
        message.setError((Boolean) resultMap.get("is_error"));
        message.setPlayTime((Integer) resultMap.get("play_time"));
        message.setProgramProvider((Integer) resultMap.get("program_provider"));
        message.setRegion1((Integer) resultMap.get("region1"));
        message.setRegion2((Integer) resultMap.get("region2"));
        message.setRegion3((Integer) resultMap.get("region3"));
        message.setRegion4((Integer) resultMap.get("region4"));
        message.setServiceOperator((Integer) resultMap.get("service_operator"));
        message.setVtr((Float) resultMap.get("vtr"));
        message.setZipCode((Integer) resultMap.get("zip_code"));*//*

        return message;

    }*/

    private Kryo kryo = new Kryo();

    /*private byte[] toByteArray(ImpressionLog impressionLog) {

        kryo.register(ImpressionLog.class);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16384);
             Output output = new Output(byteArrayOutputStream)) {

            kryo.writeObject(output, impressionLog);
            output.close();

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            log.error("[KafkaMessageWorker toByteArray ERROR] : {} ", e.getMessage());
            return null;
        }
    }*/

    private AtomicInteger keyCounter = new AtomicInteger(0);

    private String generateKey() {

        int keyIndex = keyCounter.getAndIncrement();

        if (keyIndex == Integer.MAX_VALUE) {
            keyCounter.set(0);
            return "0";
        }

        return Integer.toString(keyIndex);
    }

}
