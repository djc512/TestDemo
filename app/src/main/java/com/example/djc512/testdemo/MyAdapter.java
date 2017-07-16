package com.example.djc512.testdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by DjC512 on 2017-7-16.
 */

public class MyAdapter extends SwipeMenuAdapter<MyAdapter.MyHolder> {
    private Context ctx;
    private List<UserBean.DataBean> list;

    public MyAdapter(Context ctx, List<UserBean.DataBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    public void updateData(List<UserBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);
        return view;
    }

    @Override
    public MyHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        MyHolder holder = new MyHolder(realContentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        UserBean.DataBean dataBean = list.get(position);
        holder.tv_id.setText(dataBean.getId() + "");
        holder.tv_name.setText(dataBean.getName());
        holder.tv_phone.setText(dataBean.getCellphone());
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView tv_id;
        private final TextView tv_name;
        private final TextView tv_phone;

        public MyHolder(View view) {
            super(view);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        }
    }
}
