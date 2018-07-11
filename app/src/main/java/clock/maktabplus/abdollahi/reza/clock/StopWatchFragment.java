package clock.maktabplus.abdollahi.reza.clock;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchFragment extends Fragment {

    private final long MILL_IS_IN_FUTURE = 35640000; //MAX StopWatch Timer is 99:00:00.00
    private final long COUNT_DOWN_IN_TERVAL = 10;
    int hours;
    int minutes;
    int seconds;
    int milliseconds;
    long startTime;
    long time;
    TextView txtStopWatchClock;
    Button btnStartStopWatch;
    Button btnStopStopWatch;
    CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stop_watch_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configureViews();
        btnStopStopWatch.setEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        btnStartStopWatch.setEnabled(true);
        btnStopStopWatch.setEnabled(false);
    }

    private void findViews(View view){
        txtStopWatchClock = view.findViewById(R.id.txt_stopwatch_clock);
        btnStartStopWatch = view.findViewById(R.id.btn_stopwatch_start);
        btnStopStopWatch = view.findViewById(R.id.btn_stopwatch_stop);
    }

    private void configureViews(){
        txtStopWatchClock.setTypeface(Typeface.MONOSPACE);
        btnStartStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartStopWatch.setEnabled(false);
                btnStopStopWatch.setEnabled(true);
                startStopWatch();
            }
        });
        btnStopStopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                btnStopStopWatch.setEnabled(false);
                btnStartStopWatch.setEnabled(true);
            }
        });
    }

    private void startStopWatch(){
        startTime = SystemClock.uptimeMillis();
        UpdateTime();
    }

    private void UpdateTime(){
        countDownTimer = new CountDownTimer(MILL_IS_IN_FUTURE , COUNT_DOWN_IN_TERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = SystemClock.uptimeMillis() - startTime;
                computingTime(time);
                txtStopWatchClock.setText(getString(R.string.time_stopwatch , hours , minutes , seconds , milliseconds));
            }
            @Override
            public void onFinish() { }
        };
        countDownTimer.start();
    }

    private void computingTime(long time){
        milliseconds = (int)(time % 1000)/10;
        seconds = (int)(time / 1000);
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;
    }
}
