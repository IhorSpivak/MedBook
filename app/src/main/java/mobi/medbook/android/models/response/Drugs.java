package mobi.medbook.android.models.response;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Drugs {
    @SerializedName("id")
    private Integer id;

    @SerializedName("pharm_advice_id")
    private Integer pharm_advice_id;

    @SerializedName("drug_id")
    private Integer drug_id;

    @SerializedName("qty")
    private Integer qty;

    @SerializedName("author_id")
    private Integer author_id;

    @SerializedName("created_at")
    private Integer created_at;

    @SerializedName("updated_at")
    private Integer updated_at;

    @SerializedName("deleted_at")
    private Integer deleted_at;

    @SerializedName("drug")
    private Drug drug;

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

    public Integer getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Integer drug_id) {
        this.drug_id = drug_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Parcel
    public class Drug {
        @SerializedName("id")
        private Integer id;

        @SerializedName("title")
        private String title;

        @SerializedName("manufacturer_id")
        private Integer manufacturer_id;

        @SerializedName("morion_id")
        private Integer morion_id;

        @SerializedName("category")
        private String category;

        @SerializedName("our")
        private Integer our;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getManufacturer_id() {
            return manufacturer_id;
        }

        public void setManufacturer_id(Integer manufacturer_id) {
            this.manufacturer_id = manufacturer_id;
        }

        public Integer getMorion_id() {
            return morion_id;
        }

        public void setMorion_id(Integer morion_id) {
            this.morion_id = morion_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getOur() {
            return our;
        }

        public void setOur(Integer our) {
            this.our = our;
        }
    }
}
