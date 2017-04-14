package mainSH;

public class Star {
	
	private short x, y, z; 	//coordinates
	private byte size; 		//0 = small, 1 = med, 2 = large
	private byte type;		// 0 yellow, 1 white, 2 red, 3 blue, 4 orange
	
	private String name;	//name
	
	private int mass;		//mass available to consume in earth masses
	
	public Star(short x, short y, short z, byte type, byte size, String name){//constuctor

		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.size = size;
		this.name = name;
		
		mass = (size+1)*500;
		
	}
	
	//Getters & setters:
	public short[] getCoordinates(){
		short[] coordinates = {x,y,z};
		return coordinates;
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
	public byte getType(){
		return type;
	}
	public byte getSize(){
		return size;
	}
	public String getName(){
		return name;
	}
	public int getMass(){
		return mass;
	}
	public void setMass(int newMass){
		mass = newMass;
	}

}
