package com.example.djc512.testdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et;
    private Button search;
    private Button add;
    private SwipeMenuRecyclerView rv;
    private List<UserBean.DataBean> data;
    private MyAdapter adapter;
    private Context ctx;
    private String BaseUrl = "http://121.40.226.116:8899";
    private String searchUrl = "/contact/list/";
    private String addUrl = "/contact/new/";
    private String deleteUrl = "/contact/delete/<id>/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        search.setOnClickListener(this);
        add.setOnClickListener(this);

        rv.setSwipeMenuCreator(swipeMenuCreator);
        rv.setSwipeMenuItemClickListener(menuItemClickListener);
    }

    private void initData() {
        data = new ArrayList<>();
        adapter = new MyAdapter(this, data);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

    }

    private void initView() {
        et = (EditText) findViewById(R.id.et);
        search = (Button) findViewById(R.id.search);
        add = (Button) findViewById(R.id.add);
        rv = (SwipeMenuRecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                String trim = et.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                String list = getStrFromAssets("list.json");
                Log.i("list", list);
                UserBean userBean = new Gson().fromJson(list, UserBean.class);
                if (null != userBean) {
                    data = userBean.getData();
                    adapter.updateData(data);
                }
                request(BaseUrl + searchUrl, trim);
                break;
            case R.id.add:
                addRequest();
                break;
        }
    }

    private void addRequest() {
        request(BaseUrl + addUrl, null);
        String addlist = getStrFromAssets("add.json");
        UserBean userBean = new Gson().fromJson(addlist, UserBean.class);
        List<UserBean.DataBean> newData = userBean.getData();
        data.addAll(newData);
        adapter.updateData(data);
    }

    private void request(String url, String trim) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getStrFromAssets(String name) {
        String strData = null;
        try {
            InputStream inputStream = getAssets().open(name);
            byte buf[] = new byte[1024];
            inputStream.read(buf);
            strData = new String(buf);
            strData = strData.trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("strData = " + strData);
        return strData;
    }

    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(ctx)
                    .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(DensityUtil.dip2px(MainActivity.this, 96))
                    .setHeight(DensityUtil.dip2px(MainActivity.this, 80));
            rightMenu.addMenuItem(deleteItem);
        }
    };
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                int id = data.get(adapterPosition).getId();
                deleteRequest(id);
                data.remove(adapterPosition);
                adapter.updateData(data);
            }
        }
    };

    private void deleteRequest(int id) {
        request(BaseUrl + deleteUrl, id + "");
    }
}
