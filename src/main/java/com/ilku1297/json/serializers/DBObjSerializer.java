package com.ilku1297.json.serializers;

import com.google.gson.*;
import com.ilku1297.db.DBObj;
import com.ilku1297.objects.User;

import java.lang.reflect.Type;

@Deprecated
public class DBObjSerializer implements JsonSerializer<DBObj> {
    public JsonElement serialize(DBObj dbObj, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        JsonArray girls = new JsonArray();
        for (User user : dbObj.getItems()) {
            girls.add(context.serialize(user));
        }
        return result;
    }
}
