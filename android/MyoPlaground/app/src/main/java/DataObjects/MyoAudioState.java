package DataObjects;

import com.thalmic.myo.Arm;
import com.thalmic.myo.Pose;
import com.thalmic.myo.XDirection;

/**
 * Created by nick on 2014-10-04.
 */
public class MyoAudioState
{
    public MyoActionState actionState;
    public Pose pose;
    public Arm arm;
    public XDirection direction;

    public boolean finishedAction;
    public boolean volumeMode;

    public enum MyoActionState
    {
        UNKNOWN,
        ACTIVE,
        INACTIVE
    }

    public MyoAudioState()
    {
        resetState();
    }

    public void resetState()
    {
        actionState = MyoActionState.UNKNOWN;
        pose = Pose.UNKNOWN;
        arm = Arm.UNKNOWN;
        direction = XDirection.UNKNOWN;
        finishedAction = false;
        volumeMode = false;
    }

    public boolean readyForInput()
    {
        return (actionState == MyoActionState.ACTIVE);
    }

    public boolean brokenConnection()
    {
        return (actionState == MyoActionState.UNKNOWN);
    }

}
