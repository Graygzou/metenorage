package Engine.Helper;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */


import javax.vecmath.Quat4f;

public class QuaternionHelper {

    public static float getPitch(Quat4f q){
        return (float)(Math.atan2(2.0 * (q.y * q.z + q.w * q.x), q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z));
    }

    public static float getYaw(Quat4f q){
        return (float)(Math.asin(-2.0 * (q.x * q.z - q.w * q.y)));
    }

    public static float getRoll(Quat4f q){
        return (float)(Math.atan2(2.0 * (q.x * q.y + q.w * q.z), q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z));
    }

}
