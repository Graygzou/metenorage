package Engine.System;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.Component;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseSystem implements GameSystem {
    public void iterate(List<Entity> entities) {
        entities.forEach(entity -> getLocalSystemComponentsFor(entity).forEach(this::applyComponent));
    }

    public List<Component> getLocalSystemComponentsFor(Entity entity) {
        List<Class<? extends Component>> systemComponents = getLocalSystemComponents();

        return entity.getComponents().stream()
                .filter(component -> systemComponents.contains(component.getClass()))
                .collect(Collectors.toList());
    }

    public void applyComponent(Component component) {
        component.apply();
    }

    public abstract List<Class<? extends Component>> getLocalSystemComponents();
}
