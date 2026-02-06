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

        double intakeVal = 0;
        double shooterVal = 0;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Call the drive method from the helper class
            telemetry.addData("RT", gamepad1.right_trigger);
            telemetry.addData("LT", gamepad1.left_trigger);
            telemetry.update();

            // intake logic
            if (gamepad1.right_trigger > 0) {
                intakeVal = 0.9;
            }
            else if (gamepad1.a) {
                intakeVal = 0.1;
            }
            else if (gamepad1.b) {
                intakeVal = -0.1;
            }
            else {
                intakeVal = 0;
            }

            // shooter logic
            if(gamepad1.left_trigger > 0)
                shooterVal = 1;
            else {
                shooterVal = 0;
            }

            intake.setPower(intakeVal);
            shooter.setPower(shooterVal);

            drive.teleopDrive(gamepad1);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
