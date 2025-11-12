package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


    public class movement {
        private DcMotor frontLeftDrive;
        public DcMotor backLeftDrive;
        public DcMotor frontRightDrive;
        public DcMotor backRightDrive;

        public movement(HardwareMap hardwareMap)
        {
            // Initialize the hardware variables. Note that the strings used here must correspond
            // to the names assigned during the robot configuration step on the DS or RC devices.
            frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_motor");
            backLeftDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
            frontRightDrive = hardwareMap.get(DcMotor.class, "back_left_motor");
            backRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");

            frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
            frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
            backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        }

        public void teleopDrive(Gamepad gamepad1) {
                double max;

                // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
                double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
                double lateral = gamepad1.left_stick_x;
                double yaw = gamepad1.right_stick_x;

                // Combine the joystick requests for each axis-motion to determine each wheel's power.
                // Set up a variable for each drive wheel to save the power level for telemetry.
                double frontLeftPowerSet = axial + lateral + yaw;
                double frontRightPowerSet = axial - lateral - yaw;
                double backLeftPowerSet = axial - lateral + yaw;
                double backRightPowerSet = axial + lateral - yaw;

                // Normalize the values so no wheel power exceeds 100%
                // This ensures that the robot maintains the desired motion.
                max = Math.max(Math.abs(frontLeftPowerSet), Math.abs(frontRightPowerSet));
                max = Math.max(max, Math.abs(backLeftPowerSet));
                max = Math.max(max, Math.abs(backRightPowerSet));

                if (max > 1.0) {
                    frontLeftPowerSet /= max;
                    frontRightPowerSet /= max;
                    backLeftPowerSet /= max;
                    backRightPowerSet /= max;
                }

                // initializing power variables
                double frontLeftPower = 0;
                double frontRightPower = 0;
                double backLeftPower = 0;
                double backRightPower = 0;

                // incrementing power variables to meet values within a second
                for (int t = 0; t < 10; t++)
                {
                    if (frontLeftPower < frontLeftPowerSet) {
                        frontLeftPower += 0.1 * frontLeftPowerSet;
                    }
                    if (frontRightPower < frontRightPowerSet) {
                        frontRightPower += 0.1 * frontRightPowerSet;
                    }
                    if (backLeftPower < backLeftPowerSet) {
                        backLeftPower += 0.1 * backLeftPowerSet;
                    }
                    if (backRightPower < backRightPowerSet) {
                        backRightPower += 0.1 * backRightPowerSet;
                    }

                    // Send calculated power to wheels
                    frontLeftDrive.setPower(frontLeftPower);
                    frontRightDrive.setPower(frontRightPower);
                    backLeftDrive.setPower(backLeftPower);
                    backRightDrive.setPower(backRightPower);

                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }



                // This is test code:
                //
                // Uncomment the following code to test your motor directions.
                // Each button should make the corresponding motor run FORWARD.
                //   1) First get all the motors to take to correct positions on the robot
                //      by adjusting your Robot Configuration if necessary.
                //   2) Then make sure they run in the correct direction by modifying the
                //      the setDirection() calls above.
                // Once the correct motors move in the correct direction re-comment this code.

            /*
            frontLeftPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            backLeftPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            frontRightPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            backRightPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */



            }
        }
