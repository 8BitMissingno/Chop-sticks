package game;

/**
 * Represents a finger on a hand in the game chop sticks.
 * @author Lucas Gomez
 * @version 1.0
 */
public class Finger 
{
	/**
	 * Whether the finger is raised.
	 */
	private boolean isRaised;
	
	
	/**
	 * Creates a new finger ready to play chop sticks.
	 */
	public Finger()
	{
		isRaised = false;
	}
	
	
	/**
	 * Raises this finger.
	 */
	public void raise()
	{
		isRaised = true;
	}
	
	
	/**
	 * @return Whether or not this finger is raised.
	 */
	public boolean isRaised()
	{
		return isRaised;
	}
}
