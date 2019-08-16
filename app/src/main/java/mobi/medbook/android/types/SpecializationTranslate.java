package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SpecializationTranslate implements Parcelable {
    public int id;
    public int specialization_id;
    public String language;
    public String name;

    protected SpecializationTranslate(Parcel in) {
        id                  = in.readInt();
        specialization_id   = in.readInt();
        language            = in.readString();
        name                = in.readString();
    }

    public static final Creator<SpecializationTranslate> CREATOR = new Creator<SpecializationTranslate>() {
        @Override
        public SpecializationTranslate createFromParcel(Parcel in) {
            return new SpecializationTranslate(in);
        }

        @Override
        public SpecializationTranslate[] newArray(int size) {
            return new SpecializationTranslate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(specialization_id);
        dest.writeString(language);
        dest.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
