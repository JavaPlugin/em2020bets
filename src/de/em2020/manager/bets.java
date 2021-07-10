package de.em2020.manager;

import javax.swing.JOptionPane;

import de.em2020.main;
import de.em2020.enums.Teams;

public class bets {
	
	game game;
	
	Teams winner;
	
	Integer result1;
	Integer result2;
	
	Integer amount;
	
	Boolean outpayed;
	
	public bets(game game, Teams winner, Integer result1, Integer result2, Integer amount, Boolean outpayed) {
		
		this.game = game;
		
		this.winner = winner;
		
		this.result1 = result1;
		this.result2 = result2;
		
		this.amount = amount;
		
		this.outpayed = outpayed;
		
		/*if(!outpayed) {
			if(game.hasResult()) {
				
				outpayed = true;
				
				if(winner.equals(game.getWinner()) && result1 == game.getResult1() && result2 == game.getResult2()){//a=5
					main.userManager.addMoney(amount*5);
					main.userManager.addUserBet(this);
					
					JOptionPane.showMessageDialog(null,"You are the best! You got the right result! " + game.getTeam1() + " vs " + game.getTeam2() + " you won " + amount*5 + " €!");
					
				}else if(winner.equals(game.getWinner())) {//a=2
					main.userManager.addMoney(amount*2);
					main.userManager.addUserBet(this);
					
					JOptionPane.showMessageDialog(null,"you have chosen the right team " + game.getTeam1() + " vs " + game.getTeam2() + " you won " + amount*2 + " €!");
					
				}else {//a=0
					main.userManager.addUserBet(this);
					
					JOptionPane.showMessageDialog(null,"Next time it will go better! " + game.getTeam1() + " vs " + game.getTeam2());
				}
				
			}
		}*/
		
	}

	public void setOutpayed(Boolean outpayed) {
		this.outpayed = outpayed;
	}

	public Boolean getOutpayed() {
		return outpayed;
	}

	public game getGame() {
		return game;
	}
	public Teams getWinner() {
		return winner;
	}

	public Integer getResult1() {
		return result1;
	}

	public Integer getResult2() {
		return result2;
	}
	public Integer getAmount() {
		return amount;
	}

	
	

}
