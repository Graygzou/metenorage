package Engine.System.Logic.Component;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 */

public class TestComponent extends BaseComponent {
    private boolean hasBeenApplied = false;

    public TestComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        if(isActive() && !hasBeenApplied) {
            System.out.println("Hello from TestComponent! :)");
            hasBeenApplied = true;
        }
    }
}
