package com.example.user.homeworktinkoff5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int color = 0xFF33B5E5;
    private SeekBar mRedSeekBar, mGreenSeekBar, mBlueSeekBar;
    private ArrayList<Float> xPoints = new ArrayList<>();
    private ArrayList<Float> yPoints = new ArrayList<>();

    private EditText xInput;
    private EditText yInput;
    private Button colorTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        xInput = findViewById(R.id.input_x);
        yInput = findViewById(R.id.input_y);
        Button addPoint = findViewById(R.id.add_point);
        Button showGraph = findViewById(R.id.show_graph);
        colorTest = findViewById(R.id.color_test);
        mRedSeekBar = findViewById(R.id.seekBar_Red);
        mGreenSeekBar = findViewById(R.id.seekBar_Green);
        mBlueSeekBar = findViewById(R.id.seekBar_Blue);

        mRedSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mGreenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mBlueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newX = xInput.getText().toString();
                String newY = yInput.getText().toString();
                if (!(newX.isEmpty() || newY.isEmpty())) {
                    addNewPoint(Float.valueOf(newX), Float.valueOf(newY));
                    xInput.setText("");
                    yInput.setText("");
                }
            }
        });

        showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(xPoints.isEmpty() && yPoints.isEmpty()))
                    ActivityForGraphic.start(MainActivity.this, xPoints.toArray(new Float[xPoints.size()]), yPoints.toArray(new Float[yPoints.size()]));
            }
        });
    }

    private void addNewPoint(float x, float y) {
        xPoints.add(x);
        yPoints.add(y);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateLineColor();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void updateLineColor() {
        int redValue, greenValue, blueValue;
        redValue = mRedSeekBar.getProgress();
        greenValue = mGreenSeekBar.getProgress();
        blueValue = mBlueSeekBar.getProgress();
        color = 0xff000000 + redValue * 0x10000 + greenValue * 0x100
                + blueValue;
        colorTest.setBackgroundColor(color);
    }
}
