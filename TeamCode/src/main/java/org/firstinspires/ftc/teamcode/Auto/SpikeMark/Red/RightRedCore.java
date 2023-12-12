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

package org.firstinspires.ftc.teamcode.Auto.SpikeMark.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto.ArmAutoCore;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.Auto.ServoAutoCore;
import org.firstinspires.ftc.teamcode.Auto.TensorCore;

//COMPLETE
@Autonomous(name="RightRed", group="Red")
public class RightRedCore extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Red.tflite";
    private static final String[] LABELS = {
            "Red",
    };

    DriveAutoCore driveAutoCore = new DriveAutoCore();
    ArmAutoCore armAutoCore = new ArmAutoCore();
    ServoAutoCore servoAutoCore = new ServoAutoCore();
    TensorCore tensorCore = new TensorCore();

    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    double x;

    String pos;


    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        x = tensorCore.telemetryTfod(telemetry);
        time.reset();
        double t = time.time();
        while (x == 0 || t < 6){
            x = tensorCore.telemetryTfod(telemetry);
            t = time.time();
            if (t >= 6){
                break;
            }
            telemetry.addData("time", t);
            telemetry.addData("x", x);
            telemetry.update();
        }
        x = tensorCore.telemetryTfod(telemetry);
        pos = tensorCore.getPos("rred", x);
        tensorCore.visionPortal.close();
        telemetry.addData("Pos: ", x);
        telemetry.update();

        //super helpful drive diagram https://gm0.org/en/latest/_images/mecanum-drive-directions.png
        sleep(150);

        decide(pos);
    }

    private void initialize() {
        driveAutoCore.init(hardwareMap);
        armAutoCore.init(hardwareMap);
        servoAutoCore.init(hardwareMap);
        tensorCore.initTfod(hardwareMap, LABELS, TFOD_MODEL_ASSET);
    }

    public void decide(String position) throws InterruptedException {
        switch (position){
            case "left":
                LeftGo();
                break;
            case "right":
                RightGo();
                break;
            case "middle":
                MiddleGo();
        }
    }

    public void LeftGo() throws InterruptedException {
        driveAutoCore.strafeRight(750, 15, opModeIsActive(), 15); //Change this to how far we need to strafe away
        driveAutoCore.fwdDrive(750, 24.25, opModeIsActive(), 12); //Change this to how far we need to be to line up with left tape once turned
        driveAutoCore.turnAmount(90, opModeIsActive()); //Keep this
        driveAutoCore.fwdDrive(750, 13.5, opModeIsActive(), 12); //Change this to how far we need to go for arm to reach left tape
        armAutoCore.move(500, 1350, opModeIsActive(), 250); //Keep this
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly //Keep this
        servoAutoCore.lClaw.setPosition(0.23);  //Keep this
        sleep(150); //Keep this
        armAutoCore.move(500, 150, opModeIsActive(), 250); //Keep this
        driveAutoCore.strafeLeft(750, 22, opModeIsActive(), 12); // Make this whatever we drove forward -2
        driveAutoCore.revDrive(2000, 34, opModeIsActive(), 12); //This is 42 - whatever we strafed right (deviated from original)
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    public void RightGo() throws InterruptedException{
        driveAutoCore.strafeRight(750, 6, opModeIsActive(), 15);
        driveAutoCore.fwdDrive(750, 16, opModeIsActive(), 12);
        armAutoCore.move(500, 1350, opModeIsActive(), 250);
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        driveAutoCore.revDrive(750, 13, opModeIsActive(), 12); // Make this whatever we drove forward -2
        driveAutoCore.strafeRight(2000, 36, opModeIsActive(), 12); //This is 42 - whatever we strafed right
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    public void MiddleGo() throws InterruptedException{
        driveAutoCore.fwdDrive(750, 19, opModeIsActive(), 12);
        armAutoCore.move(500, 1350, opModeIsActive(), 250);
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        driveAutoCore.revDrive(750, 17, opModeIsActive(), 12);
        driveAutoCore.strafeRight(2000, 42, opModeIsActive(), 12);
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }
}