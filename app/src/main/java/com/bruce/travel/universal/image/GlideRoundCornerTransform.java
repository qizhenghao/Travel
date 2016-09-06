package com.bruce.travel.universal.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.bruce.travel.universal.utils.Methods;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by sunfusheng on 16/4/6.
 */
public class GlideRoundCornerTransform extends BitmapTransformation {

    private static float mRadius = Methods.computePixelsWithDensity(14);

    public GlideRoundCornerTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundedCorner(pool, toTransform);
    }

    private static Bitmap roundedCorner(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(result);
        /**
         * 首先绘制圆角矩形
         */
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
