package ua.profarma.medbook.types;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialization implements Parcelable {
    public int id;
    public int morion_id;
    //1 = medpred, spec = null or = 0 -> doctor
    public Integer is_medpred;
    public SpecializationTranslate[] translations;


    protected Specialization(Parcel in) {
        id = in.readInt();
        morion_id = in.readInt();
        translations = in.createTypedArray(SpecializationTranslate.CREATOR);
    }

    public static final Creator<Specialization> CREATOR = new Creator<Specialization>() {
        @Override
        public Specialization createFromParcel(Parcel in) {
            return new Specialization(in);
        }

        @Override
        public Specialization[] newArray(int size) {
            return new Specialization[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(morion_id);
        dest.writeTypedArray(translations, flags);
    }
}
