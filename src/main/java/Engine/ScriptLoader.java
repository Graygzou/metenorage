package Engine;

public class ScriptLoader extends ClassLoader {

    public ScriptLoader(){
        super(ScriptLoader.class.getClassLoader());
    }

}
