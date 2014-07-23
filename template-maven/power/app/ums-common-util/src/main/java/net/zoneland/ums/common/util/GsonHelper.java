/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author gag
 * @version $Id: GsonHelper.java, v 0.1 2012-10-16 下午3:44:52 gag Exp $
 */
public class GsonHelper {

    private static final String    format     = "yyyyMMddHHmmss";

    public static SimpleDateFormat dateFormat = new SimpleDateFormat(format);

    private static final Gson      g          = new GsonBuilder()
                                                  .registerTypeAdapter(Date.class,
                                                      new UtilDateSerializer())
                                                  .setDateFormat(format).setPrettyPrinting()
                                                  .create();

    public static Gson gson() {
        return g;
    }

    private static class UtilDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
                                                                                                   throws JsonParseException {
            try {
                return dateFormat.parse(element.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {

            }
            return null;
        }

    }

}
