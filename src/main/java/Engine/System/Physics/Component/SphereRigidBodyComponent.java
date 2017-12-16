package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import com.bulletphysics.collision.shapes.SphereShape;

import javax.vecmath.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class SphereRigidBodyComponent extends RigidBodyComponent {
    float radius;

    public SphereRigidBodyComponent(Entity entity, float mass, float radius) {
        super(entity, mass);
        this.collisionShape = new SphereShape(radius);

        this.radius = radius;
    }

    public void apply() {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void initialize() {
        super.initialize();
    }
}
