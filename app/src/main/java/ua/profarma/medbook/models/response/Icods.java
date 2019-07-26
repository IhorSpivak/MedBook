package ua.profarma.medbook.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Icods {

    @SerializedName("id")
    private Integer id;

    @SerializedName("pharm_advice_id")
    private Integer pharm_advice_id;

    @SerializedName("icod_id")
    private Integer icod_id;

    @SerializedName("icod")
    private Icod icod;

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

    public Integer getIcod_id() {
        return icod_id;
    }

    public void setIcod_id(Integer icod_id) {
        this.icod_id = icod_id;
    }

    public Icod getIcod() {
        return icod;
    }

    public void setIcod(Icod icod) {
        this.icod = icod;
    }

    @Parcel
    public class Icod {
        @SerializedName("id")
        private Integer id;

        @SerializedName("code_icod")
        private String code_icod;

        @SerializedName("owner_id")
        private Integer owner_id;

        @SerializedName("translations")
        @Expose
        private List<Translation> translations = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode_icod() {
            return code_icod;
        }

        public void setCode_icod(String code_icod) {
            this.code_icod = code_icod;
        }

        public Integer getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(Integer owner_id) {
            this.owner_id = owner_id;
        }

        public List<Translation> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation> translations) {
            this.translations = translations;
        }

        @Parcel
        public class Translation {
            @SerializedName("id")
            private Integer id;

            @SerializedName("icod_id")
            private Integer icod_id;

            @SerializedName("language")
            private String language;

            @SerializedName("title")
            private String title;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getIcod_id() {
                return icod_id;
            }

            public void setIcod_id(Integer icod_id) {
                this.icod_id = icod_id;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
