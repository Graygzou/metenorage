package Engine.System.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import org.joml.Vector3f;


    /*
     * @author Noemy Artigouha
     */

public class Transform extends BaseComponent implements Component {

    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scale;

    public Transform(Entity entity) {
        super(entity);
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1f, 1f, 1f);
    }

    @Override
    public void apply() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void onMessage(Message message) {
        switch (message.getInstruction()) {
            case "setPosition":
                setPosition((Vector3f)message.getData());
                break;
            case "setRotation":
                rotate((Vector3f)message.getData());
                break;
            default:
                System.out.println(message.getInstruction() + ": Corresponding method can't be found");
                break;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void rotate(float offsetX, float offsetY, float offsetZ) {
        this.rotation.x += offsetX;
        this.rotation.y += offsetY;
        this.rotation.z += offsetZ;
    }

    public void rotate(Vector3f vect) {
        this.rotation.x += vect.x;
        this.rotation.y += vect.y;
        this.rotation.z += vect.z;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float s) {
        this.scale.x = s;
        this.scale.y = s;
        this.scale.z = s;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if (offsetX != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public void movePosition(Vector3f offset) {
        this.movePosition(offset.x, offset.y, offset.z);
    }
}
