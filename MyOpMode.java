package teamcode;

import RobotUtilities.MovementVars;

import java.util.ArrayList;
import static teamcode.RobotMovement.*;

public class MyOpMode extends OpMode{
    @Override
    public void init(){}

    @Override
    public void loop(){
         //ArrayList<CurvePoint> allpoints = new ArrayList<>();
         //allpoints.add(new CurvePoint(50,300 ,0.7,0.7,30,0,0));
         //allpoints.add(new CurvePoint(300,300 ,0.7,0.7,30,0,0));
        //allpoints.add(new CurvePoint(300,300,0,10));
        //allpoints.add(new CurvePoint(50 ,300,0,10));
         //allpoints.add(new CurvePoint(100,0  ,0.3,0.7,50,Math.toRadians(50),1.0));
         //followCurve(allpoints,0);

        RobotMovement.goToPositionNew(50,300,0.7,0,0.3);
        //MovementVars.movement_x = 0;
        //MovementVars.movement_y = 1;

    }
}
