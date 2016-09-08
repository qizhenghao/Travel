package com.bruce.travel.travels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bruce.travel.R;
import com.bruce.travel.base.BaseActivity;
import com.bruce.travel.finds.model.TravelNotesInfo;
import com.bruce.travel.travels.view.PopWindowView;
import com.bruce.travel.travels.view.TimePickerView;
import com.bruce.travel.universal.photopicker.camera.PhotoPickManger;
import com.bruce.travel.universal.photopicker.tools.PhotoGridManager;
import com.bruce.travel.universal.photopicker.tools.SimpleImageLoader;
import com.bruce.travel.universal.utils.Methods;


import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 梦亚 on 2016/8/22.
 */
public class NewRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.local_time_btn)
    Button local_time_btn;
    @Bind(R.id.location_btn)
    Button location_btn;
    @Bind(R.id.voice_record_btn)
    Button voice_record_btn;
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
    Button time_cancle_btn;

    private GridView photo_gv;
    private EditText title_et;
    private EditText content_et;

    //photo
    private InputMethodManager imm;
    private PhotoPickManger pickManager;
    private PhotoGridManager photoGridManager;

    //locate
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor = "gcj02";
    private String myLocation;
    TimePickerView timePicker;
    private String pickTimeStr;
    private List<TravelNotesInfo> list;
    private TravelNotesInfo travelInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record_layout);
        ButterKnife.bind(this);
        init();

        SimpleImageLoader.init(this.getApplicationContext());

        {
            pickManager = new PhotoPickManger("pick1", this, savedInstanceState, new PhotoPickManger.OnPhotoPickFinsh() {
                @Override
                public void onPhotoPick(List<File> list) {
                    photoGridManager.getAdapter().notifyDataSetChanged();
                }
            });
            photoGridManager = new PhotoGridManager(photo_gv,pickManager, 8, 4);
            setPickMangerAndPhotoGridManger(pickManager, photoGridManager);
        }

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
    }



    @Override
    protected void initViews() {
        imm = ( InputMethodManager ) getSystemService(Context.INPUT_METHOD_SERVICE);
        title_et = (EditText) findViewById(R.id.record_title_et);
        content_et = (EditText) findViewById(R.id.record_content_et);

        photo_gv = (GridView) findViewById(R.id.photo_display_gridview);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        timePicker = (TimePickerView) findViewById(R.id.time_picker);

        final PopWindowView popWindow = new PopWindowView(this, R.layout.popwindow_add_layout, new PopWindowView.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        Methods.showToast("lalala111",false);
                        break;
                    case 1:
                        Methods.showToast("lalala222",false);
                        break;
                }
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

        title_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    photo_gv.setVisibility(View.GONE);
                }
            }
        });

        content_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    photo_gv.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void initListeners() {
        local_time_btn.setOnClickListener(this);
        location_btn.setOnClickListener(this);
        voice_record_btn.setOnClickListener(this);
        photo_select_btn.setOnClickListener(this);
        return_btn.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        time_confirm_btn.setOnClickListener(this);
        time_cancle_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.photo_select_btn:
                createDialog(pickManager);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.local_time_btn:
                time_picker_ll.setVisibility(View.VISIBLE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.date_pick_confirm_btn:
                pickTimeStr = timePicker.getPickTime();
                time_picker_ll.setVisibility(View.GONE);
                Methods.showToast(pickTimeStr, true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pickManager.onActivityResult(requestCode, resultCode, data);
        photo_gv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        pickManager.onSaveInstanceState(outState);
    }

    public void setPickMangerAndPhotoGridManger(final PhotoPickManger pickManger,PhotoGridManager photoGridManager){
        photoGridManager.setDrawableAdd(R.drawable.add_photo_icon);
        photoGridManager.setDrawableDel(R.drawable.photo_del_black);
        final PhotoGridManager finalPhotoGridManager = photoGridManager;
        pickManger.setOnPhotoPickFinsh(new PhotoPickManger.OnPhotoPickFinsh() {
            @Override
            public void onPhotoPick(List<File> list) {
                finalPhotoGridManager.getAdapter().notifyDataSetChanged();
            }
        });

        photoGridManager.setOnItemAddAction(new PhotoGridManager.OnItemAction() {
            @Override
            public void onItemAciton(PhotoGridManager photoGridManager) {
                pickManger.setReturnFileCount(8 - pickManger.getSelectsPhotos().size());
                createDialog(pickManger);
            }
        });
        pickManger.flushBundle();
    }

    public void createDialog(final PhotoPickManger pickManger){
        new AlertDialog.Builder(pickManger.getActivity()).setTitle("单选框").setIcon(
                android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                new String[]{"系统相机", "系统相册"},-1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                pickManger.start(PhotoPickManger.Mode.SYSTEM_CAMERA);
                                break;
                            case 1:
                                pickManger.start(PhotoPickManger.Mode.SYSTEM_IMGCAPTRUE);
                                break;
                        }
                    }
                }).show();
    }



}
