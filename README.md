# Chop-sticks
This is a program designed to simulate the game of chop sticks. 

The rules are very simple: each player hold out both hands with one finger 
raised; players take turns 'attacking' one of their opponent's hands with 
their hand by raising fingers on the opponent's hand equal to the number 
of fingers on the attacking hand; when a hand has all 5 fingers (counting
thumb as a finger) raised, it is out; once all players but one have both
hands out, the winner is the remaining player.

This simulation's goal is to observe the optimal strategy of the game. The
method for accomplishing this is to give each player a distinct strategy
from a set of 5 strategies:

Random: takes a turn without regard to strategy
Slow: uses hand with least fingers to attack opposing hand with least
Fast: uses hand with most fingers to attack opposing hand with most
Aggressive: uses hand with most fingers to attack opposing hand with least
Passive: uses hand with least fingers to attack opposing hand with most

Random is considered to be all possible other strategies that lie in between
the basic ones. In a game with more than 2 players, the program ensures a
player will assess all possible moves that cohere with their strategy and 
pick one randomly to emulate how a human might choose between opponents.

The optimal strategy for 2 players is to go first and use the fast strategy,
the only counter to this is random, which can win 25% of the time. This
means that there is a carefully considered strategy not included in this 
simulation that can win out against fast when going second.
