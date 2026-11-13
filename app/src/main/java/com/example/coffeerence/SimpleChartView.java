package com.example.coffeerence;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class SimpleChartView extends View {
    private Paint linePaint, pointPaint, backgroundPaint;
    private Path path;
    private float[] dataPoints = {91.2f, 92.5f, 92.1f, 93.0f, 92.8f, 92.5f, 92.5f};

    public SimpleChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#4CAF50"));
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#2E7D32"));
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#E0E0E0"));
        backgroundPaint.setStrokeWidth(1f);
        path = new Path();
    }

    public void updateData(float[] newData) {
        this.dataPoints = newData;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float padding = 50f;

        for (int i = 0; i <= 4; i++) {
            float y = padding + (height - padding * 2) * (i / 4f);
            canvas.drawLine(padding, y, width - padding, y, backgroundPaint);
        }

        float minVal = Float.MAX_VALUE, maxVal = Float.MIN_VALUE;
        for (float val : dataPoints) {
            if (val < minVal) minVal = val;
            if (val > maxVal) maxVal = val;
        }
        minVal -= 0.5f; maxVal += 0.5f;

        path.reset();
        float xStep = (width - padding * 2) / (dataPoints.length - 1);

        for (int i = 0; i < dataPoints.length; i++) {
            float x = padding + i * xStep;
            float normalizedY = (dataPoints[i] - minVal) / (maxVal - minVal);
            float y = height - padding - (normalizedY * (height - padding * 2));
            if (i == 0) path.moveTo(x, y);
            else path.lineTo(x, y);
            canvas.drawCircle(x, y, 8f, pointPaint);
        }
        canvas.drawPath(path, linePaint);
    }
}