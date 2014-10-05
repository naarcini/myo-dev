package Fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolasarciniega.myoplaground.R;
import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.XDirection;

public class TextResponseFragment extends Fragment
{
    private static final int REQUEST_ENABLE_BT = 1;
    TextView txtResponseStatus;

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

        // If Bluetooth is not enabled, request to turn it on.
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Hub.getInstance().pairWithAnyMyo();
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

    private DeviceListener mTextResponseListener = new AbstractDeviceListener()
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
            txtResponseStatus.setTextColor(getResources().getColor(R.color.red));
            txtResponseStatus.setText(getResources().getString(R.string.text_response_not_recognized));
        }

        @Override
        public void onArmRecognized(Myo myo, long l, Arm arm, XDirection xDirection)
        {
            currentPose = null;
            txtResponseStatus.setTextColor(getResources().getColor(R.color.green));
            txtResponseStatus.setText(getResources().getString(R.string.text_response_recognized));
        }

        @Override
        public void onArmLost(Myo myo, long l)
        {
            currentPose = null;
            txtResponseStatus.setTextColor(getResources().getColor(R.color.red));
            txtResponseStatus.setText(getResources().getString(R.string.text_response_not_recognized));
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
    };
}
