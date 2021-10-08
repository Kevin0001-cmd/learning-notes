package com.kuang.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
    public static String getJson(Object object,String dateFormat){

        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
}
