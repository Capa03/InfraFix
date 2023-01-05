package com.capa.infrafix.Form;

import android.graphics.Bitmap;

public class BitmapImage {
    private Bitmap bitmapImage;

    public BitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public Bitmap getBitmap() {
        return bitmapImage;
    }

    public void setBitmap(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }
}
