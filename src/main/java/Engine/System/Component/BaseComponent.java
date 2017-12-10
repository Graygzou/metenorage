package Engine.System.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;

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

    /*
     * Keep track of all the ID created
     */
    private static int currentIDNumber = 0;

    /*
     * This ID will allow component to talk to each other
     */
    private int ID;
    
    private boolean active;

    public BaseComponent(Entity entity) {
        this.entity = entity;
        this.active = true;
        // Create a ramdom ID for the component.
        this.ID = BaseComponent.currentIDNumber++;
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

    public int getID() {
        return this.ID;
    }

    @Override
    public abstract void apply();

    @Override
    public abstract void onMessage(Message message);
}
