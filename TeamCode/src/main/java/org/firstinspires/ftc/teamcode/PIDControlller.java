package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDControlller {
    private double kP, kI, kD;
    private double lastError = 0;
    private double integralSum = 0;
    private ElapsedTime timer = new ElapsedTime();

    public PIDControlller (double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double update (double targetPosition, double currentPosition) {
        // Calculate the error
        double error = targetPosition - currentPosition;

        // Proportional term
        double proportional = error * kP;

        // Integral term
        integralSum += error * timer.seconds();

        // Derivative term
        double derivative = (error - lastError) / timer.seconds();

        // Calculate the total output
        double output = proportional + (kI * integralSum) + (kD * derivative);

        // Update the last error and timer
        lastError = error;
        timer.reset();

        return output;
    }
}
