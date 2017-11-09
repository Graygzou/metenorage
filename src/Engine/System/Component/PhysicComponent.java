package Engine.System.Component;

import Engine.Main.Entity;
import javafx.geometry.Point3D;

/*
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 */

public abstract class PhysicComponent implements Component {
    /*
     * Handle to the entity the component is linked to.
     */
    private Entity entity;
    
    private boolean active;
    
    private Point3D coordinates;

    public PhysicComponent(Entity entity) {
        this.entity = entity;
        this.active = false;
        
        // Place the current object at the center of the scene.
        // Maybe we need to place it depending of the target position.
        coordinates = Point3D.ZERO;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public boolean isActive() {
    	return active;
    }
    
    public void setActiveState(boolean state) {
    	active = state;
    }
    
    public double getX() {
    	return coordinates.getX();
    }
    
    public double getY() {
    	return coordinates.getY();
    }
    
    public double getZ() {
    	return coordinates.getZ();
    }
    
    public void setX(double x, double y, double z) {
    	coordinates = coordinates.add(x, y, z);
    }

    @Override
    public abstract void apply();
}
