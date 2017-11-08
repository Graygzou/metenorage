package Engine.System;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.System.Component.Component;
import Engine.Main.Entity;

import java.util.List;

public abstract class BaseSystem implements GameSystem {
    public void iterate(List<Entity> entities) {
        entities.forEach(entity -> getLocalSystemComponentsFor(entity).forEach(this::applyComponent));
    }

    public abstract List<Component> getLocalSystemComponentsFor(Entity entity);

    public abstract void applyComponent(Component component);

    public abstract List<Component> getLocalSystemComponents();
}
