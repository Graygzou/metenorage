package Engine;

import Engine.System.Graphics.Camera;
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

    public static Matrix4f getViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();

        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        viewMatrix.identity();

        viewMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));

        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        return viewMatrix;
    }
}
