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
import android.view.animation.AccelerateInterpolator;
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
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.mizuk.gift.exRecAdapter.OnItemClickListener;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    exRecAdapter adapter;
    private ViewGroup group;
    ArrayList<imageItem> list;
    ArrayList<Bitmap> bao;
    Random rd;
    int testcnt = 0;
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rd=new Random();
        dataLoad();
        initRec();
        testcnt = 0;
    }
    void initRec() {
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(800);
        recyclerView.getItemAnimator().setRemoveDuration(800);
        recyclerView.getItemAnimator().setMoveDuration(800);
        recyclerView.getItemAnimator().setChangeDuration(800);
        adapter = new exRecAdapter(this,recyclerView, list);
        SlideInBottomAnimationAdapter slideInLeftAnimationAdapter=new SlideInBottomAnimationAdapter(adapter);
        slideInLeftAnimationAdapter.setFirstOnly(false);
        slideInLeftAnimationAdapter.setDuration(800);
        slideInLeftAnimationAdapter.setInterpolator(new AccelerateInterpolator());
        recyclerView.setAdapter(slideInLeftAnimationAdapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.v("pos",""+position);
                 adapter.change(list.get(rd.nextInt(list.size())).img,bao.get(rd.nextInt(bao.size())),position);
                //Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_LONG).show();
            }
        });

        for(int i=0;i<30;i++)
            adapter.add(list.get(rd.nextInt(list.size())).clone());

    }
    public void loadMore()
    {
        Thread asyncLoad=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<10;i++)
                    adapter.add(list.get(rd.nextInt(list.size())).clone());
            }
        });
        asyncLoad.start();

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
        try{
            AssetManager assets = getAssets();
            String[] items = assets.list("Bao");
            bao = new ArrayList<>(items.length);
            for (String path : items) {
                is = assets.open("Bao/" + path);
                bao.add(BitmapFactory.decodeStream(is));
            }
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Log.v("dataLoad", "" + list.size());
    }
}

class imageItem implements Cloneable{
    Bitmap img;
    boolean state;
    imageItem(Bitmap bitmap) {
        img = bitmap;
        state=true;
    }
    public Bitmap getImg() {
        return img;
    }
    @Override
    public imageItem clone()
    {
        try {
            return (imageItem)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
