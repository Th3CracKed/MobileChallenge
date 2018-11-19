package com.mobileChallenge.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobileChallenge.R;

import androidx.databinding.BindingAdapter;

/**
 * Sub Model used @Item
 * Contains : owner repo login and avatar url
 */
public class Owner {
    private String login;
    private String avatar_url;

    public Owner(String login, String avatar_url) {
        this.login = login;
        this.avatar_url = avatar_url;
    }

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
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.avatarImage) == null || !imageView.getTag(R.id.avatarImage).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.avatarImage, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.avatarImage, null);
            imageView.setImageBitmap(null);
        }
    }
}
