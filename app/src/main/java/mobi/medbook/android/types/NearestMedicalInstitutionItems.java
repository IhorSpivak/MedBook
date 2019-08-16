package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NearestMedicalInstitutionItems implements Parcelable {
    public ArrayList<MedicalInstitution> items;

    protected NearestMedicalInstitutionItems(Parcel in) {
        items = in.createTypedArrayList(MedicalInstitution.CREATOR);
    }

    public static final Creator<NearestMedicalInstitutionItems> CREATOR = new Creator<NearestMedicalInstitutionItems>() {
        @Override
        public NearestMedicalInstitutionItems createFromParcel(Parcel in) {
            return new NearestMedicalInstitutionItems(in);
        }

        @Override
        public NearestMedicalInstitutionItems[] newArray(int size) {
            return new NearestMedicalInstitutionItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }
}
