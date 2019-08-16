package mobi.medbook.android.types;

import android.os.Parcel;
import android.os.Parcelable;

public class CityTranslation implements Parcelable {
    public int id;
    public int city_id;
    public String language;
    public String name;

    protected CityTranslation(Parcel in) {
        id = in.readInt();
        city_id = in.readInt();
        language = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(city_id);
        dest.writeString(language);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CityTranslation> CREATOR = new Creator<CityTranslation>() {
        @Override
        public CityTranslation createFromParcel(Parcel in) {
            return new CityTranslation(in);
        }

        @Override
        public CityTranslation[] newArray(int size) {
            return new CityTranslation[size];
        }
    };
}
