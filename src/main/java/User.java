import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class User {
    @SerializedName("id")
    private BigInteger ID;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo")
    private String photo;
}
