package com.ilku1297.json.desirializers;

import com.google.gson.*;
import com.ilku1297.objects.User;

import java.lang.reflect.Type;

@Deprecated
public class UserDesirializer implements JsonDeserializer<User> {
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
       /* user.setID(BigInteger.valueOf(jsonObject.get("id").getAsInt()));
        user.setFirstName(jsonObject.get("first_name").getAsString());
        user.setLastName(jsonObject.get("last_name").getAsString());
        user.setPhoto(jsonObject.get("photo").getAsString());
        //user.setSex(jsonObject.get("sex"));
        if(jsonObject.get("last_seen") != null){
            user.setLastSeen(jsonObject.get("last_seen").getAsJsonObject().get("time").getAsInt());
        }
        if(jsonObject.get("home_town") != null){
            user.setHomeTown(jsonObject.get("home_town").getAsInt());
        }
        if(jsonObject.get("has_photo") != null){
            user.setHasPhoto(jsonObject.get("has_photo").getAsInt());
        }
        if(jsonObject.get("friend_status") != null){
            user.setFriendStatus(jsonObject.get("friend_status").getAsInt());
        }
        if(jsonObject.get("followers_count") != null){
            user.setFollowersCount(jsonObject.get("followers_count").getAsInt());
        }

        //user.setEducation((Education) context.deserialize(jsonObject.get("education"), Education.class));

        return user;*/
        return null;
    }
}
