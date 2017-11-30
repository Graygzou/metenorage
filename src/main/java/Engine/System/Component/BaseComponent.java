package Engine.System.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;
import Engine.System.Component.Messaging.MessageQueue;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Gregoire Boiron     <gregoire.boiron@gmail.com>
 * @author Florian Vidal       <florianvidals@gmail.com>
 */

public abstract class BaseComponent implements Component {
    /*
     * Handle to the entity the component is linked to.
     */
    private Entity entity;
    
    private boolean active;

    public BaseComponent(Entity entity) {
        this.entity = entity;
        this.active = true;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public boolean isActive() {
    	return active;
    }
    
    public void setActiveState(boolean state) {
    	active = state;
    }

    @Override
    public abstract void apply();

    @Override
    public abstract void onMessage(Message message);
}
