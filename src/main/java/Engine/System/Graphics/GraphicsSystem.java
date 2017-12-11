package Engine.System.Graphics;

import Engine.Main.Entity;
import Engine.Main.Light.DirectionalLight;
import Engine.Main.Light.PointLight;
import Engine.Main.Light.SpotLight;
import Engine.ShadersHandler;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Graphics.Component.Text2D;
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
    private ShadersHandler basicShadersHandler;

    private ShadersHandler hudShadersHandler;

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

    private HUD hud;

    public GraphicsSystem(Window window) {
        this.window = window;

        this.camera = new Camera();
    }

    public void resetProjectionMatrix() throws Exception {
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
        System.out.println("Projection matrix reset with aspect ratio: " + aspectRatio + " to:\n" + projectionMatrix);
        basicShadersHandler.setUniform("projectionMatrix", projectionMatrix);
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

            if(this.hud != null)
                this.hud.updateSize(this.window);

            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        // Update the view matrix.
        Matrix4f viewMatrix = TransformationUtils.getViewMatrix(camera);

        int currentPointLightIndex = 0, currentSpotLightIndex = 0;

        for (Entity entity : entities) {
            // Update the model-view matrix for the current entity.
            basicShadersHandler.setUniform("modelViewMatrix",
                    TransformationUtils.getModelViewMatrix(entity, viewMatrix));

            basicShadersHandler.bind();

            if(entity instanceof PointLight && currentPointLightIndex < MAX_POINT_LIGHTS) {
                PointLight currentPointLight = new PointLight((PointLight) entity);
                Vector3f lightPosition = currentPointLight.getPosition();
                Vector4f viewPosition = new Vector4f(lightPosition, 1);
                viewPosition.mul(viewMatrix);
                lightPosition.x = viewPosition.x;
                lightPosition.y = viewPosition.y;
                lightPosition.z = viewPosition.z;
                basicShadersHandler.setUniform("pointLights", currentPointLight, currentPointLightIndex);
                currentPointLightIndex++;
            } else if(entity instanceof DirectionalLight) {
                DirectionalLight currentDirectionalLight = new DirectionalLight((DirectionalLight) entity);
                Vector4f viewDirection = new Vector4f(currentDirectionalLight.getDirection(), 0);
                viewDirection.mul(viewMatrix);
                currentDirectionalLight.setDirection(new Vector3f(viewDirection.x, viewDirection.y, viewDirection.z));
                basicShadersHandler.setUniform("directionalLight", currentDirectionalLight);
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

                basicShadersHandler.setUniform("spotLights", currentSpotLight, currentSpotLightIndex);
                currentSpotLightIndex++;
            }

            basicShadersHandler.setUniform("ambientLight", ambientLight);
            basicShadersHandler.setUniform("specularPower", 10f);

            for (Component component : getLocalSystemComponentsFor(entity)) {
                if(component instanceof Mesh3D) {
                    if(((Mesh3D) component).getMaterial() != null)
                        basicShadersHandler.setUniform("material", ((Mesh3D) component).getMaterial());
                }

                component.initialize();
                component.apply();
            }

            basicShadersHandler.unbind();
        }

        if(this.hud != null)
            renderHud(this.hud);
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return GraphicsComponent.class;
    }

    @Override
    public void initialize() throws Exception {
        basicShadersHandler = new ShadersHandler();
        basicShadersHandler.createVertexShader(Utils.readTextResource("Shader/basicShader.vs"));
        basicShadersHandler.createFragmentShader(Utils.readTextResource("Shader/basicShader.fs"));
        basicShadersHandler.link();

        basicShadersHandler.createUniform("projectionMatrix");
        basicShadersHandler.createUniform("modelViewMatrix");
        basicShadersHandler.createUniform("textureSampler");

        basicShadersHandler.setUniform("modelViewMatrix", new Matrix4f());
        basicShadersHandler.setUniform("textureSampler", 0);

        basicShadersHandler.createUniform("specularPower");
        basicShadersHandler.createUniform("ambientLight");
        basicShadersHandler.createMaterialUniform("material");
        basicShadersHandler.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
        basicShadersHandler.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
        basicShadersHandler.createDirectionalLightUniform("directionalLight");

        // HUD shaders.
        setupHudShader();
    }

    private void setupHudShader() throws Exception {
        hudShadersHandler = new ShadersHandler();
        hudShadersHandler.createVertexShader(Utils.readTextResource("/Shader/hudShader.vs"));
        hudShadersHandler.createFragmentShader(Utils.readTextResource("/Shader/hudShader.fs"));
        hudShadersHandler.link();

        hudShadersHandler.createUniform("projectionModelMatrix");
        hudShadersHandler.createUniform("color");
    }

    private void renderHud(HUD hud) {
        hudShadersHandler.bind();

        Matrix4f orthogonalMatrix = TransformationUtils
                .getOrthogonalProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
        for (Text2D text : hud.getHUDMeshes()) {
            text.initialize();

            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = TransformationUtils
                    .getOrthogonalModelProjectionModelMatrix(text.getEntity(), orthogonalMatrix);

            // Set the uniforms.
            hudShadersHandler.setUniform("projectionModelMatrix", projModelMatrix);
            hudShadersHandler.setUniform("color", text.getMaterial().getAmbientColor());

            // Render the mesh for this HUD item.
            System.out.println("Rendering text " + text.getText() + " at " + text.getEntity().getPosition());
            text.render();
        }

        hudShadersHandler.unbind();
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

    public void setHUD(HUD hud) {
        this.hud = hud;
    }
}
