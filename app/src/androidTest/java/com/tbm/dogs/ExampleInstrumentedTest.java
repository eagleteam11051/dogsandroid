package com.tbm.dogs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.tbm.dogs", appContext.getPackageName());
//        HandlerP handlerP = new HandlerP(null,appContext);
//
//        handlerP.getJsonDirection(Var.INSTANCE.getMAP_DIRECTION_URL(),"21,105","21,106",Var.INSTANCE.getMAP_DIRECTION_KEY());
    }
}
