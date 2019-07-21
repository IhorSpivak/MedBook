package ua.profarma.medbook.types.responses;

import android.os.Parcel;
import android.os.Parcelable;

public class VerificationPhoneResponse implements Parcelable {

    public boolean status;
    public String message;
    public String user_message;
    public VerificationDate data;

    protected VerificationPhoneResponse(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
        user_message = in.readString();
        data = in.readParcelable(VerificationDate.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
        dest.writeString(user_message);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerificationPhoneResponse> CREATOR = new Creator<VerificationPhoneResponse>() {
        @Override
        public VerificationPhoneResponse createFromParcel(Parcel in) {
            return new VerificationPhoneResponse(in);
        }

        @Override
        public VerificationPhoneResponse[] newArray(int size) {
            return new VerificationPhoneResponse[size];
        }
    };
}

