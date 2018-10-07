package app.view.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    RecyclerView mRecyclerView;
    RvAdapter mAdapter;
    List<RVBean> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 20; i++) {
            RVBean rvBean = new RVBean();
            rvBean.setText("数据" + i);
            mData.add(rvBean);
        }
        mAdapter = new RvAdapter(mData, this);
        mRecyclerView =  findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemViewClick(new RvAdapter.onItemViewClick() {
            @Override
            public void onItemViewClick(View view, int position) {
                Toast.makeText(MainActivity.this, "View点击事件" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnClickListener(new RvAdapter.onClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Toast.makeText(MainActivity.this, "控件点击事件" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnLongClickListener(new RvAdapter.onLongClickListener() {
            @Override
            public void onLongClickListener(View view, int position) {
                Toast.makeText(MainActivity.this, "控件长按事件" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnItemViewLongClick(new RvAdapter.onItemViewLongClick() {
            @Override
            public void onItemViewLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "View长按事件" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setHeaderView(addHeader());
        mAdapter.setFooterView(addFooter());
    }

    private View addFooter() {
        View footer= LayoutInflater.from(this).inflate(R.layout.foot_view,mRecyclerView,false);
        mAdapter.setFooterView(footer);
        return footer;
    }

    private View addHeader() {
        View header= LayoutInflater.from(this).inflate(R.layout.header_view,mRecyclerView,false);
        mAdapter.setHeaderView(header);
        return header;
    }
}
