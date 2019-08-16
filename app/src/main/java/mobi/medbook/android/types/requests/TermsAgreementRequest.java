package mobi.medbook.android.types.requests;

import android.os.Parcel;
import android.os.Parcelable;

public class TermsAgreementRequest implements Parcelable {
    public int terms_agreement;

    public TermsAgreementRequest(int terms_agreement) {
        this.terms_agreement = terms_agreement;
    }

    protected TermsAgreementRequest(Parcel in) {
        terms_agreement = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(terms_agreement);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TermsAgreementRequest> CREATOR = new Creator<TermsAgreementRequest>() {
        @Override
        public TermsAgreementRequest createFromParcel(Parcel in) {
            return new TermsAgreementRequest(in);
        }

        @Override
        public TermsAgreementRequest[] newArray(int size) {
            return new TermsAgreementRequest[size];
        }
    };
}
