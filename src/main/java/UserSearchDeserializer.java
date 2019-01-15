import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigInteger;

public class UserSearchDeserializer implements JsonDeserializer<UserSearchResponse>
{
    public UserSearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject("response");

        UserSearchResponse userSearchResponse = new UserSearchResponse();
        userSearchResponse.setCount(jsonObject.get("count").getAsInt());

        JsonArray items = jsonObject.getAsJsonArray("items");
        for(JsonElement item : items) {
            //userSearchResponse.addWeapon((UniqueWeapon) context.deserialize(item, UniqueWeapon.class));
            userSearchResponse.addItem(new User(BigInteger.valueOf(1), "", "", ""));
//            if(item.isJsonPrimitive()) {
//                userSearchResponse.addItem(new Weapon(item.getAsString()));
//            } else {
//                userSearchResponse.addWeapon((UniqueWeapon) context.deserialize(item, UniqueWeapon.class));
//            }
        }

        return userSearchResponse;
    }
}