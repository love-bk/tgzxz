package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by lx on 2017/7/20.
 */

public class EditTextUtil {
    public void set(final EditText et, final String msg) {
     final String   regular   ="[a-zA-Z\\d]*";
        et.addTextChangedListener(new TextWatcher() {
            String before = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                before = s.toString();
            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(regular) && !"".equals(s.toString())) {
                    et.setText(before);
                    et.setSelection(et.getText().toString().length());
                    if (msg != null) {
                        Toast.makeText(et.getContext(), msg, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }

}

