package io.manasobi.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Component
public class PointPayloadBuilder implements PayloadBuilder<Point> {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public Point build() {

        String uuid = UUID.randomUUID().toString();

        Point point = Point.builder()
                           .timestamp(System.currentTimeMillis())
                           .tagId("TAG-ID-")
                           .tagName("TAG-NAME-")
                           /*.tagId("TAG-ID-" + uuid)
                           .tagName("TAG-NAME-" + uuid)*/
                           .type("TYPE-")
                           .value("VALUE-")
                           .siteId("SITE-ID-")
                           .opcId("OPC-ID-")
                           .groupName("GROUP-NAME-")
/*
                           .type("TYPE-" + uuid)
                           .value("VALUE-" + uuid)
                           .siteId("SITE-ID-" + uuid)
                           .opcId("OPC-ID-" + uuid)
                           .groupName("GROUP-NAME-" + uuid)
*/
                           .quality(1)
                           .errorCode(9999)
                           .build();

        System.out.println(atomicInteger.incrementAndGet());

        return point;
    }
}
