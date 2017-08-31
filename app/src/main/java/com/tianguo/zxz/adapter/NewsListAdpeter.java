package com.tianguo.zxz.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.iflytek.voiceads.NativeADDataRef;
import com.tianguo.zxz.MainActivity;
import com.tianguo.zxz.R;
import com.tianguo.zxz.activity.GGWed;
import com.tianguo.zxz.activity.ReWebActivity;
import com.tianguo.zxz.activity.WebListActivity;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.NewsListBean;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.fragment.homefragment.NewsListFragment;
import com.tianguo.zxz.natives.NativeAdInfo;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.DownGGUtils;
import com.tianguo.zxz.uctils.DowonGgDilaog;
import com.tianguo.zxz.uctils.GetAwardUtils;
import com.tianguo.zxz.uctils.LogUtils;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;

import static com.tianguo.zxz.Flat.ShenMaUrl;


/**
 * Created by lx on 2017/4/19.
 */

public class NewsListAdpeter extends BaseAdapter {
    private HotWordAdapter gcSo;
   private MainActivity main;
    private List<Object> data;
    private  HashMap<String, String> map = new HashMap<>();
    private   ArrayList<ReCiBean.ListBean> datas = new ArrayList<>();
    private NewsListFragment fragment;
    private static final int ItemViewTypeCount = 2; // Listview的item类型数量
    private static final int DEFAULT_TYPE = 0; // 默认类型
    private DowonGgDilaog dilaog;
    private String[] split;
    public void setScoll(boolean scoll) {
        isScoll = scoll;
    }
    private  boolean isScoll = false;
    private int pon = 0;
    private static final int AD_TYPE = 1; // 广告类型22
    int ps = 0;
    private  List<Object> asb;
    int  pos = 5;

    private int id;
    //储存展现热词的位置\
    private    ArrayList<Integer> listreci = new ArrayList<>();
    private   List<Integer> integers = new ArrayList<>();
    private  Intent intent;
    private   List<MyGGbean.Cpa3Bean>myGG;
    public NewsListAdpeter(NewsListFragment fragment, MainActivity main, List<Object> data, List<Object> asb, int position,List<MyGGbean.Cpa3Bean>myGG) {
        this.main = main;
        this.data = data;
        this.fragment = fragment;
        this.ps = position;
        this.myGG=myGG;

        intent = new Intent(main, GGWed.class);
        this.asb = asb;
        if (integers != null && integers.size() != 0) {
            integers.clear();
        }

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }


    public boolean isAdPosition(int position) {
        if (position == 0) {
            return false;
        } else {
            if (position == 1) {
                return true;
            }
            return position % 5 == 0;

        }
    } // 加载广告


