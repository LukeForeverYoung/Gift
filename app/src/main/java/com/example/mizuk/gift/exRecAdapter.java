package com.example.mizuk.gift;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class exRecAdapter extends RecyclerView.Adapter<exRecAdapter.exViewHolder>  implements View.OnClickListener{
    private ArrayList<imageItem> list;
    private MainActivity context;
    RecyclerView recyclerView;
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    RecyclerView.Adapter<exRecAdapter.exViewHolder> a=this;
    public exRecAdapter(MainActivity context,RecyclerView recyclerView, ArrayList<imageItem> list) {
        //this.list=list;
        this.recyclerView=recyclerView;
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
    public void change(Bitmap img,Bitmap ixmg,int position)
    {
        imageItem item=list.get(position);

        if(item.state==true)
        {
            item.state=false;
            item.img=ixmg;
        }
        else {
            item.state = true;
            item.img = img;
        }
        notifyItemChanged(position);
    }
    public void remove() {
        int position=list.size()-1;
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public exViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_content, parent, false);
        view.setOnClickListener(this);
        return new exViewHolder(view);
    }

    @Override
    public void onBindViewHolder(exViewHolder holder, int position) {
        holder.position = position;
        holder.imgView.setImageBitmap(list.get(position).getImg());
        if(position==getItemCount()-1)
        {
            context.loadMore();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,recyclerView.getChildViewHolder(v).getAdapterPosition());
        }
    }

    class exViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;
        public int position;

        public exViewHolder(View parentView) {
            super(parentView);
            imgView = parentView.findViewById(R.id.img);
        }

    }
}
