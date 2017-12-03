package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import com.bulletphysics.collision.shapes.SphereShape;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public abstract class SphereRigidBodyComponent extends RigidBodyComponent {
    float radius;

    public SphereRigidBodyComponent(Entity entity, float mass, float radius) {
        super(entity, mass);

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
        this.collisionShape = new SphereShape(radius);
    }
}
