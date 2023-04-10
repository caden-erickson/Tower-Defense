package game;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A <code>ResourceLoader</code> object will load, store, and distribute all of
 * the necessary resources (images, files, paths, etc) for the tower defense
 * game.
 * 
 * Each resource will only be loaded once- if accessed or asked for again, the
 * object will be able to to return the previously loaded resource.
 * 
 * This class follows the Singleton pattern, so only one
 * <code>ResourceLoader</code> object will ever be built in a given execution.
 * 
 * @author Caden Erickson
 * @version November 22, 2021
 */
public class ResourceLoader
{
	// Fields
	static private ResourceLoader instance;
	private Map<String, BufferedImage> imageMap;
	private Map<String, Path> pathMap;
	private Map<String, Scanner> enemyMap;

	/**
	 * Control method for this class following the Singleton pattern.
	 * 
	 * @return the single instantiated ResourceLoader object
	 */
	static public ResourceLoader getLoader()
	{
		if (instance == null)
			instance = new ResourceLoader();

		return instance;
	}

	/**
	 * ResourceLoader constructor. Because this class follows the Singleton pattern,
	 * this constructor is private and can only be called from the
	 * <code>getLoader()</code> method.
	 */
	private ResourceLoader()
	{
		imageMap = new HashMap<String, BufferedImage>();
		pathMap  = new HashMap<String, Path>();
		enemyMap = new HashMap<String, Scanner>();
	}
	
	/**
	 * Returns a specified image from the map of loaded images contained in the
	 * single object of this class. If the image has not yet been loaded into the
	 * map, this method will load it into the map and then return it.
	 * 
	 * @param startButtonFile a <code>String</code> containing the name of the file to
	 *                 return, including file extension
	 * @return the corresponding image, loaded as a <code>BufferedImage</code>.
	 */
	public BufferedImage getImage(String filename)
	{
		BufferedImage backdrop = null;
		
		// Load the backdrop image and path from the resources folder
    	try
    	{
    		if(imageMap.containsKey(filename))
    		{
    			// If this image has already been loaded, just get it from the map and return it
    			return imageMap.get(filename);
    		}
    		else
    		{
    			// Open a class loader and input stream with the given file name
		    	ClassLoader loader = this.getClass().getClassLoader();
		    	InputStream is = loader.getResourceAsStream("resources/" + filename);
		    	backdrop = javax.imageio.ImageIO.read(is);
		    	
		    	//System.out.println("image loaded"); //debug
		    	
		    	// Put it in the map, keyed to the file name
		    	imageMap.put(filename, backdrop);
    		}
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	return backdrop;
	}
	
	/**
	 * Returns a specified path from the map of loaded <code>Path</code> objects
	 * contained in the single object of this class. If the path has not yet been
	 * loaded into the map, this method will load it into the map and then return
	 * it.
	 * 
	 * @param startButtonFile a <code>String</code> containing the name of a text file
	 *                 (with file extension) containing path coordinate info
	 * @return the corresponding path, loaded as a <code>Path</code> object.
	 */
	public Path getPath(String filename)
	{
		Path currentPath = null;
		
		// Load the backdrop image and path from the resources folder
    	try
		{
    		if(pathMap.containsKey(filename))
    		{
    			// If this path has already been loaded & created, just get it from the map and return it
    			return pathMap.get(filename);
    		}
    		else
    		{
    			// Open a ClassLoader and Scanner with the given file name, create a Path object with the Scanner
		    	ClassLoader loader = this.getClass().getClassLoader();		    	
		    	Scanner pathScanner = new Scanner(loader.getResourceAsStream("resources/" + filename));
		    	currentPath = new Path(pathScanner);
		    	
		    	//System.out.println("path loaded"); //debug
		    	
		    	// Put it in the map, keyed to the file name
		    	pathMap.put(filename, currentPath);
    		}
    	}
    	catch (NullPointerException e)
    	{
    		System.out.println("Could not load the path.");
    		System.exit(0);
    	}
    	
    	return currentPath;
	}
	
	/**
	 * Returns a Scanner from the map of loaded <code>Scanner</code> objects
	 * contained in the single object of this class, set up to read from the
	 * specified text file. If the Scanner has not yet been loaded into the map,
	 * this method will load it into the map and then return it.
	 * 
	 * @param startButtonFile a <code>String</code> containing the name of a text file
	 *                 (with file extension) containing enemy generation info
	 * @return a <code>Scanner</code>, set up to read from the specified text file
	 */
	public Scanner getEnemyFile(String filename)
	{
		Scanner currentFile = null;
		
		// Load the enemy text file from the resources folder
    	try
		{
    		if(enemyMap.containsKey(filename))
    		{
    			// If this Scanner has already been loaded & created, just get it from the map and return it
    			return enemyMap.get(filename);
    		}
    		else
    		{
    			// Open a ClassLoader and Scanner with the given file name
		    	ClassLoader loader = this.getClass().getClassLoader();		    	
		    	currentFile = new Scanner(loader.getResourceAsStream("resources/" + filename));
		    	
		    	//System.out.println("file scanner loaded"); //debug
		    	
		    	// Put it in the map, keyed to the file name
		    	enemyMap.put(filename, currentFile);
    		}
    	}
    	catch (NullPointerException e)
    	{
    		System.out.println("Could not load the enemy file.");
    		System.exit(0);
    	}
		
		return currentFile;
	}
}
