package Engine.System.Logic;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */
public class LogicSystem extends BaseSystem {

    @Override
    public List<Component> getLocalSystemComponentsFor(Entity entity) {
        return null;
    }

    @Override
    public void applyComponent(Component component) {

    }

    @Override
    public List<Component> getLocalSystemComponents() {
        List<Component> systemComponents = new ArrayList<>();

        systemComponents.add(new TestComponent());

        return systemComponents;
    }
}
