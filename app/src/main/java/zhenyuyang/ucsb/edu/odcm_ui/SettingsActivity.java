package zhenyuyang.ucsb.edu.odcm_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class SettingsActivity extends AppCompatActivity {
    private StepperTouch stepperTouch;
    private TextView textView_settingsTitle_2,textView_settingsDetail_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView_settingsTitle_2 = (TextView) findViewById(R.id.textView_settingsTitle_2);
        textView_settingsDetail_2 = (TextView) findViewById(R.id.textView_settingsDetail_2);

        stepperTouch = (StepperTouch)findViewById(R.id.stepperTouch_2);
        stepperTouch.stepper.setMax(5);
        stepperTouch.stepper.setMin(0);
        stepperTouch.setVisibility(View.INVISIBLE);
        textView_settingsTitle_2.setVisibility(View.INVISIBLE);
        textView_settingsDetail_2.setVisibility(View.INVISIBLE);
        stepperTouch.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int i, boolean b) {
                if(i==0) {
                    Toast.makeText(getApplicationContext(), "Sensitivity cannot be lower than 0", Toast.LENGTH_SHORT).show();
                }

                if(i==5) {
                    Toast.makeText(getApplicationContext(), "Sensitivity cannot be higher than 5", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ((Switch)findViewById(R.id.switch_alert)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    Toast.makeText(getApplicationContext(), "Alert on", Toast.LENGTH_SHORT).show();
                    stepperTouch.setVisibility(View.VISIBLE);
                    textView_settingsTitle_2.setVisibility(View.VISIBLE);
                    textView_settingsDetail_2.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), "Alert off", Toast.LENGTH_SHORT).show();
                    stepperTouch.setVisibility(View.INVISIBLE);
                    textView_settingsTitle_2.setVisibility(View.INVISIBLE);
                    textView_settingsDetail_2.setVisibility(View.INVISIBLE);
                }

            }
        });

    }
}
