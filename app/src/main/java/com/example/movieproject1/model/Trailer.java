package com.example.movieproject1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {

    private String mID;
    private String mKey;
    private String mName;
    private String mSite;
    private String mType;

    public Trailer(String mID, String mKey, String mName, String mSite, String mType) {
        this.mID = mID;
        this.mKey = mKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mType = mType;
    }

    public Trailer(Parcel parcel) {
        mID =parcel.readString();
        mKey = parcel.readString();
        mName = parcel.readString();
        mSite = parcel.readString();
        mType = parcel.readString();
    }

    public String getID() {
        return mID;
    }

    public String getKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    public String getSite() {
        return mSite;
    }

    public String getType() {
        return mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mID);
        parcel.writeString(mKey);
        parcel.writeString(mName);
        parcel.writeString(mSite);
        parcel.writeString(mType);

    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[i];
        }
    };
}
//TODO Parcelable