package com.example.connectfour.game;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

public class Streak {
    public Piece.Type type;
    public int count;

    Streak(int count, Piece.Type type){
        this.type = type;
        this.count = count;
    }


}

class sortStreak implements Comparator<Streak> {

    @Override
    public int compare(Streak streak1, Streak streak2) {
        return streak1.count - streak2.count;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Comparator<Streak> thenComparing(Comparator<? super Streak> other) {
        return Comparator.super.thenComparing(other);
    }
}
