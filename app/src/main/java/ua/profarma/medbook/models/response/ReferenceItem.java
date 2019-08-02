package ua.profarma.medbook.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

@Parcel
@SuppressWarnings("serial")
public class ReferenceItem implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("template")
    private Integer template;

    @SerializedName("date")
    private String date;


    @SerializedName("drugs")
    @Expose
    private List<Drugs> drugs = null;

    @SerializedName("icods")
    @Expose
    private List<Icods> icods = null;

    @SerializedName("patients")
    @Expose
    private List<Patient> patients = null;

    public List<Icods> getIcods() {
        return icods;
    }

    public void setIcods(List<Icods> icods) {
        this.icods = icods;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Drugs> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drugs> drugs) {
        this.drugs = drugs;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
