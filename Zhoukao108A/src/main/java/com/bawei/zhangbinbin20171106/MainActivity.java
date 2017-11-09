package com.bawei.zhangbinbin20171106;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.zhangbinbin20171106.adapter.MyAdapter;
import com.bawei.zhangbinbin20171106.bean.MyBean;
import com.bawei.zhangbinbin20171106.presenter.ListPresenter;
import com.bawei.zhangbinbin20171106.view.Api;
import com.bawei.zhangbinbin20171106.view.IView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    /**
     * 编辑
     */
    private TextView mTvBj;
    private RecyclerView mRcv;
    /**
     * 全选
     */
    private CheckBox mCkAll;
    /**
     * 删除
     */
    private TextView mDeleteAll;
    private RelativeLayout mAll;
    private MyAdapter myAdapter;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //presener层
        ListPresenter listPresenter = new ListPresenter(this);
        listPresenter.loadList(Api.PATH);
    }

    @Override
    public void showList(List<MyBean.SongListBean> list) {
        //设置recyclerview展示
        myAdapter = new MyAdapter(list, this);
        mRcv.setAdapter(myAdapter);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showError(String e) {
        Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        Log.e("哈哈哈哈啊哈哈哈哈", e);
    }

    private void initView() {
        mTvBj = (TextView) findViewById(R.id.tv_bj);
        mTvBj.setOnClickListener(this);
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mCkAll = (CheckBox) findViewById(R.id.ck_all);
        mDeleteAll = (TextView) findViewById(R.id.delete_all);
        mDeleteAll.setOnClickListener(this);
        mAll = (RelativeLayout) findViewById(R.id.all);
        //全选
        mCkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCkAll.isChecked()) {
                    mCkAll.setChecked(true);
                    myAdapter.ckall();
                } else {
                    mCkAll.setChecked(false);
                    myAdapter.cknull();
                }
            }
        });
    }

    //全选
    public void setCb(boolean bool) {
        mCkAll.setChecked(bool);
    }

    //点击返回键，若在编辑状态退出编辑状态，再次点击退出应用，若不在编辑状态直接退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (flag == 1) {
                flag = 0;
                mAll.setVisibility(View.GONE);
                myAdapter.setVisible(View.GONE);
                myAdapter.notifyDataSetChanged();
                mTvBj.setText("编辑");
            } else {
                finish();
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_bj:
                //显示与隐藏
                switch (flag) {
                    case 0:
                        mAll.setVisibility(View.VISIBLE);
                        myAdapter.setVisible(View.VISIBLE);
                        myAdapter.notifyDataSetChanged();
                        mTvBj.setText("完成");
                        flag = 1;
                        break;
                    case 1:
                        mAll.setVisibility(View.GONE);
                        myAdapter.setVisible(View.GONE);
                        myAdapter.notifyDataSetChanged();
                        mTvBj.setText("编辑");
                        flag = 0;
                        break;
                }
                break;
            case R.id.delete_all:
                //删除
                myAdapter.ckdelete();
                break;
        }
    }
}
