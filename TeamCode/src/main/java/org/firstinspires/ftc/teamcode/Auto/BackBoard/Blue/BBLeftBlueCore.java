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

package org.firstinspires.ftc.teamcode.Auto.BackBoard.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto.ArmAutoCore;
import org.firstinspires.ftc.teamcode.Auto.DriveAutoCore;
import org.firstinspires.ftc.teamcode.Auto.ServoAutoCore;
import org.firstinspires.ftc.teamcode.Auto.TensorCore;

//PSUEDO
@Disabled
@Autonomous
public class BBLeftBlueCore extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Blue.tflite";
    private static final String[] LABELS = {
            "Blue",
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
        pos = tensorCore.getPos("lblue", x);
        tensorCore.visionPortal.close();
        telemetry.addData("Pos: ", x);
        telemetry.update();

        /*The code above is quite possibly the stupidest code I have ever written
        x should be equal to whatever tensorflow returns when it detects the object and its position
        HOWEVER, because the camera can't see one side, wait 6 seconds and if detcts nothing just
        say it's that side, like I said, so stupid but it works, please make this better I BEG*/

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
    }//Decides which auto function to execute depending on what tensorflow returns

    public void LeftGo() throws InterruptedException {
        driveAutoCore.fwdDrive(750, 30, opModeIsActive(), 12); //However much to line up left side of backboard
        driveAutoCore.turnAmount(-90, opModeIsActive(), telemetry); //Face backboard
        driveAutoCore.fwdDrive(500, 36, opModeIsActive(), 500); //Drive so arm reach
        armAutoCore.move(500, 750, opModeIsActive(), 250); //Move arm however amount ticks
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    public void RightGo() throws InterruptedException{
        driveAutoCore.fwdDrive(750, 35, opModeIsActive(), 12); //However much to line up right side of backboard
        driveAutoCore.turnAmount(-90, opModeIsActive(), telemetry); //Face backboard
        driveAutoCore.fwdDrive(500, 36, opModeIsActive(), 500); //Drive so arm reach
        armAutoCore.move(500, 750, opModeIsActive(), 250); //Move arm however amount ticks
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    public void MiddleGo() throws InterruptedException{
        driveAutoCore.fwdDrive(750, 32.5, opModeIsActive(), 12); //However much to line up middle side of backboard
        driveAutoCore.turnAmount(-90, opModeIsActive(), telemetry); //Face backboard
        driveAutoCore.fwdDrive(500, 36, opModeIsActive(), 500); //Drive so arm reach
        armAutoCore.move(500, 750, opModeIsActive(), 250); //Move arm however amount ticks
        servoAutoCore.rClaw.setPosition(0.20);  //open slightly
        servoAutoCore.lClaw.setPosition(0.23);
        sleep(150);
        armAutoCore.move(500, 150, opModeIsActive(), 250);
        servoAutoCore.lClaw.setPosition(0.8); //(open)
        servoAutoCore.rClaw.setPosition(0.8); //(open)
    }

    //The auto functions depending on whether it's spike mark center, left, or middle are above
}