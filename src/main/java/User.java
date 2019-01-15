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

    public User(BigInteger ID, String firstName, String lastName, String photo) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }
}
