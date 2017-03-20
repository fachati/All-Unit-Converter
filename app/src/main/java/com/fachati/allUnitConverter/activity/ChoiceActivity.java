package com.fachati.allUnitConverter.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.fachati.allUnitConverter.ChoiceAdapter;
import com.fachati.allUnitConverter.ConverterItem;
import com.fachati.allUnitConverter.R;

import java.util.ArrayList;
import java.util.List;

public class ChoiceActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ChoiceAdapter adapter;
    private List<ConverterItem> choiceList;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        choiceList = new ArrayList<>();
        adapter = new ChoiceAdapter(this, choiceList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        prepareAlbums();

    }

    private void prepareAlbums() {

        int[] icons = new int[]{
                R.drawable.area,//
                R.drawable.cooking,//
                R.drawable.currency,//
                R.drawable.storage,
                R.drawable.energy,
                R.drawable.fuel,//
                R.drawable.lenght,
                R.drawable.mass,
                R.drawable.ic_power,
                R.drawable.pressure,
                R.drawable.speed,//
                R.drawable.ic_temperature,//
                R.drawable.time,//
                R.drawable.ic_torque,
                R.drawable.ic_volume,};

        String[] texts = new String[]{
                getString(R.string.area),
                getString(R.string.cooking),
                getString(R.string.currency),
                getString(R.string.storage),
                getString(R.string.energy),
                getString(R.string.fuel_consumption),
                getString(R.string.length),
                getString(R.string.mass),
                getString(R.string.power),
                getString(R.string.pressure),
                getString(R.string.speed),
                getString(R.string.temperature),
                getString(R.string.time),
                getString(R.string.torque),
                getString(R.string.volume)};

        ConverterItem a;
        for(int i=0;i<texts.length;i++){
            a = new ConverterItem(texts[i],icons[i]);
            choiceList.add(a);
        }




        adapter.notifyDataSetChanged();
    }

}
