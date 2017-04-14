package mainSH;
import java.sql.SQLException;
import java.util.Random;

public class Communicator {
	
	Random rand = new Random();
	
	private Galaxy galaxy;
	private Player[] player;//players stored here
	private GUI ui;
	
	private DatabaseHandler db = new DatabaseHandler();
	
	int time = Integer.MIN_VALUE;//unused

	public void newGame(int starCount, int playerCount){	//creating a new game
		galaxy = new Galaxy(starCount);
		player = new Player[playerCount];
		
		Fleet.setStaticCount(0);	//numbering fleets
		
		for (int i = 0; i < player.length; i++){
			player[i] = new Player(galaxy.getSingleStar(rand.nextInt(galaxy.getStarAmount())));	//new player created with randomized starting location for initial fleet
			player[i].newFleet(galaxy.getSingleStar(rand.nextInt(galaxy.getStarAmount())));
		}
		
	}

	
	public boolean passTime(){//very confusing
		//one tick at a time:

		for (int plaInc = 0; plaInc < player.length; plaInc++){//for each player
			
			boolean positiveXVector = true;//vector between start and end locations
			boolean currentVector = true;//vector between fleet and destination
			
			int fleInc = 0;
			
			while (fleInc < player[plaInc].getFleetCount()){
				
				boolean firstRound = true;
				
				if (player[plaInc].getFleet(fleInc) == null ){
					break;
				}

				try {
					positiveXVector = (player[plaInc].getFleet(fleInc).getDestination().getX() - player[plaInc].getFleet(fleInc).getLocation().getX()) > 0; //getDestination might be null
					
					if (firstRound){
						currentVector = (player[plaInc].getFleet(fleInc).getDestination().getX() - player[plaInc].getFleet(fleInc).getX()) > 0;
						firstRound = false;
					}
					
				} catch (Exception e){
					System.out.println("ERROR, but not really");
					break;
				}

				if (positiveXVector == currentVector){	//check if we're going in the right direction
				
					try {
						calculateNewPosition(player[plaInc].getFleet(fleInc) );
						ui.callForRepaint(false);
					} catch (Exception ex){
						//do nothing
					}
					
					currentVector = (player[plaInc].getFleet(fleInc).getDestination().getX() - player[plaInc].getFleet(fleInc).getX()) >= 0;
					
				}
				if ((positiveXVector != currentVector) ) {
					//fleet is now at destination
					player[plaInc].getFleet(fleInc).setLocation(player[plaInc].getFleet(fleInc).getDestination());//a hack
					player[plaInc].getFleet(fleInc).setDestination(null);
					//end = true;
					ui.callForRepaint(true);
					return false;
				}
				currentVector = (player[plaInc].getFleet(fleInc).getDestination().getX() - player[plaInc].getFleet(fleInc).getX()) > 0;
				fleInc++;
			}

		}
		return true;//send to gui to get next tick (?)
	}
	
	private void calculateNewPosition(Fleet f){
		//some kind of vector magic:
		short x0 = f.getX();
		short y0 = f.getY();
		short z0 = f.getZ();

		short xN = f.getDestination().getX();
		short yN = f.getDestination().getY();
		short zN = f.getDestination().getZ();
		
		//double l = Math.sqrt((xN-x0)*(xN-x0)+(yN-y0)*(yN-y0)+(zN-z0)*(zN-z0));
		//sometimes NaN? reason: double can't handle numbers this high... neither can int... long breaks something
		//solution: (i don't think this solved it, but I may have solved it somewhere in this method...)
		/* no, this isn't the solution
		double l = Math.sqrt(
				((xN-x0)*(xN-x0))/1000000 +
				((yN-y0)*(yN-y0))/1000000 +
				((zN-z0)*(zN-z0))/1000000
				)*1000;
		*/
		
		double l = Math.sqrt(
				 ((xN-x0)/100)*((xN-x0)/100)
				+((yN-y0)/100)*((yN-y0)/100)
				+((zN-z0)/100)*((zN-z0)/100)
				)*100;	//hopefully it works now

		double nvX = (xN-x0)/l;//normalized vector X
		double nvY = (yN-y0)/l;
		double nvZ = (zN-z0)/l;

		x0 += f.getThrust()*nvX;
		y0 += f.getThrust()*nvY;
		z0 += f.getThrust()*nvZ;

		
		if ( ((x0 == f.getDestination().getX() && y0 == f.getDestination().getY() && zN == f.getDestination().getZ())) || Math.round(l)==0){//fleet has arrived
			f.setLocation(f.getDestination());	//also handles coordinates
		} else {
			f.setX(x0);
			f.setY(y0);
			f.setZ(z0);
		}
	}
	
	public Communicator(){	//this is the default constructor
		galaxy = new Galaxy(0);	//set up empty
		player = new Player[0];
	}
	
	//Setters & getters
	public Galaxy getGalaxy(){
		return galaxy;
	}
	public void setUI(GUI gui){
		ui = gui;
	}
	public int getPlayerCount(){
		return player.length;
	}
	public Player getPlayer(int index){
		try {
			return player[index];
		} catch (ArrayIndexOutOfBoundsException e){
			return null;
		} 
	}
	
	//Database stuff:
	//This is boring. I'll only store the galaxy
	
	public boolean connectionSuccess(String url, String user, String password){	//used for connecting to user's DB
		try {
			db.tryConnection(url, user, password); 
		} catch (SQLException e){
			return false;
		}
		
		return true; //the connection was a success
		
	}
	
	public void fixCustomGalaxy(){
		db.moveStar();
	}
	
	public void exportData(){
		db.store(galaxy);
	}
	
	public void addData(){
		
		//pro kludging:
		Star s = new Galaxy(1).getSingleStar(0);
		
		db.store(s);
	}
	
	public void deleteStar(){
		
		db.deleteStar();
		
	}
	
	public void importData(){

		galaxy = db.getGalaxy();
		
		System.out.println("Amount of stars in gal: "+galaxy.getStarAmount());
		//System.out.println("xyz of star:0  :"+galaxy.getSingleStar(3).getX()+" : "+galaxy.getSingleStar(3).getY()+" : "+galaxy.getSingleStar(3).getZ());

		//player = db.getPlayers();
		
		player = new Player[1];
		
		Fleet.setStaticCount(0);	//numbering fleets
		System.out.println("going for fleet creation:");
		for (int i = 0; i < player.length; i++){
			System.out.println("GERONIMOOOO");
			
			Star s = galaxy.getSingleStar(rand.nextInt(galaxy.getStarAmount()));
			System.out.println("amount of stars: "+ galaxy.getStarAmount());
			System.out.println(s.getName());
			
			player[i] = new Player(s);	//new player created with randomized starting location for initial fleet
			
			
			System.out.println("hoho");
			player[i].newFleet(galaxy.getSingleStar(rand.nextInt(galaxy.getStarAmount())));
		}
		
		ui.callRepaint();
		
	}
	
}
