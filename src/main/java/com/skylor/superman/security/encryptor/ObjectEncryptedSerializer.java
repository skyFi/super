package com.skylor.superman.security.encryptor;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.undercouch.bson4jackson.BsonFactory;

/**
 * Created by darcy on 2016/1/29.
 */
@Component
public class ObjectEncryptedSerializer {

    private final String secretKey = "7HzZ8uPk0426s60p";

    private ObjectMapper bsonMapper;

    public ObjectEncryptedSerializer() {
        bsonMapper = new ObjectMapper(new BsonFactory());
        bsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        bsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        bsonMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        bsonMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        bsonMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        bsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public String serialize(Object object) throws EncryptedSerializationException {
        try {
            byte[] bytes = bsonMapper.writeValueAsBytes(object);

            Key aesKey = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(bytes);

            String securityCookie = new String(Base64.encodeBase64(encrypted));

            return securityCookie;
        } catch (Exception e) {
            throw new EncryptedSerializationException(e);
        }
    }

    public <T> T deserialize(String string, Class<T> type) throws EncryptedSerializationException {
        try {
            byte[] encrypted = Base64.decodeBase64(string);

            Key aesKey = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] bytes = cipher.doFinal(encrypted);
            T object = bsonMapper.readValue(bytes, type);

            return object;
        } catch (Exception e) {
            throw new EncryptedSerializationException(e);
        }
    }

}