package io.manasobi.domain;

import io.manasobi.utils.RandomUtils;
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

        String randomText = RandomUtils.getString(5);

        Point point = Point.builder()
                           .timestamp(System.currentTimeMillis())
                           .tagId("TAG-ID-")
                           .tagName("TAG-NAME-")
                           .tagId("TAG-ID-" + randomText)
                           .tagName("TAG-NAME-" + randomText)
                           .type("TYPE-")
                           .value("VALUE-")
                           .siteId("SITE-ID-")
                           .opcId("OPC-ID-")
                           .groupName("GROUP-NAME-")
                           .quality(1)
                           .errorCode(9999)
                           .build();

        System.out.println(atomicInteger.incrementAndGet());

        return point;
    }
}
