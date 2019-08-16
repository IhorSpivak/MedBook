package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MedicalInstitutionItems implements Parcelable {
    public ArrayList<MedicalInstitutionTranslate> items;

    protected MedicalInstitutionItems(Parcel in) {
        items = in.createTypedArrayList(MedicalInstitutionTranslate.CREATOR);
    }

    public static final Creator<MedicalInstitutionItems> CREATOR = new Creator<MedicalInstitutionItems>() {
        @Override
        public MedicalInstitutionItems createFromParcel(Parcel in) {
            return new MedicalInstitutionItems(in);
        }

        @Override
        public MedicalInstitutionItems[] newArray(int size) {
            return new MedicalInstitutionItems[size];
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
