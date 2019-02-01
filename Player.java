package game;

import enums.Handed;
import enums.Strategy;

/**
 * A class designed to represent a player in the game chop sticks
 * @author Lucas Gomez
 * @version 1.0
 */
public class Player 
{
	/**
	 * Hand object representing Player's left hand
	 */
	private Hand left;
	/**
	 * Hand object representing Player's right hand
	 */
	private Hand right;
	/**
	 * Boolean representing whether or not this Player has lost.
	 */
	private boolean isLoser;
	/**
	 * This Player's index
	 */
	private int index;
	/**
	 * This Player's game strategy
	 */
	private Strategy strategy;
	
	
	/**
	 * Sets up a new Player to play chop sticks.
	 */
	public Player(int index, Strategy strategy)
	{
		this.strategy = strategy;
		this.index = index;		
		left = new Hand(Handed.LEFT);
		right = new Hand(Handed.RIGHT);
		isLoser = false;
	}
	
	
	/**
	 * @returns This Player's index
	 */
	public int lookIndex()
	{
		return index;
	}
	
	
	/**
	 * @returns This Player's game strategy.
	 */
	public Strategy lookStrategy()
	{
		return strategy;
	}
	
	/**
	 * @returns Boolean representing whether or not this player has lost.
	 */
	public boolean isLoser()
	{
		return isLoser;
	}
	
	
	/**
	 * Adds all fingers from a hand to one of the opponent's hands.
	 * @param attackHand Own hand to add from.
	 * @param defenseHand Opponent's hand to add to.
	 * @param opponent Opponent being attacked.
	 */
	void attack(
			Handed attackHand, 
			Handed defenseHand, 
			Player opponent)
	{
		opponent.getHand(defenseHand).raiseFingers(this.getHand(attackHand));
		opponent.checkIfLoser();
	}

	
	/**
	 * @param hand Desired hand of Player.
	 * @returns Left or right hand of Player.
	 */
	Hand getHand(Handed hand)
	{
		Hand output = null;
		if (hand == Handed.LEFT)
		{
			output = left;
		}
		else if (hand == Handed.RIGHT)
		{
			output = right;
		}
		return output;
	}
	
	
	/**
	 * @returns Hand with the highest number of fingers raised. Returns null if
	 * player is out.
	 */
	Handed getHighHand()
	{
		Hand highHand = null;
		if (left.isIn() && right.isIn())
		{
			if (left.count() > right.count()) 
			{
				highHand = left;
			}
			else
			{
				highHand = right;
			}
		}
		else
		{
			if (left.isIn()) 
			{
				highHand = left;
			}
			else if (right.isIn())
			{
				highHand = right;
			}
		}
		return highHand.lookHanded();
	}
	
	
	/**
	 * @returns Hand with the lowest number of fingers raised.
	 */
	Handed getLowHand()
	{
		Hand lowHand = null;
		if (left.count() < right.count()) 
		{
			lowHand = left;
		}
		else
		{
			lowHand = right;
		}
		return lowHand.lookHanded();
	}
	
	/**
	 * Checks this Player to see if they have had both hands eliminated from 
	 * the game and removes them from the game if so.
	 */
	private void checkIfLoser()
	{
		if (!left.isIn() && !right.isIn())
		{
			isLoser = true;
		}
	}
}
