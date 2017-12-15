package Engine.Main;

/**
 * @author Gregoire Boiron
 */
public abstract class Metadata {

    private static int currentIDNumber = 0;

    /**
     * A unique ID used to identify the entity and its components.
     */
    protected int uniqueID;

    public Metadata() {
        // Create a "ramdom" ID for the entity.
        this.uniqueID = Metadata.currentIDNumber;
        Metadata.currentIDNumber++;
    }

    public Integer getUniqueID() { return this.uniqueID; }
}
