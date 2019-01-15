import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class UserSearchResponse{
    @SerializedName("count")
    private Integer count;
    @SerializedName("items")
    private List<User> items;

    public UserSearchResponse() {
    }

    public Integer getCount() {
        return this.count;
    }

    public List<User> getItems() {
        return this.items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserSearchResponse that = (UserSearchResponse) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

    public void addItem(User user){
        items.add(user);
    }
}