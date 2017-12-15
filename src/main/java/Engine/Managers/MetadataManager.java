package Engine.Managers;

import Engine.GameEngine;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.Main.ScriptFile;
import Engine.Main.Sound;
import Engine.System.Component.Component;

import java.util.*;

public class MetadataManager {

    private static MetadataManager instance;

    private Map<Integer, Entity> entity = new HashMap<>();
    private Map<Integer, Material> materials = new HashMap<>();
    private Map<Integer, Sound> sounds = new HashMap<>();
    private Map<Integer, ScriptFile> scripts = new HashMap<>();

    public MetadataManager() {
        instance = this;
    }

    /**
     * Use a singleton pattern to represent this Manager
     * @return the instance of the class
     */
    public static MetadataManager getInstance() {
        if(instance == null)
            instance = new MetadataManager();

        return instance;
    }

    public void registerEntity(Entity entity) {
        this.entity.put(entity.getUniqueID(), entity);
        // // Register the transform AND all the components to be able to communicate with him.
        GameEngine.componentManager.registerComponent(entity.getTransform());
        GameEngine.componentManager.registerComponent(entity.getComponents().stream().toArray(Component[]::new));
    }

    public void registerMaterial(Material material) {
        this.materials.put(material.getUniqueID(), material);
    }

    public void registerSound(Sound sound) {
        this.sounds.put(sound.getUniqueID(), sound);
    }

    public void registerScript(ScriptFile scriptFile) {
        this.scripts.put(scriptFile.getUniqueID(), scriptFile);
    }

    public List<Entity> getEntities() { return new LinkedList<>(this.entity.values()); }

    public List<Material> getMaterials() { return new LinkedList<>(this.materials.values()); }

    public List<Sound> getSounds() { return new LinkedList<>(this.sounds.values()); }

    public List<ScriptFile> getScriptFile() { return new LinkedList<>(this.scripts.values()); }

    public void removeEntity(Entity entity) {
        this.entity.remove(entity.getUniqueID());
        GameEngine.componentManager.removeComponent(entity.getTransform());
        GameEngine.componentManager.removeComponent(entity.getComponents().stream().toArray(Component[]::new));
    }

    public void removeMaterial(Material material) {
        this.materials.remove(material.getUniqueID());
    }

    public void removeSound(Sound sound) {
        this.sounds.remove(sound.getUniqueID());
    }

    public void removeScriptFile(ScriptFile scriptFile) {
        this.scripts.remove(scriptFile.getUniqueID());
    }

    /**
     * clears all element from the manager
     */
    public void cleanUp() {
        this.entity.clear();
        this.materials.clear();
        this.scripts.clear();
        this.sounds.clear();
    }
}
