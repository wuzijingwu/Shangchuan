package com.bawei.zhangbinbin20171106.presenter;

import com.bawei.zhangbinbin20171106.bean.MyBean;
import com.bawei.zhangbinbin20171106.model.IModel;
import com.bawei.zhangbinbin20171106.model.ListModel;
import com.bawei.zhangbinbin20171106.model.OnRequestListener;
import com.bawei.zhangbinbin20171106.view.IView;

import java.util.List;

/**
 * Created by Zhang on 2017/11/6.
 */

public class ListPresenter implements IPresenter {

    private IView iView;
    private IModel iModel;

    public ListPresenter(IView iView) {
        this.iView = iView;
        iModel = new ListModel();
    }

    //model层与view层交互
    @Override
    public void loadList(String url) {
        iModel.RequestData(url, new OnRequestListener() {
            @Override
            public void OnSuccess(List<MyBean.SongListBean> list) {
                iView.showList(list);
            }

            @Override
            public void OnError(String e) {
                iView.showError(e);
            }
        });
    }
}
