package ua.profarma.medbook.types.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthorizeRequest implements Parcelable {
    public String username;
    public String password;
    public String device_uuid;

    public AuthorizeRequest(String username, String password, String device_uuid) {
        this.username = username;
        this.password = password;
        this.device_uuid = device_uuid;
    }

    protected AuthorizeRequest(Parcel in) {
        username = in.readString();
        password = in.readString();
        device_uuid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(device_uuid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthorizeRequest> CREATOR = new Creator<AuthorizeRequest>() {
        @Override
        public AuthorizeRequest createFromParcel(Parcel in) {
            return new AuthorizeRequest(in);
        }

        @Override
        public AuthorizeRequest[] newArray(int size) {
            return new AuthorizeRequest[size];
        }
    };
}
