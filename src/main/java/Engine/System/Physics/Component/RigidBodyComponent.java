package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import Engine.System.Physics.BasePhysicsComponent;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public abstract class RigidBodyComponent extends BasePhysicsComponent {

    protected float mass;

    /**
     * Specifies how bouncy the object is. Constrained between 0 and 1.
     */
    protected float restitution = 0.25f;

    /**
     * Models the capacity of the rigid body to resist forces trying to make it rotate.
     */
    protected float angularDamping = 0.95f;


    /**
     * The inertia vector of the rigid body.
     * Defaults to no inertia being applied.
     */
    protected Vector3f inertia = new Vector3f();


    /**
     * The shape of the rigid body.
     */
    protected CollisionShape collisionShape;

    protected MotionState motionState;

    protected RigidBody rigidBody;

    public RigidBodyComponent(Entity entity, float mass) {
        super(entity);

        this.mass = mass;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public void apply() {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void initialize() {
        this.motionState = new DefaultMotionState(
                new Transform(
                        new Matrix4f(
                                new Quat4f(0, 0, 0, 1),
                                new Vector3f(
                                        this.getEntity().getTransform().getPosition().x,
                                        this.getEntity().getTransform().getPosition().y,
                                        this.getEntity().getTransform().getPosition().z),
                                1f)));

        // Store the material properties.
        RigidBodyConstructionInfo constructionInfo =
                new RigidBodyConstructionInfo(this.mass, this.motionState, this.collisionShape, this.inertia);
        constructionInfo.restitution = this.restitution;
        constructionInfo.angularDamping = this.angularDamping;

        this.rigidBody = new RigidBody(constructionInfo);
    }

    public float getMass() {
        return mass;
    }

    public CollisionShape getCollisionShape() {
        return collisionShape;
    }

    public MotionState getMotionState() {
        return motionState;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }
}
