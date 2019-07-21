package ua.profarma.medbook.types.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class AccessToken implements Parcelable {

    public String authorization_code;


    public AccessToken(String authorization_code) {
        this.authorization_code =  authorization_code;
    }

    protected AccessToken(Parcel in) {
        authorization_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorization_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccessToken> CREATOR = new Creator<AccessToken>() {
        @Override
        public AccessToken createFromParcel(Parcel in) {
            return new AccessToken(in);
        }

        @Override
        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };
}
