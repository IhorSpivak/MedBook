package ua.profarma.medbook.types;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    public int id;
    public int district_id;
    public CityTranslation[] translations;

    protected City(Parcel in) {
        id = in.readInt();
        district_id = in.readInt();
        translations = in.createTypedArray(CityTranslation.CREATOR);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(district_id);
        dest.writeTypedArray(translations, flags);
    }
}
