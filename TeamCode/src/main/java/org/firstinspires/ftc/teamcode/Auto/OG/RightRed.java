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

package org.firstinspires.ftc.teamcode.Auto.OG;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="RightRed", group="Red")
public class RightRed extends LinearOpMode {

    private DcMotorEx frontLeft, frontRight, backLeft, backRight, arm;

    Servo lClaw, rClaw;


    static final double TicksPerRev = 560;
    static final double WheelInches = (75 / 25.4);
    static final double TicksPerIn = TicksPerRev / (WheelInches * Math.PI);

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(250);

        Drive(750, 23, 23, 23, 23, 12);
        armMove(500, 1350, 250);
        rClaw.setPosition(0.20);
        lClaw.setPosition(0.23);
        sleep(150);
        armMove(500, 0, 250);
        Drive(750, -21, -21, -21, -21, 12);
        Drive(2000, 42, -42, -42, 42, 0);
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
            frontLeft.getCurrentPosition();



            allMotorMode(DcMotor.RunMode.RUN_TO_POSITION);

            allMotorVelocity(Math.abs(velocity));

            while (opModeIsActive() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                telemetry.addLine("WE ARE MOVING, WOOOOO!");
                telemetry.update();
            }

            allMotorVelocity(0);

            allMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(timeout);
        }
    }

    public void armMove(double velocity, int ticks, int timeout){
        arm.setTargetPosition(ticks);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(velocity);

        while (opModeIsActive() && arm.isBusy()){
            telemetry.addData("Position: ", arm.getCurrentPosition());
            telemetry.addData("Target: ", ticks);
            telemetry.update();
        }
        arm.setVelocity(0);

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(timeout);
    }

    public void outputArm(){
        telemetry.addData("current arm position", arm.getCurrentPosition());
        telemetry.update();
    }



    private void initialize() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft".toLowerCase());
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight".toLowerCase());
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft".toLowerCase());
        backRight = hardwareMap.get(DcMotorEx.class, "backRight".toLowerCase());
        arm = hardwareMap.get(DcMotorEx.class, "left".toLowerCase());


        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rClaw = hardwareMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hardwareMap.get(Servo.class, "lclaw".toLowerCase());
        rClaw.setDirection(Servo.Direction.FORWARD);

        lClaw.setDirection(Servo.Direction.REVERSE);

        rClaw.setPosition(0.10);
        lClaw.setPosition(0.13);


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

