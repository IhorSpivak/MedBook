package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

public class RegistrationInfo implements Parcelable {

    public boolean success;
    public Errors errors;

    protected RegistrationInfo(Parcel in) {
        success = in.readByte() != 0;
        errors = in.readParcelable(Errors.class.getClassLoader());
    }

    public static final Creator<RegistrationInfo> CREATOR = new Creator<RegistrationInfo>() {
        @Override
        public RegistrationInfo createFromParcel(Parcel in) {
            return new RegistrationInfo(in);
        }

        @Override
        public RegistrationInfo[] newArray(int size) {
            return new RegistrationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeParcelable(errors, flags);
    }
}
