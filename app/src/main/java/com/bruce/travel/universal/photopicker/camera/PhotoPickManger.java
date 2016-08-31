package com.bruce.travel.universal.photopicker.camera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import com.bruce.travel.universal.photopicker.tools.PictureUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



/**
 * 图片选择器
 */
public final class PhotoPickManger {

    public interface OnProcessedPhotos {
        void onProcessed(List<File> list);
    }

    public interface OnPhotoPickFinsh {
        public void onPhotoPick(List<File> list);
    }


    /**
     * 模式
     */
    public enum Mode {
        /**
         * 系统相机
         */SYSTEM_CAMERA,
        /**
         * 系统图库
         */SYSTEM_IMGCAPTRUE,
        /**
         * 类似微信图库
         */AS_WEIXIN_IMGCAPTRUE
    }


    /**
     * 用于区别哪一个图片选择器
     */
    private static String currentPickMangerName;

    /**
     * 测试用
     */
    private boolean isDebugToast = false;

    /**
     * 用于区别其他图片选择器
     */
    private String name;


    public final String SAVE_STATIC_NAME = "save_currentPickMangerName";

    /**
     * 字段保存所已选择的图片
     */
    public final String SAVE_SELECTED_PHOTOS = "save_selected_photos";

    /**
     * 字段保存所拍照已选择的图片
     */
    public final String SAVE_CACHE_CAMERA = "save_cache_camera";

    /**
     * 字段保存所拍照是否选择裁剪
     */
    public final String SAVE_CACHE_IS_CUT = "save_cache_is_cut";


    /**
     * 字段保存待处理图片
     */
    public final String SAVE_CACHE_CUT_QUEUE = "save_cache_cut_queue";


    /**
     * 是否裁剪 只对系统相机和系统相册有效
     */
    private boolean isCut = false;
    /**
     * 是否缩略
     */
    private boolean isOptimize = false;

    /**
     * 设置返回最大图片数 对系统相机和相册调用无效
     * 默认1
     */
    private int returnFileCount = 1;


    private OnPhotoPickFinsh onPhotoPickFinsh;

    private File tempFile;

    private Handler handler = new Handler();

    /**
     * 已经选择的拍照图片
     */
    public ArrayList<File> selectsPhotos = new ArrayList<>();


    private Activity activity;

    /**
     * 用于缓存
     */
    public Bundle bundle;
    /**
     * 系统拍照标示code
     */
    private final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    /**
     * 系统相册标示code
     */
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    /**
     * 系统照片返回标示code
     */
    private final int PHOTO_REQUEST_CUT = 3;// 结果
    /**
     * 仿微信相册返回code
     */
    public final static int AS_WEIXIN_REQUEST_CODE = 4;

    /**
     * 如果多图待剪切则保存到该队列里
     */
    public ArrayList<File> willCutOfFileQueue = new ArrayList<>();


    /**
     * 图片缓存地址
     */
    public String cacheFilePath = "/img/";

    /**
     * 默认裁剪大小
     */
    public int defaultCutSize = 150;

    /**
     * 至少大于多少的图片进行处理
     */
    public int needProcessFileLength = (int) 0.5 * 1024 * 1024;

    /***
     * 构造方法
     *
     * @param name             为图片选择器默认一个别名 用来区别那一个选择器被选择了
     * @param activity
     * @param bundle           当系统内存不足时,重建时取出变量
     * @param onPhotoPickFinsh 图片选择成功时回调
     */
    public PhotoPickManger(String name, Activity activity, Bundle bundle, OnPhotoPickFinsh onPhotoPickFinsh) {
        this.onPhotoPickFinsh = onPhotoPickFinsh;
        this.name = name;
        this.activity = activity;
        this.bundle = bundle;

        if (bundle != null) {
            isCut = bundle.getBoolean(SAVE_CACHE_IS_CUT + "_" + name);
            currentPickMangerName = bundle.getString(SAVE_STATIC_NAME);
            willCutOfFileQueue = (ArrayList<File>) bundle.getSerializable(SAVE_CACHE_CUT_QUEUE + "name");
        }
    }

