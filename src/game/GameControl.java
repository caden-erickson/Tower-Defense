package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.Timer;

import enemy.EnemyBlue;
import enemy.EnemyGreen;
import enemy.EnemyPink;
import screen.ScreenStart;
import screen.ScreenWaveTitle;
import screen.ScreenWin;

/**
 * A GameControl object represents all of the logic and control needed to make
 * the game operate. The control is responsible for setting up the animation
 * timer, updating positions, dealing with user actions, etc.
 * 
 * There is exactly one GameControl object for the entire game. (That's it's job
 * - to control the game.)
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class GameControl implements Runnable, ActionListener
{
	// Fields
	// high-level fields
	private GameView view;
	private GameState state;

	// timer fields
	private Timer timer;
	private long previousTime;
	private static double BILLION = 1_000_000_000.0;

	// enemy spawn fields
	double nextSpawnTime;
	private static double QUARTER = 0.25;
	Scanner enemyLineUp;
	
	
	/**
	 * Constructor - default (currently).<br>
	 * All setup is done in the <code>run()</code> method.
	 */
    public GameControl() { }    
    
	/**
	 * Where the magic happens.<br>
	 * This method sets up the game state and window, adds the backgrounds to the
	 * window, and runs the timer.
	 */
    public void run()
    {    	
    	// Build the game state.
    	state = new GameState();
    	
    	// Build a view.  Note that the view builds it's own frame, etc.  All the work is there.
    	view = new GameView(state);
    	
    	// Add initial view to the game
    	state.addGameObject(new ScreenStart(state, view));
    	
    	// Start the animation loop.
    	// This timer expires and repeats ~60 times/second, causing an ActionEvent
    	// each time, which is handled in the actionPerformed method below.
    	timer = new Timer(16, this);
    	timer.start();
    	
    	// Initialize time tracker
    	previousTime = System.nanoTime();
    	    	
		/*
		 * Variables for adding the enemies systematically.
		 * The enemies text file contains integers corresponding to enemies
		 * to add, and nextSpawn will be a progressing variable the reading of 
		 * that file at intervals. nextSpawn starts at the time the play is
		 * activated, plus one second.
		 */
    	nextSpawnTime = 0.0;
		enemyLineUp = ResourceLoader.getLoader().getEnemyFile("enemies.txt");
    }

	/**
	 * Called whenever an action is performed for which we are listening. The timer
	 * above registers this object as a listener for its events.
	 * 
	 * This method gets called repeatedly, so it acts like a loop. This makes it a great
	 * tool as a cycling animation method.
	 * 
	 * @param e the <code>ActionEvent</code> object to be used for progressing the
	 *          animation
	 */
	public void actionPerformed(ActionEvent e)
	{			
		// If the lives dropped below 0 and tripped the inPlay flag,
		// then stop the timer
		if (state.isOver())
		{
			timer.stop();
		}
		
		// If no more enemies are coming, and there are no enemies alive,
		// stop the timer and display the win screen
		if (!state.moreEnemiesComing() && state.getNumEnemies() == 0)
		{
			state.addGameObject(new ScreenWin(state));
			timer.stop();
		}

		// Get the current runtime of the game in nanoseconds
		long currentTime = System.nanoTime();
		
		// If the enemy generation text file has more data, and we've passed the next
		// spawn time, call the addEnemies method and bump the next spawn time by a
		// quarter second.
		if (enemyLineUp.hasNext() && state.getTime() > nextSpawnTime && state.isInPlay())
		{
			addEnemies();
			nextSpawnTime += QUARTER;
		}		
		
		// Calculate time since last tick- used in updating positions of Animatable
		// objects
		double elapsedTime = (currentTime - previousTime) / BILLION;
		previousTime = currentTime;
		state.updateTime(elapsedTime);
		
		// Update the game objects
		state.updateAll(elapsedTime);
		
		// Consume click event if no object did so
		state.consumeMouseClick();
	
		// Draw/redraw the game objects
		view.repaint();
	}
	
	/**
	 * This method adds <code>Enemy</code> objects to the <code>List</code> of
	 * <code>Animatable</code> objects in <code>GameState</code>, the type of
	 * <code>Enemy</code> being determined by the data found in the enemy generation
	 * text file.
	 */
	public void addEnemies()
	{
		// Read in another digit from the enemy generation text file,
		// and add an enemy accordingly:
		// 1 = pink, 2 = blue, 3 = green, 0 = spacer
		// 9 marks the end of the enemy generation file
		// Two-digits starting with 1 (11, 12, 13, etc) divide between waves
		int scanDigit = enemyLineUp.nextInt();
		switch(scanDigit)
		{
			case 0: 
				break;
			case 1:
				state.addGameObject(new EnemyPink(0.0, state));
				break;
			case 2:
				state.addGameObject(new EnemyBlue(0.0, state));
				break;
			case 3:
				state.addGameObject(new EnemyGreen(0.0, state));
				break;
			case 9:
				state.noMoreEnemies();
				break;
			default:
				state.addGameObject(new ScreenWaveTitle(state, scanDigit));
		}
	}
}
