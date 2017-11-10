package Engine.System.Physics.Component;
import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Physics.PhysicsComponent;

/*
 * @author Gr�goire Boiron <gregoire.boiron@gmail.com>
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class TestPhysicComponent extends BaseComponent implements PhysicsComponent {
	
    private boolean hasBeenApplied = false;

    public TestPhysicComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        if(!hasBeenApplied) {
            System.out.println("This can be a Collider or a Rigidbody!");
            hasBeenApplied = true;
        }
    }
}
