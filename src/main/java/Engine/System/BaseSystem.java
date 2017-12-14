package Engine.System;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal
 */

import Engine.Main.Entity;
import Engine.System.Component.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSystem implements GameSystem {

    protected List<Entity> pendingEntities;
    protected List<Entity> trackedEntities;

    public BaseSystem(){
        this.pendingEntities = new ArrayList<>();
        this.trackedEntities = new ArrayList<>();
    }

    public void iterate() {
        checkPendingEntities();
        trackedEntities.forEach(entity -> getLocalSystemComponentsFor(entity).forEach(this::applyComponent));
    }

    protected abstract void checkPendingEntities();

    protected List<Component> getLocalSystemComponentsFor(Entity entity) {
        List<Component> componentsToApply = new ArrayList<>();

        for(Component component : entity.getComponents())
            if(getRecognizedInterface().isAssignableFrom(component.getClass()))
                componentsToApply.add(component);

        return componentsToApply;
    }

    @Override
    public void addEntity(Entity entity) {
        this.pendingEntities.add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        this.pendingEntities.remove(entity);
        this.trackedEntities.remove(entity);
    }

    protected void applyComponent(Component component) {
        component.apply();
    }

    public abstract Class<? extends Component> getRecognizedInterface();
}
