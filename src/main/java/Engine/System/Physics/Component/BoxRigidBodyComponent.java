package Engine.System.Physics.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import com.bulletphysics.collision.shapes.BoxShape;

import javax.vecmath.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class BoxRigidBodyComponent extends RigidBodyComponent {
    public BoxRigidBodyComponent(Entity entity, float mass, float dx, float dy, float dz) {
        super(entity, mass);

        this.collisionShape = new BoxShape(new Vector3f(dx, dy, dz));
    }

    public void apply() {

    }

    @Override
    public void onMessage(Message message) {
        switch (message.getInstruction()) {
            case "initialize":
                initialize();
                break;
            default:
                System.out.println(message.getInstruction() + ": Corresponding method can't be found");
                break;
        }
    }

    @Override
    public void initialize() {
        super.initialize();
    }
}
