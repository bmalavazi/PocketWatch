package com.pocketwatch.demo.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bmalavazi on 12/30/14.
 */
public class Calendar {
    private static final String TAG = "Calendar";
    private String mDateString;
    private static final String mFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private SimpleDateFormat mSdf;
    private Date mDate;
    private String mFormattedDateString;

    public Calendar(String date) {
        final String func = "Calendar()";
        Utils.Entry(TAG, func);

        mDateString = date;
        mSdf = new SimpleDateFormat(mFormat);
        try {
            mDate = mSdf.parse(mDateString);
            mSdf.applyPattern("EEEE, MMM dd, yyyy hh:mm a");
            mFormattedDateString = mSdf.format(mDate);
            Log.d(TAG, mFormattedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Utils.Exit(TAG, func);
    }

    public String getDateString() {
        return mFormattedDateString;
    }
}
