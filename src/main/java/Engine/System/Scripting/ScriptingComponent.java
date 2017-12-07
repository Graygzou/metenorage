package Engine.System.Scripting;

import Engine.System.Component.Component;

public interface ScriptingComponent extends Component {

    void awake();

    void start();

    void update();

    void fixedUpdate();

}
