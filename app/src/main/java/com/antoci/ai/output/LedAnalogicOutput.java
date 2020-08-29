package com.antoci.ai.output;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;

/**
 * Created by Adrian Antoci on 02/03/2018.
 */
public class LedAnalogicOutput {
    private IOIO mRef;
    private int mPin;
    private PwmOutput mOutput;

    public LedAnalogicOutput(IOIO ref, int pin){
        mPin = pin;
        mRef = ref;
    }

    public IOIO ref(){
        return this.mRef;
    }

    public boolean setIntensity(int value){
        if (value < 0 || value > 100){
            return false;
        }

        value = 100 - value;

        try {

            if (mOutput == null) {
                mOutput = mRef.openPwmOutput(mPin, 100);
            }

            float calc = (value / 100F);

            mOutput.setDutyCycle(calc);

            return true;
        } catch (Exception e) {
            if (mOutput != null) {
                mOutput.close();
            }
            mOutput = null;
            return false;
        }
    }
}
