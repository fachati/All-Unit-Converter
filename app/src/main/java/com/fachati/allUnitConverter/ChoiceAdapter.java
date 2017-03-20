package com.fachati.allUnitConverter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fachati.allUnitConverter.activity.MainActivity;
import com.fachati.allUnitConverter.models.Conversion;

import java.util.List;

/**
 * Created by ismailfachati on 19/03/2017.
 */
public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.MyViewHolder> implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<ConverterItem> choiceList;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("position","click - "+i);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.TextViewName);
            icon = (ImageView) view.findViewById(R.id.imageViewIcon);

        }
    }


    public ChoiceAdapter(Context mContext, List<ConverterItem> choiceList) {
        this.mContext = mContext;
        this.choiceList = choiceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choice_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ConverterItem choice = choiceList.get(position);
        Typeface typeface=Typeface.createFromAsset(mContext.getAssets(), "fonts/Pacifico.ttf");
        holder.title.setTypeface(typeface);
        holder.title.setText(choice.getText());

        holder.icon.setImageResource(choice.getIcon());
        //Glide.with(mContext).load(choice.getIcon()).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mContext, MainActivity.class);
                intent.putExtra("position",position);
                mContext.startActivity(intent);
                Log.e("test","click -"+position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return choiceList.size();
    }
}