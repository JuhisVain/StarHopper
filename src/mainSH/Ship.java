package mainSH;

public class Ship {
	
	String name;
	int mass;	//unit is earthmass
	int population;	//unit is mega units
	int popCap;
	int massStorage;	//EMs
	int cpu = 0;
	
	//ONLY USE FOR MOTHERSHIP:
	public Ship(String name, int mass, int population, int popCap, int massStorage){
		
		cpu = 100; //???
		
		this.name = name;
		this.mass = mass;
		this.population = population;
		this.popCap = popCap;
		this.massStorage = massStorage;
	}

}
