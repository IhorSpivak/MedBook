package ua.profarma.medbook.types.responses;

import android.os.Parcel;
import android.os.Parcelable;

import ua.profarma.medbook.types.Errors;

public class AccessTokenInfo implements Parcelable {

    public boolean success;
    public Errors errors;
    public AccessTokenItem[] items;

    protected AccessTokenInfo(Parcel in) {
        success = in.readByte() != 0;
        errors = in.readParcelable(Errors.class.getClassLoader());
        items = in.createTypedArray(AccessTokenItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeParcelable(errors, flags);
        dest.writeTypedArray(items, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccessTokenInfo> CREATOR = new Creator<AccessTokenInfo>() {
        @Override
        public AccessTokenInfo createFromParcel(Parcel in) {
            return new AccessTokenInfo(in);
        }

        @Override
        public AccessTokenInfo[] newArray(int size) {
            return new AccessTokenInfo[size];
        }
    };
}
