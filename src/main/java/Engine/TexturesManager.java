package Engine;

/*
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

import Engine.System.Graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TexturesManager {
    private static TexturesManager instance;

    private Map<String, Texture> textures = new HashMap<>();

    public TexturesManager() {
        instance = this;
    }

    public static TexturesManager getInstance() {
        if(instance == null)
            instance = new TexturesManager();

        return instance;
    }

    public Map<String, Texture> getTextures() {
        return this.textures;
    }

    public Texture getTexture(String name) {
        return this.textures.get(name);
    }

    public void addTexture(String name, Texture object) {
        System.out.println("TextureManager: added " + name);
        this.textures.put(name, object);
    }
}
