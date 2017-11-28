package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();

	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {

		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);

		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.putHandToGame(GPH, h);
		}
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());

			h.AddCard(c);

			this.putHandToGame(GPH, h);

		}
		return c;
	}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		Hand h = this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack) h.ScoreHand();

		// TODO: Determine if the player can draw another card (are they busted?)
		for (Integer i : HSB.getNumericScores()) {
			if (i <= 21) {
				bCanPlayerDraw = true;
				break;
			}
		}

		return bCanPlayerDraw;
	}

	public boolean bDoesDealerHaveToDraw() throws HandException {
		boolean bDoesDealerHaveToDraw = true;

		HandScoreBlackJack HSB = (HandScoreBlackJack) hDealer.ScoreHand();

		for (Integer i : HSB.getNumericScores()) {
			if (((i <= 21) && (i >= 17)) || (i > 21)) {
				bDoesDealerHaveToDraw = false;
				break;
			}

		}

		return bDoesDealerHaveToDraw;
	}

	public void ScoreGame(GamePlayerHand GPH) {
		boolean bIsHandWinner = false;
		
		Iterator it = this.getHmGameHands().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			GamePlayerHand sGPH = (GamePlayerHand) pair.getKey();
			HandBlackJack sH = (HandBlackJack) pair.getValue();

			if (sGPH.getGameID() == GPH.getGameID()) {
				sH.seteBlackJackResult(ScoreDealerPlayer((HandBlackJack) hDealer, sH));
				if (sH.geteBlackJackResult()==eBlackJackResult.WIN)
				{
					bIsHandWinner = true;
				}
			}
		}
	}
	
	private boolean isBusted(HandScoreBlackJack HSB) {
		boolean isBusted = true;

		for (Integer i : HSB.getNumericScores()) {

			if (i <= 21) {
				isBusted = false;

				break;
			}
		}

		return isBusted;

	}
	
	private int HighestNotBustedScore(HandScoreBlackJack HSB) {
		int iHighestScore = 0;
		for (Integer i : HSB.getNumericScores()) {
			if (i <= 21) {
				iHighestScore = i;
			}
		}
		return iHighestScore;
	}
	
	private eBlackJackResult ScoreDealerPlayer(HandBlackJack hDealer, HandBlackJack sH) {
		if (isBusted((HandScoreBlackJack) sH.getHS())) {
			return eBlackJackResult.LOSE;
			// Dealer is busted
		} else if (isBusted((HandScoreBlackJack) hDealer.getHS())) {
			return eBlackJackResult.WIN;
			// Player and dealer have the same scores
		} else if (HighestNotBustedScore((HandScoreBlackJack) sH.getHS()) == HighestNotBustedScore(
				(HandScoreBlackJack) hDealer.getHS())) {
			return eBlackJackResult.TIE;
			// Player has higher score than dealer
		} else if (HighestNotBustedScore((HandScoreBlackJack) sH.getHS()) > HighestNotBustedScore(
				(HandScoreBlackJack) hDealer.getHS())) {
			return eBlackJackResult.WIN;
			// Only condition left - dealer has higher score than player
		} else {
			return eBlackJackResult.LOSE;
		}
	}
	public Player getpDealer() {
		return pDealer;
	}
	public void sethDealer(Hand hDealer) {
		this.hDealer = hDealer;
	}


}

