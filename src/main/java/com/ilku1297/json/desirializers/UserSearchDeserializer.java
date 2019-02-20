package com.ilku1297.json.desirializers;

import com.google.gson.*;
import com.ilku1297.objects.User;
import com.ilku1297.objects.UserSearchResponse;

import java.lang.reflect.Type;

@Deprecated
public class UserSearchDeserializer implements JsonDeserializer<UserSearchResponse> {
    public UserSearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject("response");

        UserSearchResponse userSearchResponse = new UserSearchResponse();
        //userSearchResponse.setCount(jsonObject.get("count").getAsInt());

        JsonArray items = jsonObject.getAsJsonArray("items");
        for (JsonElement item : items) {
            userSearchResponse.addItem((User) context.deserialize(item, User.class));
            //userSearchResponse.addItem(Main.builder.create().fromJson(item, User.class));
        }

        return userSearchResponse;
    }
}