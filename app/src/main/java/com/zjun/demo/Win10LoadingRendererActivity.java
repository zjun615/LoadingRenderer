package com.zjun.demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zjun.loadingrenderer.Win10LoadingRenderer;

import java.util.Random;

public class Win10LoadingRendererActivity extends AppCompatActivity {

    private LinearLayout ll_parent;
    private Button btn_start;
    private Win10LoadingRenderer win10_1;

    private int[] mDotColors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.YELLOW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win10_loading_renderer);

        initView();
    }

    private void initView() {
        ll_parent = $(R.id.ll_parent);
        win10_1 = $(R.id.win10_1);
        btn_start = $(R.id.btn_start);

        stopAnim();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_color:
                win10_1.setDotColor(mDotColors[new Random().nextInt(mDotColors.length)]);
                break;

            case R.id.btn_start:
                startOrStopAnim();
                break;
        }
    }

    private void startOrStopAnim() {
        if ("开始".equals(btn_start.getText().toString())) {
            btn_start.setText("停止");
            startAnim();
        } else {
            btn_start.setText("开始");
            stopAnim();
        }
    }

    private void startAnim() {
        int count = ll_parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = ll_parent.getChildAt(i);
            if (view instanceof Win10LoadingRenderer) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void stopAnim() {
        int count = ll_parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = ll_parent.getChildAt(i);
            if (view instanceof Win10LoadingRenderer) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    private <V extends View> V $(int id) {
        return (V) findViewById(id);
    }
}
