package com.bawei.zhangbinbin20171106.view;

import com.bawei.zhangbinbin20171106.bean.MyBean;

import java.util.List;

/**
 * Created by Zhang on 2017/11/6.
 */

public interface IView {
    void showList(List<MyBean.SongListBean> list);
    void showError(String e);
}
