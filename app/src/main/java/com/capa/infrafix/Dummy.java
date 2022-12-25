package com.capa.infrafix;

import android.graphics.Bitmap;

public class Dummy {

    private static Dummy single_instance;
    private Bitmap bitmap = null;
    private Dummy(){

    }

    public static Dummy getInstance(){
        if (single_instance == null)
            single_instance = new Dummy();

        return single_instance;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}


