package com.example.kashishgrover.mttn.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.kashishgrover.mttn.R;

public class ProgressActivity extends AppCompatActivity {

    protected ProgressBar mProgressBar;

    @Override
    public void setContentView(View view) {
        init().addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, init(), true);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        init().addView(view, params);
    }

    private ViewGroup init() {
        super.setContentView(R.layout.progress);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        return (ViewGroup) findViewById(R.id.activity_frame);
    }

    protected ProgressBar getProgressBar() {
        return mProgressBar;
    }
}