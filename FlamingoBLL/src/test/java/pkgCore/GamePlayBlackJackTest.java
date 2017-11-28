package pkgCore;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import pkgEnum.eBlackJackResult;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWinning() {

		Table t = new Table();
		Player p1 = new Player("XXX", 1);
		t.AddPlayerToTable(p1);
		Deck d = new Deck();
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);

		HandBlackJack hand1 = new HandBlackJack();
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.ACE));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.QUEEN));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.KING));
		hand1.AddCard(new Card(eSuit.HEARTS, eRank.SEVEN));

		gpBJ.sethDealer(hand1);

		HandBlackJack hand2 = new HandBlackJack();
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.NINE));
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.FOUR));

		GamePlayerHand GPH = new GamePlayerHand(gpBJ.getGameID(), p1.getPlayerID(), hand2.getHandID());
		gpBJ.putHandToGame(GPH, hand2);

		gpBJ.ScoreGame(GPH);
		assertEquals(eBlackJackResult.WIN, hand2.geteBlackJackResult());
	}

	@Test
	public void TestPlayerLosing() {
		Table t = new Table();
		Player p1 = new Player("XXX", 1);
		t.AddPlayerToTable(p1);
		Deck d = new Deck();
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);

		HandBlackJack hand1 = new HandBlackJack();
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.ACE));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.JACK));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.KING));
		hand1.AddCard(new Card(eSuit.HEARTS, eRank.SEVEN));

		// Assign a hand to the dealer
		gpBJ.sethDealer(hand1);

		// Create a hand for the player
		HandBlackJack hand2 = new HandBlackJack();
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.NINE));
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.FOUR));
		GamePlayerHand GPH = new GamePlayerHand(gpBJ.getGameID(), p1.getPlayerID(), hand2.getHandID());
		gpBJ.putHandToGame(GPH, hand2);

		gpBJ.ScoreGame(GPH);
		assertEquals(eBlackJackResult.LOSE, hand2.geteBlackJackResult());
	}

	@Test
	public void TestPlayerPush() {
		Table t = new Table();
		
		Player p1 = new Player("Mike", 1);
		Player p2 = new Player("John", 2);
		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);
		
		Deck d = new Deck();
		
		GamePlayBlackJack testGame = new GamePlayBlackJack(t.getHmTablePlayer(), d);
		
		HandBlackJack hand1 = new HandBlackJack();
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.FIVE));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.THREE));
		hand1.AddCard(new Card(eSuit.HEARTS, eRank.SEVEN));
		
		testGame.sethDealer(hand1);
		
		HandBlackJack hand2 = new HandBlackJack();
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.NINE));
		hand2.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		
		GamePlayerHand GPH = new GamePlayerHand(testGame.getGameID(),p1.getPlayerID(),hand2.getHandID());
		testGame.putHandToGame(GPH, hand2);

		testGame.ScoreGame(GPH);
		assertEquals(eBlackJackResult.TIE, hand2.geteBlackJackResult());
		
			
	}

	@Test
	public void TestTwoPlayersWinning() {
		Table t = new Table();

		Player p1 = new Player("XXX XXX", 1);
		Player p2 = new Player("XX  XX", 2);

		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);

		Deck deck = new Deck();

		GamePlayBlackJack testGame = new GamePlayBlackJack(t.getHmTablePlayer(), deck);

		HandBlackJack hand1 = new HandBlackJack();
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.FIVE));
		hand1.AddCard(new Card(eSuit.CLUBS, eRank.THREE));
		hand1.AddCard(new Card(eSuit.HEARTS, eRank.SEVEN));
		testGame.sethDealer(hand1);

		HandBlackJack playerHand1 = new HandBlackJack();
		playerHand1.AddCard(new Card(eSuit.CLUBS, eRank.TEN));
		playerHand1.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		playerHand1.AddCard(new Card(eSuit.CLUBS, eRank.EIGHT));

		HandBlackJack playerHand2 = new HandBlackJack();
		playerHand2.AddCard(new Card(eSuit.CLUBS, eRank.THREE));
		playerHand2.AddCard(new Card(eSuit.CLUBS, eRank.TWO));
		playerHand2.AddCard(new Card(eSuit.CLUBS, eRank.SEVEN));
		playerHand2.AddCard(new Card(eSuit.CLUBS, eRank.FOUR));
		playerHand2.AddCard(new Card(eSuit.CLUBS, eRank.FIVE));

		GamePlayerHand GPH1 = new GamePlayerHand(testGame.getGameID(), p1.getPlayerID(), playerHand1.getHandID());
		testGame.putHandToGame(GPH1, playerHand1);

		GamePlayerHand GPH2 = new GamePlayerHand(testGame.getGameID(), p2.getPlayerID(), playerHand2.getHandID());
		testGame.putHandToGame(GPH2, playerHand2);

		testGame.ScoreGame(GPH1);

		assertEquals(eBlackJackResult.WIN, playerHand1.geteBlackJackResult());
		assertEquals(eBlackJackResult.WIN, playerHand2.geteBlackJackResult());
	}

}
