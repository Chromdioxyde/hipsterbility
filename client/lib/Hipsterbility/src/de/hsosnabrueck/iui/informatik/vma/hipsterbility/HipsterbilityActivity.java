package de.hsosnabrueck.iui.informatik.vma.hipsterbility;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Albert on 26.03.2014.
 */
public abstract class HipsterbilityActivity extends Activity {

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Hipsterbility.relayMotionEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}
