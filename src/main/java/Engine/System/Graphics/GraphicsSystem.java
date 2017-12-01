package Engine.System.Graphics;

import Engine.Main.Entity;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.PointLight;
import Engine.Main.Light.SpotLight;
import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.TransformationUtils;
import Engine.Utils;
import Engine.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class GraphicsSystem extends BaseSystem {
    private ShadersHandler shadersHandler;

    private Window window;

    /**
     * Field of View in radians.
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 1f;

    private static final float Z_FAR = 100.f;

    private static final int MAX_POINT_LIGHTS = 5;

    private static final int MAX_SPOT_LIGHTS = 5;

    private Matrix4f projectionMatrix;

    private Matrix4f viewMatrix;

    private Camera camera;

    private Vector3f ambientLight;

    private boolean isInitialized = false;

    public GraphicsSystem(Window window) {
        this.window = window;

        this.camera = new Camera();
    }

    public void resetProjectionMatrix() throws Exception {
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
        System.out.println("Projection matrix reset with aspect ratio: " + aspectRatio + " to:\n" + projectionMatrix);
        shadersHandler.setUniform("projectionMatrix", projectionMatrix);
    }

    @Override
    public void cleanUp() {
        glDisableVertexAttribArray(0);
    }

    @Override
    public void iterate(List<Entity> entities) {
        if (window.isResized()) {
            try {
                resetProjectionMatrix();
            } catch (Exception e) {
                System.out.println("GraphicsEngine: could not reset projection matrix.");
            }
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        // Update the view matrix.
        Matrix4f viewMatrix = TransformationUtils.getViewMatrix(camera);

        int currentPointLightIndex = 0, currentSpotLightIndex = 0;

        for (Entity entity : entities) {
            // Update the model-view matrix for the current entity.
            shadersHandler.setUniform("modelViewMatrix",
                    TransformationUtils.getModelViewMatrix(entity, viewMatrix));

            shadersHandler.bind();

            if(entity instanceof PointLight && currentPointLightIndex < MAX_POINT_LIGHTS) {
                PointLight currentPointLight = new PointLight((PointLight) entity);
                Vector3f lightPosition = currentPointLight.getPosition();
                Vector4f viewPosition = new Vector4f(lightPosition, 1);
                viewPosition.mul(viewMatrix);
                lightPosition.x = viewPosition.x;
                lightPosition.y = viewPosition.y;
                lightPosition.z = viewPosition.z;
                shadersHandler.setUniform("pointLights", currentPointLight, currentPointLightIndex);
                currentPointLightIndex++;
            } else if(entity instanceof DirectionalLight) {
                DirectionalLight currentDirectionalLight = new DirectionalLight((DirectionalLight) entity);
                Vector4f viewDirection = new Vector4f(currentDirectionalLight.getDirection(), 0);
                viewDirection.mul(viewMatrix);
                currentDirectionalLight.setDirection(new Vector3f(viewDirection.x, viewDirection.y, viewDirection.z));
                shadersHandler.setUniform("directionalLight", currentDirectionalLight);
            } else if(entity instanceof SpotLight && currentSpotLightIndex < MAX_SPOT_LIGHTS) {
                SpotLight currentSpotLight = new SpotLight((SpotLight) entity);
                Vector4f viewDirection = new Vector4f(currentSpotLight.getConeDirection(), 0);
                viewDirection.mul(viewMatrix);
                currentSpotLight.setConeDirection(new Vector3f(viewDirection.x, viewDirection.y, viewDirection.z));
                Vector3f lightPosition = currentSpotLight.getPointLight().getPosition();

                Vector4f aux = new Vector4f(lightPosition, 1);
                aux.mul(viewMatrix);
                lightPosition.x = aux.x;
                lightPosition.y = aux.y;
                lightPosition.z = aux.z;

                shadersHandler.setUniform("spotLights", currentSpotLight, currentSpotLightIndex);
                currentSpotLightIndex++;
            }

            shadersHandler.setUniform("ambientLight", ambientLight);
            shadersHandler.setUniform("specularPower", 10f);

            for (Component component : getLocalSystemComponentsFor(entity)) {
                if(component instanceof Mesh3D) {
                    if(((Mesh3D) component).getMaterial() != null)
                        shadersHandler.setUniform("material", ((Mesh3D) component).getMaterial());
                }

                component.initialize();
                component.apply();
            }

            shadersHandler.unbind();
        }
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return GraphicsComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        shadersHandler = new ShadersHandler();
        shadersHandler.createVertexShader(Utils.readTextResource("Shader/basicShader.vs"));
        shadersHandler.createFragmentShader(Utils.readTextResource("Shader/basicShader.fs"));
        shadersHandler.link();

        shadersHandler.createUniform("projectionMatrix");
        shadersHandler.createUniform("modelViewMatrix");
        shadersHandler.createUniform("textureSampler");

        shadersHandler.setUniform("modelViewMatrix", new Matrix4f());
        shadersHandler.setUniform("textureSampler", 0);

        shadersHandler.createUniform("specularPower");
        shadersHandler.createUniform("ambientLight");
        shadersHandler.createMaterialUniform("material");
        shadersHandler.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
        shadersHandler.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
        shadersHandler.createDirectionalLightUniform("directionalLight");
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }
}
