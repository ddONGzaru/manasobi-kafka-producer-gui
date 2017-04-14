package io.manasobi.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.nio.charset.Charset;

/**
 * Created by manasobi on 2017-04-14.
 */
public class UTF8Serializer extends Serializer {

    @Override
    public void write(Kryo kryo, Output output, Object object) {

    }

    @Override
    public Charset read(Kryo kryo, Input input, Class type) {
        System.out.println(">>>>>>>>>>>>>>>"+kryo);
        return Charset.forName("UTF-8");
    }

}
