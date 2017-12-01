package Engine;

import Engine.Main.Entity;
import Engine.System.Component.Component;
import Engine.System.Graphics.Component.Mesh3D;
import Engine.System.Graphics.GraphicsComponent;
import Engine.System.Logic.Component.TestComponent;
import Engine.System.Logic.LogicComponent;

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
    
    
    public static String parser(String filename, GameEngine gameEngine) {
    	java.nio.file.Path path = Paths.get("./resources/", filename);
    	try {
			FileInputStream jsonFile = new FileInputStream(path.toFile());
			
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			
            String line;
            while ((line = reader.readLine()) != null) {
            	String toto = line;
            	if(line.trim().contains("Entity")) {
            		gameEngine.addEntity(createEntity(reader));
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
    
    private static Entity createEntity(BufferedReader reader) throws IOException {
    	Entity entity = new Entity();
    	String line;
		while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
            Component currentComponent = null;
			if(line.contains("Name")) {
				System.out.println(line.split(":")[1]);
				//entity.setName();
			} else if(line.contains("GraphicsComponent")) {
                currentComponent = createGraphicComponent(entity, reader);
			} else if(line.contains("LogicComponent")) {
                currentComponent = createLogicComponent(entity, reader);
			}
			if(currentComponent != null) {
                entity.addComponent(currentComponent);
            }
		}
    	return entity;
    }
    
    private static GraphicsComponent createGraphicComponent(Entity entity, BufferedReader reader) throws IOException {
    	GraphicsComponent component = new Mesh3D(entity);
    	String line;
    	while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
            if(line.trim().contains("Name")) {
                System.out.println(line.split(":")[1]);
                switch(line.split(":")[1]) {
                    case "Mesh3D":
                        //component = new Mesh3D(entity);
                        break;
                }
            } else if(line.contains("active")) {
                System.out.println(line.split(":")[1]);
                //entity.setActive();
            } else if(line.contains("vertices")) {
                component.setVertices(Utils.getFloatValues(line));
            } else if(line.contains("indices")) {
                component.setIndices(Utils.getIntegerValues(line));
            }
        }
    	return component;
    }

    private static LogicComponent createLogicComponent(Entity entity, BufferedReader reader) throws IOException {
        LogicComponent component = null;
        String line;
        while (!(line = reader.readLine().replaceAll("\\s+","")).contains("}")) {
            if(line.trim().contains("Name")) {
                System.out.println(line.split(":")[1]);
                switch(line.split(":")[1]) {
                    case "TestComponent":
                        component = new TestComponent(entity);
                        break;
                }
            } else if(line.contains("active")) {
                System.out.println(line.split(":")[1]);
                //entity.setActive();
            }
        }
        return component;
    }

    private static float[] getFloatValues(String line) {
        int counter = 0;
        String[] stringValues = line.substring(line.indexOf("[")+1, line.indexOf("]")).split(",");
        float[] floatValues = new float[stringValues.length];
        for(String value : stringValues) {
            System.out.println(value);
            floatValues[counter++] = Float.parseFloat(value);
        }
        return floatValues;
    }

    private static int[] getIntegerValues(String line) {
        int counter = 0;
        String[] stringValues = line.substring(line.indexOf("[")+1, line.indexOf("]")).split(",");
        System.out.println(stringValues);
        int[] floatValues = new int[stringValues.length];
        for(String value : stringValues) {
            floatValues[counter++] = Integer.parseInt(value);
        }
        return floatValues;
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
