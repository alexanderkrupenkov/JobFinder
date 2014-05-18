package de.softan.jobfinder.data.workers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alexander on 17.05.2014.
 */
public class Vacancy implements Parcelable {

    public static final String DESCRIPTION = "description";
    public static final String EXPERIENCE = "experience";
    public static final String NAME = "name";
    public static final String SCHEDULE = "schedule";

    public String description;
    public String experience;
    public String schedule;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(experience);
        dest.writeString(schedule);
    }

    public static final Creator<Vacancy> CREATOR = new Creator<Vacancy>() {
        public Vacancy createFromParcel(Parcel in) {
            return new Vacancy(in);
        }
        public Vacancy[] newArray(int size) {
            return new Vacancy[size];
        }
    };

    private Vacancy(Parcel in) {
        description = in.readString();
        experience = in.readString();
        schedule = in.readString();
    }

    public Vacancy() {
    }
}