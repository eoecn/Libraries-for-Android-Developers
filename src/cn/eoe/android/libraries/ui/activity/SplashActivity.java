package cn.eoe.android.libraries.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import cn.eoe.android.libraries.R;
import cn.eoe.android.libraries.util.LogUtil;

public class SplashActivity extends Activity {

    /**
     * @brief 模拟一个耗时4s的网络请求 此处可以做版本更新之类的操作
     * @param long millisInFuture
     * @param long countDownInterval
     */
    public CountDownTimer countdowntimer = new CountDownTimer(2000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            LogUtil.i("正在加载中···");
        }

        @Override
        public void onFinish() {
            startMain();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoTitle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        countdowntimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countdowntimer.cancel();
    }

}
