package com.example.mizuk.gift;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    exRecAdapter adapter;
    ArrayList<imageItem> list;
    int testcnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataLoad();
        initRec();
        Button btn=findViewById(R.id.testbtn);
        testcnt=0;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add(list.get(testcnt++));

            }
        });
    }
    void initRec()
    {
        recyclerView=findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter=new exRecAdapter(this,list));

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        recyclerView.setItemAnimator(animator);
        for(int i=0;i<list.size();i++)
            adapter.add(list.get(i));
    }
    void dataLoad()
    {
        InputStream is;
        try
        {
            AssetManager assets= getAssets();
            String[] items=assets.list("Img");
            list=new ArrayList<>(items.length);
            for (String path:items)
            {
                is=assets.open("Img/"+path);
                list.add(new imageItem(BitmapFactory.decodeStream(is)));
            }
        }
        catch (IOException ioe){ioe.printStackTrace();}
        Log.v("dataLoad",""+list.size());
    }
    class exRecAdapter extends RecyclerView.Adapter<exViewHolder> {
        private ArrayList<imageItem> list;
        private Context context;
        public exRecAdapter(Context context, ArrayList<imageItem> list) {
            //this.list=list;
            this.list=new ArrayList<>();
            this.context=context;
            notifyDataSetChanged();
        }

        public void add(imageItem item)
        {
            list.add(0,item);
            notifyItemChanged(0);
        }
        @Override
        public exViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_content, parent,false);

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
    }
    private class exViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgView;
        public int position;

        public exViewHolder(View parentView) {
            super(parentView);
            imgView = parentView.findViewById(R.id.img);
        }
    }
}


class imageItem
{
    Bitmap img;
    imageItem(Bitmap bitmap)
    {
        img=bitmap;
    }
    public Bitmap getImg() {
        return img;
    }
}