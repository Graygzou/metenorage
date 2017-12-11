package Engine;

import Engine.Main.Entity;
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

    public static Matrix4f getModelViewMatrix(Entity entity, Matrix4f viewMatrix) {
        Matrix4f modelViewMatrix = new Matrix4f();
        Vector3f rotation = entity.getRotation();

        modelViewMatrix.identity().translate(entity.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(entity.getScale());

        Matrix4f currentView = new Matrix4f(viewMatrix);

        return currentView.mul(modelViewMatrix);
    }

    public static Matrix4f getOrthogonalProjectionMatrix(float left, float right, float bottom, float top) {
        Matrix4f orthogonalMatrix = new Matrix4f();
        orthogonalMatrix.identity();
        orthogonalMatrix.setOrtho2D(left, right, bottom, top);
        return orthogonalMatrix;
    }

    public static Matrix4f getOrthogonalModelProjectionModelMatrix(Entity entity, Matrix4f orthoMatrix) {
        Vector3f rotation = entity.getRotation();
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity().translate(entity.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(entity.getScale());
        Matrix4f orthoMatrixCurr = new Matrix4f(orthoMatrix);
        orthoMatrixCurr.mul(modelMatrix);
        return orthoMatrixCurr;
    }
}
