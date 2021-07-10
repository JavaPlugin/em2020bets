package de.em2020.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.em2020.enums.Teams;

public class game {
	
	String name;
	
	Date date;
	long timeMillisOfDate;
	String game_time;
	
	Teams team1;
	Teams team2;
	
	Teams winner;
	
	Integer result1;
	public void setWinner(Teams winner) {
		this.winner = winner;
	}
	public void setResult1(Integer result1) {
		this.result1 = result1;
	}
	public void setResult2(Integer result2) {
		this.result2 = result2;
	}
	Integer result2;
	
	public game(String name, Teams team1, Teams team2, Teams winner, Integer result1, Integer result2, String game_time) {
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy;hh:mm");
			Date date = dateFormat.parse(game_time);
			
			this.date = date;
			this.timeMillisOfDate = date.getTime();
			this.game_time = game_time;
		
			this.name = name;
			
			this.team1 = team1;
			this.team2 = team2;
			
			this.winner = winner;
			
			this.result1 = result1;
			this.result2 = result2;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public game(String name, Teams team1, Teams team2, String game_time) {
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy;hh:mm");
			
			Date date = dateFormat.parse(game_time);
			
			this.date = date;
			this.timeMillisOfDate = date.getTime();
			this.game_time = game_time;
		
			this.name = name;
			
			this.team1 = team1;
			this.team2 = team2;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public game(String name, Teams team1, Teams team2, Date game_time) {
			Date date = game_time;
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy;hh:mm");
			
			this.date = date;
			this.timeMillisOfDate = date.getTime();
			this.game_time = dateFormat.format(date);
		
			this.name = name;
			
			this.team1 = team1;
			this.team2 = team2;
	}


	
	public String getGame_time() {
		return game_time;
	}


	public String getName() {
		return name;
	}


	public Teams getTeam1() {
		return team1;
	}


	public Teams getTeam2() {
		return team2;
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


	public long getTimeMillisOfDate() {
		return timeMillisOfDate;
	}
	public Date getDate() {
		return date;
	}
	
	public boolean isPlaying() {
		
		if(System.currentTimeMillis() > timeMillisOfDate) {
			return true;
		}
		
		return false;
		
	}
	public boolean hasResult() {
		if(result1 != null && result2 != null) {
			
			if(result1 > -1 && result2 > -1) {
				return true;
			}
			return false;
			
		}
		return false;
	}
}