    @SuppressLint("WrongConstant")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try{

            //若是广告位置，则请求广告
            if (isAdPosition(position)) {
                if (ps!=7){
                    loadAd(position);
                }else {

                    if (position==1&&asb.size()<1) {
                        fragment.getfeed();

                    }else {
                        if (position / pos- 1 >= asb.size()){
                            fragment.getfeed();

                        }
                    }

                }
            }
            int type = getItemViewType(position);
            Object dataws = data.get(position);
            switch (type) {
                case AD_TYPE:
                    LogUtils.e(ps+"ssssssss");
                    // 广告数据
                    NewsListFragment.IFLYAd iflyAd = (NewsListFragment.IFLYAd) dataws;
                    // 广告容器
                    RelativeLayout adContainer = (RelativeLayout) View.inflate(main, R.layout.nativelistitem, null);
                    // 保存广告容器
                    iflyAd.adContainer = adContainer;
                    // 加载并显示广告内容
                    showAD(new AQuery(adContainer), iflyAd.aditem, position);
                    return adContainer;
                case DEFAULT_TYPE:
                    final ViewHolder viewHolder;
                    final NewsListBean.NewsBean bean = (NewsListBean.NewsBean) dataws;
                    if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = LayoutInflater.from(main).inflate(R.layout.adapter_news_list, null);
                        viewHolder.vv_tv_news_texts_zhezhao = convertView.findViewById(R.id.vv_tv_news_texts_zhezhao);
                        viewHolder.in_my_gg = (RelativeLayout) convertView.findViewById(R.id.in_my_gg);
                        viewHolder.tvNewsImage = (ImageView) convertView.findViewById(R.id.tv_news_image);
                        viewHolder.ivCentGg = (TextView) convertView.findViewById(R.id.iv_cent_gg);
                        viewHolder.gvCentGg = (ImageView) convertView.findViewById(R.id.gv_cent_gg);
                        viewHolder.iv_isshou_hongbao = (ImageView) convertView.findViewById(R.id.iv_isshou_hongbao);
                        viewHolder.iv_isshou_hongbao_duo = (ImageView) convertView.findViewById(R.id.iv_isshou_hongbao_duo);
                        viewHolder.tvNewsText = (TextView) convertView.findViewById(R.id.tv_news_text);
                        viewHolder.llNewsOne = (RelativeLayout) convertView.findViewById(R.id.ll_news_one);
                        viewHolder.tvNewsTexts = (TextView) convertView.findViewById(R.id.tv_news_texts);
                        viewHolder.gcNewsImag = (GridView) convertView.findViewById(R.id.gc_news_imag);
                        viewHolder.v_start = (LinearLayout) convertView.findViewById(R.id.v_start);
                        viewHolder.llNewsDuo = (RelativeLayout) convertView.findViewById(R.id.ll_news_duo);
                        viewHolder.gv_cent_shishi = (GridView) convertView.findViewById(R.id.gv_cent_shishi);
                        viewHolder.gv_cent_layout = (LinearLayout) convertView.findViewById(R.id.gv_cent_layout);
                        viewHolder.pix = (RelativeLayout) convertView.findViewById(R.id.icon_and_blurb);
                        viewHolder.teile = (TextView) convertView.findViewById(R.id.teile);
                        viewHolder.content = (TextView) convertView.findViewById(R.id.content);
                        viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
                        viewHolder.bigim = (ImageView) convertView.findViewById(R.id.im_bit);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }
                    //创建如果有图片就切割添加图片集
                    if (ps == 3) {
                        viewHolder.llNewsDuo.setVisibility(View.VISIBLE);
                        viewHolder.iv_isshou_hongbao_duo.setVisibility(View.GONE);
                        Spanned spanned = Html.fromHtml(bean.getBody());
                        viewHolder.gcNewsImag.setVisibility(View.GONE);
                        viewHolder.tvNewsTexts.setText(spanned);
                    } else {
                        final String body = bean.getThumb();
                        if (body != null && !body.isEmpty()) {
                            split = body.split("[,]");
                        }
                        main.setScllorlistner(new MainActivity.OnScllorListner() {
                            @Override
                            public void isBoutto(int postion) {
                                try {
                                    if (data.get(postion) instanceof NewsListBean.NewsBean) {
                                        NewsListBean.NewsBean o = (NewsListBean.NewsBean) data.get(postion);
                                        o.setAward(0);
                                        if (null != adapaternumListe) {
                                            adapaternumListe.onAdapaterlistner();
                                        }
                                        NewsListAdpeter.this.notifyDataSetChanged();
                                    }

                                } catch (Exception ee) {

                                }
                            }
                        });
                        viewHolder.vv_tv_news_texts_zhezhao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(main, WebListActivity.class);
                                LogUtils.e(bean.getAward() + "ssss");
                                intent.putExtra("award", bean.getAward());
                                NewsListAdpeter.this.notifyDataSetChanged();
                                intent.putExtra("id", bean.getI());
                                intent.putExtra("award", bean.getAward());
                                intent.putExtra("position", position);
                                intent.putExtra("ps", ps);
                                main.startActivityForResult(intent, 204);
                                notifyDataSetChanged();
                            }
                        });
                        viewHolder.v_start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                map.put("tepy", bean.getTitle());
                                MobclickAgent.onEvent(main, "click_banner", map);
                                Intent intent = new Intent(main, WebListActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("award", bean.getAward());
                                intent.putExtra("ps", ps);
                                intent.putExtra("id", bean.getI());
                                main.startActivityForResult(intent, 204);
                                LogUtils.e(bean.getAward() + "ssss");
                                NewsListAdpeter.this.notifyDataSetChanged();
                            }
                        });
