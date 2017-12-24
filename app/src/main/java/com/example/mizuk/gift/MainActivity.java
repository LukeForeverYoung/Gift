package com.example.mizuk.gift;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    exRecAdapter adapter;
    private ViewGroup group;
    ArrayList<imageItem> list;
    int testcnt = 0;
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataLoad();
        initRec();
        Button btn = findViewById(R.id.testbtn);
        testcnt = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.createViewHolder(group, 0);
                adapter.add(list.get(testcnt++));
            }
        });
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapter.remove();
                return true;
            }
        });

    }


    void initRec() {
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
        adapter = new exRecAdapter(this, list);
        SlideInLeftAnimationAdapter slideInLeftAnimationAdapter=new SlideInLeftAnimationAdapter(adapter);
        slideInLeftAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(slideInLeftAnimationAdapter);
        for(int i=0;i<list.size();i++)
            adapter.add(list.get(i));

    }

    void dataLoad() {
        InputStream is;
        try {
            AssetManager assets = getAssets();
            String[] items = assets.list("Img");
            list = new ArrayList<>(items.length);
            for (String path : items) {
                is = assets.open("Img/" + path);
                list.add(new imageItem(BitmapFactory.decodeStream(is)));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Log.v("dataLoad", "" + list.size());
    }
}

class imageItem {
    Bitmap img;

    imageItem(Bitmap bitmap) {
        img = bitmap;
    }

    public Bitmap getImg() {
        return img;
    }
}
