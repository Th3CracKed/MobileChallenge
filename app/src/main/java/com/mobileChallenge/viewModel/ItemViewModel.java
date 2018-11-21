package com.mobileChallenge.viewModel;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobileChallenge.model.Item;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * ViewModel for RecyclerView Adapter.
 * Store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 */
public class ItemViewModel extends AndroidViewModel {

    private static Context sContext;
    private MutableLiveData<Item> mutableLiveData;
    private ObservableField<String> name;
    private ObservableField<String> description;
    private ObservableField<String> watchers;
    private ObservableField<String> login;
    private ObservableField<String> avatar_url;
    private ObservableField<String> html_url;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        sContext = application.getApplicationContext();
    }

    public MutableLiveData<Item> getMutableLiveData() {
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void setItem(Item item){
        if(mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
        }
        mutableLiveData.setValue(item);
        init();
    }

    public ObservableField<String> getName() {
        if(name == null){
            name = new ObservableField<>();
        }
        return name;
    }

    public ObservableField<String> getDescription() {
        if(description == null){
            description = new ObservableField<>();
        }
        return description;
    }

    public ObservableField<String> getWatchers() {
        if(watchers == null){
            watchers = new ObservableField<>();
        }
        return watchers;
    }

    public ObservableField<String> getLogin() {
        if(login == null){
            login = new ObservableField<>();
        }
        return login;
    }

    public ObservableField<String> getAvatar_url() {
        if(avatar_url == null){
            avatar_url = new ObservableField<>();
        }
        return avatar_url;
    }

    public ObservableField<String> getHtml_url() {
        if(html_url == null){
            html_url = new ObservableField<>();
        }
        return html_url;
    }

    /*
     * Init binding layout
     */
    private void init(){
        Item item = mutableLiveData.getValue();
        name = new ObservableField<>(Objects.requireNonNull(item).getName());
        description = new ObservableField<>(item.getDescription());
        watchers = new ObservableField<>(item.getWatchers());
        login = new ObservableField<>(item.getOwner().getLogin());
        avatar_url = new ObservableField<>(item.getOwner().getAvatar_url());
        html_url = new ObservableField<>(item.getHtml_url());
    }

    /**
     * used by data binding library to fetch image
     * @param imageView the imageView that will be be binded
     * @param imageUrl the url to fetch
     */
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageView != null) {
            Glide.with(sContext).load(imageUrl).into(imageView);
        }
    }
}
