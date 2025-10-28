package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class void mainDrive extends OpMode()
{
    @Override
    init()
    {

    }
    public void runOpMode() { // EDIT
    leftmotor = hardwareMap.get(DcMotorEx.class, "leftmotor");
    rightmotor = hardwareMap.get(DcMotorEx.class, "rightmotor");

    leftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rightmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}