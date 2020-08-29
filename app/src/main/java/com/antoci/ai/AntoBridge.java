package com.antoci.ai;


import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

/**
 * The connection with the IOIO board.
 *
 */
public class AntoBridge extends BaseIOIOLooper {

    private Callback mCallback;

    public AntoBridge(Callback callback){
        mCallback = callback;
    }

    @Override
    protected void setup() throws ConnectionLostException {
        if (mCallback!=null) {
            showVersions(ioio_);
            mCallback.onConnected(ioio_);
        }
    }

    @Override
    public void loop() {
        if (mCallback!=null) {
            mCallback.loop();
        }
    }
    @Override
    public void disconnected() {
        if (mCallback!=null) {
            mCallback.onDisconnected();
        }
    }
    @Override
    public void incompatible() {
        mCallback.onLog("Incompatible");
    }

    private void showVersions(IOIO ioio) {
        if (mCallback  == null){
            return;
        }

        mCallback.onLog(String.format(
                        "IOIOLib: %s\n" +
                        "Application firmware: %s\n" +
                        "Bootloader firmware: %s\n" +
                        "Hardware: %s",
                ioio.getImplVersion(IOIO.VersionType.IOIOLIB_VER),
                ioio.getImplVersion(IOIO.VersionType.APP_FIRMWARE_VER),
                ioio.getImplVersion(IOIO.VersionType.BOOTLOADER_VER),
                ioio.getImplVersion(IOIO.VersionType.HARDWARE_VER)));
    }
}
