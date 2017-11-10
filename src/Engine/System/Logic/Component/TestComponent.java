package Engine.System.Logic.Component;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import Engine.System.Logic.LogicComponent;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Gr�goire Boiron <gregoire.boiron@gmail.com>
 */

public class TestComponent extends BaseComponent implements LogicComponent {
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

    @Override
    public void Update() {

    }
}