//            判断图片集是否为空如果有就展示
                        if ( split.length == 3) {
                            viewHolder.llNewsDuo.setVisibility(View.VISIBLE);
                            viewHolder.llNewsOne.setVisibility(View.GONE);
                            viewHolder.gcNewsImag.setAdapter(new gcAdapeter(split));
                            viewHolder.tvNewsTexts.setText(bean.getTitle());
                            if (bean.getAward() != 0) {
                                viewHolder.iv_isshou_hongbao_duo.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.iv_isshou_hongbao_duo.setVisibility(View.GONE);
                            }
                        } else if ( split.length == 1) {
                            viewHolder.llNewsDuo.setVisibility(View.GONE);
                            viewHolder.llNewsOne.setVisibility(View.VISIBLE);
                            try {
                                Glide.with(main).load(split[0]).placeholder(R.mipmap.img_bg).centerCrop().override(100,100).into(viewHolder.tvNewsImage);

                            } catch (Exception e) {

                            }
                            viewHolder.tvNewsText.setText(bean.getTitle());
                            if (bean.getAward() != 0) {
                                viewHolder.iv_isshou_hongbao.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.iv_isshou_hongbao.setVisibility(View.GONE);
                            }
                        }
                    }
                    viewHolder.in_my_gg.setVisibility(View.GONE);
                    if (position != 0 && position % 7 == 0&&position/7-1<myGG.size()) {
                        viewHolder.in_my_gg.setVisibility(View.VISIBLE);
                        viewHolder.ivCentGg.setText(myGG.get(position/7-1).getN());
                        Glide.with(main).load(myGG.get(position/7-1).getP()).placeholder(R.mipmap.img_bg).centerCrop().into(viewHolder.gvCentGg);
                        viewHolder.in_my_gg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(myGG.get(position/7-1).getU())) {
                                    if (dilaog == null) {
                                        dilaog = new DowonGgDilaog(main, R.style.dialog, new DowonGgDilaog.OnDownLoadListner() {
                                            @Override
                                            public void istrue() {
                                                Intent service = new Intent(main, DownGGUtils.class);
                                                service.putExtra("downloadurl", myGG.get(position/7-1).getW());
                                                service.putExtra("teile", myGG.get(position/7-1).getA());
                                                service.putExtra("descrption", myGG.get(position/7-1).getN());
                                                main.startService(service);
                                                dilaog.dismiss();
                                            }

                                            @Override
                                            public void isflas() {
                                                dilaog.dismiss();
                                            }
                                        });
                                    }
                                    dilaog.show();
                                } else {
                                    Intent intent = new Intent(main, GGWed.class);
                                    intent.putExtra("url", myGG.get(position/7-1).getU());
                                    main.startActivity(intent);
                                }
                            }
                        });
                    }
                    viewHolder.pix.setVisibility(View.GONE);
                    //判断什么时候显示百度广告
                    if (isAdPosition(position)&&ps==7) {
                        if (asb==null||asb.size()==0){
                            viewHolder.pix.setVisibility(View.GONE);
                        } else {
                            if (position==1){
                                pos=1;
                            }else {
                                pos=5;
                            }
                            if (position / pos- 1 > asb.size()&&position!=1) {
                                viewHolder.pix.setVisibility(View.GONE);
                            } else {
                                try {
                                    viewHolder.pix.setVisibility(View.VISIBLE);
                                    final NativeAdInfo asdbean = (NativeAdInfo) asb.get(position / pos - 1);
                                    if (!TextUtils.isEmpty(asdbean.getImageUrl())) {
                                        Glide.with(main).load(asdbean.getImageUrl()).placeholder(R.mipmap.img_bg).centerCrop().into(viewHolder.bigim);
                                    }
                                    Glide.with(main).load(asdbean.getIconUrl()).placeholder(R.mipmap.img_bg).override(100,100).centerCrop().into(viewHolder.photo);
                                    viewHolder.content.setText(asdbean.getDescription());
                                    viewHolder.pix.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            asdbean.onClick(new View(main));
                                        }
                                    });
                                } catch (Exception e) {
                                    viewHolder.pix.setVisibility(View.GONE);

                                }
                            }
                        }

                    }

