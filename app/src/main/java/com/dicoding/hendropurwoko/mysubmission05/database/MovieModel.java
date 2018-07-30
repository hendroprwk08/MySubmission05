package com.dicoding.hendropurwoko.mysubmission05.database;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.dicoding.hendropurwoko.mysubmission05.database.MovieContract.getColumnInt;
import static com.dicoding.hendropurwoko.mysubmission05.database.MovieContract.getColumnString;

public class MovieModel implements Parcelable {
    int id;
    String title, overview, release_date, popularity, favorite, poster;

    protected MovieModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        popularity = in.readString();
        favorite = in.readString();
        poster = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieModel() {  }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieModel(String title, String overview, String release_date, String popularity) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    /* CONTENT PROVIDER */
    public MovieModel(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, MovieContract.MovieColumns.TITLE);
        this.release_date = getColumnString(cursor, MovieContract.MovieColumns.RELEASE_DATE);
        this.overview = getColumnString(cursor, MovieContract.MovieColumns.OVERVIEW);
        //this.favorite = getColumnString(cursor, MovieContract.MovieColumns.FAVORITE);
        this.popularity = getColumnString(cursor, MovieContract.MovieColumns.POPULARITY);
        this.poster = getColumnString(cursor, MovieContract.MovieColumns.POSTER);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(popularity);
        dest.writeString(favorite);
        dest.writeString(poster);
    }
}
