package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Physics.BasePhysicsComponent;
import Engine.System.Physics.PhysicsComponent;

/*
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class TestPhysicsComponent extends BasePhysicsComponent implements PhysicsComponent {
	
    private boolean hasBeenApplied = false;

    public TestPhysicsComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        if(!hasBeenApplied) {
            System.out.println("This can be a Collider or a Rigidbody!");
            hasBeenApplied = true;
        }
    }
}
