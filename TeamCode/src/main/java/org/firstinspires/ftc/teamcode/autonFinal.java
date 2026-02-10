package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "PID_Full_Auton", group = "FTC")
public class autonFinal extends LinearOpMode {

    public DcMotorEx frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;
    public DcMotorEx shooter; // Added shooter motor
    public VoltageSensor batteryVoltageSensor;

    // PID Constants
    double Kp = 1.5;
    double Ki = 0.01;
    double Kd = 0.1;

    double integralSum = 0;
    double lastError = 0;
    double lastTime = 0;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Hardware Mapping
        frontLeftDrive  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeftDrive   = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRightDrive  = hardwareMap.get(DcMotorEx.class, "backRight");
        shooter         = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        // --- THE RANGE OF MOTIONS ---

        // 1. Move Forward for 1.5 seconds
        telemetry.addLine("Moving Forward...");
        movePID(1.5, 1, 1, 1, 1);

        // 2. Short pause
        stopRobot();
        sleep(200);

        // 3. Turn Right for 0.8 seconds (Left side positive, Right side negative)
        telemetry.addLine("Turning Right...");
        movePID(0.8, 1, -1, 1, -1);

        stopRobot();
        sleep(200);

        // 4. Shoot the ball (Simple timed action, usually no PID needed for shooter)
        telemetry.addLine("Shooting...");// ...
        runShooter(2.0, 1.0);

        // 5. Move Backward for 1 second
        telemetry.addLine("Moving Backward...");
        movePID(1.0, -1, -1, -1, -1);

        stopRobot();
    }

    /**
     * Universal PID movement method
     * @param targetTime How long to move
     * @param fl, fr, bl, br Motor power multipliers (use 1, -1, or 0)
     */
    public void movePID(double targetTime, double fl, double fr, double bl, double br) {
        timer.reset();
        integralSum = 0;
        lastError = 0;
        lastTime = 0;

        while (opModeIsActive() && timer.seconds() < targetTime) {
            double currentTime = timer.seconds();
            double error = targetTime - currentTime;
            double dt = currentTime - lastTime;

            // Get current voltage
            double currentVoltage = batteryVoltageSensor.getVoltage();

            // Calculate a compensation factor (Assuming you tuned your robot at 12.0 Volts)
            double voltageCompensation = 12.0 / currentVoltage;

            if (dt == 0) dt = 0.0001; // Prevent divide by zero

            double derivative = (error - lastError) / dt;
            integralSum += (error * dt);

            double out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);
            double power = Math.max(-1, Math.min(1, out));

            // Apply power multiplied by compensation
            // If battery is low (10V), it boosts power. If battery is high (14V), it lowers power.
            double drivePower = (power * voltageCompensation);

            // Apply the power to each motor based on the multipliers
            frontLeftDrive.setPower(drivePower * fl);
            frontRightDrive.setPower(drivePower * fr);
            backLeftDrive.setPower(drivePower * bl);
            backRightDrive.setPower(drivePower * br);

            lastError = error;
            lastTime = currentTime;

            telemetry.addData("Time Left", error);
            telemetry.update();
        }
        stopRobot();
    }

    public void runShooter(double seconds, double power) {
        shooter.setPower(power);
        sleep((long)(seconds * 1000));
        shooter.setPower(0);
    }

    public void setDriveMode(DcMotor.RunMode mode) {
        frontLeftDrive.setMode(mode);
        frontRightDrive.setMode(mode);
        backLeftDrive.setMode(mode);
        backRightDrive.setMode(mode);
    }

    public void stopRobot() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
    }
}