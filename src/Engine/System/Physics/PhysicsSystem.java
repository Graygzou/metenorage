package Engine.System.Physics;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;
import Engine.System.Logic.Component.TestComponent;
import Engine.System.Logic.Component.TestPhysicComponent;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public class PhysicsSystem extends BaseSystem {

	@Override
    public List<Class<? extends Component>> getLocalSystemComponents() {
        List<Class<? extends Component>> systemComponents = new ArrayList<>();

        systemComponents.add(TestPhysicComponent.class);

        return systemComponents;
    }
}
