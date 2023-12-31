/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Disabled;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
@Disabled

@Autonomous(name="STRAIGHT", group="imu")
public class Straight extends LinearOpMode {

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

     IMU imu;
    IMU.Parameters imuparams;

    YawPitchRollAngles robotOrientation;

    AngularVelocity myRobotAngularVelocity;


    static final double TicksPerRev = 560;
    static final double WheelInches = (75 / 25.4);
    static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        if (opModeIsActive()){
            Drive(9999, 25, 25, 25, 25, 400);
            Drive(9999, 25, 25, 25, 25, 400);
            /*
            robotOrientation = imu.getRobotYawPitchRollAngles();
            myRobotAngularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

            float zRotationRate = myRobotAngularVelocity.zRotationRate;
            float xRotationRate = myRobotAngularVelocity.xRotationRate;
            float yRotationRate = myRobotAngularVelocity.yRotationRate;
            double Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
            if (opModeIsActive()){
                while (Yaw > -90) {
                    setMotorVelocity(300, -300, 300, -300);
                    robotOrientation = imu.getRobotYawPitchRollAngles();
                    Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
                    telemetry.addData("Yaw:", "%.2f", Yaw);
                    telemetry.update();
                }
                allMotorVelocity(0);
                if (Yaw < -90) {
                    robotOrientation = imu.getRobotYawPitchRollAngles();
                    Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
                    double error = Yaw - -90;
                    while (Math.abs(error) > 0.2){
                        setMotorVelocity(-300, 300, -300, 300);
                        robotOrientation = imu.getRobotYawPitchRollAngles();
                        Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
                        error = Yaw - 90;
                        telemetry.addData("Error: ", "%.2f", error);
                        telemetry.addData("Yaw:", "%.2f", Yaw);
                        telemetry.update();
                    }
                    allMotorVelocity(0);
                }
            }


            telemetry.addData("Yaw:", "%.2f", Yaw);
            telemetry.addData("Pitch: ", "%.2f", Pitch);
            telemetry.addData("Roll: ", "%.2f", Roll);
            telemetry.addData("zRate: ", "%.2f", zRotationRate);
            telemetry.addData("xRate: ", "%.2f", xRotationRate);
            telemetry.addData("yRate: ", "%.2f", yRotationRate);
            telemetry.update();
             */
        }
        /*
        Drive(565, 6,6,6,6, 2);
        Drive(555, -6,-6,-6,-6, 2);
        Drive(545, -6,6,6,-6, 3);
         */
    }

    public void Drive(double velocity,
                      double frontLeftInches, double frontRightInches,
                      double backLeftInches, double backRightInches,
                      long timeout) {
        int frontLeftTarget;
        int frontRightTarget;
        int backLeftTarget;
        int backRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            frontLeftTarget = frontLeft.getCurrentPosition() + (int) (frontLeftInches * TicksPerIn);
            frontRightTarget = frontRight.getCurrentPosition() + (int) (frontRightInches * TicksPerIn);
            backLeftTarget = backLeft.getCurrentPosition() + (int) (backLeftInches * TicksPerIn);
            backRightTarget = backRight.getCurrentPosition() + (int) (backRightInches * TicksPerIn);

            allTargetPosition(frontLeftTarget, frontRightTarget, backLeftTarget, backRightTarget);

            allMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

            allMotorVelocity(Math.abs(velocity));

            while (opModeIsActive() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addLine("WE ARE MOVING, WOOOOO!");
                telemetry.update();
            }
                allMotorVelocity(0);

                robotOrientation = imu.getRobotYawPitchRollAngles();
                myRobotAngularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

                float zRotationRate = myRobotAngularVelocity.zRotationRate;
                float xRotationRate = myRobotAngularVelocity.xRotationRate;
                float yRotationRate = myRobotAngularVelocity.yRotationRate;
                double Yaw = robotOrientation.getYaw(AngleUnit.DEGREES);
            if (opModeIsActive()){
                while (Yaw > -90){
                    setMotorVelocity(300, -300, 300, -300);
                    robotOrientation = imu.getRobotYawPitchRollAngles();
                    Yaw   = robotOrientation.getYaw(AngleUnit.DEGREES);
                    telemetry.addData("Yaw:", "%.2f", Yaw);
                    telemetry.update();
                }
                allMotorVelocity(0);
            }

            allMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(timeout);
        }
    }



    private void initialize() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        imu = hardwareMap.get(IMU.class, "botIMU");
        imuparams = new IMU.Parameters(new RevHubOrientationOnRobot
                (RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuparams);
        imu.resetYaw();

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        allMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void allMotorMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    public void allMotorVelocity(double velocity) {
        frontLeft.setVelocity(velocity);
        frontRight.setVelocity(velocity);
        backLeft.setVelocity(velocity);
        backRight.setVelocity(velocity);
    }

    public void setMotorVelocity(double fLvelocity, double fRvelocity, double bLvelocity, double bRvelocity) {
        frontLeft.setVelocity(fLvelocity);
        frontRight.setVelocity(fRvelocity);
        backLeft.setVelocity(bLvelocity);
        backRight.setVelocity(bRvelocity);
    }

    public void allMotorPower(double power){
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void setMotorPower(double frontLeftPower, double frontRightPower, double backLeftPower,
                              double backRightPower){
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }

    public void allTargetPosition(int frontLeftPos, int frontRightPos,
                                  int backLeftPos, int backRightPos){
        frontLeft.setTargetPosition(frontLeftPos);
        frontRight.setTargetPosition(frontRightPos);
        backLeft.setTargetPosition(backLeftPos);
        backRight.setTargetPosition(backRightPos);
    }
}