//判断显示什么搜索
                    if (isShenMa(position)) {
                        if (listreci.get(position) != 201) {
                            gcSo = new HotWordAdapter(main, datas,true);
                            viewHolder.gv_cent_shishi.setAdapter(gcSo);
                            getWords(position);
                        }
                        viewHolder.gv_cent_shishi.setAdapter(gcSo);
                        viewHolder.gv_cent_layout.setVisibility(View.VISIBLE);
                        gcSo.setOnSoListner(new HotWordAdapter.OnSoListner() {
                            @Override
                            public void onsoListner(String s,int position) {
                                Intent intent = new Intent(main, ReWebActivity.class);
                                intent.putExtra("urls", ShenMaUrl + s);
                                main.startActivity(intent);
                            }
                        });

                    } else {
                        viewHolder.gv_cent_layout.setVisibility(View.GONE);
                    }
            }


            if (convertView==null){
                return LayoutInflater.from(main).inflate(R.layout.adapter_news_list, null);
            }else {
                return convertView;

            }
        }catch (Exception e){

        }
        return convertView;
    }


    public void getWords(final int posin) {
        if (posin == id) {
            return;
        }
        id = posin;
        if (datas != null && datas.size() > 0) {
            datas.clear();
        }
        main.setLoadingFlag(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        map.put("num",4);
        Observable words = RetroFactory.getInstance().getWords(map);
        words.compose(main.composeFunction).subscribe(new BaseObserver<ReCiBean>(main, main.pd) {
            @Override
            protected void onHandleSuccess(ReCiBean bean) {
                if (bean != null){
                    gcSo.addAll(bean.getList());
                    listreci.add(posin, 201);
                }

            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
            }
        });


    }

    public boolean isBaiduItme(int potion) {
        if (potion != 0 && potion % 6 == 0) {
            return true;
        }
        return false;
    }


    class ViewHolder {
        TextView ivCentGg;
        ImageView gvCentGg;
        RelativeLayout in_my_gg;
        LinearLayout gv_cent_layout;
        ImageView tvNewsImage;
        TextView tvNewsText;
        RelativeLayout llNewsOne;
        TextView tvNewsTexts;
        GridView gcNewsImag;
        LinearLayout v_start;
        RelativeLayout llNewsDuo;
        GridView gv_cent_shishi;
        ImageView iv_isshou_hongbao;
        ImageView iv_isshou_hongbao_duo;
        View vv_tv_news_texts_zhezhao;
        RelativeLayout pix;
        TextView teile;
        TextView content;
        ImageView photo;
        ImageView bigim;
    }


    private class gcAdapeter extends BaseAdapter {
        String[] arryList;
        int id;

        public gcAdapeter(String[] arryList) {
            this.arryList = arryList;
            this.id = id;
        }

        @Override
        public int getCount() {
            return  split.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoders ho = null;
            if (convertView == null) {
                ho = new ViewHoders();
                convertView = View.inflate(main, R.layout.itme_image, null);
                ho.viewById = (ImageView) convertView.findViewById(R.id.iv_item_image);
                convertView.setTag(ho);
            } else {
                ho = (ViewHoders) convertView.getTag();
            }
            Glide.with(main).load(arryList[position]).override(100,100).placeholder(R.mipmap.img_bg).centerCrop().into(ho.viewById);
            return convertView;
        }

        class ViewHoders {
            ImageView viewById;
        }
    }


    //热词
    public boolean isShenMa(int position) {
        if (position == 0) {
            return false;
        } else {
            listreci.add(position);
            return position % 10 == 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return ItemViewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            // 如果是IFLYAd类型数据，view类型为AD_TYPE
            if (data.get(position) instanceof NewsListFragment.IFLYAd) {
                return AD_TYPE;
            }
        } catch (Exception e) {
            return 0;

        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 加载广告
    public void loadAd(int position) {
        if (!fragment.requested.get(position, false)) {
            fragment.requested.put(position, true);
            fragment.iflyAds.add(fragment.new IFLYAd(position));
        }
    }

    // 展示广告
    private void showAD(AQuery $, final NativeADDataRef adItem, final int position) {
        $.id(R.id.ad_source_mark).text(adItem.getAdSourceMark());
        $.id(R.id.img_poster).image(adItem.getImage(), false, true);
        $.id(R.id.text_name).text((String) adItem.getTitle());
        $.id(R.id.rl_nate_man).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adItem.onClicked(view);
                if (SharedPreferencesUtil.getFeed(main) != 2) {
                    GetAwardUtils.getAward(3, System.currentTimeMillis() + "", main, new GetAwardUtils.GetAwardListner() {
                        @Override
                        public void getAward(double m) {
                            SharedPreferencesUtil.saveFeed(main, SharedPreferencesUtil.getFeed(main) + 1);
                            Random random = new Random();
                            int i = random.nextInt(2);
                            if (i == 2) {
                                GetAwardUtils.getAward(4, System.currentTimeMillis() + "", main, new GetAwardUtils.GetAwardListner() {
                                    @Override
                                    public void getAward(double m) {

                                    }

                                    @Override
                                    public void nullAward() {

                                    }
                                });
                            }

                        }

                        @Override
                        public void nullAward() {
                            SharedPreferencesUtil.saveFeed(main, 2);

                        }
                    });

                }
            }
        });
    }


    OnAdapaernumLisnte adapaternumListe;

    public void setOnAdapaternumListe(OnAdapaernumLisnte adapaternumListe) {
        this.adapaternumListe = adapaternumListe;

    }

    //更新總數毀掉
    public interface OnAdapaernumLisnte {
        void onAdapaterlistner();
    }


}
