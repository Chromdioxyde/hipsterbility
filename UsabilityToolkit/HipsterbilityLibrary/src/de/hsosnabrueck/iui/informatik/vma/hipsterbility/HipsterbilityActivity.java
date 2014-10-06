package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * Created by Albert on 26.03.2014.
 */
public abstract class HipsterbilityActivity extends Activity {

    /**
     * Overridden to send all MotionEvents to the Hipsterbility Framework at the source where the Activity first gets them.
     * This way, it can be ensured that all MotionEvents, also the ones consumed by Views, will be registered.
     *
     * @param ev MotionEvent from Android system
     * @return super implementation return value
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Hipsterbility.relayMotionEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}
