package mobi.medbook.android.types.points;

import android.os.Parcel;
import android.os.Parcelable;

public class ExecuteTransactionData implements Parcelable {
    public int user_id;
    public String verification_type;
    public int value;
    public int AmountToEnrollment;

    public ExecuteTransactionData() {
    }

    protected ExecuteTransactionData(Parcel in) {
        user_id = in.readInt();
        verification_type = in.readString();
        value = in.readInt();
        AmountToEnrollment = in.readInt();
    }

    public static final Creator<ExecuteTransactionData> CREATOR = new Creator<ExecuteTransactionData>() {
        @Override
        public ExecuteTransactionData createFromParcel(Parcel in) {
            return new ExecuteTransactionData(in);
        }

        @Override
        public ExecuteTransactionData[] newArray(int size) {
            return new ExecuteTransactionData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeString(verification_type);
        dest.writeInt(value);
        dest.writeInt(AmountToEnrollment);
    }
}
