package com.example.wereL.config.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import com.esotericsoftware.kryo.io.Output;
import org.springframework.data.redis.serializer.RedisSerializer;


import java.io.ByteArrayOutputStream;

public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private static final ThreadLocal<Kryo> kryoTL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    private final Class<T> type;

    public KryoRedisSerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public byte[] serialize(T value) {
        if (value == null) return new byte[0];
        Output output = new Output(256, -1);
        kryoTL.get().writeClassAndObject(output, value);
        return output.toBytes();
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        Input input = new Input(bytes);
        return (T) kryoTL.get().readClassAndObject(input);
    }
}