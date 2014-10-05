package Fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
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
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;

import DataObjects.MyoAudioState;

public class AudioControlFragment extends Fragment
{
    private static final int REQUEST_ENABLE_BT = 1;
    TextView txtAudioStatus;

    public AudioControlFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_control, container, false);


        txtAudioStatus = (TextView) view.findViewById(R.id.txt_audio_status);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Hub.getInstance().addListener(mAudioListener);
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
        Hub.getInstance().removeListener(mAudioListener);
        super.onDestroy();
    }

    // Listeners
    private DeviceListener mAudioListener= new AbstractDeviceListener()
    {
        MyoAudioState audioState = new MyoAudioState();

        @Override
        public void onPair(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Paired with Myo!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnect(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Connected to Myo!", Toast.LENGTH_SHORT).show();

            audioState.actionState = MyoAudioState.MyoActionState.INACTIVE;
        }

        @Override
        public void onDisconnect(Myo myo, long l)
        {
            Toast.makeText(getActivity().getBaseContext(), "Disconnected from Myo!", Toast.LENGTH_SHORT).show();
            txtAudioStatus.setTextColor(getResources().getColor(R.color.red));
            txtAudioStatus.setText(getResources().getString(R.string.text_response_not_recognized));

            audioState.resetState();
        }

        @Override
        public void onArmRecognized(Myo myo, long l, Arm arm, XDirection xDirection)
        {
            txtAudioStatus.setTextColor(getResources().getColor(R.color.green));
            txtAudioStatus.setText(getResources().getString(R.string.text_response_recognized));

            audioState.pose = Pose.UNKNOWN;
            audioState.actionState = MyoAudioState.MyoActionState.INACTIVE;
            audioState.arm = arm;
            audioState.direction = xDirection;
        }

        @Override
        public void onArmLost(Myo myo, long l)
        {
            txtAudioStatus.setTextColor(getResources().getColor(R.color.red));
            txtAudioStatus.setText(getResources().getString(R.string.text_response_not_recognized));

            audioState.resetState();
        }

        @Override
        public void onPose(Myo myo, long l, Pose pose)
        {
            if (audioState.pose == pose || audioState.brokenConnection())
            {
                return;
            }

            audioState.pose = pose;

            if (!audioState.readyForInput())
            {
                // Activate for action
                if (pose == Pose.THUMB_TO_PINKY)
                {
                    audioState.actionState = MyoAudioState.MyoActionState.ACTIVE;
                    myo.vibrate(Myo.VibrationType.SHORT);
                }
            }
            else
            {
                // Perform an action then deactivate
                audioState.actionState = MyoAudioState.MyoActionState.ACTIVE;
            }
        }

        @Override
        public void onOrientationData(Myo myo, long l, Quaternion quaternion)
        {
            if (audioState.brokenConnection() || !audioState.readyForInput())
            {
                return;
            }

            if (audioState.pose == Pose.FIST)
            {
                // TODO: Track Orientation for volume
            }
        }

        @Override
        public void onAccelerometerData(Myo myo, long l, Vector3 vector3)
        {
            // TODO: Maybe add activation by acceleration
        }
    };
}
