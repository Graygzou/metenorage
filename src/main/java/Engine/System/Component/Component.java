package Engine.System.Component;

import Engine.Main.Entity;
import Engine.System.Component.Messaging.Message;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal       <florianvidals@gmail.com>
 */

public interface Component {
    void setEntity(Entity entity);

    Entity getEntity();

    void apply();

    void initialize();

    void onMessage(Message message);
}
