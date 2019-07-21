package ua.profarma.medbook.types;

import android.os.Parcel;
import android.os.Parcelable;

public class Errors implements Parcelable {
    public int code;
    public String message;

    protected Errors(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<Errors> CREATOR = new Creator<Errors>() {
        @Override
        public Errors createFromParcel(Parcel in) {
            return new Errors(in);
        }

        @Override
        public Errors[] newArray(int size) {
            return new Errors[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }
}
