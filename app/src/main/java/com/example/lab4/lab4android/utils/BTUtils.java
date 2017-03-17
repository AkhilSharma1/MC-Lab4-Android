package com.example.lab4.lab4android.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lab4.lab4android.DeviceScanListAdapter;
import com.example.lab4.lab4android.R;
import com.robotpajamas.blueteeth.BlueteethManager;

import timber.log.Timber;


public class BTUtils {

    public static final ParcelUuid SERVICE_UUID = ParcelUuid
            .fromString("00726f62-6f74-7061-6a61-6d61732e6361");

    private static final int REQ_BLUETOOTH_ENABLE = 1000;
    private static final int DEVICE_SCAN_MILLISECONDS = 10000;

    public static  void checkBluetoothSupport(Context context) {
        // Check for BLE support - also checked from Android manifest.
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            exitApp(context, context.getString(R.string.msg_no_ble));
        }

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            exitApp(context, context.getString(R.string.msg_no_ble));
        }

        if (btAdapter!=null && !btAdapter.isEnabled()) {
            enableBluetooth(context);
        }
    }

    private static void exitApp(Context context, String reason) {
        Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
        ((Activity)context).finish();
    }

    private static void enableBluetooth(Context context) {
        ((Activity)context).startActivityForResult(
                new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQ_BLUETOOTH_ENABLE);
    }

    public static  void startScanning(Context context, DeviceScanListAdapter mDeviceAdapter, CompoundButton buttonView, ProgressBar progressBar) {

        BlueteethManager.with(context).scanForPeripherals(DEVICE_SCAN_MILLISECONDS, bleDevices -> {
            Timber.d("On Scan completed");
            bleDevices.stream().filter(device -> !TextUtils.isEmpty(device.getBluetoothDevice().getName())).forEach(device ->
            {
                Timber.d("%s - %s", device.getName(), device.getMacAddress());
                buttonView.setChecked(false);
                progressBar.setVisibility(View.INVISIBLE);

                mDeviceAdapter.add(device);
            });
        });
    }

    public static void stopScanning(Context context) {
        BlueteethManager.with(context).stopScanForPeripherals();
        Timber.d("Scan stopped");
    }


}
