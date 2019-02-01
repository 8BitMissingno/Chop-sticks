package control;

import java.util.ArrayList;

import enums.Strategy;
import game.ChopsticksGame;
import game.Player;

/**
 * Simulates the game of chop sticks. 
 * @author Lucas Gomez
 * @version 1.0
 */
public class Main {
	/**
	 * The number of games to run for each combination of strategies.
	 */
	private static final int NUM_GAMES = 100;
	
	
	public static void main(String[] args)
	{
		ArrayList<Strategy[]> games = twoPlayerCombos();
		ArrayList<Player> winners = new ArrayList<Player>();
		ChopsticksGame game = null;
		int paradigmNum = 1;
		int strategy0Wins = 0;
		int strategy1Wins = 0;
		// Run analysis
		for (Strategy[] paradigm : games)
		{
			// Play games
			for (int i = 0; i < NUM_GAMES; i++)
			{
				game = new ChopsticksGame(paradigm);
				game.play(false);
				winners.add(game.getWinPlayer());
			}
			// Print analysis
			for (Player player : winners)
			{
				if (player.lookStrategy() == paradigm[0])
				{
					strategy0Wins++;
				}
				else if (player.lookStrategy() == paradigm[1])
				{
					strategy1Wins++;
				}
			}
			System.out.println(
					"Paradigm " + paradigmNum + ": \n" + paradigm[0] + 
					" with " + strategy0Wins + " wins\n" + paradigm[1] + 
					" with " + strategy1Wins + " wins\n");
			paradigmNum++;
			strategy0Wins = 0;
			strategy1Wins = 0;
			winners.clear();
		}
	}
	
	
	/**
	 * @returns An ArrayList containing all possible combinations of strategies
	 * in a two-player game of chop sticks. Each entry represents a game of two
	 * players with a strategy corresponding to each.
	 */
	public static ArrayList<Strategy[]> twoPlayerCombos()
	{
		ArrayList<Strategy[]> games = new ArrayList<Strategy[]>();
		Strategy[] strategies = { 
				Strategy.RANDOM,
				Strategy.FAST,
				Strategy.SLOW,
				Strategy.AGGRESSIVE,
				Strategy.PASSIVE };
		// Add all games where both players use the same strategy.
		for (int i = 0; i < strategies.length; i++)
		{
			Strategy[] temp = { strategies[i], strategies[i] };
			games.add(temp);
		}
		// Add all games considered in an nPr calculation.
		for (int i = 0; i < strategies.length - 1; i++)
		{
			for (int k = i + 1; k < strategies.length; k++)
			{
				Strategy[] temp1 = { strategies[i], strategies[k] };
				games.add(temp1);
				Strategy[] temp2 = { strategies[k], strategies[i] };
				games.add(temp2);
			}
		}
		return games;
	}
}
