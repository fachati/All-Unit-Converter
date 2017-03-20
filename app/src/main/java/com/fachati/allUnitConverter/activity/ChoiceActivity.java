package com.fachati.allUnitConverter.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        int[] covers = new int[]{
                R.drawable.ic_cook,
                R.drawable.ic_currency,
                R.drawable.ic_fuel,
                R.drawable.ic_energy};

        ConverterItem a = new ConverterItem("True Romance",covers[0]);
        choiceList.add(a);

        a = new ConverterItem("True Romance",covers[1]);
        choiceList.add(a);

        a = new ConverterItem("True Romance",covers[3]);
        choiceList.add(a);

        a = new ConverterItem("True Romance",covers[2]);
        choiceList.add(a);



        adapter.notifyDataSetChanged();
    }

}
