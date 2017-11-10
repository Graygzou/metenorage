package Engine.System.Physics;

import Engine.Main.Entity;
import Engine.System.Component.BaseComponent;
import javafx.geometry.Point3D;

/*
 * @author Grégoire Boiron <gregoire.boiron@gmail.com>
 * @author Matthieu Le Boucher <matt.leboucher@gmail.com>
 */

public abstract class BasePhysicsComponent extends BaseComponent implements PhysicsComponent {
    private Point3D coordinates;

    public BasePhysicsComponent(Entity entity) {
        super(entity);
        
        // Place the current object at the center of the scene.
        // Maybe we need to place it depending of the target position.
        coordinates = Point3D.ZERO;
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
