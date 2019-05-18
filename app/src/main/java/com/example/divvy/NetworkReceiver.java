package com.example.divvy;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class NetworkReceiver extends ResultReceiver {
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */

    DataReceiver receiver;
    public NetworkReceiver(Handler handler, DataReceiver receiver) {
        super(handler);
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        receiver.onReceiveResult(resultCode,resultData);
    }

    public interface DataReceiver {
         void onReceiveResult(int resultCode, Bundle resultData);
    }

}