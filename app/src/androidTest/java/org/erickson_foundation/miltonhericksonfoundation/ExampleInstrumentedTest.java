package org.erickson_foundation.miltonhericksonfoundation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation no_info_to_display, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under no_info_to_display.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.erickson_foundation.miltonhericksonfoundation", appContext.getPackageName());
    }
}
