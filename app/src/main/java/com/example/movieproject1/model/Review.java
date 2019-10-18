package com.example.movieproject1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private String mAuthor;
    private String mContent;
    private String mID;
    private String mURL;

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public Review(String mAuthor, String mContent, String mID, String mURL) {
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mID = mID;
        this.mURL = mURL;
    }

    public Review(Parcel parcel) {
        this.mAuthor = parcel.readString();
        this.mContent = parcel.readString();
        this.mID = parcel.readString();
        this.mURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mID);
        parcel.writeString(mURL);

    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[i];
        }
    };
}
//TODO Parcelable