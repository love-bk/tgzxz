package com.tianguo.zxz.uctils;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * Created by lx on 2017/5/17.
 */

public   class PhoneCodeUtiles {
    public  static  void  PhoneNum(EditText...view){
        for ( int i =0;i<view.length;i++){
            view[i].setInputType(EditorInfo.TYPE_CLASS_PHONE);
        }
    }
}
