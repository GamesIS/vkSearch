package com.ilku1297.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ilku1297.objects.User;

import java.lang.reflect.Type;

public class UserSesirializer implements JsonSerializer<User> {
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
