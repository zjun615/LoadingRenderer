package com.zjun.demo;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 动画的两个核心类演示
 */
public class AnimTwoCentralActivity extends AppCompatActivity {

    private static final long ANIM_DURATION = 5000;

    private View v_normal;
    private View v_interpolator;
    private View v_evaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_two_central);

        initView();
    }

    private void initView() {
        v_normal = $(R.id.v_normal);
        v_interpolator = $(R.id.v_interpolator);
        v_evaluator = $(R.id.v_evaluator);

        startAnim();
    }

    private void startAnim() {
        normalAnim();
        interpolatorAnim();
        evaluatorAnim();
    }

    /**
     * 自定义估值器的动画
     */
    private void evaluatorAnim() {
        ValueAnimator va = ValueAnimator.ofObject(
                new TypeEvaluator<PointF>() {
                    /**
                     * 估算结果
                     *
                     * @param fraction 由插值器提供的值，一般∈[0, 1]
                     * @param startValue 开始值
                     * @param endValue 结束值
                     * @return
                     */
                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF p = new PointF();
                        float distance = endValue.x - startValue.x;
                        p.x = fraction * distance;
                        float halfDistance = distance / 2;
                        float sinX = (float) Math.sin(fraction * Math.PI / 0.5);
                        p.y = -halfDistance * sinX;
                        return p;
                    }
                },
                new PointF(0, 0),
                new PointF(300, 0)
        );
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                v_evaluator.setTranslationX(pointF.x);
                v_evaluator.setTranslationY(pointF.y);
            }
        });
        va.setDuration(ANIM_DURATION);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.start();
    }

    /**
     * 自定义差值器的动画
     */
    private void interpolatorAnim() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v_interpolator, "translationX", 0, 300);
        oa.setInterpolator(new TimeInterpolator() {
            /**
             * 获取差值器的值
             * @param input 原生帧值[0, 1]
             * @return 校正后的值
             */
            @Override
            public float getInterpolation(float input) {
                // 前半段时间为直线（匀速运动），后半段贝塞尔曲线（先反向）
                if (input < 0.5) {
                    return input;
                }
                // 把贝塞尔曲线范围[0.5, 1]转换成[0, 1]范围
                input = (input - 0.5f) * (1 - 0) / (1 - 0.5f);
                float tmp = 1 - input;
                return tmp * tmp * 0.5f + 2 * input * tmp * 0 + input * input * 1;
            }
        });
        oa.setDuration(ANIM_DURATION);
        oa.setRepeatCount(ValueAnimator.INFINITE);
        oa.start();
    }

    /**
     * 默认动画
     * 差值器默认为AccelerateDecelerateInterpolator
     */
    private void normalAnim() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v_normal, "translationX", 0, 300);
        oa.setDuration(ANIM_DURATION);
        oa.setRepeatCount(ValueAnimator.INFINITE);
        oa.start();
    }

    private <V extends View> V $(int id) {
        return (V) findViewById(id);
    }

    private void logD(String format, Object... args) {
        Log.d(AnimTwoCentralActivity.class.getSimpleName(), "@zjun: " + String.format(format, args));
    }
}
