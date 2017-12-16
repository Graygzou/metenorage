package Engine;

import Engine.Helper.Loader.OBJLoader;
import Engine.Main.Entity;
import Engine.Main.Material;
import Engine.Main.ScriptFile;
import Engine.Main.Sound;
import Engine.System.Component.Component;
import Engine.System.Graphics.Camera;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Graphics.GraphicsComponent;
import Engine.System.Logic.Component.TestComponent;
import Engine.System.Logic.LogicComponent;
import Engine.System.Physics.Component.BoxRigidBodyComponent;
import Engine.System.Scripting.Component.Script;
import org.joml.Vector3f;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Matthieu Le Boucher
 * @author : Gregoire Boiron <gregoire.boiron@gmail.com>
 */
public class Utils {
    public static String readTextResource(String filename) throws IOException {
    	java.nio.file.Path path = Paths.get("./resources/", filename);
    	if(Files.exists(path)) {
    		byte[] file = Files.readAllBytes(path);
    		return new String(file);
    	} else {
    		throw new IOException("the file in "+ path +" doesn't exist.");
    	}
    }

    /**
     * Method used to load a game configuration into the engine
     * @param filename : file that contains the configuration
     * @param gameEngine : the gameEngine that will load the game
     * @return ??
     */
    public static String parser(String filename, GameEngine gameEngine) {
    	java.nio.file.Path path = Paths.get("./resources/", filename);
    	try {
			FileInputStream jsonFile = new FileInputStream(path.toFile());
			
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			
            String line;
            while ((line = reader.readLine()) != null) {
            	switch(line.trim().split(":")[0].replaceAll("\"","")) {
                    case "Materials":
                        createMaterials(reader, gameEngine);
                        break;
                    case "Sounds":
                        createSounds(reader, gameEngine);
                        break;
                    case "Scripts":
                        createScripts(reader, gameEngine);
                        break;
                    case "Game":
                        createGame(reader, gameEngine);
                        break;
                    case "Camera":
                        createCamera(reader, gameEngine);
                        break;
                }
            }
            reader.close();
			jsonFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
            e.printStackTrace();
        }
    	
		return filename;
    }

