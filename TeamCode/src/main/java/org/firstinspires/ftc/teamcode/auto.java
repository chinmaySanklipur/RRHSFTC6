package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous
public class auto extends OpMode{
    //TODO
    /* check again if you need to declare the motors
    frontLeftDrive = hardwareMap.get(DcMotor .class, "front_left_drive");
    backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
    frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
    backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
    */

    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState{
        //StartPos -> EndPos
        //Example: StartPos_EndPos
        //State types: DRIVE(Movement), SHOOT(shooting)

        DRIVEStartPos_ShootPos,
        SHOOTPreLoad,
        DRIVEShootPos_RowOnePos,
        DRIVERowOnePos_ShootPos,
        SHOOTRowOne,
        DRIVEShootPos_RowTwoPos,
        DRIVERowTwoPos_RowTwoEnd,
        DRIVERowTwoEnd_RowTwoPos,
        DRIVERowTwoPos_ShootPos,
        SHOOTRowTwo
    }

    PathState pathState;

    private final Pose startPose = new Pose(19.918367346938776,123.10204081632654, Math.toRadians(140));
    private final Pose shootPose = new Pose(59.10204081632653,84.24489795918367);
    private final Pose rowOnePose = new Pose(9.63265306122449, 83.59183673469389, Math.toRadians(180));
    private final Pose rowTwoStartPose = new Pose(58.93877551020408,60.08163265306122, Math.toRadians(180));
    private final Pose rowTwoEndPose = new Pose(9.63265306122449, 60.08163265306122);

    public PathChain StartToShoot;
    public PathChain ShootToRowOneEnd;
    public PathChain RowOneEndToShoot;
    public PathChain ShootToRowTwoStart;
    public PathChain RowTwoStartToRowTwoEnd;
    public PathChain RowTwoEndToRowTwoStart;
    public PathChain RowTwoStartToShoot;

    public void buildPaths(){
        StartToShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(startPose, shootPose)
                )
                .setConstantHeadingInterpolation(Math.toRadians(140))
                .build();

        ShootToRowOneEnd = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(shootPose, rowOnePose)
                )
                .setConstantHeadingInterpolation(Math.toRadians(18))
                .build();

        RowOneEndToShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(rowOnePose, shootPose)
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();

        ShootToRowTwoStart = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(shootPose, rowTwoStartPose)
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        RowTwoStartToRowTwoEnd = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(rowTwoStartPose, rowTwoEndPose)
                )
                .setTangentHeadingInterpolation()
                .build();

        RowTwoEndToRowTwoStart = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(rowTwoEndPose, rowTwoStartPose)
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        RowTwoStartToShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(rowTwoStartPose, shootPose)
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();
    }

    public void statePathUpdate(){
        switch (pathState){
            case DRIVEStartPos_ShootPos:
                follower.followPath(StartToShoot, true);
                setPathState(PathState.SHOOTPreLoad);
                break;

            case SHOOTPreLoad:
                //Check if the follower finished its path
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5){
                    //Logic for auton flywheel with preload
                    telemetry.addLine("Made it to shooting position with preload");
                    setPathState(PathState.DRIVEShootPos_RowOnePos);
                }
                break;

            case DRIVEShootPos_RowOnePos:
                //Start the intake
                follower.followPath(ShootToRowOneEnd, true);
                try {
                    wait(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //End the Intake
                setPathState(PathState.DRIVERowOnePos_ShootPos);
                break;

            case DRIVERowOnePos_ShootPos:
                follower.followPath(RowOneEndToShoot);
                setPathState(PathState.SHOOTRowOne);
                break;

            case SHOOTRowOne:
                //Check if the follower finished its path
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 15){
                    //Logic for auton flywheel with preload
                    telemetry.addLine("Made it to shooting position with the Row One artifacts");
                    setPathState(PathState.DRIVEShootPos_RowTwoPos);
                }
                break;

            case DRIVEShootPos_RowTwoPos:
                follower.followPath(ShootToRowTwoStart);
                setPathState(PathState.DRIVERowTwoPos_RowTwoEnd);
                break;

            case DRIVERowTwoPos_RowTwoEnd:
                //Start the intake
                follower.followPath(RowTwoStartToRowTwoEnd);
                try {
                    wait(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //End the Intake
                setPathState(PathState.DRIVERowTwoEnd_RowTwoPos);
                break;

            case DRIVERowTwoEnd_RowTwoPos:
                follower.followPath(RowTwoEndToRowTwoStart);
                setPathState(PathState.DRIVERowTwoPos_ShootPos);
                break;

            case DRIVERowTwoPos_ShootPos:
                follower.followPath(RowTwoStartToShoot, true);
                setPathState(PathState.SHOOTRowTwo);
                break;

            case SHOOTRowTwo:
                //Check if the follower finished its path
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 15){
                    //Logic for auton flywheel with preload
                    telemetry.addLine("Made it to shooting position with the Row Two artifacts");
                }
                break;

            default:
                telemetry.addLine("No State");
                break;
        }
    }

    public void setPathState(PathState newState){
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.DRIVEStartPos_ShootPos;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower((hardwareMap));
        //Add other init mechanisms like the flywheel and april tag sensing

        buildPaths();
        follower.setPose(startPose);
    }

    public void start(){
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        telemetry.addData("Path State: ", pathState.toString());
        telemetry.addData("X Value: ", follower.getPose().getX());
        telemetry.addData("Y Value: ", follower.getPose().getY());
        telemetry.addData("Robot Heading: ", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Path Time Elapsed: ", pathTimer.getElapsedTimeSeconds());
    }
}
