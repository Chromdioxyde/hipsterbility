package de.hsosnabrueck.iui.informatik.vma.hipsterbility.activities.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.model.DeviceEntity;

/**
 * Created by Albert on 30.05.2014.
 */
public class DeviceDetailsFragment extends Fragment {

    private DeviceEntity device;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(de.hsosnabrueck.iui.informatik.vma.hipsterbility.R.layout.device_details_fragment, container, false);
    }
}