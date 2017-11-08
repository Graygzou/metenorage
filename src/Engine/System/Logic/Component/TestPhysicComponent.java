package Engine.System.Logic.Component;
import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;

/*
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 */

public class TestPhysicComponent extends BaseComponent {
	
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
