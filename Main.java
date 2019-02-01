package control;

import java.util.ArrayList;

import enums.Strategy;
import game.ChopsticksGame;
import game.Player;

/**
 * This is a program designed to simulate the game of chop sticks. 
 * 
 * The rules are very simple: each player hold out both hands with one finger 
 * raised; players take turns 'attacking' one of their opponent's hands with 
 * their hand by raising fingers on the opponent's hand equal to the number 
 * of fingers on the attacking hand; when a hand has all 5 fingers (counting
 * thumb as a finger) raised, it is out; once all players but one have both
 * hands out, the winner is the remaining player.
 * 
 * This simulation's goal is to observe the optimal strategy of the game. The
 * method for accomplishing this is to give each player a distinct strategy
 * from a set of 5 strategies:
 * 
 * Random: takes a turn without regard to strategy
 * Slow: uses hand with least fingers to attack opposing hand with least
 * Fast: uses hand with most fingers to attack opposing hand with most
 * Aggressive: uses hand with most fingers to attack opposing hand with least
 * Passive: uses hand with least fingers to attack opposing hand with most
 * 
 * Random is considered to be all possible other strategies that lie in between
 * the basic ones. In a game with more than 2 players, the program ensures a
 * player will assess all possible moves that cohere with their strategy and 
 * pick one randomly to emulate how a human might choose between opponents.
 * 
 * The optimal strategy for 2 players is to go first and use the fast strategy,
 * the only counter to this is random, which can win 25% of the time. This
 * means that there is a carefully considered strategy not included in this 
 * simulation that can win out against fast when going second.
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