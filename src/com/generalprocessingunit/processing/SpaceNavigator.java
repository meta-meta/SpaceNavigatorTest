package com.generalprocessingunit.processing;

        import processing.core.*;
        import procontroll.*;
        import net.java.games.input.*;

public class SpaceNavigator extends PApplet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static void main(String args[]) {
        PApplet.main(new String[] { /*"--present",*/ SpaceNavigator.class.getCanonicalName() });
    }


    ControllIO controll;
    ControllDevice device; // my SpaceNavigator
    ControllSlider sliderXpos; // Positions
    ControllSlider sliderYpos;
    ControllSlider sliderZpos;
    ControllSlider sliderXrot; // Rotations
    ControllSlider sliderYrot;
    ControllSlider sliderZrot;
    ControllButton button1; // Buttons
    ControllButton button2;

    PVector location = new PVector();
    PVector momentum = new PVector();
    Orientation orientation = new Orientation();
    PVector rotMomentum = new PVector();

    public void setup() {
        size(1280, 720, P3D);

        controll = ControllIO.getInstance(this);
        device = controll.getDevice("SpaceNavigator");
        device.setTolerance(1.00f);

        sliderXpos = device.getSlider(2);
        sliderYpos = device.getSlider(0);
        sliderZpos = device.getSlider(1);
        sliderXrot = device.getSlider(5);
        sliderYrot = device.getSlider(3);
        sliderZrot = device.getSlider(4);
        button1 = device.getButton(0);
        button2 = device.getButton(1);
        sliderXpos.setMultiplier(0.01f); // sensitivities
        sliderYpos.setMultiplier(0.01f);
        sliderZpos.setMultiplier(0.01f);
        sliderXrot.setMultiplier(-0.0001f);
        sliderYrot.setMultiplier(-0.0001f);
        sliderZrot.setMultiplier(-0.0001f);


        super.setup();
    }

    public void draw() {
        background(33, 170, 170);

        updateScene();

        translate(width/2, height/2);

        pushMatrix();

        translate(location.x, location.y, location.z);

        AxisAngle aa = orientation.getOrientation();
        rotate(aa.w, aa.x, aa.y, aa.z);

        fill(204, 102, 0);
        box(20, 30, 20);

        pushMatrix();
        fill(0);
        translate(0, 15, -15);
        box(30,3, 70);
        popMatrix();

        pushMatrix();
        translate(0, 0, -30);

        fill(200, 40, 200);
        box(20, 20, 20);
        popMatrix();

        popMatrix();
    }

    public void updateScene() {

        PVector m = PVector.add(
                PVector.add(
                    PVector.mult(orientation.xAxis(), sliderXpos.getValue() / 10),
                    PVector.mult(orientation.yAxis(), sliderYpos.getValue() / 10)
                ),
                PVector.mult(orientation.zAxis(), sliderZpos.getValue() / 10)
        ) ;

        momentum.add(m);

        rotMomentum.add(
                sliderXrot.getValue() / 10,
                sliderYrot.getValue() / 10,
                sliderZrot.getValue() / 10
        );
        
        location.add(momentum);

        orientation.yaw(rotMomentum.y);
        orientation.pitch(rotMomentum.x);
        orientation.roll(rotMomentum.z);


        float drag = 0.02f;
        momentum.x = momentum.x - momentum.x * drag;
        momentum.y = momentum.y - momentum.y * drag;// + 0.01f;
        momentum.z = momentum.z - momentum.z * drag;
        rotMomentum.x = rotMomentum.x - rotMomentum.x * drag;
        rotMomentum.y = rotMomentum.y - rotMomentum.y * drag;
        rotMomentum.z = rotMomentum.z - rotMomentum.z * drag;
    }
}