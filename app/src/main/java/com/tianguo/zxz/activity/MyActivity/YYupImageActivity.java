package com.tianguo.zxz.activity.MyActivity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tianguo.zxz.R;
import com.tianguo.zxz.base.BaseActivity;
import com.tianguo.zxz.bean.YYdetallBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by lx on 2017/8/15.
 */

public class YYupImageActivity extends BaseActivity {

    private ImageView im;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_yyup_image;
    }

    @Override
    protected void initView() {
//带配置
        im = (ImageView)findViewById(R.id.iv_add_imge);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryFinal.openGalleryMuti(12,3,  mOnHanlderResultCallback);
            }
        });


    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                upimage(resultList);
                Glide.with(YYupImageActivity.this).load(resultList.get(0).getPhotoPath()).into(im);
            }
        }
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            LogUtils.e(errorMsg);
        }
    };
    public void upimage(List<PhotoInfo> list) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型;
        for (int i =0;i<list.size();i++){
            File file = new File( list.get(i).getPhotoPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), requestFile);
        }
        builder.addFormDataPart("sso", SharedPreferencesUtil.getSSo(this));
        builder.addFormDataPart("id",getIntent().getIntExtra("id", -1)+"");
        List<MultipartBody.Part> parts = builder.build().parts();
        Observable observable = RetroFactory.getInstance().UPimage(parts);
        observable.compose(composeFunction).subscribe(new BaseObserver<YYdetallBean>(this, pd) {
            @Override
            protected void onHandleSuccess(YYdetallBean yYdetallBean) {

            }

        });
    }

    @Override
    protected void initData() {

    }
}
