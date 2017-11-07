package Engine.System.Logic.Component;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

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
