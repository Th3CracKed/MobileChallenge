package com.mobileChallenge.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mobileChallenge.R;
import com.mobileChallenge.databinding.RowItemBinding;
import com.mobileChallenge.viewModel.ItemViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ItemViewModel> items;

    public void setItems(List<ItemViewModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewModel itemViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ItemViewModel.class);
        RowItemBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.row_item,parent,false);
        binding.setViewModel(itemViewModel);
        return new ViewHolder(binding);
    }

    /**
     * Replace the contents of a view
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.bind(items.get(position));
        viewHolder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(items.get(position).getHtml_url().get()));
            viewHolder.binding.getRoot().getContext().startActivity(intent);
        });
    }

    /**
     * Return the size of dataSet
      */
    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    /**
     * setup recyclerView's Adapter
     * @param recyclerView that will receive the adapter
     * @param adapter bind the recyclerView
     */
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding binding;

        ViewHolder(RowItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
        void bind(ItemViewModel viewModel) {
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
