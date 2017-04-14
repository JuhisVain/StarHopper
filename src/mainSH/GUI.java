package mainSH;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.Color;


import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;



public class GUI {

	static Surface spaceG;
	static JButton btnCounterCw;
	static JButton btnClockwise;
	static JFrame frame;

	boolean mouseHeld = false;

	static Timer timerClockWise;
	static Timer timerCounterClock;
	
	static Timer animationTimer;
	
	static private JPanel interfacePanel;
	static private JPanel dataPanel;	// This will have to be able to show 3 different kinds of data sets
	
	private JComboBox<String> comboBox;
	JLabel lblMassStored, lblPopulation, lblConsumeSpeed, lblThrust;
	
	JLabel lblStarname, lblStarmass, lblDistancefromfleet, lblEta;
	TravelButtonListen travelBtnListen; //...
	
	Communicator coms;
	private JLabel lblFleetdestination;
	private JButton btnPassTime;


	/**
	 * @wbp.parser.entryPoint
	 */
	void createGUI(){	//Creates the GUI
		
		
		frame = new JFrame();
		frame.setBounds(new Rectangle(0, 0, 800, 600));
		frame.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addComponentListener(new windowSizeListener());

		spaceG = new Surface();
		spaceG.setBounds(10, 44, 540, 507);
		frame.getContentPane().add(spaceG);
		
		spaceG.setBackground(new Color(0,0,0,255));
		
		interfacePanel = new JPanel();
		interfacePanel.setBounds(560, 10, 214, 541);
		interfacePanel.setLayout(null);
		frame.getContentPane().add(interfacePanel);
		
		btnCounterCw = new JButton("Counter CW");
		btnCounterCw.setBounds(0, 518, 101, 23);
		
		btnClockwise = new JButton("Clockwise");
		btnClockwise.setBounds(113, 518, 101, 23);
		
		interfacePanel.add(btnCounterCw);
		interfacePanel.add(btnClockwise);
		
		interfacePanel.setBackground(new Color(50,50,50)); //color test for positioning
		
		dataPanel = new JPanel();
		dataPanel.setBounds(0, 101, 214, 414);	//windowbuilder gave me these...  fixed in windowSizeListener method.
		interfacePanel.add(dataPanel);
		dataPanel.setLayout(null);
		
		dataPanel.setBackground(new Color(200,20,20));	//color test for positioning
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 11, 194, 20);
		dataPanel.add(comboBox);
		
		comboBox.addActionListener(new ComboBoxListen());
		
		lblMassStored = new JLabel("M. stored :");
		lblMassStored.setBounds(10, 42, 110, 14);
		dataPanel.add(lblMassStored);
		
		lblPopulation = new JLabel("Pop. : ");
		lblPopulation.setBounds(120, 42, 84, 14);
		dataPanel.add(lblPopulation);
		
		lblConsumeSpeed = new JLabel("Cons. Speed : ");
		lblConsumeSpeed.setBounds(10, 67, 110, 14);
		dataPanel.add(lblConsumeSpeed);
		
		lblThrust = new JLabel("Thrust : ");
		lblThrust.setBounds(120, 67, 84, 14);
		dataPanel.add(lblThrust);
		
		lblStarname = new JLabel("StarName");		
		lblStarname.setBounds(10, 120, 110, 14);
		dataPanel.add(lblStarname);
		
		lblStarmass = new JLabel("StarMass");	
		lblStarmass.setBounds(10, 145, 110, 14);
		dataPanel.add(lblStarmass);
		
		lblDistancefromfleet = new JLabel("distanceFromFleet");
		lblDistancefromfleet.setBounds(10, 198, 194, 14);
		dataPanel.add(lblDistancefromfleet);
		
		lblEta = new JLabel("eta");
		lblEta.setBounds(10, 223, 194, 14);
		dataPanel.add(lblEta);
		
		JButton btnTravelHere = new JButton("Travel here");
		btnTravelHere.setBounds(54, 248, 110, 23);
		dataPanel.add(btnTravelHere);
		
		lblFleetdestination = new JLabel("FleetDestination");
		lblFleetdestination.setBounds(10, 92, 194, 14);
		dataPanel.add(lblFleetdestination);
		
		btnPassTime = new JButton("Pass time");
		btnPassTime.setBounds(10, 282, 194, 23);
		dataPanel.add(btnPassTime);
		
		travelBtnListen = new TravelButtonListen();// gotta store that destination star somewhere
		btnTravelHere.addActionListener(travelBtnListen);
		
