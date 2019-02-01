package game;

import java.util.HashMap;
import java.util.Map.Entry;

import enums.Handed;
import enums.Strategy;

import java.util.Random;

/**
 * Simulates the game of chop sticks. 
 * @author Lucas Gomez
 * @version 1.0
 */
public class ChopsticksGame 
{
	/**
	 * Random number generator.
	 */
	private static Random rng;
	/**
	 * Each player in the game.
	 */
	private Player[] players;
	/**
	 * Whether this game has been won.
	 */
	private boolean isWon;
	
	
	/**
	 * Begins a new game of chop sticks.
	 * @param strategies List of each player's strategy.
	 */
	public ChopsticksGame(Strategy... strategies)
	{
		players = new Player[strategies.length];
		rng = new Random();
		isWon = false;
		for (int i = 0; i < strategies.length; i++)
		{
			players[i] = new Player(i, strategies[i]);
		}
	}
	
	
	/**
	 * Plays a game of chop sticks.
	 * @param doPrint Whether or not to print the status of the game.
	 */
	public void play(boolean doPrint)
	{
		long calcTime = System.currentTimeMillis();
		Player player = null;
		int playerIndex = 0;
		int turn = 1;
		// Play the game.
		if (doPrint)
		{
			printStatus(0, players);
		}
		while (!isWon)
		{
			// Ensure out players do not take a turn.
			if (!players[playerIndex].isLoser())
			{
				player = players[playerIndex];
				takeTurn(player);
				checkIfWon();
				if (doPrint)
				{
					printStatus(turn, players);
				}
				turn++;
			}
			// Wrap around to player 1 if on the last player.
			if (playerIndex == players.length - 1)
			{
				playerIndex = 0;
			}
			else
			{
				playerIndex++;
			}
		}
		calcTime = System.currentTimeMillis() - calcTime;
		if (doPrint)
		{
			printResults(turn, player, calcTime);
		}
		
	}
	
	
	/**
	 * @returns The winning player of this game. Game must be completed or 
	 * this method will return the first player.
	 */
	public Player getWinPlayer()
	{
		int i = 0;
		boolean isFound = false;
		while (!isFound)
		{
			if (!players[i].isLoser())
			{
				isFound = true;
			}
			else
			{
				i++;
			}
		}
		return players[i];
	}
	
	
	/**
	 * Checks to see if this game has been won and ends the game if so.
	 */
	private void checkIfWon()
	{
		int remainingPlayers = 0;
		for (Player player : players)
		{
			if (!player.isLoser())
			{
				remainingPlayers++;
			}
		}
		if (remainingPlayers == 1)
		{
			isWon = true;
		}
	}
	
	
	/**
	 * Takes a turn using each player's strategy to select their decision.
	 * @param player Attacking player.
	 */
	private void takeTurn(Player player)
	{
		HashMap<Player, Handed> candidates = new HashMap<Player, Handed>();
		Strategy strategy = player.lookStrategy();
		Hand searchHand = null;
		Handed playerHand = null;
		// Setup strategy
		switch (strategy)
		{
			case RANDOM:
				Player opponent = randomPlayer(player);
				playerHand = randomHand(player);
				candidates.put(opponent, randomHand(opponent));
				break;
			case SLOW:
				searchHand = searchLowest(player);
				playerHand = player.getLowHand();
				break;
			case FAST:
				searchHand = searchHighest(player);
				playerHand = player.getHighHand();
				break;
			case AGGRESSIVE:
				searchHand = searchLowest(player);
				playerHand = player.getHighHand();
				break;
			case PASSIVE:
				searchHand = searchHighest(player);
				playerHand = player.getLowHand();
				break;
		}
		// Only find candidates if we are not randomly picking
		if (searchHand != null)
		{
			findCandidates(candidates, player, searchHand);
		}
		// Make decision and attack.
		Entry<Player, Handed> decision = 
				candidates.entrySet().iterator().next();
		player.attack(playerHand, decision.getValue(), decision.getKey());
	}
	
	
	/**
	 * Finds all hands except for the turn player's which match the search hand
	 * and stores each match in candidates.
	 * @param candidates Empty map to be filled with possible moves
	 * @param player Turn player to not be considered
	 * @param searchHand Hand to find matches of
	 */
	private void findCandidates(
			HashMap<Player, Handed> candidates,
			Player player,
			Hand searchHand)
	{
		// Find candidates
		for (int i = 0; i < players.length; i++)
		{
			if (i != player.lookIndex() && !players[i].isLoser())
			{
				if (players[i].getHand(Handed.LEFT).count() == 
						searchHand.count())
				{
					candidates.put(players[i], Handed.LEFT);
				}
				if (players[i].getHand(Handed.RIGHT).count() == 
						searchHand.count())
				{
					candidates.put(players[i], Handed.RIGHT);
				}
			}
		}
	}
	
	
	/**
	 * Randomly selects a player who is not out and not the turn player.
	 * @param turnPlayer Index of the player that should not be randomly 
	 * selected.
	 * @returns A randomly selected player that is not at the specified index.
	 */
	private Player randomPlayer(Player player)
	{
		int randomPlayer = player.lookIndex();
		while (randomPlayer == player.lookIndex() || 
				players[randomPlayer].isLoser())
		{
			randomPlayer = rng.nextInt(players.length);
		}
		return players[randomPlayer];
	}
	
	
	/**
	 * @param player Who to randomly select a hand from.
	 * @returns A randomly selected handedness which corresponds to a hand of
	 * the player which is not out.
	 */
	private Handed randomHand(Player player)
	{
		Handed hand = (rng.nextBoolean() ? Handed.LEFT : Handed.RIGHT);
		// If one hand is out, pick the other.
		if (!player.getHand(hand).isIn())
		{
			if (hand == Handed.LEFT)
			{
				hand = Handed.RIGHT;
			}
			else if (hand == Handed.RIGHT)
			{
				hand = Handed.LEFT;
			}
		}
		return hand;
	}
	
	
	/**
	 * Searches for the lowest hand among all players except the turn player.
	 * @param player Turn player to not be considered.
	 * @returns Lowest hand.
	 */
	private Hand searchLowest(Player player)
	{
		Hand hand = new Hand();
		for (int i = 0; i < players.length; i++)
		{
			if (i != player.lookIndex())
			{
				if (hand.count() > players[i].getHand(Handed.LEFT).count())
				{
					hand = players[i].getHand(Handed.LEFT);
				}
				if (hand.count() > players[i].getHand(Handed.RIGHT).count())
				{
					hand = players[i].getHand(Handed.RIGHT);
				}
			}
		}
		return hand;
	}
	
	
	/**
	 * Searches for the highest hand among all players except the turn player.
	 * @param player Turn player to not be considered.
	 * @returns Highest hand.
	 */
	private Hand searchHighest(Player player)
	{
		/* Handed does not matter, just using the constructor to create a 
		 * starting hand. */
		Hand hand = new Hand(Handed.LEFT);
		for (int i = 0; i < players.length; i++)
		{
			if (i != player.lookIndex())
			{
				if (hand.count() < players[i].getHand(Handed.LEFT).count() &&
						players[i].getHand(Handed.LEFT).isIn())
				{
					hand = players[i].getHand(Handed.LEFT);
				}
				if (hand.count() < players[i].getHand(Handed.RIGHT).count() &&
						players[i].getHand(Handed.RIGHT).isIn())
				{
					hand = players[i].getHand(Handed.RIGHT);
				}
			}
		}
		return hand;
	}
	
	
	/**
	 * Prints out the current game state for each player.
	 * @param players A list of every Player in the game.
	 */
	private static void printStatus(int turn, Player... players)
	{
		int playerNum = 1;
		System.out.println("---TURN " + turn + "---");
		for (Player player : players)
		{
			System.out.print(
					"<PLAYER " + playerNum + ">\n" + 
					"right is in: " +
					player.getHand(Handed.RIGHT).isIn() +
					", count " +
					player.getHand(Handed.RIGHT).count() +
					"\nleft is in: " +
					player.getHand(Handed.LEFT).isIn() +
					", count " +
					player.getHand(Handed.LEFT).count() +
					"\nplayer is out: " +
					player.isLoser());
			playerNum++;
			System.out.println("\n");
		}
		System.out.println();
	}
	
	
	/**
	 * Prints out information from a completed game.
	 * @param turn Number of turns taken.
	 * @param player Victorious player.
	 * @param calcTime Time taken for this game to be simulated.
	 */
	private void printResults(int turn, Player player, long calcTime)
	{
		System.out.println(
				">>> GAME RESULTS <<<\n--------------------\n" +
				"Number of Players: " + players.length +
				"\nNumber of Turns: " + turn +
				"\nWinner: PLAYER " + (player.lookIndex() + 1) +
				"\nWinning Strategy: " + player.lookStrategy() +
				"\nCalculation Time: " + (calcTime / 60000) + 
				" minute(s) (" + ((float) calcTime / 1000) + " seconds)");
	}
}