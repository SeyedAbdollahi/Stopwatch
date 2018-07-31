package clock.maktabplus.abdollahi.reza.clock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button btnStopWatch;
    Button btnCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        configureViews();
    }

    private void findViews(){
        btnStopWatch = findViewById(R.id.btn_stopwatch);
        btnCountDown = findViewById(R.id.btn_countdown);
    }

    private void configureViews(){
        btnStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopWatchFragment stopWatchFragment = new StopWatchFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.frame, stopWatchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownFragment countDownFragment = new CountDownFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.frame , countDownFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // Set Font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
