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
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PhysicsSystem extends BaseSystem {
    private float simulationTimeStep;
    /**
     * Instance of the physical world.
     */
    private DynamicsWorld dynamicsWorld;

    private List<Entity> trackedEntities = new ArrayList<>();

    /**
     * Stores all the rigid bodies that exist within the world.
     */
    private Set<RigidBodyComponent> rigidBodiesComponents = new HashSet<>();

	@Override
    public Class<? extends Component> getRecognizedInterface() {
        return PhysicsComponent.class;
    }

    @Override
    public void cleanUp() {

    }

    public void iterate(List<Entity> entities, float timeStep) {
        System.out.println("PhysicsSystem: simulating with timestep: " + timeStep);
        dynamicsWorld.stepSimulation(timeStep);

        for (RigidBodyComponent rigidBodyComponent : rigidBodiesComponents) {
            MotionState motionState = rigidBodyComponent.getRigidBody().getMotionState();
            Transform worldTransform = new Transform();
            motionState.getWorldTransform(worldTransform);
            rigidBodyComponent.getEntity().setPosition(worldTransform.origin.x, worldTransform.origin.y, worldTransform.origin.z);
            System.out.println("Entity " + rigidBodyComponent.getEntity() + " moved to " + rigidBodyComponent.getEntity().getPosition());
        }
    }

    @Override
    public void initialize() throws Exception {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher collisionDispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver constraintSolver = new SequentialImpulseConstraintSolver();

        // Create and set the physical world up.
        dynamicsWorld = new DiscreteDynamicsWorld(collisionDispatcher, broadphase, constraintSolver,
                collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0, -9.81f, 0));

        for (RigidBodyComponent rigidBodyComponent : rigidBodiesComponents) {
            dynamicsWorld.addRigidBody(rigidBodyComponent.getRigidBody());
        }
    }

    public void addEntity(Entity entity) {
        List<Component> components = getLocalSystemComponentsFor(entity);

        if (components.isEmpty())
            return;

        trackedEntities.add(entity);

        for(Component component : components) {
            component.initialize();

            if(component instanceof BoxRigidBodyComponent || component instanceof SphereRigidBodyComponent) {
                RigidBody rigidBody = ((RigidBodyComponent) component).getRigidBody();

                rigidBodiesComponents.add((RigidBodyComponent) component);

                if(dynamicsWorld != null)
                    dynamicsWorld.addRigidBody(rigidBody);
            }
        }
    }

    public void removeEntity(Entity entity) {
	    if (trackedEntities.contains(entity)) {
            trackedEntities.remove(entity);

            for(Component component : getLocalSystemComponentsFor(entity)) {
                if(component instanceof BoxRigidBodyComponent || component instanceof SphereRigidBodyComponent) {
                    RigidBody rigidBody = ((RigidBodyComponent) component).getRigidBody();

                    rigidBodiesComponents.remove(rigidBody);

                    if(dynamicsWorld != null)
                        dynamicsWorld.removeRigidBody(rigidBody);
                }
            }
        }
    }

    public float getSimulationTimeStep() {
        return simulationTimeStep;
    }

    public void setSimulationTimeStep(float simulationTimeStep) {
        this.simulationTimeStep = simulationTimeStep;
    }
}
