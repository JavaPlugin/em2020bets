package de.em2020;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.API.EinSpigotPlugin.API.Stats.StatsAPI;
import de.API.EinSpigotPlugin.SQL.SQL;
import de.em2020.manager.gameManager;
import de.em2020.manager.userManager;
import de.em2020.window.window;

public class main {
	
	public static SQL sql;
	
	public static StatsAPI user_data;
	public static StatsAPI game_data;
	public static StatsAPI user_bet_data;
	
	public static userManager userManager;
	public static gameManager gameManager;
	
	public static void main(String[] args) {
		
		
		sql = new SQL("webserver5.deinserverhost.de", "3306", "em2020_bets", "em2020bets", "v0R7vf9!");
		SQL.connect();
		
		
		user_data = new StatsAPI("user_data");
		user_data.loadTable().addText("password").addInt("user_money").addText("user_informations").create();
		
		game_data = new StatsAPI("game_data");
		game_data.loadTable().addText("country1").addText("country2").addText("winner").addInt("result1").addInt("result2").addText("game_time").create();
		
		user_bet_data = new StatsAPI("user_bet_data");
		user_bet_data.loadTable().addText("game_id").addText("winner").addInt("result1").addInt("result2").addInt("amount").addText("outpayed").create();
		
		gameManager = new gameManager(); 
		
		window.main(args);
		
	}
	
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                                  // get an operation conducted fill 0
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
