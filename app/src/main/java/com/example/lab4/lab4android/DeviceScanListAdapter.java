package com.example.lab4.lab4android;

import android.content.Context;
import android.graphics.Color;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab4.lab4android.utils.BTUtils;
import com.robotpajamas.blueteeth.BlueteethDevice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class DeviceScanListAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private List<BlueteethDevice> mDevices;

     DeviceScanListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDevices = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public BlueteethDevice getItem(int position) {
        return mDevices.get(position).;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listitem_device_scan, parent, false);
            holder = new DeviceHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (DeviceHolder) convertView.getTag();
        }

        BlueteethDevice device = getItem(position);
        holder.deviceName.setText(device.getName());
        holder.deviceMac.setText(device.getMacAddress());
        ParcelUuid[] uuids = device.getBluetoothDevice().getUuids();
        String serviceUUID = BTUtils.SERVICE_UUID.toString();


        if(uuids==null){

            Timber.e("uuids[] is null");

        }else {

            for (ParcelUuid uuid : uuids) {
                if (serviceUUID.equalsIgnoreCase(uuid.toString())) {
                    Timber.d("found uuid for mac " + device.getMacAddress());
                    convertView.setBackgroundColor(Color.BLUE);
                    break;
                } else {
                    convertView.setBackgroundColor(Color.GRAY);
                }
            }

        }
        return convertView;
    }

    public void add(BlueteethDevice device) {
        // Add only unique devices
        boolean isAlreadyInList = false;
        for (BlueteethDevice d : mDevices) {
            if (device.getMacAddress().equals(d.getMacAddress())) {
                isAlreadyInList = true;
                break;
            }
        }

        if (!isAlreadyInList) {
            mDevices.add(device);
            notifyDataSetChanged();
        }
    }

     void clear() {
        mDevices.clear();
        notifyDataSetChanged();
    }

     public static class DeviceHolder {

        @BindView(R.id.textview_device_name)
        TextView deviceName;

        @BindView(R.id.textview_device_mac)
        TextView deviceMac;

         DeviceHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }

}