    private static void createScripts(BufferedReader reader, GameEngine gameEngine) throws IOException {
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("]")) {
            ScriptFile scriptFile = new ScriptFile();
            while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
                String[] temp = line.trim().split(":");
                switch (temp[0].replaceAll("\"", "")) {
                    case "Name":
                        scriptFile.setName(temp[1].replaceAll("\"", "").replaceAll(",", ""));
                        break;
                }
            }
            gameEngine.addScript(scriptFile);
        }
    }

    private static void createCamera(BufferedReader reader, GameEngine gameEngine) throws IOException {
        Camera camera = new Camera();
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("]")) {
            while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
                String[] temp = line.trim().split(":");
                switch (temp[0].replaceAll("\"", "")) {
                    case "Name":
                        camera.setName(temp[1].replaceAll("\"", "").replaceAll(",", ""));
                        break;
                    case "Script":
                        List<ScriptFile> scripts = gameEngine.metadataManager.getScriptFile();
                        for(ScriptFile currentScript : scripts) {
                            if(currentScript.getName().equals(temp[1].replaceAll("\"","").replaceAll(",",""))) {
                                camera.addComponent(new Script(camera, currentScript));
                            }
                        }
                        break;
                }
            }
            gameEngine.setCamera(camera);
        }
    }

    private static void createMaterials(BufferedReader reader, GameEngine gameEngine) throws IOException {
        Material currentMaterial = null;
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("]")) {
            while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
                String[] temp = line.trim().split(":");
                switch (temp[0].replaceAll("\"", "")) {
                    case "Name":
                        currentMaterial = new Material(temp[1].replaceAll("\"", "").replaceAll(",", ""));
                        break;
                    case "Reflectance":
                        currentMaterial.setReflectance(Float.parseFloat(temp[1].replaceAll("\"", "").replaceAll(",", "")));
                        break;
                }
            }
            gameEngine.addMaterial(currentMaterial);
        }
    }

    private static void createSounds(BufferedReader reader, GameEngine gameEngine) throws IOException {
        Sound currentSound = new Sound();
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "Name":
                    currentSound.setName(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "Path":
                    currentSound.setPathSound(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
            }
        }
        gameEngine.addSound(currentSound);
    }

    private static void createGame(BufferedReader reader, GameEngine gameEngine) throws IOException {
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("]")) {
            Entity entity = createEntity(reader, gameEngine);
            if(entity != null) {
                gameEngine.addEntity(entity);
            }
        }

    }
    
    private static Entity createEntity(BufferedReader reader, GameEngine gameEngine) throws IOException {
    	Entity entity = new Entity();
    	String line;
		while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "Name":
                    entity.setName(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "Tag":
                    if(temp[1].replaceAll("\"","").replaceAll(",","") == "player")
                        System.out.println("ici");
                    entity.setTag(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "Position":
                    entity.getTransform().setPosition(create3DVector(reader));
                    break;
                case "Rotation":
                    entity.getTransform().setRotation(create3DVector(reader));
                    break;
                case "Scale":
                    entity.getTransform().setScale(create3DVector(reader));
                    break;
                case "Components":
                    createComponents(reader, entity, gameEngine);
                    break;
            }
		}
    	return entity;
    }

    private static Vector3f create3DVector(BufferedReader reader) throws IOException {
        Vector3f vector = new Vector3f();
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "x":
                    vector.x = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "y":
                    vector.y = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "z":
                    vector.z = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
            }
        }
        return vector;
    }

    private static javax.vecmath.Vector3f create3DVectorVecMath(BufferedReader reader) throws IOException {
        javax.vecmath.Vector3f vector = new javax.vecmath.Vector3f();
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "dx":
                    vector.x = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "dy":
                    vector.y = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
                case "dz":
                    vector.z = Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",",""));
                    break;
            }
        }
        return vector;
    }

    private static void createComponents(BufferedReader reader, Entity entity, GameEngine gameEngine) throws IOException {
        Component component = null;
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("]")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "Type":
                    switch (temp[1].replaceAll("\"","").replaceAll(",","")) {
                        case "Mesh3D":
                            entity.addComponent(createGraphicComponent(reader, entity, gameEngine));
                            break;
                        case "BoxRigidBodyComponent":
                            entity.addComponent(createPhysicsComponent(reader, entity));
                            break;
                        case "Script":
                            entity.addComponent(createPhysicsComponent(reader, entity));
                            break;
                    }
                    break;
            }
        }

    }

    private static GraphicsComponent createGraphicComponent(BufferedReader reader, Entity entity, GameEngine gameEngine) throws IOException {
    	Mesh3D component = null;
    	String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "Model":
                    try {
                        component = OBJLoader.loadMesh(temp[1].replaceAll("\"","").replaceAll(",",""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Material":
                    List<Material> materials = gameEngine.metadataManager.getMaterials();
                    for(Material currentMaterial : materials) {
                        if(currentMaterial.getTextureName().equals(temp[1].replaceAll("\"","").replaceAll(",",""))) {
                            component.setMaterial(currentMaterial);
                        }
                    }
                    break;
            }
        }
    	return component;
    }

    private static BoxRigidBodyComponent createPhysicsComponent(BufferedReader reader, Entity entity) throws IOException {
        BoxRigidBodyComponent component = new BoxRigidBodyComponent(entity);
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).startsWith("}")) {
            String[] temp = line.trim().split(":");
            switch(temp[0].replaceAll("\"","")) {
                case "Mass":
                    component.setMass(Float.parseFloat(temp[1].replaceAll("\"","").replaceAll(",","")));
                    break;
                case "Scale":
                    component.setCollisionShape(create3DVectorVecMath(reader));
                    break;
            }
        }
        return component;
    }

    public static List<String> readAllLines(String fileName) throws Exception {
        List<String> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Utils.class.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                list.add(line);
        }

        return list;
    }
}
