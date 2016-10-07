package com.zjun.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_win10:
                startActivity(new Intent(this, Win10LoadingRendererActivity.class));
                break;

            case R.id.btn_anim:
                startActivity(new Intent(this, AnimTwoCentralActivity.class));
                break;

            case R.id.btn_track:
                startActivity(new Intent(this, AnimTrackActivity.class));
                break;
        }
    }
}
