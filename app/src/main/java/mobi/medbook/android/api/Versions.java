package mobi.medbook.android.api;

import android.os.Parcel;
import android.os.Parcelable;


class Versions implements Parcelable {
    public String android;
    public String ios;

    protected Versions(Parcel in) {
        android = in.readString();
        ios = in.readString();
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
        dest.writeString(android);
        dest.writeString(ios);
    }
}
