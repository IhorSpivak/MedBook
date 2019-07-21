package ua.profarma.medbook.types.responses;

import android.os.Parcel;
import android.os.Parcelable;

public class VerificationDate implements Parcelable {
    public String transaction;
    public int key;

    protected VerificationDate(Parcel in) {
        transaction = in.readString();
        key = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transaction);
        dest.writeInt(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerificationDate> CREATOR = new Creator<VerificationDate>() {
        @Override
        public VerificationDate createFromParcel(Parcel in) {
            return new VerificationDate(in);
        }

        @Override
        public VerificationDate[] newArray(int size) {
            return new VerificationDate[size];
        }
    };
}
