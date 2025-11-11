package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Autonomous
public class auto extends LinearOpMode{
    //TODO

    /* check again if you need to declare the motors
    frontLeftDrive = hardwareMap.get(DcMotor .class, "front_left_drive");
    backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
    frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
    backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
    */

    @Override
    public void runOpMode() throws InterruptedException{
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //TODO: Figure out the coordinates of the starting position; (x, y, radians *use Math.toRadians()); coordinates are calculated in inches
        //TODO: REPLACE COORDINATES!!!!!
        /*The origin (0, 0) is typically set at the center of the FTC field.
        The positive X-axis runs toward the Blue Alliance side.
        The positive Y-axis runs toward the Audience side.
        Heading (rotation) is measured in radians, with $0$ or $2\pi$ radians pointing along the positive X-axis (toward Blue),
        and $\pi/2$ (90 degrees) pointing along the positive Y-axis (toward the Audience).
         */
        Pose2d startPos = new Pose2d(48, -60, Math.toRadians(90));
        // (x in inches, y in inches, angle in radians); placing gemini coordinates for red side wall pos as placeholder

        drive.setPoseEstimate(startPos);

        // TODO: autonomous path --> use TrajectorySequenceBuilder here?
        TrajectorySequenceBuilder path = drive.trajectorySequenceBuilder(startPos);


        waitForStart();

        if (isStopRequested()) {
            return;
        }

        // TODO: figure this part out
        /*if (opModeIsActive()) {
            driveForward(10, 0.5); // Drive forward 10 inches at 50% power
            // Example: turn(90, 0.7); // Turn 90 degrees at 70% power
        }*/
    }
}
