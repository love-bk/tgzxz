package com.tianguo.zxz.fragment.threefragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianguo.zxz.R;
import com.tianguo.zxz.adapter.MyStudentAdapter;
import com.tianguo.zxz.base.BaseFragment;
import com.tianguo.zxz.bean.DiscipleBean;
import com.tianguo.zxz.net.BaseObserver;
import com.tianguo.zxz.net.RetroFactory;
import com.tianguo.zxz.uctils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;


/**
 * Created by lx on 2017/5/17.
 */

public class MyStudentFrgament extends BaseFragment {
    @BindView(R.id.ll_student_tab)
    LinearLayout llstudenttab;
    @BindView(R.id.tv_studeng_ranking_2)
    TextView tvStudengRanking2;
    @BindView(R.id.tv_studeng_ranking_2_money)
    TextView tvStudengRanking2Money;
    @BindView(R.id.tv_studeng_ranking_1)
    TextView tvStudengRanking1;
    @BindView(R.id.tv_studeng_ranking_1_money)
    TextView tvStudengRanking1Money;
    @BindView(R.id.tv_studeng_ranking_3)
    TextView tvStudengRanking3;
    @BindView(R.id.tv_studeng_ranking_3_money)
    TextView tvStudengRanking3Money;
    @BindView(R.id.my_list_student)
    RecyclerView myListStudent;
    List<DiscipleBean.FollowBean> data;
    int i = 1;
    private MyStudentAdapter myStudentAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        i = 1;
        data = new ArrayList<>();
        myStudentAdapter = new MyStudentAdapter(main, data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myListStudent.setLayoutManager(linearLayoutManager);
        myListStudent.setAdapter(myStudentAdapter);
        GetStudentList();
    }


    /**
     * 获取邀请排行榜
     */
    private void GetStudentList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sso", SharedPreferencesUtil.getSSo(main));
        Observable logins = RetroFactory.getInstance().getInviteList(map);
        logins.compose(main.composeFunction).subscribe(new BaseObserver<DiscipleBean>(main, main.pd) {
            @SuppressLint("WrongConstant")
            @Override
            protected void onHandleSuccess(final DiscipleBean bean) {
                if (bean != null && bean.getFollow() != null && bean.getFollow().size() > 0) {
                    List<DiscipleBean.FollowBean> followBeenList = bean.getFollow();
                    if (followBeenList.size() >= 1) {
                        DiscipleBean.FollowBean followBean = followBeenList.get(0);
                        tvStudengRanking1Money.setVisibility(View.VISIBLE);
                        tvStudengRanking1.setText(followBean.getFollower() + "\n" + followBean.getUserPhone());
                        double money = followBean.getMoney() / 100.0;
                        tvStudengRanking1Money.setText(money + "元");
                    }
                    if (followBeenList.size() >= 2) {
                        DiscipleBean.FollowBean followBean = followBeenList.get(1);
                        tvStudengRanking2Money.setVisibility(View.VISIBLE);
                        tvStudengRanking2.setText(followBean.getFollower() + "\n" + followBean.getUserPhone());
                        double money2 = followBean.getMoney() / 100.0;
                        tvStudengRanking2Money.setText(money2 + "元");
                    }
                    if (followBeenList.size() >= 3) {
                        DiscipleBean.FollowBean followBean = followBeenList.get(2);
                        tvStudengRanking3Money.setVisibility(View.VISIBLE);
                        tvStudengRanking3.setText(followBean.getFollower() + "\n" + followBean.getUserPhone());
                        double money3 = followBean.getMoney() / 100.0;
                        tvStudengRanking3Money.setText(money3 + "元");
                    }
                    if (followBeenList.size() > 3) {
                        if (llstudenttab.getVisibility() == View.GONE) {
                            llstudenttab.setVisibility(View.VISIBLE);
                        }
                        for (int j = 3; j < followBeenList.size(); j++) {
                            data.add(followBeenList.get(j));
                        }
                        myStudentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onHandleError(int code, String message) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three_my_student;
    }

}
