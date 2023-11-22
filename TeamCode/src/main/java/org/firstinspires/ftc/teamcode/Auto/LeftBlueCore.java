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

package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="LeftBlueCore", group="Blue")
public class LeftBlueCore extends LinearOpMode {

    private DcMotorEx arm;

    DriveAutoCore  driveAutoCore = new DriveAutoCore();
    Servo lClaw, rClaw;


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(250);

        driveAutoCore.fwdDrive(750, 23, opModeIsActive(), 12);
        armMove(500, 1350, 250);
        rClaw.setPosition(0.20);  //open slightly
        lClaw.setPosition(0.23);
        sleep(150);
        armMove(500, 0, 250);
        driveAutoCore.revDrive(750, 21, opModeIsActive(), 12);
        driveAutoCore.strafeLeft(2000, 42, opModeIsActive(), 12);
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


    private void initialize() {
        driveAutoCore.init(hardwareMap);
        arm = hardwareMap.get(DcMotorEx.class, "left".toLowerCase());

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rClaw = hardwareMap.get(Servo.class, "rclaw".toLowerCase());
        lClaw = hardwareMap.get(Servo.class, "lclaw".toLowerCase());
        rClaw.setDirection(Servo.Direction.FORWARD);

        lClaw.setDirection(Servo.Direction.REVERSE);

        rClaw.setPosition(0.10);
    }
}

