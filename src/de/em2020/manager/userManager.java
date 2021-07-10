package de.em2020.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.API.EinSpigotPlugin.API.Stats.StatsAPI;
import de.API.EinSpigotPlugin.SQL.SQL;
import de.em2020.main;
import de.em2020.enums.Teams;

public class userManager {
	
	String unsername;
	String password;
	
	ArrayList<bets> user_betlist;
	
	Integer money;
	String user_informations;
	
	public userManager(String username, String password) {
		
		this.unsername = username;
		this.password = password;
		
		this.money = main.user_data.getPlayer(username).setGetTable("user_money").getInt();
		
		getUserBets();
		
	}
	
	public void getUserBets() {
		
		user_betlist = new ArrayList<>();
		
		 ResultSet rs = SQL.query("SELECT * FROM user_bet_data WHERE UUID= '" + unsername + "'");
		 
		 try {
			while(rs.next()) {
				 
				boolean outpayed = true;
				
				if(rs.getString("outpayed").equalsIgnoreCase("false"))outpayed = false;
				
				 bets bet = new bets(main.gameManager.getGame(rs.getString("game_id")), Teams.valueOf(rs.getString("winner")), rs.getInt("result1"), rs.getInt("result2"), rs.getInt("amount"), outpayed);
				 
				 user_betlist.add(bet);
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public void removeUserBet(bets bet) {
		try {
			for(bets all_bets : user_betlist ) {
				if(all_bets.getGame().getName().equals(bet.getGame().getName())) {
					SQL.update("DELETE FROM user_bet_data WHERE UUID ='" + unsername + "' AND game_id ='"+ bet.getGame().getName() +"'");
					user_betlist.remove(all_bets);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public void checkBets() {
		for(bets bet : main.userManager.getUser_betlist()) {
			if(!bet.getOutpayed()) {
				if(bet.getGame().hasResult()) {
					
					bet.setOutpayed(true);
					
					if(bet.getGame().getWinner().equals(bet.getWinner()) && bet.getResult1() == bet.getGame().getResult1() && bet.getResult2() == bet.getGame().getResult2()){//a=5
						main.userManager.addMoney(bet.getAmount()*5);
						main.userManager.addUserBet(bet);
						
						JOptionPane.showMessageDialog(null,"You are the best! You got the right result! " + bet.getGame().getTeam1() + " vs " + bet.getGame().getTeam2() + " you won " + bet.getAmount()*5 + " €!");
						
					}else if(bet.getWinner().equals(bet.getGame().getWinner())) {//a=2
						main.userManager.addMoney(bet.getAmount()*2);
						main.userManager.addUserBet(bet);
						
						JOptionPane.showMessageDialog(null,"you have chosen the right team " + bet.getGame().getTeam1() + " vs " + bet.getGame().getTeam2() + " you won " + bet.getAmount()*2 + " €!");
						
					}else {//a=0
						main.userManager.addUserBet(bet);
						
						JOptionPane.showMessageDialog(null,"Next time it will go better! " + bet.getGame().getTeam1() + " vs " + bet.getGame().getTeam2());
					}
					
				}
			}
		}
	}
	
	public void addUserBet(bets bet) {
		
		removeUserBet(bet);
		
		user_betlist.add(bet);
		main.user_bet_data.createPlayer(unsername).addValue(bet.getGame().getName()).addValue(bet.getWinner()).addValue(bet.getResult1()).addValue(bet.getResult2()).addValue(bet.getAmount()).addValue(bet.getOutpayed().toString()).send();
		
	}

	public String getUnsername() {
		return unsername;
	}

	public void setUnsername(String unsername) {
		this.unsername = unsername;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<bets> getUser_betlist() {
		return user_betlist;
	}

	public void addUser_betlist(bets bet) {
		this.user_betlist.add(bet);
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
		main.user_data.updatePlayer(unsername).option("user_money").put(money).update();
	}
	
	public void addMoney(Integer add) {
		setMoney(this.money+add);
	}
	
	public void removeMoney(Integer remove) {
		setMoney(this.money-remove);
	}


	public String getUser_informations() {
		return user_informations;
	}

	public void setUser_informations(String user_informations) {
		this.user_informations = user_informations;
	}

}
