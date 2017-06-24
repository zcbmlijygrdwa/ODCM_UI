package zhenyuyang.ucsb.edu.odcm_ui;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gigamole.library.ArcProgressStackView;

import java.util.ArrayList;

public class DriveActivity extends AppCompatActivity {
    private ArcProgressStackView arcProgressStackView;
    private TextView textView_test;
    private int updatePeriod = 2;  //seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        textView_test = (TextView)findViewById(R.id.textView_test);

        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Turning", 25, ContextCompat.getColor(getApplicationContext(), R.color.colorTurningLight), ContextCompat.getColor(getApplicationContext(), R.color.colorTurning)));
        models.add(new ArcProgressStackView.Model("Break", 50, ContextCompat.getColor(getApplicationContext(), R.color.colorBreakLight), ContextCompat.getColor(getApplicationContext(), R.color.colorBreak)));
        models.add(new ArcProgressStackView.Model("Accelerate", 25, ContextCompat.getColor(getApplicationContext(), R.color.colorAccelerateLight), ContextCompat.getColor(getApplicationContext(), R.color.colorAccelerate)));
        //models.add(new ArcProgressStackView.Model("Circle", 25, bgColors[0], mStartColors[0]));
        //models.add(new ArcProgressStackView.Model("Progress", 50, bgColors[1], mStartColors[1]));
        //models.add(new ArcProgressStackView.Model("Stack", 75, bgColors[2], mStartColors[2]));
        //models.add(new ArcProgressStackView.Model("View", 100, bgColors[3], mStartColors[3]));

        arcProgressStackView = (ArcProgressStackView) findViewById(R.id.apsv);
        arcProgressStackView.setModels(models);
        arcProgressStackView.setAnimationDuration((long)(updatePeriod*1000*0.7));





        //fake update
        (new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        Thread.sleep(updatePeriod*1000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {

                            @Override
                            public void run()
                            {
                                int breakValue  = randomWithRange(1,50);
                                int turningValue  = randomWithRange(1,50);
                                int accelerateValue  = randomWithRange(1,50);
                                displayData(breakValue,turningValue,accelerateValue); // this action have to be in UI thread
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        })).start(); // the while thread will start in BG thread

    }

    void displayData(int breakValue,int turningValue,int accelerateValue ){
        textView_test.setText("breakValue = "+breakValue+", turningValue = "+turningValue+", accelerateValue = "+accelerateValue);
        arcProgressStackView.getModels().get(0).setProgress(turningValue);
        arcProgressStackView.getModels().get(1).setProgress(breakValue);
        arcProgressStackView.getModels().get(2).setProgress(accelerateValue);
        arcProgressStackView.animateProgress();
    }

    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
