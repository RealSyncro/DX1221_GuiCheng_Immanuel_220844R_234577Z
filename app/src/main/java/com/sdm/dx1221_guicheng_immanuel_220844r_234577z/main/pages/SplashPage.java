package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

public class SplashPage extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    startActivity(new Intent().setClass(SplashPage.this, MainMenu.class));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        splashThread.start();
    }

    @Override
    public void onClick(View v) {
    }
}
