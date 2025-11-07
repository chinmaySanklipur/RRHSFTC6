package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class main extends LinearOpMode {
    @Override
    public void runOpMode() {
        /*
        // Initialize motors
        // Front Left has the green sticker near it
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");

        //Have to set right motors reverse for since theyre opposite the lefts
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Waits for the driver to press the start button before running the code
        waitForStart();

        //keeps the code running as long as the robot is active
        while (opModeIsActive()) {

            // Read the joystick values from the controller:
            // y = forward/backward movement (left stick up/down)
            // x = left/right movement (left stick left/right)
            // rx = rotation/spin movement (right stick left/right)
            double y = -gamepad1.left_stick_y; // Negative to make pushing up go forward
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // Calculate how much power each motor should get
            // This combination of y, x, and rx values allows the robot
            // to move in all directions (forward, sideways, diagonally, and turn)
            double frontLeftPower = y + x + rx;
            double backLeftPower = y - x + rx;
            double frontRightPower = y - x - rx;
            double backRightPower = y + x - rx;

            // Normalize motor powers by finding the largest absolute motor power value
            // This is used to make sure no motor power goes above 1.0 (the max)
            double maxMagnitude = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(backLeftPower), Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));

            // If any motor power is too high, scale all of them down evenly
            // so that the robot moves in the correct direction but safely
            if (maxMagnitude > 1) {
                frontLeftPower /= maxMagnitude;
                backLeftPower /= maxMagnitude;
                frontRightPower /= maxMagnitude;
                backRightPower /= maxMagnitude;
            }

            // Set motor powers
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
         */
        while(OpModeIsActive()) {
            movement.Drive();
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }
}