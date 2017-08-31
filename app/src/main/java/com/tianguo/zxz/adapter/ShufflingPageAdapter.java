package com.tianguo.zxz.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/17.
 */

public class ShufflingPageAdapter extends PagerAdapter {

    //图片地址资源
    private String[] imageUrls = {
            "http://img3.qianzhan123.com/news/201412/18/20141218-2091c7a937a72778_580x1300.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1694950311,3763516851&fm=26&gp=0.jpg",
            "http://www.cnr.cn/jingji/gs/201305/W020130511631480027123.jpg",
            "http://cnpic.crntt.com/upload/201412/30/103550832.jpg",
            "http://365jia.cn/uploads/13/0720/51ea2c48cca21.png",
            };
    //存放图片的数组
    private List<ImageView> mList = new ArrayList<>();
    private final Context context;

    public ShufflingPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position%imageUrls.length;
        ImageView imageView = new ImageView(context);
        //加载网络图片
        Glide.with(context).load(imageUrls[position])
                    .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        mList.add(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    public String[] getImageUrls() {
        return imageUrls;
    }
}
