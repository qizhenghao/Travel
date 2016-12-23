package com.bruce.travel.travels.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce.travel.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by 梦亚 on 2016/8/31.
 */
public class TimePickerView extends LinearLayout {
    public static final String PICKED_TIME_EXT = "picked_time";
    private static final int UPDATE_TITLE_MSG = 0x111;
    private static final int UPDATE_WHEEL = 0x112;
    private static final int UPDATE_UpdateDay_MSG = 0x113;
    private static final int START_YEAR = 2013;
    private static final int END_YEAR = 2100;
    private TextView mPickerTitle;
    private static WheelView mWheelYear;
    private static WheelView mWheelMonth;
    private static WheelView mWheelDay;
    private static WheelView mWheelHour;
    private Button mCancelBtn;
    private Button mConfirmBtn;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private static int mHour;

    private String mStrMonth;
    private String mStrDay;
    private String pickTime;

    private Calendar mCalendar;
    private static int mDefaultDayWhellIndex = 0;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                case UPDATE_TITLE_MSG: {
//                    updateTitle();
//                }
//                break;
                case UPDATE_WHEEL: {
                    updateWheel();
                }
                break;
                case UPDATE_UpdateDay_MSG: {
                    updateDay(mMonth);
                }
                break;
            }

        }
    };
    private WheelView.OnSelectListener mYearListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int year, String text) {
            mYear = START_YEAR + year;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };

    private WheelView.OnSelectListener mMonthListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int month, String text) {
            mMonth = month;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
            mHandler.sendEmptyMessage(UPDATE_UpdateDay_MSG);
        }

        @Override
        public void selecting(int id, String text) {
        }
    };

    private WheelView.OnSelectListener mDayListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int day, String text) {
            mDay = day + 1;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private WheelView.OnSelectListener mHourListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int hour, String text) {
            mHour = hour;
            mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private Activity mContext;

    public TimePickerView(Context context) {
        this(context, null);
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = (Activity) getContext();
        LayoutInflater.from(mContext).inflate(R.layout.time_picker_layout, this);
//        mPickerTitle = (TextView) findViewById(R.id.picker_title);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelHour = (WheelView) findViewById(R.id.hour);
//        mCancelBtn = (Button) findViewById(R.id.cancel);
//        mConfirmBtn = (Button) findViewById(R.id.confirm);
        mWheelYear.setOnSelectListener(mYearListener);
        mWheelMonth.setOnSelectListener(mMonthListener);
        mWheelDay.setOnSelectListener(mDayListener);
        mWheelHour.setOnSelectListener(mHourListener);
//        mConfirmBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TimePickerView.class.getSimpleName(), mContext.getString(R.string.picker_title, mYear, mMonth + 1, mDay, mHour));

//                mCalendar.set(Calendar.YEAR, mYear);
//                mCalendar.set(Calendar.MONTH, mMonth);
//                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
//                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
//                Intent intent = new Intent();
//                intent.putExtra(PICKED_TIME_EXT, mCalendar.getTimeInMillis());
//                mContext.setResult(WuliuQQConstants.RESULT_CODE_TIME_PICKER, intent);
//                mContext.finish();
//            }
//        });

//        mCancelBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.finish();
//            }
//        });
    }

    private static void updateDay(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        mWheelDay.resetData(getDayData(maxDay));
        if (mDay > maxDay) {
            mWheelDay.setDefault(mDefaultDayWhellIndex);
            mDay = mDefaultDayWhellIndex + 1;
        } else {
            mWheelDay.setDefault(mDay - 1);
        }
    }

    /**
     * set WLQQTimePicker date
     * @param date
     */
    public void setDate(long date) {
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.setTimeInMillis(date);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);

        mWheelYear.setData(getYearData());
        mWheelMonth.setData(getMonthData());
        mWheelDay.setData(getDayData(mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
        mWheelHour.setData(getHourData());

        mHandler.sendEmptyMessage(UPDATE_TITLE_MSG);
        mHandler.sendEmptyMessage(UPDATE_WHEEL);
    }

//    private void updateTitle() {
//        mPickerTitle.setText(mContext.getString(R.string.picker_title, mYear, mMonth + 1, mDay, mHour));
//    }

    private static void updateWheel() {
        mWheelYear.setDefault(mYear - START_YEAR);
        mWheelMonth.setDefault(mMonth);
        mWheelDay.setDefault(mDay - 1);
        mWheelHour.setDefault(mHour);
    }

     public String getPickTime() {

         if(mMonth > 0  && mMonth < 9 ) {
             mStrMonth = "0" + (mMonth + 1);
         } else {
             mStrMonth = (mMonth + 1) + "";
         }
         if(mDay > 0 && mDay < 9) {
             mStrDay = "0" + mDay;
         } else {
             mStrDay = mDay + "";
         }
         pickTime = mYear + "-" + mStrMonth + "-" + mStrDay;
         return pickTime;
     }

    private ArrayList<String> getYearData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            list.add(i + "年");
        }
        return list;
    }

    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            list.add(i + "月");
        }
        return list;
    }

    private static ArrayList<String> getDayData(int endDay) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= endDay; i++) {
            list.add(i + "日");
        }
        return list;
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            list.add(i + ":00");
        }
        return list;
    }
}
