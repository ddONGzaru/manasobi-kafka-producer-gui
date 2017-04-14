package io.manasobi.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.KryoObjectOutput;
import com.esotericsoftware.kryo.io.Output;
import io.manasobi.domain.PayloadWorker;
import io.manasobi.domain.Point;
import io.manasobi.domain.PointPayloadBuilder;
import io.manasobi.utils.DateUtils;
import io.manasobi.utils.FileUtils;
import io.manasobi.utils.UTF8Serializer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Slf4j
public class DataSetWriter {

    public void write(int size) {

        PointPayloadBuilder payloadBuilder = new PointPayloadBuilder();

        PayloadWorker worker = new PayloadWorker();
        worker.setPayloadBuilder(payloadBuilder);

        List<Point> pointList = worker.work(size);

        Kryo kryo = new Kryo();

        try(FileOutputStream fos = new FileOutputStream(buildDataSetName(size));
            ByteBufferOutput output = new ByteBufferOutput(fos)) {

            KryoObjectOutput objectOutput = new KryoObjectOutput(kryo, output);

            objectOutput.writeObject(pointList);

        } catch(IOException e) {

            log.error(e.getMessage());
        }
    }

    private String buildDataSetName(int size) {

        String userDir = System.getProperty("user.dir");

        if (FileUtils.existsDir(userDir + "/src/main/resources/config/dataset/")) {

        }
        String dir = userDir + "/src/main/resources/dataset/";

        String namePrefix = DateUtils.getCurrentDateAsString("yyyyMMdd");

        String name = namePrefix + "_point-msg_size_" + String.format("%07d", size) + ".jdo";

        return dir + name;
    }

    enum Size {

        _100_000(100000), _300_000(300000), _500_000(500000), _1_000_000(1000000), _3_000_000(3000000), _5_000_000(5000000);

        @Getter
        int size;

        Size(int size) {
            this.size = size;
        }
    }

    public static void main(String[] args) {

        DataSetWriter dataSetWriter = new DataSetWriter();

        dataSetWriter.write(Size._100_000.getSize());

    }

}
