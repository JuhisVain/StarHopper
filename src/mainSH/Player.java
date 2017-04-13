package mainSH;

import java.awt.Color;
import java.util.Random;

public class Player {

	private static int counter = 0;
	private String factionName;
	private Color playerColor;						//wrong but easy
	
	private Fleet[] fleet = new Fleet[10]; 			//max fleets 10 for now
	
	public Player(Star location){
		
		Random rand = new Random();			//should be in communicator...
		
		factionName = "Player "+counter;	//Player 1, Player 2 etc..
		counter++;
		
		fleet[0] = new Fleet(location);		//initial fleet
		
		for (int i = 1; i < fleet.length; i++){
			fleet[i] = null;
		}
		
		playerColor = new Color(rand.nextInt(155)+100,rand.nextInt(155)+100,rand.nextInt(155+100));	//will create problematic colors for multiple players
		
	}
	
	public Player(String name){//not used for now
		factionName = name;
		counter++;
	}
	
	public void newFleet(Star location){
		fleet[1] = new Fleet(location);
	}
	
	
	//getters & setters
	public Color getPlayerColor(){
		return playerColor;
	}
	
	public int getFleetCount(){
		return fleet.length;
	}
	
	public Fleet getFleet(int index){
		return fleet[index];
	}

	
	
	
}
