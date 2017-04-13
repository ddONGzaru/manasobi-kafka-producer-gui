package io.manasobi.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.manasobi.domain.PayloadBuilder;
import io.manasobi.domain.PayloadWorker;
import io.manasobi.domain.Point;
import io.manasobi.domain.PointPayloadBuilder;
import io.manasobi.utils.DateUtils;
import io.manasobi.utils.FileUtils;
import lombok.Cleanup;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by tw.jang on 2017-04-13.
 */
public class DataSetWriter {

    public void write(int size) {

        PointPayloadBuilder payloadBuilder = new PointPayloadBuilder();

        PayloadWorker worker = new PayloadWorker();
        worker.setPayloadBuilder(payloadBuilder);

        List<Point> pointList = worker.work(size);

        Kryo kryo = new Kryo();

        Output output;

        try {
            output = new Output(new FileOutputStream(buildDataSetName(size)));
            kryo.writeObject(output, pointList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String buildDataSetName(int size) {

        String userDir = System.getProperty("user.dir");

        if (FileUtils.existsDir(userDir + "/src/main/resources/config/dataset/")) {

        }
        String dir = userDir + "/src/main/resources/dataset/";

        String namePrefix = DateUtils.getCurrentDateAsString("yyyyMMdd");

        String name = namePrefix + "_point-dataset_size_" + String.format("%07d", size) + ".jdo";

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
