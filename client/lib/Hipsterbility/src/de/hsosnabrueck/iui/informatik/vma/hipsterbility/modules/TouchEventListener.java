package de.hsosnabrueck.iui.informatik.vma.hipsterbility.modules;

import android.app.Activity;
import android.view.MotionEvent;

import java.util.EventListener;

/**
 * Created by Albert on 22.05.2014.
 */
public interface TouchEventListener extends EventListener{

    void onTouchEvent(TouchEvent e);
}
