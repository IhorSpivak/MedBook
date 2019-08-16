package mobi.medbook.android.types.requests;

public class Register {
    public String email;
    public String password;
    public String first_name;
    public String middle_name;
    public String last_name;
    public int specialization_id;
    public int medical_institution_id;

    public Register(String email, String password, String first_name, String middle_name, String last_name, int specialization_id, int medical_institution_id) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.specialization_id = specialization_id;
        this.medical_institution_id = medical_institution_id;
    }
}
