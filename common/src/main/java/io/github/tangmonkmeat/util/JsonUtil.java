package io.github.tangmonkmeat.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tangmonkmeat.dto.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Json to bean 转换工具
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午11:26
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static final TypeReference<Map<String, String>> simpleJsonMap =
            new TypeReference<Map<String, String>>() {
            };

    public static Map<String, Object> fromJson(String rawData, TypeReference typeReference) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rawData, typeReference);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T fromJson(String rawData, Class<T> classType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rawData, classType);
        } catch (IOException e) {
            LOGGER.error("json to bean fail, json={}, beanClass={} fail",rawData,classType,e);
        }
        return null;
    }

    public static String toJson(Object object) {

        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("bean to json fail, bean={}", object,e);
        }
        return null;
    }
}
