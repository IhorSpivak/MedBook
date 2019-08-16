package mobi.medbook.android.models.response;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Patient {

    @SerializedName("id")
    private Integer id;

    @SerializedName("pharm_advice_id")
    private Integer pharm_advice_id;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("author_id")
    private Integer author_id;

    @SerializedName("created_at")
    private Integer created_at;

    @SerializedName("updated_at")
    private Integer updated_at;

    @SerializedName("deleted_at")
    private Integer deleted_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPharm_advice_id() {
        return pharm_advice_id;
    }

    public void setPharm_advice_id(Integer pharm_advice_id) {
        this.pharm_advice_id = pharm_advice_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }

    public Integer getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Integer updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Integer deleted_at) {
        this.deleted_at = deleted_at;
    }
}
