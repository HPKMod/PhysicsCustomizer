package io.github.hyper1423.physicscustomizer.config;

import com.google.gson.*;
import io.github.hyper1423.physicscustomizer.config.entry.ConfigEntry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSONConfigAdapter /* implements JsonSerializer<Map<ConfigKey<?>, ?>>, JsonDeserializer<Map<ConfigKey<?>, ?>> */ {
//    @Override
//    public JsonElement serialize(Map<ConfigKey<?>, ?> src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject obj = new JsonObject();
//
//        for (var e: src.entrySet()) {
//            var k = e.getKey();
//            JsonObject subObject = new JsonObject();
//            subObject.add("key", context.serialize(k));
//            subObject.add("value", context.serialize(e.getValue()));
//
//            obj.add(k.name(), subObject);
//        }
//
//        return obj;
//    }
//
//    @Override
//    public Map<ConfigKey<?>, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        var map = new HashMap<ConfigKey<?>, ?>();
//
//        var rawMap = json.getAsJsonObject().asMap();
//        for (var e: rawMap.entrySet()) {
//            var subObject = e.getValue().getAsJsonObject();
//            ConfigKey<?> k = context.deserialize(subObject.get("key"), ConfigKey.class);
//            Object v = context.deserialize());
//            map.put(k, v);
//        }
//
//        return map;
//    }
}
