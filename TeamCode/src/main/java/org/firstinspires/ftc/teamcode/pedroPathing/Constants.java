package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
    //have to weigh the robot everytime we make changes to the physical robot and update this value
        .mass(6.80388554);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("frontRight")
            .rightRearMotorName("backRight")
            .leftRearMotorName("backLeft")
            .leftFrontMotorName("frontLeft")
            .leftFrontMotorDirection(DcMotor.Direction.FORWARD)
            .leftRearMotorDirection(DcMotor.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotor.Direction.REVERSE)
            .rightRearMotorDirection(DcMotor.Direction.REVERSE);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .twoWheelLocalizer(localizerConstants)
                .build();
    }

    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("frontLeft")
            .strafeEncoder_HardwareMapName("backRight")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                            RevHubOrientationOnRobot.UsbFacingDirection.UP
                    )
            )
            /*
            .forwardPodY(distance of the odon wheel from the center(will be +y))
            .strafePodX(distance of the odon wheel from the center(will be -x))
            TODO: check if either encoder is reversed by running the localization test in tuning
                  if the x coordinate does not increase, add this: .strafeEncoderDirection(Encoder.REVERSE)
                  if the y coordinate does not increase, add this: .forwardEncoderDirection(Encoder.REVERSE)
             */

            /*
            Forward Tuning:
                Run this test multiple times

            TODO: Run the forward tuner in the tuning OpMode
                  Push the robot forward 48 inches(EXACTLY 2 field tiles)
                  Once the distance has been pushed, two numbers will be displayed: the distance the robot thinks its moved and the multiplier.
                  We will use the multiplier via .forwardTicksToInches(multiplier)
             */

            /*
            Lateral Tuning:
                Run this test multiple times

            TODO: Run the lateral tuner in the tuning OpMode
                  Push the robot left 48 inches(EXACTLY 2 field tiles)
                  Once the distance has been pushed, two numbers will be displayed: the distance the robot thinks its moved and the multiplier.
                  We will use the multiplier via .strafeTicksToInches(multiplier)
             */
            ;
}
