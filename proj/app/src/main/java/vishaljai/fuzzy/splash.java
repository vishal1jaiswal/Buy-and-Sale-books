package vishaljai.fuzzy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Thread t1=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent op1=new Intent(splash.this,login.class);
                    startActivity(op1);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }
}
