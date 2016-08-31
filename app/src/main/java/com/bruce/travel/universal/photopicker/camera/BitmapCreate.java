/*
 * Copyright (c) 2014-2015, KJFrameForAndroid 张涛 (kymjs123@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bruce.travel.universal.photopicker.camera;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 不会发生OOM的 BitmapFactory<br>
 * 
 * <b>创建时间</b> 2014-7-11
 * 
 * @author kymjs(kymjs123@gmail.com)
 * @version 1.1
 */
public class BitmapCreate {
    /**
     * 获取一个指定大小的bitmap
     * 
     * @param res
     *            Resources
     * @param resId
     *            图片ID
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {
        // BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        // options = BitmapHelper.calculateInSampleSize(options, reqWidth,
        // reqHeight);
        // return BitmapFactory.decodeResource(res, resId, options);

        // 通过JNI的形式读取本地图片达到节省内存的目的
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = res.openRawResource(resId);
        return bitmapFromStream(is, null, reqWidth, reqHeight);
    }

    /**
     * 获取一个指定大小的bitmap
     * 
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromFile(String pathName,
            int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return BitmapFactory.decodeFile(pathName);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);
            options = BitmapHelper.calculateInSampleSize(options,
                    reqWidth, reqHeight);
            return BitmapFactory.decodeFile(pathName, options);
        }
    }

    /**
     * 获取一个指定大小的bitmap
     * 
     * @param data
     *            Bitmap的byte数组
     * @param offset
     *            image从byte数组创建的起始位置
     * @param length
     *            the number of bytes, 从offset处开始的长度
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromByteArray(byte[] data, int offset,
            int length, int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return BitmapFactory
                    .decodeByteArray(data, offset, length);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, offset, length,
                    options);
            options = BitmapHelper.calculateInSampleSize(options,
                    reqWidth, reqHeight);
            return BitmapFactory.decodeByteArray(data, offset,
                    length, options);
        }
    }

    /**
     * 获取一个指定大小的bitmap<br>
     * 实际调用的方法是bitmapFromByteArray(data, 0, data.length, w, h);
     * 
     * @param is
     *            从输入流中读取Bitmap
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromStream(InputStream is,
            int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return BitmapFactory.decodeStream(is);
        } else {
            byte[] data = input2byte(is);
            return bitmapFromByteArray(data, 0, data.length,
                    reqWidth, reqHeight);
        }
    }

    /**
     * 输入流转byte[]<br>
     *
     * <b>注意</b> 你必须手动关闭参数inStream
     */
    public static final byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        }
        byte[] in2b = null;
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(swapStream);
        }
        return in2b;
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取一个指定大小的bitmap
     * 
     * @param is
     *            从输入流中读取Bitmap
     * @param outPadding
     *            If not null, return the padding rect for the bitmap if it
     *            exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
     *            is returned (null) then padding is unchanged.
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromStream(InputStream is,
            Rect outPadding, int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return BitmapFactory.decodeStream(is);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, outPadding, options);
            options = BitmapHelper.calculateInSampleSize(options,
                    reqWidth, reqHeight);
            return BitmapFactory
                    .decodeStream(is, outPadding, options);
        }
    }
}
