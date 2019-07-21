package ua.profarma.medbook.types;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SpecializationItems implements Parcelable {

    public boolean success;
    public ArrayList<Specialization> items;

    protected SpecializationItems(Parcel in) {
        success = in.readByte() != 0;
        items = in.createTypedArrayList(Specialization.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpecializationItems> CREATOR = new Creator<SpecializationItems>() {
        @Override
        public SpecializationItems createFromParcel(Parcel in) {
            return new SpecializationItems(in);
        }

        @Override
        public SpecializationItems[] newArray(int size) {
            return new SpecializationItems[size];
        }
    };
}
