package mainSH;

public class Fleet {
	
	private static int counter = 0;

	private String name;
	private int mStored;//unused
	private int pop;	//in mega units 10^6
	private int conSpeed;//unused
	private int thrust;
	
	private short x, y, z; // coordinates
	private Star location = null;
	private Star destination = null;
	
	//Constructor for the initial fleet:
	public Fleet(Star location){
		
		name = "Fleet " +counter;
		counter++;
		
		mStored = 135000;
		pop = 10000; //10 000 000 000 = 10 milliards/billion
		conSpeed = 5;// earth masses per time unit
		thrust = 1000;
		
		this.location = location;
		
		x = location.getX();
		y = location.getY();
		z = location.getZ();
		
	}
	
	
	//Getters & setters
	public void setLocation(Star l){
		location = l;
		x = location.getX();
		y = location.getY();
		z = location.getZ();
	}
	
	public int getMStored(){
		return mStored;
	}
	
	public static void setStaticCount(int newCount){
		counter = newCount;
	}
	
	public void setDestination(Star dest){
		destination = dest;
	}
	public Star getDestination(){
		return destination;
	}
	
	public Star getLocation(){
		return location;
	}
	
	public short getX(){
		return x;
	}
	public short getY(){
		return y;
	}
	public short getZ(){
		return z;
	}
	
	public void setX(short n){
		x=n;
	}
	public void setY(short n){
		y=n;
	}
	public void setZ(short n){
		z=n;
	}
	
	public String getFleetName(){
		return name;
	}
	
	public int getPopulation(){
		return pop;
	}
	
	public int getConSpeed(){
		return conSpeed;
	}
	public int getThrust(){
		return thrust;
	}
	
}
