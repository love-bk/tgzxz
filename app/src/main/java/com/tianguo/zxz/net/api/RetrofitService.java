package com.tianguo.zxz.net.api;


import com.tianguo.zxz.bean.BIndBean;
import com.tianguo.zxz.bean.DiscipleBean;
import com.tianguo.zxz.bean.DuiBaBean;
import com.tianguo.zxz.bean.EastHeadlineBean;
import com.tianguo.zxz.bean.FanKuiBean;
import com.tianguo.zxz.bean.HeilCentBean;
import com.tianguo.zxz.bean.HelpListsBean;
import com.tianguo.zxz.bean.IncomeDetailBean;
import com.tianguo.zxz.bean.InviteIncomeBean;
import com.tianguo.zxz.bean.JiangLiBean;
import com.tianguo.zxz.bean.LoginBean;
import com.tianguo.zxz.bean.LookBean;
import com.tianguo.zxz.bean.MYBean;
import com.tianguo.zxz.bean.MyGGbean;
import com.tianguo.zxz.bean.NewXQBean;
import com.tianguo.zxz.bean.NewsDataBean;
import com.tianguo.zxz.bean.NewsListBean;
import com.tianguo.zxz.bean.QiandaoBean;
import com.tianguo.zxz.bean.ReCiBean;
import com.tianguo.zxz.bean.RecodBean;
import com.tianguo.zxz.bean.SignBean;
import com.tianguo.zxz.bean.SoDaoBean;
import com.tianguo.zxz.bean.StudentListBean;
import com.tianguo.zxz.bean.TaskStateBean;
import com.tianguo.zxz.bean.VersionInfo;
import com.tianguo.zxz.bean.VideoModel;
import com.tianguo.zxz.bean.YYListBean;
import com.tianguo.zxz.bean.YYdetallBean;
import com.tianguo.zxz.net.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {
    /**
     * 首页获取新闻列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/gold/list.do")
    Observable<BaseEntity<NewsListBean>>getNewsList(@FieldMap Map<String, Object> map);
    /**
     * 详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/gold/detail.do")
    Observable<BaseEntity<NewXQBean>> gitNewsCent(@FieldMap Map<String, Object> map);
    /**
     * 新闻奖励
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/gold/award.do")
    Observable<BaseEntity<ReCiBean>> getNewsAwrad(@FieldMap Map<String, Object> map);
    /**
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/redbag/award/get.do")
    Observable<BaseEntity<NewXQBean>> getNewsAwradxp(@FieldMap Map<String, Object> map);

    /**
     * 詳情頁list
     */

    @FormUrlEncoded
    @POST("api/app/news/ad.do")
    Observable<BaseEntity<NewsDataBean>> getNewsDataList(@FieldMap Map<String, Object> map);
    /**
     * 更新
     *
     */
    @FormUrlEncoded
    @POST("api/app/update.do")
    Observable<BaseEntity<VersionInfo>> getGengxin(@FieldMap Map<String, Object> map);
    /**
     * denglu
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/login/mobile.do")
    Observable<BaseEntity <LoginBean>> getLogin(@FieldMap Map<String, Object> map);
    /**
     * 个人
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/user/my.do")
    Observable<BaseEntity <MYBean>> getME(@FieldMap Map<String, Object> map);
    /**
     * d提现
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/withdraw.do")
    Observable<BaseEntity<VersionInfo>> getTIXIAN(@FieldMap Map<String, Object> map);
    /**
     * d验证
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/valid/code.do")
    Observable<BaseEntity<MYBean>> getSMS(@FieldMap Map<String, Object> map);
    /**
     *
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/register/code.do")
    Observable<BaseEntity<MYBean>> getNewSMS(@FieldMap Map<String, Object> map);
    /**
     * 第三方登陆
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/login.do")
    Observable<BaseEntity<LoginBean>> getLogingThree(@FieldMap Map<String, Object> map);
    /**
     * 输入输入邀请码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/code.do")
    Observable<BaseEntity<LoginBean>> getYaoQingnum(@FieldMap Map<String, Object> map);
    /**
     * 获取邀请码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/index.do")
    Observable<BaseEntity <YYListBean>> getYaoNum(@FieldMap Map<String, Object> map);
    /**
     * 可领取的奖励
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/award.do")
    Observable<BaseEntity <JiangLiBean>> getJiangLI(@FieldMap Map<String, Object> map);
    /**
     * 绑定手机号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/bind/mobile.do")
    Observable<BaseEntity <MYBean>> getBangd(@FieldMap Map<String, Object> map);
    /**
     * y驗證手機
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/wechat.do")
    Observable<BaseEntity <MYBean>> getYanzheng(@FieldMap Map<String, Object> map);
    /**
     * y绑定微信登陆
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/bind.do")
    Observable< BIndBean> getBind(@FieldMap Map<String, Object> map);
    /**
     * y帮助列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/help.do")
    Observable<BaseEntity<HelpListsBean>> getHelpList(@FieldMap Map<String, Object> map);
    /**
     * y帮助内容
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/help/detail.do")
    Observable<BaseEntity<HeilCentBean>> getHelpCent(@FieldMap Map<String, Object> map);

    /**
     * y攻略列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/guide.do")
    Observable<BaseEntity<FanKuiBean>> getFanKuiList(@FieldMap Map<String, Object> map);
    /**
     * y反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/guide/detail.do")
    Observable<BaseEntity<HeilCentBean>> getFanKuiCent(@FieldMap Map<String, Object> map);
    /**
     * y兑吧
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/duiba.do")
    Observable<BaseEntity<DuiBaBean>> getDuiBa(@FieldMap Map<String, Object> map);
    /**
     * 反馈QQ
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/feedback.do")
    Observable<BaseEntity<HeilCentBean>> getFankui(@FieldMap Map<String, Object> map);
    /**
     * 反馈後台
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/feedback/user.do")
    Observable<BaseEntity<MYBean>> getHanKui(@FieldMap Map<String, Object> map);
    /**
     *搜索导航接口
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/search.do")
    Observable<BaseEntity<SoDaoBean>> getSoDao(@FieldMap Map<String, Object> map);
    /**
     *点击搜索导航数据接口：

     * @return
     */
    @FormUrlEncoded
    @POST("api/app/click/earn.do")
    Observable<BaseEntity<LoginBean>> getManery(@FieldMap Map<String, Object> map);
    /**
     简单赚钱：

     点击进入广告赚钱数据接口：
     ：

     * @return
     */
    @FormUrlEncoded
    @POST("api/app/watch/earn.do")
    Observable<BaseEntity<LoginBean>> getOncliGG(@FieldMap Map<String, Object> map);
    /**
     简单赚钱：
     退出广告赚钱数据接口：
     ：

     * @return
     */
    @FormUrlEncoded
    @POST("api/app/click/ad.do")
    Observable<BaseEntity<ReCiBean>> getBackGG(@FieldMap Map<String, Object> map);
    /**
     简单赚钱：

     点击看看赚钱接口：

     * @return
     */
    @FormUrlEncoded
    @POST("api/app/click/check.do")
    Observable<BaseEntity<ReCiBean>> getSeeSee(@FieldMap Map<String, Object> map);
    /**
     *第三方广告
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/wosou/ad.do")
    Observable<BaseEntity<NewsListBean>> getBaiGD(@FieldMap Map<String, Object> map);
    /**
     * 获取自己广告
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/ad/news/list.do")
    Observable<BaseEntity<MyGGbean>> getMyGG(@FieldMap Map<String, Object> map);
    /**
     * 提现记录
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/withdraw/list.do")
    Observable<BaseEntity<MyGGbean>> getMoneyList(@FieldMap Map<String, Object> map);
    /**
     * 师徒列表
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/rank.do")
    Observable<BaseEntity<List<StudentListBean>>>getStudentList(@FieldMap Map<String, Object> map);
    /**
     * 头像
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/upload/head.do")
    Observable<BaseEntity<StudentListBean>>getInfo(@FieldMap Map<String, Object> map);
    /**
     * 个人信息
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/user/info.do")
    Observable<BaseEntity<StudentListBean>>getMyInfo(@FieldMap Map<String, Object> map);
    /**
     * 设置个人信息
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/user/set.do")
    Observable<BaseEntity<StudentListBean>>setMyinfo(@FieldMap Map<String, Object> map);
//    /**
//     *cloine搜索
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/app/search.do")
////    Observable<BaseEntity<MYBean>> getSoDao(@FieldMap Map<String, Object> map);

//    http://10.0.0.44:8082/api/app/taskDownload/list.do
    /**
     * 获取下载列表
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/list.do")
    Observable<BaseEntity<List<YYListBean>>> getYYList(@FieldMap Map<String, Object> map);
    /**
     * 获取下载奖励
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/download/add.do")
    Observable<BaseEntity<YYListBean>> getYYAdd(@FieldMap Map<String, Object> map);
    /**
     * 获取任务xiangqing
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/detail.do")
    Observable<BaseEntity<YYdetallBean>> getYYAddXq(@FieldMap Map<String, Object> map);

    /**
     * 上传图片
     * @return
     */
    @Multipart
    @POST("api/app/task/approve.do")
    Observable<BaseEntity<YYdetallBean>> UPimage(@Part List<MultipartBody.Part> parts);

    /**
     * 是否本地下载
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/download.do")
    Observable<BaseEntity<YYdetallBean>> getDown(@FieldMap Map<String, Object> map);
    /**
     * 开始任务
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/start.do")
    Observable<BaseEntity<YYdetallBean>> getStartTime(@FieldMap Map<String, Object> map);
    /**
     * 结束任务
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/end.do")
    Observable<BaseEntity<YYdetallBean>> getEndTime(@FieldMap Map<String, Object> map);
    /**
     * 获取热词，搜索，奖励
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/ad/award.do")
    Observable<BaseEntity<JiangLiBean>> getAward(@FieldMap Map<String, Object> map);
    /**
     * 获取签到天数
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/sign/index.do")
    Observable<BaseEntity<QiandaoBean>> getQianDao(@FieldMap Map<String, Object> map);
    /**
     * 签到
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/sign.do")
    Observable<BaseEntity<SignBean>> setQianDao(@FieldMap Map<String, Object> map);
     /**
     * 完成任务
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/done.do")
    Observable<BaseEntity<JiangLiBean>> doneTask(@FieldMap Map<String, Object> map);
    /**
     * 领取奖励
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/award.do")
    Observable<BaseEntity<JiangLiBean>> getReward(@FieldMap Map<String, Object> map);
    /**
     * 获取最近提现记录
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/withdraw/recent.do")
    Observable<BaseEntity<RecodBean>> getRecords(@FieldMap Map<String, Object> map);

    /**
     * 页面开关数据
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/sys/switch.do")
    Observable<Object> getNavList(@FieldMap Map<String, Object> map);
    /**
     * 获取收支明细
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/money/record.do")
    Observable<BaseEntity<IncomeDetailBean>> getIncomeDetail(@FieldMap HashMap<String, Object> map);
 /**
     * 获取热词
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/word.do")
    Observable<BaseEntity<ReCiBean>> getWords(@FieldMap HashMap<String, Object> map);
/**
     * 视频
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/video/list.do")
    Observable<BaseEntity<VideoModel>> getVideoData(@FieldMap HashMap<String, Object> map);
    /**
     * 注册新用户
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/login/register.do")
    Observable<BaseEntity<LoginBean>> getNewsInfo(@FieldMap HashMap<String, Object> map);
    /**
     * 账号密码登陆
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/login/password.do")
    Observable<BaseEntity <LoginBean>> getNewsLogin(@FieldMap Map<String, Object> map);
    /**
     * 忘记密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/login/password/find.do")
    Observable<BaseEntity <LoginBean>> getNewPassWordInfo(@FieldMap Map<String, Object> map);
    /**
     * 修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/password/edit.do")
    Observable<BaseEntity <LoginBean>> getModifyPassWordInfo(@FieldMap Map<String, Object> map);
    /**
     *获取简单赚钱中已看次数和总共可以看的次数
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/click/count.do")
    Observable<BaseEntity<LookBean>> getLookNum(@FieldMap Map<String, Object> map);


    /**
     *获取新闻分类
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/type.do")
    Observable<BaseEntity<NewsListBean>> getNavTags(@FieldMap Map<String, Object> map);


    /**
     *设置用户默认的新闻分类
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/type/set.do")
    Observable<BaseEntity<NewsListBean>> setMyChannel(@FieldMap Map<String, Object> map);


    /**
     *获取东方头条新闻
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/news/eastnews.do")
    Observable<BaseEntity<EastHeadlineBean>> getEastHeadline(@FieldMap Map<String, Object> map);
    /**
     *获取东方头条新闻
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/task/newbie.do")
    Observable<BaseEntity<TaskStateBean>> getTaskState(@FieldMap Map<String, Object> map);

    /**
     *获取邀请收入
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/income.do")
    Observable<BaseEntity<InviteIncomeBean>> getInviteIncome(@FieldMap Map<String, Object> map);

    /**
     *获取邀请收入
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/follow.do")
    Observable<BaseEntity<DiscipleBean>> getFollow(@FieldMap Map<String, Object> map);

    /**
     *获取邀请风云榜
     * @return
     */
    @FormUrlEncoded
    @POST("api/app/invite/income/list.do")
    Observable<BaseEntity<DiscipleBean>> getInviteList(@FieldMap Map<String, Object> map);




}
