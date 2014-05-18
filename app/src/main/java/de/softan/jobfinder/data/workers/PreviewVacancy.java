package de.softan.jobfinder.data.workers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alexander on 17.05.2014.
 */
public class PreviewVacancy  implements Parcelable {

    public static final String ID = "id";
    public static final String FOUND = "found";
    public static final String ITEMS = "items";
    public static final String LINKS = "links";
    public static final String NAME = "name";
    public static final String EMPLOYER = "employer";
    public static final String CREATED_AT = "created_at";
    public static final String URL_DETAILS = "url";

    public int id;
    public String vacancyName;
    public String employerName;
    public String employerLogo;
    public String dateCreated;
    public String url;
    public int countFound;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(vacancyName);
        dest.writeString(employerName);
        dest.writeString(dateCreated);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<PreviewVacancy> CREATOR = new Parcelable.Creator<PreviewVacancy>() {
        public PreviewVacancy createFromParcel(Parcel in) {
            return new PreviewVacancy(in);
        }
        public PreviewVacancy[] newArray(int size) {
            return new PreviewVacancy[size];
        }
    };

    private PreviewVacancy(Parcel in) {
        id = in.readInt();
        vacancyName = in.readString();
        employerName = in.readString();
        dateCreated = in.readString();
        url = in.readString();
    }

    public PreviewVacancy() {
    }
}