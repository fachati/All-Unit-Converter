package com.fachati.allUnitConverter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ismailfachati on 19/03/2017.
 */
public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.MyViewHolder> {

    private Context mContext;
    private List<ConverterItem> choiceList;

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ConverterItem choice = choiceList.get(position);
        holder.title.setText(choice.getText());

        holder.icon.setImageDrawable(mContext.getDrawable(choice.getIcon()));
        // loading album cover using Glide library
        Glide.with(mContext).load(choice.getIcon()).into(holder.icon);

    }



    @Override
    public int getItemCount() {
        return choiceList.size();
    }
}