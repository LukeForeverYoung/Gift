package com.example.mizuk.gift;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import java.util.ArrayList;

public class exRecAdapter extends RecyclerView.Adapter<exRecAdapter.exViewHolder> {
    private ArrayList<imageItem> list;
    private Context context;

    public exRecAdapter(Context context, ArrayList<imageItem> list) {
        //this.list=list;
        this.list = new ArrayList<>();
        this.context = context;
        notifyDataSetChanged();
    }

    public void add(imageItem item) {
        int position=list.size();
        list.add(position,item);
        notifyItemInserted(position);
        Log.v("siz", "" + list.size());
    }

    public void remove() {
        int position=list.size()-1;
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public exViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_content, parent, false);
        return new exViewHolder(view);
    }

    @Override
    public void onBindViewHolder(exViewHolder holder, int position) {
        holder.position = position;
        holder.imgView.setImageBitmap(list.get(position).getImg());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class exViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;
        public int position;

        public exViewHolder(View parentView) {
            super(parentView);
            imgView = parentView.findViewById(R.id.img);
        }
    }
}