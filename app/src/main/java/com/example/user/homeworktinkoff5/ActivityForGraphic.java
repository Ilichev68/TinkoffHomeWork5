package com.example.user.homeworktinkoff5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityForGraphic extends AppCompatActivity {

    public static void start(Context context, Float[] arrX, Float[] arrY) {
        Intent starter = new Intent(context, ActivityForGraphic.class);
        starter.putExtra("xArray", arrX);
        starter.putExtra("yArray", arrY);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_graphic);

        GraphicView lineChart = findViewById(R.id.graphic);

        Float[] xPoints = (Float[]) getIntent().getExtras().get("xArray");
        Float[] yPoints = (Float[]) getIntent().getExtras().get("yArray");
        lineChart.setChartData(yPoints, xPoints);
    }
}
