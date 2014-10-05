/*
 * Myo Application Development
 * Copyright (C) 2014  Nicolas Arciniega
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolasarciniega.myoplaground.R;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;

import java.util.ArrayList;

public class TextResponseFragment extends Fragment
{
    private static final int REQUEST_ENABLE_BT = 1;

    TextView txtResponseStatus;
    ScrollView svDetailsContainer;

    // Arm Data
    TextView txtArm;
    TextView txtXDirection;

    // Orientation Data
    TextView txtQuaternionX;
    TextView txtQuaternionY;
    TextView txtQuaternionZ;
    TextView txtQuaternionW;
    TextView txtQuaternionLength;
    TextView txtQuaternionPitch;
    TextView txtQuaternionYaw;
    TextView txtQuaternionRoll;

    // Acceleration Data
    TextView txtAccelX;
    TextView txtAccelY;
    TextView txtAccelZ;
    TextView txtAccelLength;

    // Gyroscope Data
    TextView txtGyroX;
    TextView txtGyroY;
    TextView txtGyroZ;
    TextView txtGyroLength;

    // RSSI Data
    TextView txtRssiSignal;

    public TextResponseFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_response, container, false);

        txtResponseStatus = (TextView) view.findViewById(R.id.txt_text_response_status);
        svDetailsContainer = (ScrollView) view.findViewById(R.id.sv_details_container);

        // Arm views
        txtArm = (TextView) view.findViewById(R.id.txt_arm_detect);
        txtXDirection = (TextView) view.findViewById(R.id.txt_xdirection_detect);

        // Orientation views
        txtQuaternionX = (TextView) view.findViewById(R.id.txt_quaternion_x);
        txtQuaternionY = (TextView) view.findViewById(R.id.txt_quaternion_y);
        txtQuaternionZ = (TextView) view.findViewById(R.id.txt_quaternion_z);
        txtQuaternionW = (TextView) view.findViewById(R.id.txt_quaternion_w);
        txtQuaternionLength = (TextView) view.findViewById(R.id.txt_quaternion_length);
        txtQuaternionPitch = (TextView) view.findViewById(R.id.txt_quaternion_pitch);
        txtQuaternionYaw = (TextView) view.findViewById(R.id.txt_quaternion_yaw);
        txtQuaternionRoll = (TextView) view.findViewById(R.id.txt_quaternion_roll);

        // Acceleration views
        txtAccelX = (TextView) view.findViewById(R.id.txt_acceleration_x);
        txtAccelY = (TextView) view.findViewById(R.id.txt_acceleration_y);
        txtAccelZ = (TextView) view.findViewById(R.id.txt_acceleration_z);
        txtAccelLength = (TextView) view.findViewById(R.id.txt_acceleration_length);

        // Gyroscope views
        txtGyroX = (TextView) view.findViewById(R.id.txt_gyroscope_x);
        txtGyroY = (TextView) view.findViewById(R.id.txt_gyroscope_y);
        txtGyroZ = (TextView) view.findViewById(R.id.txt_gyroscope_z);
        txtGyroLength = (TextView) view.findViewById(R.id.txt_gyroscope_length);

        // RSSI views
        txtRssiSignal = (TextView) view.findViewById(R.id.txt_rssi_strength);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Hub.getInstance().addListener(mTextResponseListener);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        ArrayList<Myo> myoList = Hub.getInstance().getConnectedDevices();

        if (myoList.isEmpty())
        {
            // If Bluetooth is not enabled, request to turn it on.
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled())
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            Hub.getInstance().pairWithAnyMyo();
        }
        else
        {
            // Assume only one connected at any given time
            Myo.ConnectionState connectionState = myoList.get(0).getConnectionState();
            setDataContainersReady(connectionState == Myo.ConnectionState.CONNECTED);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        Hub.getInstance().removeListener(mTextResponseListener);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth, so exit.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity().getBaseContext(), "Bluetooth not enabled. Exiting...", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setDataContainersReady(boolean ready)
    {
        if (ready)
        {
            txtResponseStatus.setTextColor(getResources().getColor(R.color.green));
            txtResponseStatus.setText(getResources().getString(R.string.text_response_recognized));
            svDetailsContainer.setVisibility(View.VISIBLE);
        }
        else
        {
            txtResponseStatus.setTextColor(getResources().getColor(R.color.red));
            txtResponseStatus.setText(getResources().getString(R.string.text_response_not_recognized));
            svDetailsContainer.setVisibility(View.INVISIBLE);
        }
    }

    private DeviceListener mTextResponseListener = new DeviceListener()
    {
        String currentPose = null;

        @Override
        public void onPair(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Paired with Myo!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnect(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Connected to Myo!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnect(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Disconnected from Myo!", Toast.LENGTH_SHORT).show();
            currentPose = null;
            setDataContainersReady(false);
        }

        @Override
        public void onArmRecognized(Myo myo, long l, Arm arm, XDirection xDirection)
        {
            currentPose = null;
            setDataContainersReady(true);

            txtArm.setText(arm.name());
            txtXDirection.setText(xDirection.name());
        }

        @Override
        public void onArmLost(Myo myo, long l)
        {
            currentPose = null;
            setDataContainersReady(false);

            txtArm.setText(Arm.UNKNOWN.name());
            txtXDirection.setText(XDirection.UNKNOWN.name());
        }

        @Override
        public void onPose(Myo myo, long l, Pose pose)
        {
            if (!pose.name().equals(currentPose))
            {
                currentPose = pose.name();
                txtResponseStatus.setText("Pose: " + currentPose);
            }
        }

        @Override
        public void onOrientationData(Myo myo, long l, Quaternion quaternion)
        {
            txtQuaternionX.setText(String.format("%1.3f", quaternion.x()));
            txtQuaternionY.setText(String.format("%1.3f", quaternion.y()));
            txtQuaternionZ.setText(String.format("%1.3f", quaternion.z()));
            txtQuaternionW.setText(String.format("%1.3f", quaternion.w()));
            txtQuaternionLength.setText(String.format("%1.3f", quaternion.length()));

            txtQuaternionPitch.setText(String.format("%1.3f", Quaternion.pitch(quaternion)));
            txtQuaternionYaw.setText(String.format("%1.3f", Quaternion.yaw(quaternion)));
            txtQuaternionRoll.setText(String.format("%1.3f", Quaternion.roll(quaternion)));
        }

        @Override
        public void onAccelerometerData(Myo myo, long l, Vector3 vector3)
        {
            txtAccelX.setText(String.format("%1.3f", vector3.x()));
            txtAccelY.setText(String.format("%1.3f", vector3.y()));
            txtAccelZ.setText(String.format("%1.3f", vector3.z()));
            txtAccelLength.setText(String.format("%1.3f", vector3.length()));
        }

        @Override
        public void onGyroscopeData(Myo myo, long l, Vector3 vector3)
        {
            txtGyroX.setText(String.format("%1.3f", vector3.x()));
            txtGyroY.setText(String.format("%1.3f", vector3.y()));
            txtGyroZ.setText(String.format("%1.3f", vector3.z()));
            txtGyroLength.setText(String.format("%1.3f", vector3.length()));
        }

        @Override
        public void onRssi(Myo myo, long l, int i)
        {
            txtRssiSignal.setText(Integer.toString(i));
        }
    };
}
