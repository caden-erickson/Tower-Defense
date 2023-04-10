package tower;

import effect.EffectSquirt;
import enemy.Enemy;
import game.GameState;

/**
 * A <code>TowerSanitizer</code> object represents a Sanitizer tower drawn on
 * the active game field.
 * 
 * Objects built of this class will store data about their position, the name of
 * their image file, and the width and height at which they're displayed, and
 * will have functionality to draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class TowerSanitizer extends Tower
{
	// Fields
	double timeSinceFire;

	/**
	 * TowerBleach constructor. Objects built of this class will have functionality
	 * given by the Enemy superclass, as well as values for name, width, height, and
	 * how long it's been since they've fired an attack.
	 * 
	 * @param state the current <code>GameState</code> object
	 * @param x     the x coordinate
	 * @param y     the y coordinate
	 */
	public TowerSanitizer(GameState state, int x, int y)
	{
		super(state, x, y);
		name = "sanitizer.png";
		width = 30;
		height = 50;
		timeSinceFire = 0.0;
	}

	/**
	 * Updates the timeSinceFire variable, and detects nearby enemies, firing
	 * at them and creating attack effects when in range.
	 * 
	 * @param timeElapsed how many seconds since the last update
	 */
	public void update(double timeElapsed)
	{
		timeSinceFire += timeElapsed;
		Enemy victim = state.nearestEnemy(position);
		// If an enemy is in range, fire
		if (victim != null && position.distance(victim.getPosition()) < 100 && timeSinceFire > 1.5)
		{
			state.addGameObject(new EffectSquirt(state, position, victim.getPosition()));
			timeSinceFire = 0;
		}
	}
}
