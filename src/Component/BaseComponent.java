package Component;

import Main.Entity;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public abstract class BaseComponent implements Component {
    /*
     * Handle to the entity the component is linked to.
     */
    private Entity entity;

    public BaseComponent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
