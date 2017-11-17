package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author : Matthieu Le Boucher
 */
public class TransformationUtils {
    public static Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, Vector3f scale) {
        Matrix4f worldMatrix = new Matrix4f();

        worldMatrix.translate(offset).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
}
