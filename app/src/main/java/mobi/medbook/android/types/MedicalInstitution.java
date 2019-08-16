package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MedicalInstitution implements Parcelable {
    public int id;
    public int medical_institution_type_id;
    public int city_id;
    public double geo_lon;
    public double geo_lat;
    public int morion_id;
    public City city;
    public MedicalInstitutionTranslate translations[];


    protected MedicalInstitution(Parcel in) {
        id = in.readInt();
        medical_institution_type_id = in.readInt();
        city_id = in.readInt();
        geo_lon = in.readDouble();
        geo_lat = in.readDouble();
        morion_id = in.readInt();
        city = in.readParcelable(City.class.getClassLoader());
        translations = in.createTypedArray(MedicalInstitutionTranslate.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(medical_institution_type_id);
        dest.writeInt(city_id);
        dest.writeDouble(geo_lon);
        dest.writeDouble(geo_lat);
        dest.writeInt(morion_id);
        dest.writeParcelable(city, flags);
        dest.writeTypedArray(translations, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedicalInstitution> CREATOR = new Creator<MedicalInstitution>() {
        @Override
        public MedicalInstitution createFromParcel(Parcel in) {
            return new MedicalInstitution(in);
        }

        @Override
        public MedicalInstitution[] newArray(int size) {
            return new MedicalInstitution[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return translations[0].name;
    }
}
