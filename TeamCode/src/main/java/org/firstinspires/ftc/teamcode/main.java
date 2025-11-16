package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class main extends LinearOpMode {
    private movement drive;

    public DcMotor shooterOne;
    public DcMotor shooterTwo;
    public DcMotor intake;


    @Override
    public void runOpMode() {
        drive = new movement(hardwareMap);

        shooterOne = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterTwo = hardwareMap.get(DcMotor.class, "shooterRight");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");

        shooterOne.setDirection(DcMotor.Direction.FORWARD);
        shooterTwo.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);

        double rightTriggerValue;
        double leftTriggerValue;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Call the drive method from the helper class
            telemetry.addData("RT", gamepad1.right_trigger);
            telemetry.addData("LT", gamepad1.left_trigger);
            telemetry.update();

            rightTriggerValue = gamepad1.right_trigger;
            leftTriggerValue = gamepad1.left_trigger;

            intake.setPower(-leftTriggerValue);
            intake.setPower(rightTriggerValue);

            drive.teleopDrive(gamepad1);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