		btnPassTime.addActionListener(new PassTimeBtnListen());
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnCreator = new JMenu("Creator");
		menuBar.add(mnCreator);
		
		MenuListener menuListen = new MenuListener();
		
		JMenuItem fileNewGame = new JMenuItem("New game");
		mnFile.add(fileNewGame);
		fileNewGame.setActionCommand("newGame");	//for MenuListener
		fileNewGame.addActionListener(menuListen);
		
		JMenuItem fileSave = new JMenuItem("Export");
		mnFile.add(fileSave);
		fileSave.setActionCommand("save");
		fileSave.addActionListener(menuListen);
		
		JMenuItem fileLoad = new JMenuItem("Import");
		mnFile.add(fileLoad);
		fileLoad.setActionCommand("load");
		fileLoad.addActionListener(menuListen);
		
		JMenuItem initDB = new JMenuItem("Connect to DB");
		mnFile.add(initDB);
		initDB.setActionCommand("initDB");
		initDB.addActionListener(menuListen);
		
		JMenuItem fixGal = new JMenuItem("Fix galaxy in DB");
		mnFile.add(fixGal);
		fixGal.setActionCommand("fixgal");
		fixGal.addActionListener(menuListen);
		
		JMenuItem creatorCreate = new JMenuItem("Create star to db");
		mnCreator.add(creatorCreate);
		creatorCreate.setActionCommand("create");
		creatorCreate.addActionListener(menuListen);
		
		JMenuItem creatorDestroy = new JMenuItem("Delete star to db");
		mnCreator.add(creatorDestroy);
		creatorDestroy.setActionCommand("destroy");
		creatorDestroy.addActionListener(menuListen);
		
		//Surface's selection listener
		MouseListener surfaceMouseListen = new SurfaceMouseListener();
		spaceG.addMouseListener(surfaceMouseListen);
		
		//action listeners for view rotating buttons:
		int rotateSpeed = 25;
		
		CounterCWListener counterCWListen = new CounterCWListener();
		btnCounterCw.addActionListener(counterCWListen);
		MouseListener counterCWMListen = new CounterCWMouseListener();
		btnCounterCw.addMouseListener(counterCWMListen);
		
		CWListener CWListen = new CWListener();
		btnClockwise.addActionListener(CWListen);
		MouseListener CWMListen = new CWMouseListener();
		btnClockwise.addMouseListener(CWMListen);

		timerClockWise = new Timer(rotateSpeed, CWListen);
		timerCounterClock  = new Timer(rotateSpeed, counterCWListen);
		
		PassTimeAnim animator = new PassTimeAnim();
		animationTimer = new Timer(100, animator);
		

