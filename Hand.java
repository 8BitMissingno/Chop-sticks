package game;

import enums.Handed;

/**
 * Represents a hand in the game of chop sticks.
 * @author Lucas Gomez
 * @version 1.0
 */
public class Hand 
{
	/**
	 * Number of fingers on a hand.
	 */
	private static final int NUM_FINGERS = 5;
	/**
	 * Each finger on a hand.
	 */
	private Finger[] fingers;
	/**
	 * Whether or not the hand is still in the game.
	 */
	private boolean isIn;
	/**
	 * Which hand this is.
	 */
	private Handed handedness;
	
	
	/**
	 * Creates a new hand ready to play chop sticks.
	 */
	public Hand(Handed hand)
	{
		isIn = true;
		handedness = hand;
		fingers = new Finger[NUM_FINGERS];
		for (int i = 0; i < NUM_FINGERS; i++)
		{
			fingers[i] = new Finger();
		}
		// Hand starts with a single raised finger.
		fingers[0].raise();
	}
	
	
	/**
	 * Creates a hand which is out of the game (for searching purposes).
	 */
	public Hand()
	{
		isIn = false;
		handedness = Handed.LEFT;
		fingers = new Finger[NUM_FINGERS];
		for (int i = 0; i < NUM_FINGERS; i++)
		{
			fingers[i] = new Finger();
			fingers[i].raise();
		}
	}
	
	
	/**
	 * @returns Whether or not the hand is in the game.
	 */
	public boolean isIn()
	{
		return isIn;
	}
	
	
	/**
	 * @returns Which hand this is.
	 */
	public Handed lookHanded()
	{
		return handedness;
	}
	
	
	/**
	 * Raises fingers on defending hand equal to the fingers of an attacking
	 * hand. 
	 * @param hand Attacking hand, cannot be out of the game.
	 */
	void raiseFingers(Hand hand)
	{
		int defenseStart = 0;
		int attackItr = 0;
		// Find the first defending finger not raised.
		while (this.fingers[defenseStart].isRaised() != false)
		{
			defenseStart++;
		}
		// Raise fingers on defending hand.
		while (hand.fingers[attackItr].isRaised() == true && isIn)
		{
			this.fingers[defenseStart].raise();
			attackItr++;
			defenseStart++;
			this.checkIfIn();
		}
	}
	
	
	/**
	 * Counts how many fingers are raised on a hand.
	 * @returns Integer representing number of fingers raised.
	 */
	int count()
	{
		int count = 0;
		while (count < NUM_FINGERS && fingers[count].isRaised() == true)
		{
			count++;
		}
		return count;
	}
		
	
	/**
	 * Checks if this hand is still in the game, then removes it from the 
	 * game if not.
	 */
	private void checkIfIn()
	{
		// We only care if the hand is full, a.k.a the 5th finger is raised.
		boolean isOut = fingers[NUM_FINGERS - 1].isRaised();
		if (isOut)
		{
			isIn = false;
		}
	}
}
