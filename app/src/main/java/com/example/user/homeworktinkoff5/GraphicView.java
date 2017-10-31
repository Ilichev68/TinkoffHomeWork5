package com.example.user.homeworktinkoff5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by User on 31.10.2017.
 */

public class GraphicView extends View {
    private static final int MAX_LINES = 7;
    private static final int[] DISTANCES = {1, 2, 5};

    private Float[] ypoint = new Float[]{};
    private Float[] xpoint = new Float[]{};

    private Paint paint = new Paint();

    public void setChartData(Float[] ypoint, Float[] xpoint) {
        sortArr(xpoint, ypoint);
        this.ypoint = ypoint.clone();
        this.xpoint = xpoint.clone();
    }

    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLineChart(canvas);
    }

    private void drawBackground(Canvas canvas) {
        Float maxValueY = getMaxY(ypoint);
        Float maxValueX = getMaxX(xpoint);

        int rangeY = getLineDistance(maxValueY);
        int rangeX = getLineDistance(maxValueX);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(16);
        paint.setStrokeWidth(1);

        for (float y = 0; y < maxValueY; y += rangeY) {
            final float yPos = getYPos(y);
            paint.setAntiAlias(false);
            canvas.drawLine(0, yPos, getWidth(), yPos, paint);

            paint.setAntiAlias(true);
            canvas.drawText(String.valueOf(y), getPaddingLeft(), yPos - 2, paint);
        }
        for (float x = 0; x < maxValueX; x += rangeX) {
            final float xPos = getXPos(x);
            paint.setAntiAlias(false);
            canvas.drawLine(xPos, 0, xPos, getHeight(), paint);

            paint.setAntiAlias(true);
            canvas.drawText(String.valueOf(x), xPos, getHeight() - getPaddingBottom(), paint);
        }
    }

    private int getLineDistance(float maxValue) {
        int distance;
        int distanceIndex = 0;
        int distanceMultiplier = 1;
        int numberOfLines;

        do {
            distance = DISTANCES[distanceIndex] * distanceMultiplier;
            numberOfLines = (int) Math.ceil(maxValue / distance);

            distanceIndex++;
            if (distanceIndex == DISTANCES.length) {
                distanceIndex = 0;
                distanceMultiplier *= 10;
            }
        } while (numberOfLines > MAX_LINES);

        return distance;
    }

    private void drawLineChart(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getXPos(xpoint[0]), getYPos(ypoint[0]));
        for (int i = 1; i < ypoint.length; i++) {
            path.lineTo(getXPos(xpoint[i]), getYPos(ypoint[i]));
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(MainActivity.color);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x80000000);
        canvas.drawPath(path, paint);
        paint.setShadowLayer(0, 0, 0, 0);
    }


    private float getYPos(Float value) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        Float maxValue = getMaxY(ypoint);

        // масштабирования под высоту view
        value = (value / maxValue) * height;

        // инверсия
        value = height - value;

        // смещение чтобы учесть padding
        value += getPaddingTop();
        return value;
    }

    private float getXPos(Float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = getMaxX(xpoint);

        // масштабирования под размер view
        value = (value / maxValue) * width;

        // смещение чтобы учесть padding
        value += getPaddingLeft();
        return value;
    }

    private float getMaxY(Float[] array) {
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private float getMaxX(Float[] array) {
        Float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private void sortArr(Float[] arrX, Float[] arrY) {
        for (int i = arrX.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arrX[j] > arrX[j + 1]) {
                    Float tmpX = arrX[j];
                    arrX[j] = arrX[j + 1];
                    arrX[j + 1] = tmpX;
                    Float tmpY = arrY[j];
                    arrY[j] = arrY[j + 1];
                    arrY[j + 1] = tmpY;
                }
            }
        }
    }
}
