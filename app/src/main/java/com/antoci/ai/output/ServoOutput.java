package com.antoci.ai.output;


import com.antoci.ai.util.AtomMath;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;

/**
 * Created by alex on 02/03/2018.
 * */

public class ServoOutput {
    private IOIO mRef;
    private int mPin;
    private PwmOutput mOutput;


    public ServoOutput(IOIO ref, int pin){
        mPin = pin;
        mRef = ref;
    }

    public IOIO ref(){
        return this.mRef;
    }


    /**
     *
     * @param value In degrees
     * @return true if turned successfully
     */
    public boolean setIntensity(int value){
        value = AtomMath.map(value, 0, 180, 960, 2400);

        if (value < 700){
            value = 700;
        }

        if (value > 2500){
            value = 2500;
        }

        try {
            if (mOutput == null) {
                mOutput = mRef.openPwmOutput(new DigitalOutput.Spec(mPin, DigitalOutput.Spec.Mode.NORMAL), 100);
            }

            mOutput.setPulseWidth(value);

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
