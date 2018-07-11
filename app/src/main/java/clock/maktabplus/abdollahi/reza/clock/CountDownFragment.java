package clock.maktabplus.abdollahi.reza.clock;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class CountDownFragment extends Fragment {

    private final long MILL_IS_IN_FUTURE = 599000; //MAX CountDown Timer is 99 Minutes And 59 Seconds
    private final long COUNT_DOWN_IN_TERVAL = 1000;
    CountDownTimer countDownTimer;
    WaveLoadingView waveLoadingView;
    long timeMilliSecond;
    int div;
    int progressValue;
    int mm;
    int ss;
    TextView txtTimeUp;
    EditText edtMM;
    EditText edtSS;
    Button btnStartPause;
    Button btnReset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.count_down_fragment , container , false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configureViews();
        txtTimeUp.setVisibility(View.INVISIBLE);
        waveLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(countDownTimer != null){
            pause();
        }
    }

    public void findViews(View view){
        txtTimeUp = view.findViewById(R.id.txt_timeup);
        edtMM = view.findViewById(R.id.edt_mm);
        edtSS = view.findViewById(R.id.edt_ss);
        btnStartPause = view.findViewById(R.id.btn_countdown_start_pause);
        btnReset = view.findViewById(R.id.btn_countdown_reset);
        waveLoadingView = view.findViewById(R.id.wave_loading);
    }

    public void configureViews(){
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = btnStartPause.getText().toString();
                switch (txt){
                    case "START":
                        if(checkTime()){
                            confingWaveLoading();
                            start();
                        }
                        break;
                    case "PAUSE":
                        pause();
                        break;
                    case "RESUME":
                        resume();
                        break;
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(MILL_IS_IN_FUTURE , COUNT_DOWN_IN_TERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateWaveLoading();
                computingTime();
                edtMM.setText(getString(R.string.time_countdown_minutes , mm));
                edtSS.setText(getString(R.string.time_countdown_seconds , ss));
            }
            @Override
            public void onFinish() { }
        };
        countDownTimer.start();
    }

    private void computingTime(){
        if (ss == 0){
            if (mm == 0){
                timeUp();
                return;
            }
            mm--;
            ss = 59;
        }
        else {
            ss--;
        }
    }

    private void timeUp(){
        txtTimeUp.setVisibility(View.VISIBLE);
        reset();
    }

    private void reset(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        waveLoadingView.setVisibility(View.INVISIBLE);
        edtSS.setFocusableInTouchMode(true);
        edtMM.setFocusableInTouchMode(true);
        edtMM.setText(null);
        edtSS.setText(null);
        btnStartPause.setText(getString(R.string.btn_start));
    }

    private void start(){
        edtSS.setFocusable(false);
        edtMM.setFocusable(false);
        txtTimeUp.setVisibility(View.INVISIBLE);
        waveLoadingView.setVisibility(View.VISIBLE);
        waveLoadingView.resumeAnimation();
        btnStartPause.setText(getString(R.string.btn_pause));
        startCountDown();
    }

    private void pause(){
        countDownTimer.cancel();
        waveLoadingView.pauseAnimation();
        btnStartPause.setText(getString(R.string.btn_resume));
    }

    private void resume(){
        waveLoadingView.resumeAnimation();
        btnStartPause.setText(getString(R.string.btn_pause));
        startCountDown();
    }

    private boolean checkTime(){
        try{
            mm = Integer.valueOf(edtMM.getText().toString());
            ss = Integer.valueOf(edtSS.getText().toString());
            return (ss < 60 && mm + ss != 0); // simplest if :)
        }
        catch (Exception e){
            return false;
        }
    }

    private void confingWaveLoading(){
        timeMilliSecond = (mm * 60 + ss) * 1000;
        div = (int) timeMilliSecond / 100;
    }

    private void updateWaveLoading(){
        timeMilliSecond = timeMilliSecond - COUNT_DOWN_IN_TERVAL;
        progressValue = (int) timeMilliSecond / div;
        waveLoadingView.setProgressValue(progressValue);
        if(progressValue == 0){
            waveLoadingView.setVisibility(View.INVISIBLE);
        }
    }
}
