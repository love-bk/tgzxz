package com.tianguo.zxz.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tianguo.zxz.R;

public class ProgressWebView extends WebView {
  
    private static ProgressBar progressbar;
  
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        progressbar = new ProgressBar(context, null,  
                android.R.attr.progressBarStyleHorizontal);  
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,  
                10, 0, 0));  
  
        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);  
        addView(progressbar);
    }  
  
    public static class WebChromeClient extends android.webkit.WebChromeClient {
        @Override  
        public void onProgressChanged(WebView view, int newProgress) {  
            if (newProgress == 100) {  
                progressbar.setVisibility(GONE);  
            } else {  
                if (progressbar.getVisibility() == GONE)  
                    progressbar.setVisibility(VISIBLE);  
                progressbar.setProgress(newProgress);  
            }  
            super.onProgressChanged(view, newProgress);  
        }  
  
    }  
  
    @Override  
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {  
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();  
        lp.x = l;  
        lp.y = t;  
        progressbar.setLayoutParams(lp);  
        super.onScrollChanged(l, t, oldl, oldt);  
    }  
}     