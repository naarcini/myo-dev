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
    public double refRoll;

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
        refRoll = 0.0;
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
