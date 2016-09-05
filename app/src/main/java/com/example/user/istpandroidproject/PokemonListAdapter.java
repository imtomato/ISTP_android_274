package com.example.user.istpandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.istpandroidproject.model.OwnedPokemonInfo;

import java.util.List;

/**
 * Created by user on 2016/9/5.
 */
public class PokemonListAdapter extends ArrayAdapter<OwnedPokemonInfo> {

    int rowViewLayoutId;
    LayoutInflater mInflater;

    public PokemonListAdapter(Context context, int layoutId, List<OwnedPokemonInfo> objects) {
        super(context, layoutId, objects);

        rowViewLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {

        OwnedPokemonInfo data = getItem(position);
        ViewHolder viewHolder = null;

        if(rowView == null) {
            rowView = mInflater.inflate(rowViewLayoutId, null);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)rowView.getTag();
        }

        viewHolder.setView(data);

        return rowView;
    }

    public static class ViewHolder {

        View mRowView;
        ImageView mAppearanceImg;
        TextView mNameText;
        TextView mLevelText;
        TextView mCurrentHP;
        TextView mMaxHP;
        ProgressBar mHPBar;

        OwnedPokemonInfo mData;

        public static PokemonListAdapter mAdapter;

        public ViewHolder(View rowView) {
            mRowView = rowView;
            mAppearanceImg = (ImageView) rowView.findViewById(R.id.appearanceImg);
            mNameText = (TextView) rowView.findViewById(R.id.nameText);
            mLevelText = (TextView) rowView.findViewById(R.id.levelText);
            mCurrentHP = (TextView) rowView.findViewById(R.id.currentHP);
            mMaxHP = (TextView) rowView.findViewById(R.id.maxHP);
            mHPBar = (ProgressBar) rowView.findViewById(R.id.hpBar);

        }

        //bind mRowView with data
        public void setView(OwnedPokemonInfo data) {
            mData = data;

            mNameText.setText(data.name);
            mLevelText.setText(String.valueOf(data.level));
            mCurrentHP.setText(String.valueOf(data.currentHP));
            mMaxHP.setText(String.valueOf(data.maxHP));
            int progress = (int)((((float)data.currentHP)/ data.maxHP) * 100);
            mHPBar.setProgress(progress);

            //TODO: set image through image loader
        }

    }

}
