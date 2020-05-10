package  teamcode;

import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Range;
import org.opencv.core.Point;
import java.util.ArrayList;
import static RobotUtilities.MovementVars.*;

import static com.company.Robot.*;
import static teamcode.MathFunction.AngleWrap;

public class RobotMovement{

    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){
        for (int i =0; i < allPoints.size() - 1; i++){
            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y), new FloatPoint(allPoints.get(i + 1).x,allPoints.get(i + 1).y));
        }
        teamcode.CurvePoint followMe = getFollowPointPath(allPoints , new Point(worldXPosition, worldYPosition),allPoints.get(0).followDistance);

        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x, followMe.y));

        goToPositionNew(followMe.x,followMe.y,followMe.moveSpeed,followAngle,followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(ArrayList< CurvePoint> pathPoints, Point robotlocation, double followRadius){

        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0 ; i < pathPoints.size()-1 ; i ++ ) {
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get( i +1 );

            ArrayList<Point> intrectionx  = MathFunction.lineCircleintersaction(robotlocation , followRadius , startLine.toPoint(), endLine.toPoint());
            double closetAngle = 100000000;
            for (Point thisintractiox : intrectionx){
                System.out.println(i);
                double angle = Math.atan2(thisintractiox.y - worldYPosition , thisintractiox.x - worldXPosition );
                double deltaAngle = Math.abs(AngleWrap(angle - worldAngle_rad));
                if ( deltaAngle < closetAngle){
                    closetAngle = deltaAngle;
                    followMe.setPoint(thisintractiox);
                }
            }

        }
        return followMe;
    }






    public static void goToPositionNew(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){
        double distanceToTarget = Math.hypot(x-worldXPosition, y-worldYPosition);

        double absoluteAngleToTarget = Math.atan2(y-worldYPosition, x-worldYPosition);

        double relativeAngleToPoint = AngleWrap(absoluteAngleToTarget - (worldAngle_rad - Math.toRadians(90)));


        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;

        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        movement_x = movementXPower * movementSpeed;
        movement_y = movementYPower * movementSpeed;

        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;
        movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30),-1,1)*turnSpeed;
        movement_turn = 0;
        if (distanceToTarget < 20){
            //movement_y = 0;
            //movement_x = 0;
            movement_turn = 0;
        }
        System.out.println("x " + movement_x);
        System.out.println("y "+ movement_y);
        movement_x = 0;
    }


}