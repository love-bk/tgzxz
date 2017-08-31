package com.tianguo.zxz.uctils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipFile;

/**
 * Created by lx on 2017/5/12.
 */

public class writeApk {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeApks(File file, String comment) {
        ZipFile zipFile = null;
        ByteArrayOutputStream outputStream = null;
        RandomAccessFile accessFile = null;
        try {
            zipFile = new ZipFile(file);
            String zipComment = zipFile.getComment();
            if (zipComment != null) {
                return;
            }

            byte[] byteComment = comment.getBytes();
            outputStream = new ByteArrayOutputStream();

            outputStream.write(byteComment);
            outputStream.write(short2Stream((short) byteComment.length));
            byte[] data = outputStream.toByteArray();
            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(file.length() - 2);
            accessFile.write(short2Stream((short) data.length));
            accessFile.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (Exception e) {

            }

        }
    }



    public static String getPackagePath(Context context) {
        if (context != null) {
            return context.getPackageCodePath();
        }
        return null;
    }
    public static String readApk(File file) {
        byte[] bytes = null;
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");
            long index = accessFile.length();

            bytes = new byte[2];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            int contentLength = stream2Short(bytes, 0);

            bytes = new byte[contentLength];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            return new String(bytes, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * short转换成字节数组（小端序）
     * @return
     */
    private static short stream2Short(byte[] stream, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(stream[offset]);
        buffer.put(stream[offset + 1]);
        return buffer.getShort(0);
    }

    /**
     * 字节数组转换成short（小端序）
     *
     * @return
     */
    private static byte[] short2Stream(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(data);
        buffer.flip();
        return buffer.array();
    }

}
