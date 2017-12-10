package Engine.System.Scripting;

import Engine.System.Component.Component;

/**
 * @author Grégoire Boiron
 */
public interface ScriptingComponent extends Component {

    void awake();

    void start();

    void update();

    void fixedUpdate();

}
