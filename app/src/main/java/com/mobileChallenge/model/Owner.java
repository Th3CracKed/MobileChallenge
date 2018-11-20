package com.mobileChallenge.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 * Sub Model used @Item
 * Contains : owner repo login and avatar url
 */
public class Owner {
    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    /**
     * used by data binding library to fetch image
     * @param imageView the imageView that will be be binded
     * @param imageUrl the url to fetch
     */
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }
}
