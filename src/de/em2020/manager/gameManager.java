package de.em2020.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.API.EinSpigotPlugin.SQL.SQL;
import de.em2020.main;
import de.em2020.enums.Teams;

public class gameManager {
	
	ArrayList<game> all_games;
	
	public gameManager() {
		
		all_games = new ArrayList<>();
		
		 ResultSet rs = SQL.query("SELECT * FROM game_data");
		 
		 try {
			while(rs.next()) {
				 
				game game;
				
				try {
					 game = new game(rs.getString("UUID"), Teams.valueOf(rs.getString("country1")), Teams.valueOf(rs.getString("country2")), Teams.valueOf(rs.getString("winner")),
						 rs.getInt("result1"), rs.getInt("result2"), rs.getString("game_time"));
				} catch (Exception e) {
					game = new game(rs.getString("UUID"), Teams.valueOf(rs.getString("country1")), Teams.valueOf(rs.getString("country2")), rs.getString("game_time"));
				}
				
				
				 
				 all_games.add(game);
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void removeGame(game game) {
		for(game g : all_games) {
			if(g.getName().equals(game.getName())) {
				SQL.update("DELETE FROM game_data WHERE UUID ='" + g.getName() + "'");
				all_games.remove(g);
			}
		}
	}
	
	public void addGame(game game) {
		try {
			removeGame(game);
		} catch (Exception e) {
			addGame(game);
		}
		
		
		main.game_data.createPlayer(game.getName()).addValue(game.getTeam1()).addValue(game.getTeam2()).addValue(game.getWinner()).addValue(game.getResult1()).addValue(game.getResult2()).addValue(game.getGame_time()).send();
		all_games.add(game);
	}
	
	public void addnewGame(Teams team1, Teams team2, Date game_time) {
		
		int i = 0;
		
		
		while(main.game_data.containsPlayer(i + "")) {
			i++;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy;hh:mm");
		
		System.out.println(i+"");
		
		System.out.println(dateFormat.format(game_time));
		
		game game = new game(i+"", team1, team2, dateFormat.format(game_time));
		
		System.out.println(game.getName());
		System.out.println(game.getTeam1());
		System.out.println(game.getTeam2());
		
		main.game_data.createPlayer(game.getName()).addValue(game.getTeam1()).addValue(game.getTeam2()).addValue(game.getWinner()).addValue(-1).addValue(-1).addValue(game.getGame_time()).send();
		all_games.add(game);
		
	}
	
	public game getGame(String gamename) {
		for(game games : all_games ) {
			
			if(games.getName().equals(gamename))return games;
			
		}
		
		return null;
	}
	
	public ArrayList<game> getAll_games() {
		return all_games;
	}

	public void setAll_games(ArrayList<game> all_games) {
		this.all_games = all_games;
	}

}
