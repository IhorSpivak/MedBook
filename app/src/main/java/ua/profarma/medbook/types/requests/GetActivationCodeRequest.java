package ua.profarma.medbook.types.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class GetActivationCodeRequest {

    public GetActivationCodeRequest(int user_id, String phone_number) {
        this.user_id = user_id;
        this.phone_number = phone_number;
    }

    public int user_id;
    public String verification_type = "changephone";
    public String phone_number;

}
