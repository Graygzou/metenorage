package Engine.System.Logic;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;

public class TestComponent extends BaseComponent {
    public TestComponent(Entity entity) {
        super(entity);
    }

    public void apply() {
        System.out.println("Hello from TestComponent! :)");
    }
}
