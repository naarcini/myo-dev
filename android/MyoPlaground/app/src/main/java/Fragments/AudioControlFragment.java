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
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
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

import java.util.ArrayList;

import DataObjects.MyoAudioState;

public class AudioControlFragment extends Fragment
{
    private static final int REQUEST_ENABLE_BT = 1;
    AudioManager am;

    TextView txtAudioStatus;
    TextView txtInputActive;
    TextView txtVolumeMode;
    TextView txtArmRotation;

    public AudioControlFragment()
    {
        // Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_control, container, false);

        txtAudioStatus = (TextView) view.findViewById(R.id.txt_audio_status);
        txtInputActive = (TextView) view.findViewById(R.id.txt_audio_controller_active);
        txtVolumeMode = (TextView) view.findViewById(R.id.txt_volume_mode);
        txtArmRotation = (TextView) view.findViewById(R.id.txt_arm_rotation);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Hub.getInstance().addListener(mAudioListener);

        Activity a = getActivity();
        am = (AudioManager) a.getSystemService(Context.AUDIO_SERVICE);
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
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            Hub.getInstance().pairWithAnyMyo();
        }
        else
        {
            // Assume only one connected at any given time
            Myo.ConnectionState connectionState = myoList.get(0).getConnectionState();
            setDataFieldsReady(connectionState == Myo.ConnectionState.CONNECTED);
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
        Hub.getInstance().removeListener(mAudioListener);
        super.onDestroy();
    }

    public void setDataFieldsReady(boolean ready)
    {
        if (ready)
        {
            txtAudioStatus.setTextColor(getResources().getColor(R.color.green));
            txtAudioStatus.setText(getResources().getString(R.string.text_response_recognized));

            txtInputActive.setText("FALSE");
            txtVolumeMode.setText("FALSE");
            txtArmRotation.setText("");
        }
        else
        {
            txtAudioStatus.setTextColor(getResources().getColor(R.color.red));
            txtAudioStatus.setText(getResources().getString(R.string.text_response_not_recognized));

            txtInputActive.setText("");
            txtVolumeMode.setText("");
            txtArmRotation.setText("");
        }
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
            setDataFieldsReady(false);

            audioState.resetState();
        }

        @Override
        public void onArmRecognized(Myo myo, long l, Arm arm, XDirection xDirection)
        {
            setDataFieldsReady(true);

            audioState.pose = Pose.UNKNOWN;
            audioState.actionState = MyoAudioState.MyoActionState.INACTIVE;
            audioState.arm = arm;
            audioState.direction = xDirection;
        }

        @Override
        public void onArmLost(Myo myo, long l)
        {
            setDataFieldsReady(false);

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
            txtAudioStatus.setText("Pose: " + audioState.pose.name());

            if (!audioState.readyForInput())
            {
                // Activate for action
                if (pose == Pose.THUMB_TO_PINKY)
                {
                    audioState.actionState = MyoAudioState.MyoActionState.ACTIVE;
                    audioState.finishedAction = false;
                    txtInputActive.setText("TRUE");
                    myo.vibrate(Myo.VibrationType.SHORT);
                }
            }
            else if (!audioState.finishedAction && !audioState.volumeMode)
            {
                // Perform an action
                switch (pose)
                {
                    case FINGERS_SPREAD:
                        // TODO: Pause/Start
                        audioState.finishedAction = true;
                        break;
                    case WAVE_IN:
                        // TODO: Next song
                        audioState.finishedAction = true;
                        break;
                    case WAVE_OUT:
                        // TODO: Previous song
                        audioState.finishedAction = true;
                        break;
                    case FIST:
                        // TODO: Enter Volume mode
                        audioState.volumeMode = true;
                        txtVolumeMode.setText("TRUE");
                        break;
                    case THUMB_TO_PINKY:
                        audioState.actionState = MyoAudioState.MyoActionState.INACTIVE;
                        audioState.finishedAction = true;
                        myo.vibrate(Myo.VibrationType.MEDIUM);
                        break;
                    case REST:
                    case UNKNOWN:
                    default:
                        break;
                }

                if (audioState.finishedAction)
                {
                    txtInputActive.setText("FALSE");
                    txtVolumeMode.setText("FALSE");
                    txtArmRotation.setText("");
                }
            }
            else
            {
                // We must have already finished an action, so just deactivate
                audioState.actionState = MyoAudioState.MyoActionState.INACTIVE;
                audioState.finishedAction = true;
                audioState.volumeMode = false;

                txtInputActive.setText("FALSE");
                txtVolumeMode.setText("FALSE");
                txtArmRotation.setText("");
            }
        }

        @Override
        public void onOrientationData(Myo myo, long l, Quaternion quaternion)
        {
            if (audioState.brokenConnection() || !audioState.volumeMode)
            {
                audioState.refRoll = Quaternion.roll(quaternion);
                return;
            }

            // We are now in a fist in active mode. Adjust music volume as needed.

            double newRoll = Quaternion.roll(quaternion);
            double rollDelta = newRoll - audioState.refRoll;
            txtArmRotation.setText(String.format("%1.3f", rollDelta));

            if (Math.abs(rollDelta) > 0.15 && am != null)
            {
                audioState.refRoll = newRoll;

                if (rollDelta > 0)
                {
                    am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
                }
                else
                {
                    am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
                }
            }
        }

        @Override
        public void onAccelerometerData(Myo myo, long l, Vector3 vector3)
        {
            // TODO: Maybe add activation by acceleration
        }
    };
}
