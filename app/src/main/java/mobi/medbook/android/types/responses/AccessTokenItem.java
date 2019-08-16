package mobi.medbook.android.types.responses;

import android.os.Parcel;
import android.os.Parcelable;

public class AccessTokenItem implements Parcelable {

     public String access_token;
     public String refresh_token;
     public long expires_at;
     public long refresh_expires_at;


    protected AccessTokenItem(Parcel in) {
        access_token = in.readString();
        refresh_token = in.readString();
        expires_at = in.readLong();
        refresh_expires_at = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeString(refresh_token);
        dest.writeLong(expires_at);
        dest.writeLong(refresh_expires_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccessTokenItem> CREATOR = new Creator<AccessTokenItem>() {
        @Override
        public AccessTokenItem createFromParcel(Parcel in) {
            return new AccessTokenItem(in);
        }

        @Override
        public AccessTokenItem[] newArray(int size) {
            return new AccessTokenItem[size];
        }
    };
}
