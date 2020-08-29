package com.antoci.ai;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Date;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import static java.lang.Thread.sleep;
/**
 * This is the entry point of the App.
 *
 */
public class MainActivity extends IOIOActivity {

    // the reference to the led
    private DigitalOutput onboardLed;

    // other variables used for printing
    private Handler mHandler;
    private TextView mLog;
    boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLog = findViewById(R.id.textViewLog);
        mLog.setText("Waiting for device connection \n");
        mHandler = new Handler();
    }

    private Callback mCallback = new Callback() {
        @Override
        public void onLog(final String msg) {
            mHandler.post(new Runnable() {
                  @Override
                  public void run() {
                     mLog.append(msg+"\n");
                  }
              });
        }

        @Override
        public void onConnected(IOIO ioio) throws ConnectionLostException {
            onboardLed = ioio.openDigitalOutput(0, true);
        }

        @Override
        public void loop() {
            try {
                // write the value to the led
                onboardLed.write(on);

                // wait 0.5 seconds
                sleep(500);

                // switch the variable and wait for the second clock tick.
                on = !on;
                // wait 500 before writing the next command
            } catch (ConnectionLostException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected() {
            onLog("onDisconnected "+new Date().toGMTString());
        }
    };

    @Override
    protected IOIOLooper createIOIOLooper() {
        return new AntoBridge(mCallback);
    }
}
