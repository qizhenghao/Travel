package com.bruce.travel.travels;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.travels.activities.RecordSaveActivity;
import com.bruce.travel.travels.model.Manifest;
import com.bruce.travel.travels.view.PopWindowView;
import com.bruce.travel.travels.view.TimePickerView;
import com.bruce.travel.universal.utils.Methods;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import me.iwf.photopicker.widget.PhotoAdapter;

/**
 * Created by 梦亚 on 2016/8/22.
 */
public class NewRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.local_time_btn)
    Button local_time_btn;
    @Bind(R.id.location_btn)
    Button location_btn;
    @Bind(R.id.toolbar_emoji_btn)
    Button toolbar_emoji_btn;
    @Bind(R.id.photo_select_btn)
    Button photo_select_btn;
    @Bind(R.id.return_desktop_btn)
    Button return_btn;
    @Bind(R.id.save_record_btn)
    Button save_btn;
    @Bind(R.id.location_tv)
    TextView location_tv;
    @Bind(R.id.cancel_tv)
    TextView cancel_tv;
    @Bind(R.id.time_picker_ll)
    LinearLayout time_picker_ll;
    @Bind(R.id.date_pick_confirm_btn)
    Button time_confirm_btn;
    @Bind(R.id.date_pick_cancel_btn)
    Button time_cancel_btn;

    private EditText title_et;
    private EditText content_et;

    //photo
    private InputMethodManager imm;
    MultiPickResultView recyclerView;
    public static final int REQUEST_CODE = 1;

    private int pathNum;
    ArrayList<String> selectedPhotos;
    private PhotoAdapter adapter = new PhotoAdapter(selectedPhotos);

    //locate
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor = "gcj02";
    private String myLocation;
    TimePickerView timePicker;
    private String pickTimeStr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_layout);
        ButterKnife.bind(this);
        init();

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        recyclerView.setVisibility(View.GONE);
    }



    @Override
    protected void initViews() {
        imm = ( InputMethodManager ) getSystemService(Context.INPUT_METHOD_SERVICE);
        title_et = (EditText) findViewById(R.id.record_title_et);
        content_et = (EditText) findViewById(R.id.record_content_et);

        recyclerView = (MultiPickResultView) findViewById(R.id.photo_display_view);
        recyclerView.init(this, MultiPickResultView.ACTION_SELECT, null);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        timePicker = (TimePickerView) findViewById(R.id.time_picker);

        final PopWindowView popWindow = new PopWindowView(this, R.layout.popwindow_add_layout, new PopWindowView.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(NewRecordActivity.this, RecordSaveActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Methods.showToast("lalala222",false);
                        break;
                }
            }
        });

        photo_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.showPopupWindow(save_btn);
            }
        });



    }

    @Override
    protected void initData() {
        title_et.setFocusable(true);
        timePicker.setDate(new Date().getTime());
        pickTimeStr = timePicker.getPickTime();

        title_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    recyclerView.setVisibility(View.GONE);
                    time_picker_ll.setVisibility(View.GONE);
                }
            }
        });

        content_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    recyclerView.setVisibility(View.GONE);
                    time_picker_ll.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    protected void initListeners() {
        local_time_btn.setOnClickListener(this);
        location_btn.setOnClickListener(this);
        toolbar_emoji_btn.setOnClickListener(this);
        photo_select_btn.setOnClickListener(this);
        return_btn.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        time_confirm_btn.setOnClickListener(this);
        time_cancel_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.photo_select_btn:
                recyclerView.setVisibility(View.VISIBLE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                PhotoPickUtils.startPick(this, null);
                time_picker_ll.setVisibility(View.GONE);
                Methods.showToast(pathNum + "", true);
                //每次点击选择图片选项都重新选择有问题，先判断当前是否已经有选中的图片，如果有则只显示当前选中图片
                break;
            case R.id.local_time_btn:
                recyclerView.setVisibility(View.GONE);
                pickTimeStr = timePicker.getPickTime();
                time_picker_ll.setVisibility(View.VISIBLE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.date_pick_confirm_btn:
//                pickTimeStr = timePicker.getPickTime();
                time_picker_ll.setVisibility(View.GONE);
                Methods.showToast(pickTimeStr, false);
                break;
            case R.id.toolbar_emoji_btn:
                break;
            case R.id.date_pick_cancel_btn:
                time_picker_ll.setVisibility(View.GONE);
                break;
            case R.id.location_btn:
                InitLocation();
                mLocationClient.start();
                location_tv.setVisibility(View.VISIBLE);
                location_tv.setText("正在获取位置...");
                break;
            case R.id.return_desktop_btn:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recyclerView.onActivityResult(requestCode, resultCode, data);
//        recyclerViewShowOnly.showPics(recyclerView.getPhotos());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay!
            onClick(photo_select_btn);

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.CAMERA:
                // No need to explain to user as it is obvious
                return false;
            default:
                return true;
        }
    }

    private void checkPermission() {

        int readStoragePermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        boolean readStoragePermissionGranted = readStoragePermissionState != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermissionGranted = cameraPermissionState != PackageManager.PERMISSION_GRANTED;

        if (readStoragePermissionGranted || cameraPermissionGranted) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                String[] permissions;
                if (readStoragePermissionGranted && cameraPermissionGranted) {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
                } else {
                    permissions = new String[] {
                            readStoragePermissionGranted ? Manifest.permission.READ_EXTERNAL_STORAGE
                                    : Manifest.permission.CAMERA
                    };
                }
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            }

        } else {
            // Permission granted
            onClick(photo_select_btn);
        }

    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);
        option.setCoorType(tempcoor);
        int span = 1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String addrStr = bdLocation.getAddrStr();
            if(!TextUtils.isEmpty(addrStr)) {
                mLocationClient.stop();
                location_tv.setText(addrStr);
                cancel_tv.setVisibility(View.VISIBLE);
                myLocation = addrStr;
                cancel_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        location_tv.setVisibility(View.GONE);
                        cancel_tv.setVisibility(View.GONE);
                        myLocation = "";
                    }
                });
            }
        }
    }


}
