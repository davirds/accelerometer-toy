package com.brincadeira.brinks.brinquedinho;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by davi on 13/02/2015.
 */
public class MainActivity extends Activity {

    final public static String TAG = "APPBrinquedinho";
    public  MyView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicia uma View
        view = new MyView(this);
        setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.destroy();
    }
}
