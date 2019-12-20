package com.example.memorygame;

import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class GridImageView {
    private ImageView img;
    private int imageCode;
    private  boolean isUp = false;
    private boolean isSolved;

    public GridImageView(ImageView img, int imageCode) {
        this.img = img;
        this.imageCode = imageCode;
        this.isSolved = false;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public ImageView getImg() {
        return img;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridImageView that = (GridImageView) o;
        return imageCode == that.imageCode &&
                img.equals(that.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(img, imageCode);
    }


}
