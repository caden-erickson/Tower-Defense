package enemy;

import game.GameState;

/**
 * An <code>EnemyGreen</code> object represents a green germ drawn on the
 * viewable window.
 * 
 * Objects of this class will store data about the germ such as its position,
 * size, velocity, and strength, and will have functionality to draw the germ
 * and incrementally update its position.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EnemyGreen extends Enemy
{
	// Fields

	/**
	 * Green Enemy constructor. Objects built of this class will have functionality
	 * given by the Enemy superclass, as well as values for name, speed, and size.
	 * 
	 * @param percentTraveled how far along the path the germ has gone, given as a
	 *                        percentage between 0.0 and 1.0
	 * @param state           the current GameState object
	 */
	public EnemyGreen(double percentTraveled, GameState state)
	{
		super(percentTraveled, state);
		name = "germ_green.png";
		velocity = 0.015;
		size = 40;
		potency = -15;
	}

	/**
	 * When a green enemy dies, it will create 3 blue enemies and then erase itself.
	 * Those 3 enemies will be spaced 1.5% of a path length apart, centered at where
	 * the green enemy died.
	 */
	public void die()
	{
		/*
		 * When this condition wasn't here, two spray particles could call the die
		 * function on the same enemy and these statements would get called twice,
		 * leading to the enemy count decreasing by 2 for 1 enemy killed.
		 */
		if (!state.alreadyQueuedToDie(this))
		{
			for (int i = 0; i < 3; i++)
			{
				state.addGameObject(new EnemyBlue((percentTraveled + i*0.015) - 0.015, state));
			}
			state.decrementEnemyCount();
			state.removeGameObject(this);
		}
	}
}
