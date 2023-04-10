package game;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import enemy.Enemy;
import screen.ScreenGameOver;

/**
 * A GameState object represents the current 'state' of the game. This includes
 * things like score, tower positions, etc., but also includes smaller details
 * like mouse location and mouse click information (that we have recorded).
 * Also, this object will hold a List of all the things that move, update, or
 * interact with the screen.
 * 
 * The idea is that everything that is unique about a single game's session is
 * here. If you were to save everything stored here to a file, and then reload
 * it later, the game would be in exactly the same 'state' as it was before.
 * 
 * There is exactly one GameState object for the entire game. (It's purpose is
 * to hold the data that changes as the game changes.)
 * 
 * @author Caden Erickson
 * @version December 07, 2021
 */
public class GameState
{
	// Fields
	// object list fields
	private List<Animatable> gameObjects;
	private List<Animatable> removeQueue;
	private List<Animatable> addQueue;
	
	// game info fields
	private double timeSinceStart;
	private int credits;
	private int lives;
	private int enemiesAlive;
	private boolean inPlay;
	private boolean isOver;
	private boolean moreEnemies;
	
	// mouse info fields
	private int mouseX, mouseY;
	private boolean mouseClicked;

	
	/**
	 * GameState constructor. The object built of this class will contain a list of
	 * all the animatable objects used in the game.
	 */
	public GameState()
	{
		gameObjects = new ArrayList<Animatable>();
		removeQueue = new ArrayList<Animatable>();
		addQueue    = new ArrayList<Animatable>();
		
		timeSinceStart = 0.0;
		credits = 100;
		lives = 10;
		enemiesAlive = 0;
		inPlay = false;
		isOver = false;
		moreEnemies = true;
		
		mouseX = mouseY = 0;
		mouseClicked = false;
	}

	/**
	 * Adds the passed object to the list of <code>Animatable</code> objects. This
	 * method is called from GameControl to add entities to the current state of the
	 * game.
	 * 
	 * @param object the <code>Animatable</code> object to add
	 */
	public void addGameObject(Animatable object)
	{
		addQueue.add(object);
	}
	
	/**
	 * Removes the passed object from the list of <code>Animatable</code> objects.
	 * This method is called from GameControl to remove entities from the current
	 * state of the game.
	 * 
	 * @param object the <code>Animatable</code> object to remove
	 */
	public void removeGameObject(Animatable object)
	{
		removeQueue.add(object);
	}
	
	/**
	 * Returns true if the specified <code>Enemy</code> has already been added to
	 * the <code>List</code> of objects to be removed from the game.
	 * 
	 * @param e the <code>Enemy</code> to check for in the list
	 * @return true if in the list, false otherwise
	 */
	public boolean alreadyQueuedToDie(Enemy e)
	{
		return removeQueue.contains(e); // true if contains(e) is true, false if false. Shorthand-ish
	}

	/**
	 * Updates pertinent info for each <code>Animatable</code> object currently
	 * stored in the list. This method is called repeatedly from the actionPerformed
	 * method in GameControl.
	 */
	public void updateAll(double elapsedTime)
	{
		// If the game isn't over
		if (!isOver)
		{
			// Update all objects in main List
			for (Animatable a : gameObjects)
			{
				a.update(elapsedTime);
			}
			
			// If the player is out of lives, make sure the counter stays at 0,
			// trip the inPlay and isOver flags, and add the ScreenGameOver object
			if (lives <= 0)
			{
				lives = 0;
				inPlay = false;
				isOver = true;
				addGameObject(new ScreenGameOver());
			}
			
			// Remove all objects queued for removal
			gameObjects.removeAll(removeQueue);
			removeQueue.clear();
			
			// Add all objects queued for adding
			gameObjects.addAll(addQueue);
			addQueue.clear();
		}
	}

	/**
	 * Draws each <code>Animatable</code> object currently stored in the list. This
	 * method is called from the paint() method in GameView, which is called
	 * repeatedly from the actionPerformed method in GameControl.
	 * 
	 * @param g the <code>Graphics</code> context in which to paint
	 */
	public void drawAll(Graphics g, GameView view)
	{
		for (Animatable a : gameObjects)
		{
			a.draw(g, view);
		}
	}
	
	/**
	 * Finds the nearest Enemy to a specified <code>Point</code>.
	 * 
	 * @param p the <code>Point</code> to be used as the origin
	 * @return the nearest Enemy object to that <code>Point</code>
	 */
	public Enemy nearestEnemy(Point p)
	{
		// no nearest enemy at first
		Enemy currentNearest = null;
		
		for (Animatable a : gameObjects) // every animatable object in the game
		{
			if (a instanceof Enemy) // only do stuff it we're looking at an Enemy object
			{
				Enemy currentEnemy = (Enemy)a;
				if (currentNearest == null) // if it's the first one, it's the current nearest
				{
					currentNearest = currentEnemy;
				}
				else
				{
					if (p.distance(currentEnemy.getPosition()) < p.distance(currentNearest.getPosition()))
					{
						currentNearest = currentEnemy;
					}
				}				
			}
		}
		
		return currentNearest;
	}
	
	
	// ------------------------------- A C C E S S O R S   A N D   M U T A T O R S ------------------------------- //
	
	// MOUSE STUFF
	public void setMousePosition(int mouseX, int mouseY)
	{
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	public int getMouseX()
	{
		return mouseX;
	}
	
	public int getMouseY()
	{
		return mouseY;
	}
	
	public void setMouseClicked()
	{
		mouseClicked = true;
	}
	
	public boolean isMouseClicked()
	{
		return mouseClicked;
	}
	
	public void consumeMouseClick()
	{
		mouseClicked = false;
	}
	
	// TIME
	public double getTime()
	{
		return timeSinceStart;
	}
	
	public void updateTime(double lastTick)
	{
		timeSinceStart += lastTick;
	}
	
	public void resetTime()
	{
		// This only gets called when we reset the timer so that the gameplay
		// is counting from when the player presses start
		timeSinceStart = 0;
	}
	
	// CREDITS
	public int getCredits()
	{
		return credits;
	}
	
	public void updateCredits(int change)
	{
		credits += change;
		
		// Don't let them go negative
		if (credits <= 0)
		{
			credits = 0;
		}
	}
	
	// LIVES
	public int getLives()
	{
		return lives;
	}
	
	public void updateLives(int change)
	{
		lives += change;
	}
	
	// ENEMY COUNT
	public void decrementEnemyCount()
	{
		enemiesAlive++;
	}
	
	public void incrementEnemyCount()
	{
		enemiesAlive--;
	}
	
	public int getNumEnemies()
	{
		return enemiesAlive;
	}
	
	// INPLAY/OVER/MOREENEMIES FLAGS
	public void startPlay()
	{
		inPlay = true;
	}
	
	public boolean isInPlay()
	{
		return inPlay;
	}
	
	public boolean isOver()
	{
		return isOver;
	}
	
	public void noMoreEnemies()
	{
		moreEnemies = false;
	}
	
	public boolean moreEnemiesComing()
	{
		return moreEnemies;
	}
}
