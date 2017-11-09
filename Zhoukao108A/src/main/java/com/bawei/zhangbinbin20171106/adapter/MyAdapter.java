package com.bawei.zhangbinbin20171106.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bawei.zhangbinbin20171106.MainActivity;
import com.bawei.zhangbinbin20171106.R;
import com.bawei.zhangbinbin20171106.bean.MyBean;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Zhang on 2017/11/6.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MyBean.SongListBean> list;
    private Context context;
    private MainActivity ac;
    int visible = View.GONE;

    public MyAdapter(List<MyBean.SongListBean> list, Context context) {
        this.list = list;
        this.context = context;
        ac = (MainActivity) context;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置布局
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        //赋值
        final MyBean.SongListBean sb = list.get(position);
        holder.tv.setText(sb.getAuthor() + "--" + sb.getTitle());
        Uri uri = Uri.parse(sb.getPic_small());
        holder.img.setImageURI(uri);
        //调整checkbox
        holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sb.setCheck(isChecked);
                if (isChecked) {
                    ac.setCb(isAllChecked());
                } else {
                    ac.setCb(false);
                }
            }
        });
        holder.ck.setChecked(sb.isCheck());
        //设置checkbox的显示隐藏
        //int visible = View.VISIBLE;
        holder.ck.setVisibility(visible);
    }

    //全选
    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            MyBean.SongListBean dataBean = list.get(i);
            if (!dataBean.isCheck()) {
                return false;
            }
        }
        return true;
    }

    //全选
    public void ckall() {
        for (int i = 0; i < list.size(); i++) {
            MyBean.SongListBean dataBean = list.get(i);
            dataBean.setCheck(true);
            notifyDataSetChanged();
        }
    }

    //取消全选
    public void cknull() {
        for (int i = 0; i < list.size(); i++) {
            MyBean.SongListBean dataBean = list.get(i);
            dataBean.setCheck(false);
            notifyDataSetChanged();
        }
    }

    //删除
    public void ckdelete() {
        for (int i = 0; i < list.size(); i++) {
            MyBean.SongListBean dataBean = list.get(i);
            if (dataBean.isCheck()) {
                list.remove(dataBean);
                notifyDataSetChanged();
            }

        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //viewholder
    class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;
        TextView tv;
        CheckBox ck;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
            img = (SimpleDraweeView) itemView.findViewById(R.id.item_img);
            ck = (CheckBox) itemView.findViewById(R.id.item_ck);
        }
    }
}
