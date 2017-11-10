package Engine.System;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSystem implements GameSystem {
    public void iterate(List<Entity> entities) {
        entities.forEach(entity -> getLocalSystemComponentsFor(entity).forEach(this::applyComponent));
    }

    public List<Component> getLocalSystemComponentsFor(Entity entity) {
        List<Component> componentsToApply = new ArrayList<>();

        for(Component component : entity.getComponents())
            if(getRecognizedInterface().isAssignableFrom(component.getClass()))
                componentsToApply.add(component);

        return componentsToApply;
    }

    public void applyComponent(Component component) {
        component.apply();
    }

    public abstract Class<? extends Component> getRecognizedInterface();
}
