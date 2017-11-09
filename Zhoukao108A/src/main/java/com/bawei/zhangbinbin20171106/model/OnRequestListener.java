package com.bawei.zhangbinbin20171106.model;

import com.bawei.zhangbinbin20171106.bean.MyBean;

import java.util.List;

/**
 * Created by Zhang on 2017/11/6.
 */

public interface OnRequestListener {
    void OnSuccess(List<MyBean.SongListBean> list);
    void OnError(String e);
}
