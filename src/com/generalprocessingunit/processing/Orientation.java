package com.generalprocessingunit.processing;

import processing.core.PVector;

public class Orientation {
    private PVector xAxis = new PVector(1, 0, 0);
    private PVector yAxis = new PVector(0, 1, 0);
    private PVector zAxis = new PVector(0, 0, 1);

    private Quaternion orientation = new Quaternion();

    public void yaw(float theta) {
        Quaternion q = Quaternion.fromAxis(theta, yAxis);
        orientation = q.mult(orientation);
        xAxis = q.rotateVector(xAxis);
        zAxis = q.rotateVector(zAxis);
    }

    public void pitch(float theta) {
        Quaternion q = Quaternion.fromAxis(theta, xAxis);
        orientation = q.mult(orientation);
        yAxis = q.rotateVector(yAxis);
        zAxis = q.rotateVector(zAxis);
    }

    public void roll(float theta) {
        Quaternion q = Quaternion.fromAxis(theta, zAxis);
        orientation = q.mult(orientation);
        xAxis = q.rotateVector(xAxis);
        yAxis = q.rotateVector(yAxis);
    }

    public AxisAngle getOrientation() {
        return orientation.getAxisAngle();
    }

    public PVector xAxis() {
        return xAxis.get();
    }

    public PVector yAxis() {
        return yAxis.get();
    }

    public PVector zAxis() {
        return zAxis.get();
    }
}
