package com.sunfb.sideindexletter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunfb.sideindexletter.R;
import com.sunfb.sideindexletter.bean.CityBean;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityListViewHolder> {
    private List<CityBean> mEntities = new ArrayList<>();
    private Context mContext;

    public CityAdapter(Context mContext,List<CityBean> entities) {
        this.mContext = mContext;
        this.mEntities = entities;
    }

    public void updateData(List<CityBean> entities) {
        this.mEntities = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //第三个参数attachToRoot不能省略,否则报java.lang.IllegalStateException: ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)
        return new CityListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final CityListViewHolder holder, int position) {
        holder.tv_nmae.setText(mEntities.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.tv_nmae.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    static class CityListViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nmae;
        public ImageView iv_img;
        public View view;


        public CityListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nmae = itemView.findViewById(R.id.tv_item_name);
            iv_img = itemView.findViewById(R.id.iv_item_img);
            view = itemView;
        }
    }
}
