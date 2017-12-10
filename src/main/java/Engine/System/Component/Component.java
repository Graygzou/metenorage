package Engine.System.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal       <florianvidals@gmail.com>
 * @author Gregoire Boiron     <gregoire.boiron@gmail.com>
 */

public interface Component {
    void setEntity(Entity entity);

    Entity getEntity();

    boolean isActive();

    void setActiveState(boolean state);

    int getID();

    void apply();

    void initialize();

    void onMessage(Message message);
}