    /**
     * 处理掉重建时的缓存
     */
    public void flushBundle() {
        if (bundle != null) {
            if (isDebugToast) {
//                Toast.makeText(activity, "bundle is refresh", Toast.LENGTH_LONG).show();
            }
            selectsPhotos = (ArrayList<File>) bundle.getSerializable(SAVE_SELECTED_PHOTOS + "_" + name);
            if (selectsPhotos == null) {
                selectsPhotos = new ArrayList<>();
            }
            tempFile = (File) bundle.getSerializable(SAVE_CACHE_CAMERA + "_" + name);
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.length() > 0) {
                        if (!isCut) {
                            selectsPhotos.add(tempFile);
                        } else {
                            startPhotoZoom(Uri.fromFile(tempFile), defaultCutSize);
                        }
                        tempFile = null;
                    } else {
                        tempFile.delete();
                        tempFile = null;
                    }

                }
            }
            bundle.remove(SAVE_CACHE_CAMERA + "_" + name);
            if (!selectsPhotos.isEmpty()) {
                if (onPhotoPickFinsh != null) onPhotoPickFinsh.onPhotoPick(selectsPhotos);
            }

        }
    }

    /**
     * 保存变量
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        this.bundle = savedInstanceState;
        if (selectsPhotos != null && !selectsPhotos.isEmpty()) {
            savedInstanceState.putSerializable(SAVE_SELECTED_PHOTOS + "_" + name, selectsPhotos);
        }
        if (tempFile != null) {
            savedInstanceState.putSerializable(SAVE_CACHE_CAMERA + "_" + name, tempFile);
        }
        savedInstanceState.putBoolean(SAVE_CACHE_IS_CUT + "_" + name, isCut);
        savedInstanceState.putSerializable(SAVE_CACHE_CUT_QUEUE + "_" + name, willCutOfFileQueue);
        savedInstanceState.putSerializable(SAVE_STATIC_NAME, currentPickMangerName);
    }

    /**
     * 如果是单一拍照,在拍照前应该清理缓存
     */
    public void clearCache() {
        getSelectsPhotos().clear();
        tempFile = null;
        if (bundle != null) {
            bundle.remove(SAVE_SELECTED_PHOTOS + "_" + name);
            bundle.remove(SAVE_CACHE_CAMERA + "_" + name);
        }


    }

    /**
     * 生成一个临时的缓存文件
     */
    private File getFile() {
        File dir = new File(Environment.getExternalStorageDirectory().getPath()
                + cacheFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath() + cacheFilePath, getPhotoFileName());
        return file;
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * // 调用系统的拍照功能
     */
    private void startCamearPicCut() {
        tempFile = getFile();
        Log.d("test", "start:" + tempFile.exists() + " " + tempFile.length());
        // this.isCutOut = b;
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        intent.putExtra("showActionIcons", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        activity.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 调用系统的相册
     */
    private void startImageCaptrue() {
        tempFile = getFile();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    /**
     * 调用仿微信图库
     */

//    private void startAsWeixinImageCaptrue() {
//        PhotoPickerIntent intent = new PhotoPickerIntent(activity);
//        intent.setPhotoCount(returnFileCount);
//        activity.startActivityForResult(intent, AS_WEIXIN_REQUEST_CODE);
//    }

    /**
     * 启动
     */

    public void start(Mode mode) {
        currentPickMangerName = this.name;
        switch (mode) {
            case SYSTEM_CAMERA:
                startCamearPicCut();
                break;
            case SYSTEM_IMGCAPTRUE:
                startImageCaptrue();
                break;
//            case AS_WEIXIN_IMGCAPTRUE:
//                startAsWeixinImageCaptrue();
//                break;
        }
    }


    /**
     * 回调onActivityResult事件
     */
    @SuppressWarnings("unused")
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (currentPickMangerName == null) {
            if (bundle != null) {
                currentPickMangerName = bundle.getString(SAVE_STATIC_NAME);
                if (!currentPickMangerName.equals(name)) return;
            }
        } else {
            if (!currentPickMangerName.equals(name)) return;
        }
        try {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:

                    if (isCut) {
                        startPhotoZoom(Uri.fromFile(tempFile), defaultCutSize); // 裁剪
                    } else {
                        if (tempFile.length() == 0) {
                            tempFile.delete();
                        } else {
                            finish(tempFile);
                        }
                    }
                    return;
                case PHOTO_REQUEST_GALLERY:
                    if (isCut) {
                        if (data != null) {
                            startPhotoZoom(data.getData(), defaultCutSize);
                        }
                    } else {

                        File file = null;
                        try {
                            String path = getRealPathFromURI(data.getData());
                            file = new File(path);

                            if (file == null) return;
                            if (file.length() == 0) {
                                file.delete();
                                return;
                            }
                            finish(file);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    return;

                case PHOTO_REQUEST_CUT:
                    if (data != null) {
                        setCutPicToView(data);
                    } else {
                        flushCutPhotos();
                    }
                    return;
//                case AS_WEIXIN_REQUEST_CODE:
//                    List<String> photos = null;
//                    try {
//                        if (requestCode == AS_WEIXIN_REQUEST_CODE) {
//                            if (data != null) {
//                                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//                                if (!isCut) {
//
//                                    List<File> list = new ArrayList<File>();
//                                    if (photos != null && !photos.isEmpty()) {
//                                        for (String str : photos) {
//                                            list.add(new File(str));
//                                        }
//                                    } else {
//                                        return;
//                                    }
//                                    finish(list);
//                                } else {
//                                    if (photos != null && !photos.isEmpty()) {
//                                        /**如果只有一张照片可以直接裁剪 否则*/
//                                        if (photos.size() == 1) {
//                                            for (String str : photos) {
//                                                tempFile = getFile();
//                                                copyFile(str, tempFile.getAbsolutePath());
//                                                startPhotoZoom(Uri.fromFile(tempFile), defaultCutSize); // 裁剪
//                                            }
//                                        } else {
//                                            for (String str : photos) {
//                                                willCutOfFileQueue.add(new File(str));
//                                            }
//                                            tempFile = getFile();
//                                            copyFile(willCutOfFileQueue.get(0).getAbsolutePath(), tempFile.getAbsolutePath());
//                                            startPhotoZoom(Uri.fromFile(tempFile), defaultCutSize); // 裁剪
//                                            willCutOfFileQueue.remove(0);
//                                        }
//                                    }
//
//                                }
//                            }
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 如果多图处理裁剪队列
     */
    private void flushCutPhotos() {
        if (willCutOfFileQueue != null && !willCutOfFileQueue.isEmpty()) {
            tempFile = getFile();
            copyFile(willCutOfFileQueue.get(0).getAbsolutePath(), tempFile.getAbsolutePath());
            willCutOfFileQueue.remove(0);
            startPhotoZoom(Uri.fromFile(tempFile), defaultCutSize); // 裁剪
        }
    }

    /**
     * 文件复制
     */
    private void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {                  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);      //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;            //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对当前已选图片进行压缩
     */
    public void doProcessedPhotos(final OnProcessedPhotos on) {

        if (getSelectsPhotos() != null && !getSelectsPhotos().isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Iterator<File> it = getSelectsPhotos().iterator(); it.hasNext(); ) {
                        try {
                            File file = it.next();
                            if (file.length() > needProcessFileLength) {
                                final Bitmap bm = PictureUtil.getSmallBitmap(file.getAbsolutePath(), 720, 1200);
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bm.compress(Bitmap.CompressFormat.JPEG, 95, fos);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            on.onProcessed(getSelectsPhotos());
                        }
                    });


                }
            }).start();

        }
    }

    /**
     * 图片选择完成并回调
     */
    private void finish(final File file) {
        finish(createFiles(file));
    }

    /**
     * 图片选择完成并回调
     */
    private void finish(final List<File> files) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (Iterator<File> it = files.iterator(); it.hasNext(); ) {
                    File file = it.next();
                    if (!file.exists() || file.length() == 0) {
                        it.remove();
                    } else {
                        changFile(file.getPath());
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        selectsPhotos.addAll(files);
                        if (!files.isEmpty()) {
                            if (onPhotoPickFinsh != null) onPhotoPickFinsh.onPhotoPick(files);
                        }
                        tempFile = null;
                        flushCutPhotos();
                    }
                });
            }
        }).start();


    }

    /**
     * 三星手机将横向图片转换为竖向
     */
    public void changFile(final String file) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        int width = options.outWidth;
        int height = options.outHeight;
        //将分辨率尺寸固定在800 防止OOM
        float k=800.0f/width;
        Bitmap bit=BitmapCreate.bitmapFromFile(file,800,(int)(height*k));
        if (width > height) {
            try {
                bit = adjustPhotoRotation(bit, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            bit.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

     /**
     * 旋转图片
     */
    public Bitmap adjustPhotoRotation(Bitmap bm, int count) {
        int orientationDegree = 90;
        Matrix m = new Matrix();
        for (int i = 0; i < count; i++) {
            m.setRotate(orientationDegree, bm.getWidth(),
                    bm.getHeight());
            float targetX, targetY;
            if (orientationDegree == 90) {
                targetX = bm.getHeight();
                targetY = 0;
            } else {
                targetX = bm.getHeight();
                targetY = bm.getWidth();
            }

            final float[] values = new float[9];
            m.getValues(values);

            float x1 = values[Matrix.MTRANS_X];
            float y1 = values[Matrix.MTRANS_Y];

            m.postTranslate(targetX - x1, targetY - y1);
        }

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
                Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        bm.recycle();

        return bm1;
    }

    private List<File> createFiles(File file) {
        List<File> list = new ArrayList<>();
        list.add(file);
        return list;
    }

    /**
     * 根据Uri获得File文件路径
     */
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj,
                null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 启动裁剪
     */
    private void startPhotoZoom(Uri uri, int size) {
        Log.d("test", uri.toString());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);

        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片显示到UI界面上
    private void setCutPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            if (photo != null) {
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(tempFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            }
            finish(tempFile);

        }
    }


    /**
     * 获取一个指定大小的bitmap
     *
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     */
    public Bitmap bitmapFromFile(String pathName, int reqWidth,
                                 int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return BitmapFactory.decodeFile(pathName);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);

            options = calculateInSampleSize(options, reqWidth,
                    reqHeight);
            return BitmapFactory.decodeFile(pathName, options);
        }
    }

    /**
     * 图片压缩处理（使用Options的方法）
     * <p/>
     * <br>
     * <b>说明</b> 使用方法：
     * 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
     * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
     * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     * <p/>
     * <br>
     * <b>说明</b> BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
     * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
     * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
     * outHeight和outMimeType属性都会被赋值。
     *
     * @param reqWidth  目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     * @param reqHeight 目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     */
    public BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, final int reqWidth,
            final int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width
                    / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio
                    : widthRatio;
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }

    public Activity getActivity() {
        return activity;
    }

    public PhotoPickManger setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }


    public boolean isCut() {
        return isCut;
    }

    public PhotoPickManger setIsCut(boolean isCut) {
        this.isCut = isCut;
        return this;
    }

    public boolean isOptimize() {
        return isOptimize;
    }

    public PhotoPickManger setIsOptimize(boolean isOptimize) {
        this.isOptimize = isOptimize;
        return this;
    }

    public int getReturnFileCount() {
        return returnFileCount;
    }

    public PhotoPickManger setReturnFileCount(int returnFileCount) {
        this.returnFileCount = returnFileCount;
        return this;
    }

    public OnPhotoPickFinsh getOnPhotoPickFinsh() {
        return onPhotoPickFinsh;
    }

    public PhotoPickManger setOnPhotoPickFinsh(OnPhotoPickFinsh onPhotoPickFinsh) {
        this.onPhotoPickFinsh = onPhotoPickFinsh;
        return this;
    }

    public boolean isDebugToast() {
        return isDebugToast;
    }

    public PhotoPickManger setDebugToast(boolean isDebugToast) {
        this.isDebugToast = isDebugToast;
        return this;
    }

    public void setCut(boolean isCut) {
        this.isCut = isCut;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public PhotoPickManger setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public File getTempFile() {
        return tempFile;
    }

    public ArrayList<File> getSelectsPhotos() {
        return selectsPhotos;
    }

    public int getNeedProcessFileLength() {
        return needProcessFileLength;
    }

    public PhotoPickManger setNeedProcessFileLength(int needProcessFileLength) {
        this.needProcessFileLength = needProcessFileLength;
        return this;
    }

    public static String getCurrentPickMangerName() {
        return currentPickMangerName;
    }

    public static void setCurrentPickMangerName(String currentPickMangerName) {
        PhotoPickManger.currentPickMangerName = currentPickMangerName;
    }

    public String getCacheFilePath() {
        return cacheFilePath;
    }

    public PhotoPickManger setCacheFilePath(String cacheFilePath) {
        this.cacheFilePath = cacheFilePath;
        return this;
    }

    public String getName() {
        return name;
    }

    public PhotoPickManger setName(String name) {
        this.name = name;
        return this;
    }

    public PhotoPickManger setTempFile(File tempFile) {
        this.tempFile = tempFile;
        return this;
    }
}
