package Engine.System.Logic.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Component.Messaging.Message;
import Engine.System.Logic.LogicComponent;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Gregoire Boiron <gregoire.boiron@gmail.com>
 */

public class TestComponent extends BaseComponent implements LogicComponent {
    private boolean hasBeenApplied = false;

    @Override
    public void initialize() {

    }

    public TestComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        if(isActive() && !hasBeenApplied) {
            System.out.println("Hello from TestComponent! :)");
            hasBeenApplied = true;
        }
    }

    @Override
    public void onMessage(Message message) {
        // nothing here
    }

    @Override
    public void Update() {

    }
}
