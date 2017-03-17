package com.example.lab4.lab4android;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lab4.lab4android.utils.BTUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import timber.log.Timber;


public class ScannerFragment extends Fragment{

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private DeviceScanListAdapter mDeviceAdapter;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        ButterKnife.bind(this, view);
        mDeviceAdapter = new DeviceScanListAdapter(this.getContext());
        ListView listViewScannedItems = (ListView) view.findViewById(R.id.listViewScannedItems);
        listViewScannedItems.setAdapter(mDeviceAdapter);
        return view;
    }


    @OnCheckedChanged(R.id.buttonScanner)
    void changeScanStatus(CompoundButton buttonView, boolean isChecked){
        Timber.d("Scan button clicked");
        if(isChecked){
            // Clear existing devices (assumes none are connected)
            mDeviceAdapter.clear();
            progressBar.setVisibility(View.VISIBLE);
            BTUtils.startScanning(this.getContext(), mDeviceAdapter, buttonView,progressBar);
        }else{
            BTUtils.stopScanning(this.getContext());
            progressBar.setVisibility(View.INVISIBLE);
        }
    }




}
