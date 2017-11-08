package Engine.System.Physics;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.List;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PhysicsSystem extends BaseSystem {
	
	public void activatePhysics() {
		// get all the entities of the game
		//List<Entity> entities = getEntities();
		// iterate on all the entity of the game to find the physics one
		//for 
			// once found, activate the component
	}
	

	//public List<Component> getPhysicsComponents() {
		
	//}


	@Override
	public List<Class<? extends Component>> getLocalSystemComponents() {
		// TODO Auto-generated method stub
		return null;
	}
}
