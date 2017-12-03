package Engine.System.Physics;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Physics.Component.RigidBodyComponent;
import Engine.System.Physics.Component.SphereRigidBodyComponent;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PhysicsSystem extends BaseSystem {
    /**
     * Instance of the physical world.
     */
    private DynamicsWorld dynamicsWorld;

    private List<Entity> trackedEntities;

    /**
     * Stores all the rigid bodies that exist within the world.
     */
    private Set<RigidBody> rigidBodies = new HashSet<>();

	@Override
    public Class<? extends Component> getRecognizedInterface() {
        return PhysicsComponent.class;
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void initialize() throws Exception {
	    trackedEntities = new ArrayList<>();

        BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher collisionDispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver constraintSolver = new SequentialImpulseConstraintSolver();

        // Create and set the physical world up.
        dynamicsWorld = new DiscreteDynamicsWorld(collisionDispatcher, broadphase, constraintSolver,
                collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -9.81f, 0));

    }

    public void addEntity(Entity entity) {
        List<Component> components = getLocalSystemComponentsFor(entity);

        if (components.isEmpty())
            return;

        trackedEntities.add(entity);

        for(Component component : components) {
            component.initialize();

            if(component instanceof BoxRigidBodyComponent || component instanceof SphereRigidBodyComponent) {
                rigidBodies.add(((RigidBodyComponent) component).getRigidBody());
            }
        }
    }

    public void removeEntity(Entity entity) {
	    if (trackedEntities.contains(entity)) {
            trackedEntities.remove(entity);

            for(Component component : getLocalSystemComponentsFor(entity)) {
                if(component instanceof BoxRigidBodyComponent || component instanceof SphereRigidBodyComponent) {
                    rigidBodies.remove(((RigidBodyComponent) component).getRigidBody());
                }
            }
        }
    }
}
