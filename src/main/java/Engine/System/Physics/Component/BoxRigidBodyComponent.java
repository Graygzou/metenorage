package Engine.System.Physics.Component;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Noemy Artigouha
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
        Message<Object[]> returnMessage = null;
        switch (message.getInstruction()) {
            case "reInitialize":
                this.motionState = new DefaultMotionState(
                        new Transform(
                                new Matrix4f(
                                        new Quat4f(0, 0, 0, 1),
                                        new Vector3f(
                                                this.getEntity().getTransform().getPosition().x,
                                                this.getEntity().getTransform().getPosition().y,
                                                this.getEntity().getTransform().getPosition().z),
                                        1f)));
                this.rigidBody.setMotionState(this.motionState);
                break;
            case "setCollisionShape":
                setCollisionShape((Vector3f)message.getData());
                break;
            case "translate":
                this.getRigidBody().translate((Vector3f)message.getData());
                break;
            case "getRigidbody":
                Object[] returnRigidbody = {RigidBody.class, this.getRigidBody()};
                returnMessage =
                        new Message<>(getID(), message.getSender(), "return", returnRigidbody);
                GameEngine.messageQueue.add(returnMessage);
                break;
            case "detectCollision":
                Object[] returnBool = {Boolean.class, this.getRigidBody().checkCollideWith((RigidBody)message.getData())};
                returnMessage =
                        new Message<>(getID(), message.getSender(), "return", returnBool);
                GameEngine.messageQueue.add(returnMessage);
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
