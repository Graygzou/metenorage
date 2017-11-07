package Engine.System.Logic;

import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class LogicSystem extends BaseSystem {
    @Override
    public List<Class<? extends Component>> getLocalSystemComponents() {
        List<Class<? extends Component>> systemComponents = new ArrayList<>();

        systemComponents.add(TestComponent.class);

        return systemComponents;
    }
}
