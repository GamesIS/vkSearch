package com.ilku1297.json.desirializers;

import com.google.gson.*;
import com.ilku1297.objects.User;

import java.lang.reflect.Type;
import java.math.BigInteger;

public class UserDesirializer  implements JsonDeserializer<User> {
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        User user = new User();
        user.setID(BigInteger.valueOf(jsonObject.get("id").getAsInt()));
        user.setFirstName(jsonObject.get("first_name").getAsString());
        user.setLastName(jsonObject.get("last_name").getAsString());
        user.setPhoto(jsonObject.get("photo").getAsString());

        return user;
    }
}
