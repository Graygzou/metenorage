package Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import Engine.Main.Entity;
import Engine.System.Graphics.GraphicsComponent;

/**
 * @author : Matthieu Le Boucher
 * @author : Gregoire Boiron <gregoire.boiron@gmail.com>
 */
public class Utils {
    public static String readTextResource(String filename) throws IOException {
    	java.nio.file.Path path = Paths.get("../resources/", filename);
    	if(Files.exists(path)) {
    		byte[] file = Files.readAllBytes(path);
    		return new String(file);
    	} else {
    		throw new IOException("the file in "+ path +" doesn't exist.");
    	}
    }
    
    
    public static String parser(String filename, GameEngine gameEngine) {
    	java.nio.file.Path path = Paths.get("../resources/", filename);
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
    	Entity entity = null;
    	String line;
		while ((line = reader.readLine()) != null) {
			if(line.trim().contains("Name")) {
				System.out.println(line.split(":")[1]);
				//entity.setName();
			} else if(line.trim().startsWith("GraphicsComponent")) {
				GraphicsComponent component = createComponent(reader);
			}
		}
    	return entity;
    }
    
    private static GraphicsComponent createComponent(BufferedReader reader) throws IOException {
    	GraphicsComponent component = null;
    	String line;
    	while ((line = reader.readLine()) != null) {
    		if(line.trim().contains("Name")) {
				System.out.println(line.split(":")[1]);
				//entity.setName();
			}
        }
    	return component;
    }
}
