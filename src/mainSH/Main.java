package mainSH;

public class Main {

	public static void main(String[] args) {
		
		GUI gui = new GUI();					//Graphical user interface
		gui.createGUI();
		
		Communicator coms = new Communicator();	//This is the controller
		coms.setUI(gui);						//Pass GUI to controller
		
		gui.setCommunicator(coms);				//Pass controller to GUI
		
		
	}
	
	

}
