package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class autonFinal extends LinearOpMode {

    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;

    static final double COUNTS_PER_MOTOR_REV = 45.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH =
            COUNTS_PER_MOTOR_REV / (Math.PI * WHEEL_DIAMETER_INCHES);

    @Override
    public void runOpMode() {

        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft"); // change to frontRight if it breaks
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);



        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int target = (int) (2 * COUNTS_PER_INCH);

        frontLeftDrive.setTargetPosition(target);
        frontRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        frontLeftDrive.setPower(0.4);
        frontRightDrive.setPower(0.4);
        backLeftDrive.setPower(0.4);
        backRightDrive.setPower(0.4);

        while (opModeIsActive()
                && frontLeftDrive.isBusy()
                && frontRightDrive.isBusy()
                && backLeftDrive.isBusy()
                && backRightDrive.isBusy()
                && (getRuntime() < 3)) {
// just wait
        }

        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
    }
}

