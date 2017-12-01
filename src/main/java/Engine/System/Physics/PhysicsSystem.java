package Engine.System.Physics;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.Window;

/*
 * @author Gr�goire Boiron <gregoire.boiron@gmail.com>
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PhysicsSystem extends BaseSystem {
	@Override
    public Class<? extends Component> getRecognizedInterface() {
        return PhysicsComponent.class;
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void initialize(Window window) throws Exception {

    }
}
