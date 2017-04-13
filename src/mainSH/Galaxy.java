package mainSH;
import java.util.Random;

public class Galaxy {
	
	private Star[] stars;
	
	public Galaxy(int starAmount){	//Constructor aka the Big Bang
		
		Random rand = new Random();
		this.stars = new Star[starAmount];
		
		for (int i = 0; i < starAmount; i++){
			
			short x = Short.MIN_VALUE;	//MIN_VALUEs are just to initialize the coordinates
			short y  = Short.MIN_VALUE;
			short z = Short.MIN_VALUE;
			final short r = Short.MAX_VALUE;	//This is the radius of the galaxy or (close enough)
			
			byte size; ////0 = small, 1 = med, 2 = large
			byte type; // 0 yellow, 1 white, 2 red, 3 blue, 4 orange
			String name;
			
			name = ("STAR:"+i);// to do: name generator
			
			//not needed: minvalues in all don't fulfill following while
			x += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );	// the magic number is (2^16 - 1) / 3 : max value of unsigned short divided by 3
			y += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );
			z += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );	// should be smaller for a flat galaxy
			

			//Forcing the star into a sphere:
			while (!((x*x+y*y+z*z) <= r*r)){//values may be too large

				x += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );	// the magic number is (2^16 - 1) / 3 : max value of unsigned short divided by 3
				y += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );
				z += (short)( rand.nextInt(21846)+rand.nextInt(21846)+rand.nextInt(21846) );
			}
			
			//x == y situations create problems in drawing:
			if (x == y){
				x -= 250;
			}
			
			//determine the type / color:
			type = (byte) rand.nextInt(5);
			
			//determine size:
			size = (byte) rand.nextInt(3);
			
			this.stars[i] = new Star(x,y,z,type,size,name);

		}
	}
	
	public Galaxy(Star[] s){	//Constructor for databasehandler
		stars = new Star[s.length];
		stars = s;
	}
	
	//getters:
	public Star[] getAllStars(){
		return stars;
	}
	public int getStarAmount(){
		return stars.length;
		
	}
	
	public Star getSingleStar(int number){
		return stars[number];
	}

}
