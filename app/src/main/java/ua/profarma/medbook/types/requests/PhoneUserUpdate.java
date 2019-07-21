package ua.profarma.medbook.types.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class PhoneUserUpdate implements Parcelable {
    public String phone;

    public PhoneUserUpdate(String phone){
        this.phone  = phone;
    }

    protected PhoneUserUpdate(Parcel in) {
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhoneUserUpdate> CREATOR = new Creator<PhoneUserUpdate>() {
        @Override
        public PhoneUserUpdate createFromParcel(Parcel in) {
            return new PhoneUserUpdate(in);
        }

        @Override
        public PhoneUserUpdate[] newArray(int size) {
            return new PhoneUserUpdate[size];
        }
    };
}
