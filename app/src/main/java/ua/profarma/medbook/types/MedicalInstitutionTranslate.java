package ua.profarma.medbook.types;

import android.os.Parcel;
import android.os.Parcelable;

public class MedicalInstitutionTranslate implements Parcelable {

    public int id;
    public int medical_institution_id;
    public String language;
    public String name;
    public String address;

    protected MedicalInstitutionTranslate(Parcel in) {
        id = in.readInt();
        medical_institution_id = in.readInt();
        language = in.readString();
        name = in.readString();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(medical_institution_id);
        dest.writeString(language);
        dest.writeString(name);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedicalInstitutionTranslate> CREATOR = new Creator<MedicalInstitutionTranslate>() {
        @Override
        public MedicalInstitutionTranslate createFromParcel(Parcel in) {
            return new MedicalInstitutionTranslate(in);
        }

        @Override
        public MedicalInstitutionTranslate[] newArray(int size) {
            return new MedicalInstitutionTranslate[size];
        }
    };
}
