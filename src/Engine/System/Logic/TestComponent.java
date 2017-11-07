package Engine.System.Logic;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;

public class TestComponent extends BaseComponent {
    private boolean hasBeenApplied = false;

    public TestComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        if(!hasBeenApplied) {
            System.out.println("Hello from TestComponent! :)");
            hasBeenApplied = true;
        }
    }
}
