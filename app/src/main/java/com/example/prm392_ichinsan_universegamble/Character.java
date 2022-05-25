package com.example.prm392_ichinsan_universegamble;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Character implements Parcelable {

    private String name;
    private int image;
    private boolean isClicked;

    public Character(String name, int image) {
        this.name = name;
        this.image = image;
        isClicked = false;
    }

    protected Character(Parcel in) {
        name = in.readString();
        image = in.readInt();
        isClicked = in.readByte() != 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    @NonNull
    public static ArrayList<Character> getCharacters(){
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(new Character("Thanos", R.drawable.thanos));
        characters.add(new Character("Captain America", R.drawable.captainamerica));
        characters.add(new Character("Hulk", R.drawable.hulk));
        characters.add(new Character("Iron Man", R.drawable.ironman));
        characters.add(new Character("Scarlet Witch", R.drawable.scarletwitch));
        characters.add(new Character("Spider Man", R.drawable.spiderman));
        return characters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(image);
        parcel.writeByte((byte) (isClicked ? 1 : 0));
    }
}
