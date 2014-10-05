var index =
[
    [ "Getting Started", "getting-started.html", [
      [ "Getting To Know The Myo Armband", "getting-started.html#getting-to-know-the-myo", [
        [ "The Myo Armband's Physical Characteristics", "getting-started.html#physical", null ],
        [ "A Developer's View Of The Myo Armband", "getting-started.html#developer-view", null ],
        [ "Installing The Bluetooth Dongle Driver", "getting-started.html#dongle-driver", null ],
        [ "Myo Connect", "getting-started.html#myo-connect-guide", null ],
        [ "Controlling Applications", "getting-started.html#myo-connect-apps", null ],
        [ "Myo Scripts", "getting-started.html#myo-connect-scripts", null ],
        [ "Running The Samples", "getting-started.html#running-samples", null ]
      ] ]
    ] ],
    [ "The Myo SDK", "the-sdk.html", [
      [ "Getting To Know The SDK", "the-sdk.html#in-the-sdk", [
        [ "Contents of the SDK", "the-sdk.html#sdk-contents", null ],
        [ "The libmyo Library", "the-sdk.html#library-structure", null ],
        [ "Core SDK Concepts", "the-sdk.html#sdk-concepts", [
          [ "The Hub", "the-sdk.html#concept-hub", null ],
          [ "Events", "the-sdk.html#concept-events", null ]
        ] ],
        [ "The C++ Bindings", "the-sdk.html#cxx-bindings", null ]
      ] ],
      [ "Building Applications With The Myo SDK", "the-sdk.html#building-apps", [
        [ "Building The Samples", "the-sdk.html#building-samples", null ],
        [ "Building C++ Applications With The Myo SDK", "the-sdk.html#building-cxx-apps", [
          [ "Building Apps on Windows", "the-sdk.html#building-cxx-apps-windows", null ],
          [ "Building Apps on Mac OS X", "the-sdk.html#building-cxx-apps-macosx", null ]
        ] ]
      ] ]
    ] ],
    [ "Writing Myo Scripts", "script-tutorial.html", [
      [ "Interfacing with Myo Connect", "script-tutorial.html#script-tutorial-overview", null ],
      [ "A Simple Myo Script", "script-tutorial.html#simple-example", null ],
      [ "A More Practical Example", "script-tutorial.html#full-example", [
        [ "Keyboard Commands", "script-tutorial.html#presentation-keyboard", null ],
        [ "Lock / Unlock Behaviour", "script-tutorial.html#presentation-unlocking", null ],
        [ "Dealing With Directional Poses", "script-tutorial.html#presentation-armcomp", null ],
        [ "Handling Pose Input", "script-tutorial.html#presentation-poses", null ],
        [ "Timed Behaviour", "script-tutorial.html#presentation-timer", null ],
        [ "Controlling The Right Application", "script-tutorial.html#presentation-activeapp", null ]
      ] ]
    ] ],
    [ "Myo Script API Reference", "script-reference.html", [
      [ "The Lua Environment", "script-reference.html#script-api-overview", null ],
      [ "Global Variables", "script-reference.html#script-api-globals", [
        [ "Script ID", "script-reference.html#script-api-schemeid", null ],
        [ "Platform", "script-reference.html#script-api-platform", null ]
      ] ],
      [ "Callback Functions", "script-reference.html#script-api-callbacks", [
        [ "activeAppName()", "script-reference.html#script-api-activeappname", null ],
        [ "onForegroundWindowChange(app, title)", "script-reference.html#script-api-forgroundwindowchange", null ],
        [ "onActiveChange(isActive)", "script-reference.html#script-api-activechange", null ],
        [ "onPoseEdge(pose, edge)", "script-reference.html#script-api-poseedge", null ],
        [ "onPeriodic()", "script-reference.html#script-api-periodic", null ]
      ] ],
      [ "API Functions", "script-reference.html#script-api-scriptapi", [
        [ "myo.vibrate(vibrationType)", "script-reference.html#script-api-vibrate", null ],
        [ "myo.getArm()", "script-reference.html#script-api-getArm", null ],
        [ "myo.getXDirection()", "script-reference.html#script-api-getxdirection", null ],
        [ "myo.getTimeMilliseconds()", "script-reference.html#script-api-gettime", null ],
        [ "myo.debug(output)", "script-reference.html#script-api-debug", null ],
        [ "myo.getRoll()", "script-reference.html#script-api-getroll", null ],
        [ "myo.getPitch()", "script-reference.html#script-api-getpitch", null ],
        [ "myo.getYaw()", "script-reference.html#script-api-getyaw", null ],
        [ "myo.keyboard(key, edge, modifiers...)", "script-reference.html#script-api-keyboard", null ],
        [ "myo.mouse(button, edge)", "script-reference.html#script-api-clickMouse", null ],
        [ "myo.centerMousePosition()", "script-reference.html#script-api-centerMousePosition", null ],
        [ "myo.controlMouse(enabled)", "script-reference.html#script-api-controlMouse", null ],
        [ "myo.mouseControlEnabled()", "script-reference.html#script-api-mouseControlEnabled", null ]
      ] ]
    ] ],
    [ "Release Notes And Known Issues", "release-notes.html", [
      [ "Known Issues and Limitations", "release-notes.html#known-issues", null ],
      [ "Release History", "release-notes.html#release-history", [
        [ "Beta Release 4", "release-notes.html#release-beta4", null ],
        [ "Beta Release 3", "release-notes.html#release-beta3", null ],
        [ "Beta Release 2", "release-notes.html#release-beta2", null ],
        [ "Beta Release 1", "release-notes.html#release-beta1", null ],
        [ "Alpha Release 6", "release-notes.html#release-alpha6", null ],
        [ "Alpha Release 5", "release-notes.html#release-alpha5", null ],
        [ "Alpha Release 4", "release-notes.html#release-alpha4", null ],
        [ "Alpha Release 3", "release-notes.html#release-alpha3", null ],
        [ "Alpha Release 2", "release-notes.html#release-alpha2", null ],
        [ "Alpha Release 1", "release-notes.html#release-alpha1", null ]
      ] ]
    ] ]
];