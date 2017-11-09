package com.bawei.zhangbinbin20171106.model;

import com.bawei.zhangbinbin20171106.bean.MyBean;
import com.bawei.zhangbinbin20171106.utils.LoggingInterceptor;
import com.bawei.zhangbinbin20171106.view.ApiService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zhang on 2017/11/6.
 */

public class ListModel implements IModel {
    @Override
    public void RequestData(String url, final OnRequestListener onRequestListener) {
        //添加拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        //retrofit网络请求
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //结合rxjava
        Observable<MyBean> getdatas = apiService.getdatas();
        getdatas.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onRequestListener.OnError(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(MyBean myBean) {
                        List<MyBean.SongListBean> song_list = myBean.getSong_list();
                        onRequestListener.OnSuccess(song_list);
                    }
                });
    }
}
