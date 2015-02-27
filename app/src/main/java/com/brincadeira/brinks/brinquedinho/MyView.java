package com.brincadeira.brinks.brinquedinho;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;

/**
 * Classe MyView demontra o uso do acelerômetro para movimentar objetos na tela.
 *
 */
public class MyView extends View implements  Runnable, SensorEventListener{

    private long time = 1;
    private float x;
    private float y;
    private float directionX = 50;
    private float directionY = 50;
    private float accelerometerX;
    private float accelerometerY;
    private float radius  = 50;

    public SensorManager mSensorManager;
    public Sensor mSensor;
    public Paint text;
    public Paint paint;
    public float velocidade = 0.5f;

    public MyView(Context context) {
        super(context);
        // Inicia um sensor de serviço, captura o giroscopio e registra a
        // classe atual como ouvinte do evento.
        mSensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);


        // Permitindo Touch
        setFocusableInTouchMode(true);
        setClickable(true);
        // Permite um click longo
        setLongClickable(true);

        paint = new Paint();
        text = new Paint();

        // Registra como o metodo run da class como
        // uma thread background
        new Thread(this).start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.x = getWidth()/2;
        this.y = getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, paint);

        text.setColor(Color.BLACK);
        text.setTextSize(40);
        canvas.drawText("Position x: "+Math.round(x),10,55,text);
        canvas.drawText("Position y: "+Math.round(y),10,110,text);
    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(time);
            } catch (Exception e) {
                Log.e(MainActivity.TAG,"Deu o erro: "+e.getMessage());
            }
            loop();
            postInvalidate();
        }
    }

    /**
     * Responsavel pela movimentação da bola de teste
     *
     */
    public void loop() {

        directionY = accelerometerY *velocidade;
        directionX = -accelerometerX *velocidade;
        x += directionX;
        y += directionY;

        // Respeita os limites da tela
        if (x > getWidth() - radius)  {
            x -= 5;
            paint.setColor(Color.BLUE);
        }
        if (x < radius+1) {
            x+= 5;
            paint.setColor(Color.RED);
        }
        if (y > getHeight() - radius)  {
            y -= 5;
            paint.setColor(Color.YELLOW);
        }
        if (y < radius+1) {
            y+= 5;
            paint.setColor(Color.GREEN);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerX = event.values[0];
            accelerometerY = event.values[1];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Limpa o ouvinte de eventos do acelerômetro
     */
    public void destroy() {
        mSensorManager.unregisterListener(this);
    }
}
