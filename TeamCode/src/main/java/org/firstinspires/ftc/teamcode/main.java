package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class main extends LinearOpMode {
    private movement drive;
    public DcMotor shooter;
    public DcMotor intake;


    @Override
    public void runOpMode() {
        drive = new movement(hardwareMap);

        shooter = hardwareMap.get(DcMotor.class, "shooterMotor");
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");

        shooter.setDirection(DcMotor.Direction.FORWARD); // might need edits based on testing
        intake.setDirection(DcMotor.Direction.REVERSE);

        double rightTriggerValue = 0;
        double leftTriggerValue = 0;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Call the drive method from the helper class
            telemetry.addData("RT", gamepad1.right_trigger);
            telemetry.addData("LT", gamepad1.left_trigger);
            telemetry.update();

            if(gamepad1.right_trigger > 0)
                rightTriggerValue = 1;
            if(gamepad1.left_trigger > 0)
                leftTriggerValue = 1;

            intake.setPower(rightTriggerValue);
            shooter.setPower(leftTriggerValue);

            drive.teleopDrive(gamepad1);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
