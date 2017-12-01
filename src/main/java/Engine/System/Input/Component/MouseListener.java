package Engine.System.Input.Component;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Input.InputComponent;

public class MouseListener extends BaseComponent implements InputComponent {
    public MouseListener(Entity entity) {
        super(entity);
    }

    @Override
    public void apply() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void onMessage(Message message) {

    }
}