		frame.setVisible(true);
		
		
	}
	
	public class newGameWindow extends JFrame {	//menu panel -> file -> new game,     creates setup window for a new game
	
		JButton newGameButton;
		JTextField newStarAmount;
		JLabel newGameLabel;
		
		public newGameWindow(){

			super.setBounds(new Rectangle(0, 0, 400, 200));
			super.getContentPane().setBounds(new Rectangle(0, 0, 400, 200));
			super.getContentPane().setLayout(null);
			
			newGameLabel = new JLabel("Amount of stars in new galaxy:");
			newGameLabel.setOpaque(true);
			newGameLabel.setBounds(50, 20, 500,50);
			
			newStarAmount = new JTextField();
			newStarAmount.setBounds(50, 75, 100, 25);
			
			newGameButton = new JButton();
			newGameButton.setText("Generate new galaxy");
			newGameButton.setBounds(50, 120, 200, 25);
			
			super.add(newStarAmount);
			
			super.add(newGameButton);
			newGameButton.addActionListener(new newGameButtonListen());
			super.add(newGameLabel);
			
			super.setVisible(true);
		}
		
		private class newGameButtonListen implements ActionListener{
			public void actionPerformed(ActionEvent e){

				try {
					coms.newGame(Integer.parseInt(newStarAmount.getText()), 1);//player count = 1 for now
					comboBox.removeAllItems();
					updateShipBox();
				} catch (NumberFormatException ex) {
					newStarAmount.setText("30");
				}
				
				spaceG.repaint();
			}
		}
	}
	
	public class initDBWindow extends JFrame {	//Window for user to input his database
		
		JLabel guideLabel;
		
		JLabel urlLabel;
		JLabel userLabel;
		JLabel pwLabel;
		
		JLabel infoLabel;
		
		JTextField urlTF;
		JTextField userTF;
		JTextField pwTF;
		
		JButton goButton;
		
		public initDBWindow(){
			
			super.setBounds(new Rectangle(0, 0, 400, 200));
			super.getContentPane().setBounds(new Rectangle(0, 0, 400, 200));
			super.getContentPane().setLayout(null);
			
			guideLabel = new JLabel("Input URL, username and password of database here.");
			guideLabel.setBounds(5,-10, 500, 50);
			super.add(guideLabel);
			
			urlLabel = new JLabel("URL:");
			urlLabel.setBounds(20, 20, 500,50);
			super.add(urlLabel);
			
			userLabel = new JLabel("User:");
			userLabel.setBounds(20, 45, 500,50);
			super.add(userLabel);
			
			pwLabel = new JLabel("Password:");
			pwLabel.setBounds(20, 70, 500,50);
			super.add(pwLabel);
			
			urlTF = new JTextField();
			urlTF.setBounds(85, 32, 280, 25);
			urlTF.setText("jdbc:mysql://localhost:3306/test");	//default
			super.add(urlTF);
			
			userTF = new JTextField();
			userTF.setBounds(85, 57, 280, 25);
			userTF.setText("root");	//default
			super.add(userTF);
			
			pwTF = new JTextField();
			pwTF.setBounds(85, 82, 280, 25);
			pwTF.setText("");	//default
			super.add(pwTF);
			
			goButton = new JButton("Connect");
			goButton.addActionListener(new goButtonListen());
			goButton.setBounds(20, 120, 90, 25);
			super.add(goButton);

			infoLabel = new JLabel("");
			infoLabel.setBounds(125, 120, 500, 25);
			super.add(infoLabel);
			
			super.setVisible(true);
			
		}
		
		private class goButtonListen implements ActionListener{
			
			public void actionPerformed(ActionEvent e){
				
				if ( coms.connectionSuccess( urlTF.getText(), userTF.getText(), pwTF.getText() ) ){
					infoLabel.setText("Connection to database successful");
				} else {
					infoLabel.setText("Connection failure!");
				}
			}
		}
	}
	
	//The rabbit hole of animation starts here:
	//1) PasTimeBtnListen calls coms' passTime()
	//2) passTime() calls callForRepaint()
	//3) callForRepaint() starts animationTimer which uses PassTimeAnim class
	public void callForRepaint(boolean end){
		
		if (!end){
			if (!animationTimer.isRunning()){
				animationTimer.start();
			}
		} else {
			animationTimer.stop();
		}
		
		spaceG.repaint();
		
	}
	
	private class PassTimeAnim implements ActionListener{
		public void actionPerformed(ActionEvent e){
			spaceG.repaint();
			coms.passTime();
		}
	}
	
	private class PassTimeBtnListen implements ActionListener{
		public void actionPerformed(ActionEvent e){
			coms.passTime();
			
		}
	}
	//Animation over.
	
	public void callRepaint(){//for use with database loading
		spaceG.repaint();
	}
	
	private void updateShipBox(){	//updates fleet selection combobox
		try {
			for (int i = 0; i < coms.getPlayer(0).getFleetCount(); i++){
				try {
					comboBox.addItem(coms.getPlayer(0).getFleet(i).getFleetName());
				} catch (Exception e){
					break;
				}
			}
		} catch (Exception exc){
			//do nothing
		}
	}

	class TravelButtonListen implements ActionListener{	//sets destination based on selection in 3d-view
		
		private Star destStar;
		
		public void actionPerformed(ActionEvent e){
			
			try {
			
				for (int i = 0; i < coms.getPlayer(0).getFleetCount(); i++){
					if (coms.getPlayer(0).getFleet(i).getFleetName().equals(comboBox.getSelectedItem())){

						coms.getPlayer(0).getFleet(i).setDestination(destStar);
						lblFleetdestination.setText("Destination : "+coms.getPlayer(0).getFleet(i).getDestination().getName());
					}
				}
			}catch (NullPointerException npe){
				System.out.println("NullPointer in TravelButtonListen");
			}catch (ArrayIndexOutOfBoundsException exc){
				System.out.println("ArrayIndexOutOfBounds in TravelButtonListen");
			}
			
		} 
		
		public void setDestForButton(Star d){
			destStar = d;
		}
	}
	
	class ComboBoxListen implements ActionListener{	//listens to the fleet selection combobox
		public void actionPerformed(ActionEvent e){
			
			for (int i = 0; i < coms.getPlayer(0).getFleetCount(); i++){
				try {
					 if (coms.getPlayer(0).getFleet(i).getFleetName().equals(comboBox.getSelectedItem())){
						 lblMassStored.setText("M. Stored : "+coms.getPlayer(0).getFleet(i).getMStored());
						 lblPopulation.setText("Pop. : "+coms.getPlayer(0).getFleet(i).getPopulation());
						 lblConsumeSpeed.setText("Cons. speed : "+ coms.getPlayer(0).getFleet(i).getConSpeed());
						 lblThrust.setText("Thrust : "+ coms.getPlayer(0).getFleet(i).getThrust());
					 }
				} catch (Exception exc){
					break;
				}
			}
		}
	}
	
	public int getGUIWidth(){
		return frame.getWidth();
	}
	
	public int getGUIHeight(){
		return frame.getHeight();
	}
	
	public void setCommunicator(Communicator c){	//this is the CONTROLLER
		coms = c;
	}
	
	//LISTENERS:
	//listeners for the menu bar items, all in one solution
	class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			System.out.println("menulisten start");
			
			if (e.getActionCommand()=="newGame"){
				newGameWindow ngw = new newGameWindow();
			} else if (e.getActionCommand()=="save"){
				coms.exportData();
			} else if (e.getActionCommand()=="load"){
				coms.importData();
			} else if (e.getActionCommand()=="create"){
				
				coms.addData();	//add randomized star to the galaxy stored in the database server, not in the current galaxy
				
			} else if (e.getActionCommand()=="destroy"){
				
				coms.deleteStar(); //Deletes last star from database
				
			} else if ( e.getActionCommand()=="initDB"){
				
				initDBWindow idbw = new initDBWindow();
				
			} else if (e.getActionCommand()=="fixgal"){
				coms.fixCustomGalaxy();
			}
			else{}
		}
	}
	
	//Rotatingbuttonlisteners
	
	class CounterCWMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e){	
			timerCounterClock.start();
		}
		public void mouseReleased(MouseEvent e){
			timerCounterClock.stop();
		}
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
	}
	
	class CWMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e){	
			timerClockWise.start();
		}
		public void mouseReleased(MouseEvent e){
			timerClockWise.stop();
		}
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		
		
	}
	
	//These work with the rotating timers:
	class CWListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			spaceG.rotateCW();
			spaceG.repaint();
		}
	}
	
	class CounterCWListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			spaceG.rotateCCW();
			spaceG.repaint();
		}
	}
	//Rotatingbuttonlisteners out
	
	//This will select a star or ship or whatever when clicked on
	class SurfaceMouseListener implements MouseListener {
		
		public void mouseClicked(MouseEvent e){
			
			if (SwingUtilities.isLeftMouseButton(e)){
			
				spaceG.setSurfaceSelection(e.getX(), e.getY());
				spaceG.repaint();
			} else //if (SwingUtilities.isRightMouseButton(e))
			{
				System.out.println("unselected something!");
				spaceG.unSelect();
				spaceG.repaint();
			}
			
		}
		
		public void mouseEntered(MouseEvent e){
			//maybe write a "floating" selector square here
		}
		public void mouseExited(MouseEvent e){
			
		}
		
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		
	}
	
	
	//Resizing UI when window is resized: 
	//This method overrides all the UI's setBounds methods
	//Lots of magic numbers
	private class windowSizeListener implements ComponentListener{
		
		public void componentResized(ComponentEvent e){
			
			int surfaceWidth = calculateSurfaceX();
			int surfaceHeight = calculateSurfaceY();
			
			spaceG.setBounds(10, 10, surfaceWidth, surfaceHeight);
			spaceG.setScreenXY(surfaceWidth, surfaceHeight);
			
			interfacePanel.setBounds(surfaceWidth+20, 10, 220, surfaceHeight);	//panel 10px right of 3D view and height is same as 3D view
			
			btnCounterCw.setBounds(5, surfaceHeight-25, 101, 23);	//12, 518, 91, 23
			btnClockwise.setBounds(115, surfaceHeight-25, 101, 23);	//113, 518, 91, 23
			
			dataPanel.setBounds(0, 100, 220, surfaceHeight-128);// setBounds( X position, Y position, width, height ); height = (height of surface)-(Y position)-(button height)-(5 for fun)
			
			//menuPanel.setBounds(0, 0, frame.getContentPane().getWidth(), 22);
			
			
		}
		
		public void componentMoved(ComponentEvent e){}
		public void componentShown(ComponentEvent e){}
		public void componentHidden(ComponentEvent e){}
		
	}
	
	//for use in windowSizeListener above
	private int calculateSurfaceX(){
		return frame.getContentPane().getWidth()-250;
	}
	private int calculateSurfaceY(){
		return frame.getContentPane().getHeight()-20;
	}
	
	class Surface extends JPanel{	//great success aka. it works
		
		Graphics2D g2d;
		
		private int viewAngle = 0;
		private double viewAngleInRad;
		private double screenSizeY,screenSizeX;
		
		private int surfaceSelectionX, surfaceSelectionY;
		private int selectionThreshold = 5;
		private int selectedStarI = -1;	//if I don't set this up to a negative value, it will default to 0 which is bad.
		private boolean somethingSelected = false;
		
		private void doDrawing(Graphics g) {	//This sets up the drawing VERY IMPORTANT
	
			g2d = (Graphics2D) g;
			viewAngleInRad = (((double)viewAngle)/180)*Math.PI;
			drawGalacticBackground();
			
			try {	// if no galaxy initialized
				coms.getGalaxy().getSingleStar(0).getName();
			} catch (NullPointerException e){
				return;
			} catch (ArrayIndexOutOfBoundsException ex){
				return;
			}
			
	        for (int i = 0; i < coms.getGalaxy().getStarAmount(); i++){	//This is a mess but I've forgotten how it works so it can't be fixed
	        	
	        	double starX = coms.getGalaxy().getSingleStar(i).getX();	//these MUST BE DOUBLES
	        	double starY = coms.getGalaxy().getSingleStar(i).getY();	//Actually they probably don't but let's just make sure everything works by making everything a double
	        	double starZ = coms.getGalaxy().getSingleStar(i).getZ();

	        	double rotatingX, rotatingY, rotatingZ, rotatingYZ;
	        	double scaler;
	        	
	        	final double maxShort = 65535;	//used for scaling star coordinates
	        	
	        	double c = Math.sqrt(starX*starX + starY*starY);
	        	
	        	double defaultAngle;
	        	
	        	if (starX != 0){									// X can't be 0
	        		defaultAngle = Math.atan2(starY,starX);			//Math.atan(double) is bad: must use atan2
	        	} else {
	        		if (starY >= 0){defaultAngle = Math.PI/2;}		//	90 degrees
	        		else {defaultAngle = Math.PI+Math.PI/2;}		// 270 degrees
	        	}
	        	
	        	double modAngle = viewAngleInRad+defaultAngle;
	
	        	if (starX == starY && ( viewAngle == 315 || viewAngle == 135)){	//weird edge case, would probably function better if galaxy creator wouldn't create X = Y stars
	        		
	        		if (viewAngle == 135){
		        		if (starX >= 0){
		        			rotatingX = -c;
		        			rotatingY = 0.0;
		        		}else {
		        			rotatingX = c;
		        			rotatingY = 0.0;
		        		}
	        		} else {
	        			if (starX >= 0){
		        			rotatingX = c;
		        			rotatingY = 0.0;
		        		}else {
		        			rotatingX = -c;
		        			rotatingY = 0.0;
		        		}
	        		}
	        	} else {	//these are the the important parts:
	        		scaler = (c*Math.sin(Math.PI-modAngle))/Math.sin(modAngle);
	        		rotatingX = scaler * Math.cos(modAngle);	
	        		rotatingY = scaler * Math.sin(modAngle);
	        	}
	        	
	        	//at this point rotating X and Y are (close to) their real (rotated) values aka almost within SHORT's range (but in double) it's magic
	        	
	        	//screen width used as common scaler:
	        	rotatingX = (rotatingX/maxShort)*screenSizeX*0.7;	//scaling to unit values; scaling to screen X size; scaling to fit extremes; :: 1 / sqrt(2) =about 0.7 
	        	rotatingY = (rotatingY/maxShort)*screenSizeX*0.7;	
	        	rotatingZ = (starZ/maxShort)*screenSizeX*0.7;	
	        	
	        	rotatingY *= 0.7;	//again with the 1 / sqrt(2) = 0.7 , this tilts the view. at around *0.7 the view is isometric
	        	rotatingZ *= 0.7; 	//Z's tilt
	        	
	        	rotatingX += screenSizeX/2;		//positioning the graphics to center screen
	        	rotatingY -= screenSizeY/2;
	        	
	        	rotatingYZ = rotatingY + rotatingZ;			//summoning the 3D saint to bless my program
	        	
	        	//selection stuff:
	        	
	        	if (somethingSelected){
	
	        		if 		(rotatingX  < surfaceSelectionX + selectionThreshold && rotatingX  > surfaceSelectionX - selectionThreshold 		//comparing click coordinates to drawn stuff.
	        				 && -rotatingYZ < surfaceSelectionY + selectionThreshold  && -rotatingYZ > surfaceSelectionY - selectionThreshold){	//if (within threshold of click)
	        			
	        			if (selectedStarI != -1){

		        			if (coms.getGalaxy().getSingleStar(selectedStarI).getZ() < coms.getGalaxy().getSingleStar(i).getZ()){
		        				selectedStarI = i;	//old selectdeStarI replaced by one with higher Z
		        			}
	        			} else {//if (selectedStarI == -1)
	        				selectedStarI = i;	//this is the first one put into memory
	        			}
	
	        		}
	        		
	        		if (i == coms.getGalaxy().getStarAmount()-1){
	    				finalizeSelection();
	    			}
	        		
	        	}

	        	//drawing:
	        	
	        	//draw line from 0-plane to star
	        	g2d.setColor(new Color(70,140,255,200)); //(red,green,blue,alpha) range 0 - 255
	        	g2d.fillRect((int)rotatingX, (int)-rotatingYZ, 1, (int)rotatingZ);					//I heard it on the intertubes that rectangles are faster than lines
	        	
	        	//draw star:
	        	//type:
	        	switch (coms.getGalaxy().getSingleStar(i).getType()){ 	// 0 yellow, 1 white, 2 red, 3 blue, 4 orange
	        		case 0: g2d.setColor(Color.YELLOW); break;
	        		case 1: g2d.setColor(Color.WHITE); break;
	        		case 2: g2d.setColor(Color.RED); break;
	        		case 3: g2d.setColor(Color.BLUE); break;
	        		case 4: g2d.setColor(new Color(255,125,0)); break;//orange
	        		default:g2d.setColor(new Color(125,125,125)); break;//maybe greyish: shouldn't happen
	        	}
	        	//size:
	        	switch (coms.getGalaxy().getSingleStar(i).getSize()){	//0 = small, 1 = med, 2 = large
		        	case 0: g2d.fillRect((int)rotatingX-1, (int)-rotatingYZ-1, 3, 3); break;
		        	case 1: g2d.fillRect((int)rotatingX-1, (int)-rotatingYZ-1, 3, 3);
		        			g2d.fillRect((int)rotatingX, (int)-rotatingYZ-2, 1, 5);
		        			g2d.fillRect((int)rotatingX-2, (int)-rotatingYZ, 5, 1); break;
		        	case 2: g2d.fillRect((int)rotatingX-2, (int)-rotatingYZ-1, 5, 3);
				        	g2d.fillRect((int)rotatingX-1, (int)-rotatingYZ-2, 3, 5);
				        	g2d.fillRect((int)rotatingX-4, (int)-rotatingYZ, 9, 1);
				        	g2d.fillRect((int)rotatingX, (int)-rotatingYZ-4, 1, 9); break;
				    default:g2d.fillRect((int)rotatingX-1, (int)-rotatingYZ-1, 10, 10); break;//big square: shouldn't happen
	        	}
	        	
	        	//selection graphics:
	        	if (i == selectedStarI){
	        		g2d.setColor(new Color(255,255,255));	//selectionsquare is white
	        		g2d.drawRect((int)rotatingX-4, (int)-rotatingYZ-4, 8, 8);
	        		
	        		updateDataForSelectedStar(i); //bad... should've just made a direct reference to the star from the start
	        		
	        	}
	        	
	        }
	        
	        //drawing fleets:
	        //quick copy from star drawing for-loop
	        for (int y = 0; y < coms.getPlayerCount(); y++){
	        	
	        	for (int i = 0; i < coms.getPlayer(y).getFleetCount(); i++){
	        	
	        		try {	//quick failsafe
	        			coms.getPlayer(y).getFleet(i).getX();
	        		} catch (Exception e){
	        			//System.out.println("Exception caught!");
	        			break;
	        		}

	        		
		        	double playerX = coms.getPlayer(y).getFleet(i).getX();	//these MUST BE DOUBLES
		        	double playerY = coms.getPlayer(y).getFleet(i).getY();	//Actually they probably don't but let's just make sure everything works by making everything doubles
		        	double playerZ = coms.getPlayer(y).getFleet(i).getZ();

		        	double rotatingX, rotatingY, rotatingZ, rotatingYZ;
		        	double scaler;
		        	
		        	final double maxShort = 65535;	//used for scaling star coordinates
		        	
		        	double c = Math.sqrt(playerX*playerX + playerY*playerY);
		        	
		        	double defaultAngle;
		        	
		        	if (playerX != 0){									// X can't be 0
		        		defaultAngle = Math.atan2(playerY,playerX);			//Math.atan(double) is bad: must use atan2
		        	} else {
		        		if (playerY >= 0){defaultAngle = Math.PI/2;}		//	90 degrees
		        		else {defaultAngle = Math.PI+Math.PI/2;}		// 270 degrees
		        	}
		        	
		        	double modAngle = viewAngleInRad+defaultAngle;
		
		        	if (playerX == playerY && ( viewAngle == 315 || viewAngle == 135)){	//weird edge case, would probably function better if galaxy creator wouldn't create X = Y stars
		        		
		        		if (viewAngle == 135){
			        		if (playerX >= 0){
			        			rotatingX = -c;
			        			rotatingY = 0.0;
			        		}else {
			        			rotatingX = c;
			        			rotatingY = 0.0;
			        		}
		        		} else {
		        			if (playerX >= 0){
			        			rotatingX = c;
			        			rotatingY = 0.0;
			        		}else {
			        			rotatingX = -c;
			        			rotatingY = 0.0;
			        		}
		        		}
		        	} else {	//these are the the important parts:
		        		scaler = (c*Math.sin(Math.PI-modAngle))/Math.sin(modAngle);
		        		rotatingX = scaler * Math.cos(modAngle);	
		        		rotatingY = scaler * Math.sin(modAngle);
		        	}
		        	
		        	//at this point rotating X and Y are (close to) their real (rotated) values aka almost within SHORT's range (but in double) it's magic
		        	
		        	//screen width used as common scaler:
		        	rotatingX = (rotatingX/maxShort)*screenSizeX*0.7;	//scaling to unit values; scaling to screen X size; scaling to fit extremes; :: 1 / sqrt(2) =about 0.7 
		        	rotatingY = (rotatingY/maxShort)*screenSizeX*0.7;	
		        	rotatingZ = (playerZ/maxShort)*screenSizeX*0.7;	
		        	
		        	rotatingY *= 0.7;	//again with the 1 / sqrt(2) = 0.7 , this tilts the view. at around *0.7 the view is isometric
		        	rotatingZ *= 0.7; 	//Z's tilt
		        	
		        	rotatingX += screenSizeX/2;		//positioning the graphics to center screen
		        	rotatingY -= screenSizeY/2;
		        	
		        	rotatingYZ = rotatingY + rotatingZ;			//summoning the 3D saint to bless my program

			        	//drawing:
			        	
			        	//draw line from 0-plane to FLEET
			        	g2d.setColor(new Color(200,200,200,150)); //(red,green,blue,alpha) range 0 - 255
			        	g2d.fillRect((int)rotatingX, (int)-rotatingYZ, 1, (int)rotatingZ);					//I heard it on the intertubes that rectangles are faster than lines
			        	
			        	//draw FLEET:
	
			        	//player's color
			        	g2d.setColor(coms.getPlayer(y).getPlayerColor());
			        	
			        	//"icon":
			        	
			        	g2d.fillRect((int)rotatingX+6, (int)-rotatingYZ-1, 3, 3);
			        	g2d.fillRect((int)rotatingX+5, (int)-rotatingYZ, 5, 1);
			        	g2d.fillRect((int)rotatingX+8, (int)-rotatingYZ-2, 2, 1);
			        	g2d.fillRect((int)rotatingX+8, (int)-rotatingYZ+2, 2, 1);
			        	
			        	g2d.drawString(("F"+i), (int)rotatingX+3, (int)-rotatingYZ-1);

	        	}
	        }
	        
	    }
		
		private void updateDataForSelectedStar(int x){ //updates the info view with star data
			
			lblStarname.setText("Name : "+coms.getGalaxy().getSingleStar(x).getName());
			lblStarmass.setText("Mass : "+coms.getGalaxy().getSingleStar(x).getMass());
			
			for (int i = 0; i < coms.getPlayer(0).getFleetCount(); i++){//player(0) is the human (and only) player
				try {
					 if (coms.getPlayer(0).getFleet(i).getFleetName().equals(comboBox.getSelectedItem())){

						 //3D pythagoras:
						 int xD = coms.getGalaxy().getSingleStar(x).getX()-coms.getPlayer(0).getFleet(i).getX();
						 int yD = coms.getGalaxy().getSingleStar(x).getY()-coms.getPlayer(0).getFleet(i).getY();
						 int zD = coms.getGalaxy().getSingleStar(x).getZ()-coms.getPlayer(0).getFleet(i).getZ();
						 double dist = Math.sqrt( (xD*xD)+(yD*yD)+(zD*zD) );
						 
						 lblDistancefromfleet.setText("Distance : " + (int)dist );
						 
						 lblEta.setText("ETA : "+ Math.round((dist/coms.getPlayer(0).getFleet(i).getThrust())) );
						 
						 travelBtnListen.setDestForButton(coms.getGalaxy().getSingleStar(x));
						 
						 break;
						 
					 }
				} catch (Exception exc){
					//do nothing
				}
			}
			
			
		}
		
		private void finalizeSelection(){//used in star selection
			somethingSelected = false;
			surfaceSelectionX = -100;	//out of sight, out of mind
			surfaceSelectionY = -100;
		}
		
		public void setSurfaceSelection(int selectionX, int selectionY){//used in star selection
			
			unSelect();
			
			somethingSelected = true;
			surfaceSelectionX = selectionX;	//these are coordinates on SURFACE, not in galaxy
			surfaceSelectionY = selectionY;
		}
		
		public void unSelect(){//unselecting removes graphics
			somethingSelected = false;
			selectedStarI = -1;	//doDrawing's primary for-loop can't get i = -1 value

			lblStarname.setText("Name : ");
			lblStarmass.setText("Mass : ");
			lblDistancefromfleet.setText("Distance : ");
			lblEta.setText("ETA : ");
		}
	
		
		private void drawGalacticBackground(){//draws the circles and lines on surface
			
			//to do: maybe rewrite the circle part in this method
			
			int circle = 100;
			int scaledCircle = (int)((circle/100)*(screenSizeX/5.4));
			
			g2d.setColor(new Color(70,140,255,67));
			
			while (screenSizeX > scaledCircle*4){
				
				scaledCircle = (int)((circle/100)*(screenSizeX/5.4));
				
				//Left side of galactic circles:
				g2d.drawArc((int)(screenSizeX/2)-scaledCircle,						//screenSizeX and Y here are used to position the arcs
							(int)((screenSizeY/2)-(scaledCircle*(1/Math.sqrt(2)))),	
							scaledCircle*2, 
							(int)((scaledCircle*2)*(1/Math.sqrt(2))),
							90, 180);
				//right side:
				g2d.drawArc((int)(screenSizeX/2)-scaledCircle,
							(int)((screenSizeY/2)-(scaledCircle*(1/Math.sqrt(2)))),
							scaledCircle*2, 
							(int)((scaledCircle*2)*(1/Math.sqrt(2))),
							-90, 180);
	
				circle += 100;
			}
	
			// west to east line:
			g2d.drawLine((int)(Math.cos(-viewAngleInRad)*circle		*	0.8	+screenSizeX/2),
						 (int)(Math.sin(-viewAngleInRad)*circle*0.7	*	0.8	+screenSizeY/2),
						 (int)(-Math.cos(-viewAngleInRad)*circle	*	0.8	+screenSizeX/2),
						 (int)(-Math.sin(-viewAngleInRad)*circle*0.7*	0.8	+screenSizeY/2));
			
			// north to south line:
			double modifiedViewAngle = -viewAngleInRad - Math.PI/2;
			g2d.drawLine((int)(Math.cos(modifiedViewAngle)*circle		*	0.8	+screenSizeX/2),
						 (int)(Math.sin(modifiedViewAngle)*circle*0.7	*	0.8	+screenSizeY/2),
						 (int)(-Math.cos(modifiedViewAngle)*circle		*	0.8	+screenSizeX/2),
						 (int)(-Math.sin(modifiedViewAngle)*circle*0.7	*	0.8	+screenSizeY/2));
			
		}
		
		//used in graphical view rotation:
		public void rotateCCW(){
			if (viewAngle >= 0){viewAngle+=1;}
			else {viewAngle += 359;}
		}
		
		public void rotateCW(){
			if (viewAngle <= 360){viewAngle-=1;}
			else {viewAngle -= 359;}
		}
		
		public void setScreenXY(int newX, int newY){
			screenSizeX = newX;
			screenSizeY = newY;
		}
		
	
		
		@Override
	    public void paintComponent(Graphics g) {//from super class
	        super.paintComponent(g);
	        doDrawing(g);
	    }
		
	}
}