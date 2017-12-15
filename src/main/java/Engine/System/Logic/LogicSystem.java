package Engine.System.Logic;

import Engine.Main.Entity;
import Engine.System.BaseSystem;
import Engine.System.Component.Component;

import java.util.ArrayList;

/**
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 * @author Florian Vidal
 */
public class LogicSystem extends BaseSystem {

    public LogicSystem(){
        super();
    }

    @Override
    protected void checkPendingEntities() {
        trackedEntities.addAll(pendingEntities);
        pendingEntities.clear();
    }

    @Override
    public Class<? extends Component> getRecognizedInterface() {
        return LogicComponent.class;
    }

    @Override
    public void cleanUp() {

    }


    @Override
    public void initialize() throws Exception {

    }
}
