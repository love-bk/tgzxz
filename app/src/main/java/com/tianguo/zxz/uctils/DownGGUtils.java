package com.tianguo.zxz.uctils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.tianguo.zxz.dao.SQlishiUtils;

import java.io.File;

/**
 * Created by lx on 2017/6/8.
 */

public class DownGGUtils extends Service {
    private DownloadManager manager;
    private String url;
    private DownloadCompleteReceiver receiver;
    private String teile;
    private String descrption;
    private boolean isYY;
    private int yyId;
    private String packname;
    private SQlishiUtils sq;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//url="http://m.tianinfo.cn/tianguozixunzhuan_56_Majia_miaozhuanqianka_sign.apk";
        sq = new SQlishiUtils(this);
        url = intent.getStringExtra("downloadurl");
        teile = intent.getStringExtra("teile");
        descrption = intent.getStringExtra("descrption");
        //应用下载中用到的
        isYY = intent.getBooleanExtra("flag", false);
        yyId = intent.getIntExtra("yyId", 0);
        packname = intent.getStringExtra("packname");
        try {
            initDownManager();
        } catch (Exception e) {
            e.printStackTrace();
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent0 = new Intent(Intent.ACTION_VIEW, uri);
            intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent0);
        }

        return Service.START_NOT_STICKY;
    }

    @SuppressLint("WrongConstant")
    private void initDownManager() {

        try {
            receiver = new DownloadCompleteReceiver();
            manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            if (queryStatus()){
                LogUtils.e("进来了哈哈哈哈","gjj");
                ToastUtil.showMessage("正在下载中...");
                return;
            }
            ToastUtil.showMessage("开始下载");
            DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
            down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                    | DownloadManager.Request.NETWORK_WIFI);
            down.setAllowedOverRoaming(false);
            down.setMimeType("application/vnd.android.package-archive");
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            down.setVisibleInDownloadsUi(true);
            down.setDestinationInExternalPublicDir("", teile + ".apk");
            down.setTitle(teile);
            down.setDescription(descrption);
            long enqueue = manager.enqueue(down);
            SharedPreferencesUtil.saveDownId(this,url,enqueue);
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e) {
            SharedPreferencesUtil.removeDownId(this,url);
        }
    }

    private boolean queryStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        long downId = SharedPreferencesUtil.getDownId(this, url);
        if (downId ==-1)
            return false;
        query.setFilterById(downId);
        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            LogUtils.e("wwwwwwww");
            context.unregisterReceiver(this);
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                SharedPreferencesUtil.removeDownId(context,url);
                if (manager.getUriForDownloadedFile(downId) != null) {
                    installAPK(context, getRealFilePath(context, manager.getUriForDownloadedFile(downId)));
                    manager.remove(downId);
                } else {
                    Uri uri = Uri.parse(url);
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                }
                DownGGUtils.this.stopSelf();
            }
        }
    }

    private void installAPK(Context context, String path) {
        LogUtils.e(path);
        File file = new File(path);
        if (file.exists()) {
            openFile(file, context);
        } else {
            ToastUtil.showMessage("下载失败");
        }
    }
    public String getRealFilePath(Context context, Uri uri) {

        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 重点在这里
     */
    public void openFile(File var0, Context var1) {
        LogUtils.e(var0.toString());
        Intent var2 = new Intent(Intent.ACTION_VIEW);
        var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        var2.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(var1, "com.tianguo.zxz.fileprovider", var0);
            var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            var2.setDataAndType(uriForFile, var1.getContentResolver().getType(uriForFile));
        } else {
            var2.setDataAndType(Uri.fromFile(var0), "application/vnd.android.package-archive");
        }
        try {
            var1.startActivity(var2);

        } catch (Exception var5) {
            var5.printStackTrace();
            ToastUtil.showMessage("没有找到打开此类文件的程序");
        }
    }

}
