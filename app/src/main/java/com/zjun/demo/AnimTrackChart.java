package com.zjun.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * File Name    : AnimTrackChart
 * Description  : 动画轨迹图，展示Win10动画的时间比图
 * Author       : Ralap
 * Create Date  : 2016/10/7
 * Version      : v1
 */
public class AnimTrackChart extends View {
    private Paint mPaint;
    private Path path;
    private PathEffect pathEffect;

    private int centerX = 50;
    private int centerY = 350;
    private int scale = 300;
    private int[] colors = {Color.RED, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.BLUE};

    public AnimTrackChart(Context context) {
        this(context, null);
    }

    public AnimTrackChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1f);

        path = new Path();
        pathEffect = new DashPathEffect(new float[]{5, 5}, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画坐标
        drawCoordinate(canvas);
        // 画轨迹图
        drawTrack(canvas);
    }

    private void drawTrack(Canvas canvas) {

        long duration = 1; // 一个周期（2圈）一共运行7000ms，固定值
        int comeStepAngle = 22; // 到达的间隔角度
        int goStepAngle = 16; // 离开的间隔角度

        // 最小执行单位时间
        final float minRunUnit = duration / 16f;
        // 最小执行单位时间所占总时间的比例
        float minRunPer = minRunUnit / duration;

        for (int index = 0; index < 5; index++) {
            // 在插值器中实际值（Y坐标值），共8组
            final float[] ys = new float[]{
                    0,
                    0,
                    160 / 720f - index * comeStepAngle / 720f,
                    180 / 720f - index * goStepAngle / 720f,
                    360 / 720f,
                    520 / 720f - index * comeStepAngle / 720f,
                    540 / 720f - index * goStepAngle / 720f,
                    1
            };
            // 动画开始的时间比偏移量。剩下的时间均摊到每个圆点上
            final float offset = (float) (index * (16 - 14) * minRunPer / 5);
            // 在差值器中理论值（X坐标值），与ys对应
            final float[] xs = new float[]{
                    0,
                    offset + 0,
                    offset + 1 * minRunPer,
                    offset + 5 * minRunPer,
                    offset + 7 * minRunPer,
                    offset + 8 * minRunPer,
                    offset + 12 * minRunPer,
                    offset + 14 * minRunPer
            };
            
            
            // 放大，并把原坐标中心移动到现坐标中心
            for (int i = 0; i < ys.length; i++) {
                ys[i] = centerY - ys[i] * scale ;
                xs[i] = centerX + xs[i] * scale;
            }

            mPaint.setColor(colors[index]);
            path.reset();
            // 绘制第1段 直线
            path.moveTo(xs[0], ys[0]);
            path.lineTo(xs[1], ys[1]);

            // 绘制第2段 贝塞尔曲线
            float p1 = calculateLineY(xs[2], ys[2], xs[3], ys[3], xs[1]);
            path.quadTo(xs[1], p1, xs[2], ys[2]);

            // 绘制第3段 直线
            path.lineTo(xs[3], ys[3]);

            // 绘制第4段 贝塞尔曲线
            p1 = calculateLineY(xs[2], ys[2], xs[3], ys[3], xs[4]);
            path.quadTo(xs[4], p1, xs[4], ys[4]);

            // 绘制第5段 贝塞尔曲线
            p1 = calculateLineY(xs[5], ys[5], xs[6], ys[6], xs[4]);
            path.quadTo(xs[4], p1, xs[5], ys[5]);

            // 绘制第6段 直线
            path.lineTo(xs[6], ys[6]);

            // 绘制第7段 贝塞尔曲线
            p1 = calculateLineY(xs[5], ys[5], xs[6], ys[6], xs[7]);
            path.quadTo(xs[7], p1, xs[7], ys[7]);

            // 绘制第8段 直线
            path.lineTo(centerX + scale, centerY - scale);

            canvas.drawPath(path, mPaint);

            if (index == 0) {
                // 绘制圆点1的参考线
                path.reset();
                for (int j = 0; j < xs.length; j++) {
                    path.moveTo(xs[j], centerY);
                    path.lineTo(xs[j], ys[j]);
                }
                mPaint.setColor(Color.DKGRAY);
                mPaint.setPathEffect(pathEffect);
                canvas.drawPath(path, mPaint);

                mPaint.setPathEffect(null); // 恢复
            }


        }

    }



    private void drawCoordinate(Canvas canvas) {
        mPaint.setColor(Color.DKGRAY);
        // X轴
        canvas.drawLine(centerX, centerY, centerX + scale + 50, centerY, mPaint);
        // Y轴
        canvas.drawLine(centerX, centerY, centerX, centerY - scale - 50, mPaint);

        // x=1, y=1的参考线
        path.reset();
        path.moveTo(centerX + scale, centerY);
        path.rLineTo(0, -scale);
        path.rLineTo(-scale, 0);

        mPaint.setPathEffect(pathEffect);
        canvas.drawPath(path, mPaint);

        mPaint.setPathEffect(null); // 恢复

        // 参考坐标值
        canvas.drawText("0", centerX - 10, centerY + 12, mPaint);
        canvas.drawText("1", centerX + scale - 5, centerY + 12, mPaint);
        canvas.drawText("1", centerX - 10, centerY - scale + 6, mPaint);

    }

    private float calculateLineY(double x1, double y1, double x2, double y2, double x) {
        if (x1 == x2) {
            return (float) y1;
        }
        return (float) ((x - x1) * (y2 - y1) / (x2 - x1) + y1);
    }
}
