package com.antoci.ai;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Created by Adrian Antoci on 08/09/2017.
 */

public interface Callback {
    void onLog(String msg);
    void onConnected(IOIO ioio_) throws ConnectionLostException;
    void loop();
    void onDisconnected();
}
