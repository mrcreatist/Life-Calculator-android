package com.example.abhi.lifecalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abhi on 12/6/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<CardLayout> cardLayoutList;
    private Context context;

    public MyAdapter(List<CardLayout> cardLayoutList, Context context) {
        this.cardLayoutList = cardLayoutList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardLayout cardLayoutObject = cardLayoutList.get(position);

        holder.userName.setText(cardLayoutObject.getUserNameString());
        holder.dayValue.setText(cardLayoutObject.getDayValueString());
        holder.monthValue.setText(cardLayoutObject.getMonthValueString());
    }

    @Override
    public int getItemCount() {
        return cardLayoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, dayValue, monthValue;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userName);
            dayValue = (TextView) itemView.findViewById(R.id.dayValue);
            monthValue = (TextView) itemView.findViewById(R.id.monthValue);
        }


    }
}