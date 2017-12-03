package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public abstract class BoxRigidBodyComponent extends RigidBodyComponent {
    public BoxRigidBodyComponent(Entity entity, float mass) {
        super(entity, mass);

        //this.collisionShape = new BoxShape();
    }

    public void apply() {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void initialize() {

    }
}
