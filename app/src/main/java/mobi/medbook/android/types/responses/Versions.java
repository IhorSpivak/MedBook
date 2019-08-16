package mobi.medbook.android.types.responses;

import android.os.Parcel;
import android.os.Parcelable;


public class Versions implements Parcelable {
    public String ios;
    public String android;

    protected Versions(Parcel in) {
        ios = in.readString();
        android = in.readString();
    }

    public static final Creator<Versions> CREATOR = new Creator<Versions>() {
        @Override
        public Versions createFromParcel(Parcel in) {
            return new Versions(in);
        }

        @Override
        public Versions[] newArray(int size) {
            return new Versions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ios);
        dest.writeString(android);
    }
}
