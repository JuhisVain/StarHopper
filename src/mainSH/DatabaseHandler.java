package mainSH;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Random;

public class DatabaseHandler {//:(
	/*
	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String username = "root";
	private static String password = "Juhis123";
	*/
	private Connection connection = null;
	/*
	private PreparedStatement selectStars = null;
	private PreparedStatement insertStar = null;
	private PreparedStatement insertPlayer = null;
	*/
	//private PreparedStatement truncTable = null;
	private PreparedStatement insertGame = null;
	private PreparedStatement selectStars = null;
	private Statement statement = null;
	
	private Statement updateStar = null;
	
	Random rand = new Random();


	
	public DatabaseHandler(){//Functionality moved to tryConnection()
		
		//System.out.println("AALALAALALALAALAL");
		/*
		try {
			
			//connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			selectStars = connection.prepareStatement("SELECT * FROM stars");

			statement = connection.createStatement();
			
			insertGame = connection.prepareStatement("INSERT INTO stars VALUES (?,?,?,?,?,?,?)");

		} catch (SQLException e){
			e.printStackTrace();
		} 
		*/
	}
	
	public void tryConnection(String url, String user, String password) throws SQLException{
		

		connection = DriverManager.getConnection(url, user, password);
		selectStars = connection.prepareStatement("SELECT * FROM stars");
		updateStar = connection.createStatement();
		
		statement = connection.createStatement();
		
		insertGame = connection.prepareStatement("INSERT INTO stars VALUES (?,?,?,?,?,?,?)");
		
	}
	
	
	public void store(Galaxy g){
		
		try{
			
			statement.executeUpdate("TRUNCATE stars");

			
			for (int i = 0; i < g.getAllStars().length; i++){
				
				insertGame.setString(1, String.valueOf(i));								//ID
				insertGame.setString(2, String.valueOf(g.getSingleStar(i).getX()) );	//X
				insertGame.setString(3, String.valueOf(g.getSingleStar(i).getY()));		//Y
				insertGame.setString(4, String.valueOf(g.getSingleStar(i).getZ()));		//Z
				insertGame.setString(5, String.valueOf(g.getSingleStar(i).getType()));	//type
				insertGame.setString(6, String.valueOf(g.getSingleStar(i).getSize()));	//size
				insertGame.setString(7, g.getSingleStar(i).getName());					//name
				
				//System.out.println(insertGame.toString());
				
				insertGame.executeUpdate();
				
				insertGame = connection.prepareStatement("INSERT INTO stars VALUES (?,?,?,?,?,?,?)");
				
			}
			
		} catch (SQLException sqle){
			
			sqle.printStackTrace();
			
			System.out.println("Store galaxy failure");
		}
		
		
		
	}
	
	public void store(Star s){
		
		try{
			
			//get a usable ID number:
			int idToUse = 0;
			ResultSet resultSet = selectStars.executeQuery();
			while (resultSet.next()){
				
				if (idToUse <= resultSet.getInt(1)){
					idToUse = resultSet.getInt(1)+1;
				}
				
			}

			insertGame.setString(1, String.valueOf(idToUse));
			insertGame.setString(2, String.valueOf(s.getX()));
			insertGame.setString(3,String.valueOf(s.getY()));
			insertGame.setString(4,String.valueOf(s.getZ()));
			insertGame.setString(5,String.valueOf(s.getType()));
			insertGame.setString(6,String.valueOf(s.getSize()));
			insertGame.setString(7,s.getName());
			
			insertGame.executeUpdate();
			
			insertGame = connection.prepareStatement("INSERT INTO stars VALUES (?,?,?,?,?,?,?)");
			
		} catch (SQLException sqle){
			sqle.printStackTrace();
			
			System.out.println("Store star failure");
			
		}
		
	}
	
	public void moveStar(){	//This is the "update" in CRUD since it's required. Used to "fix" custom galaxies
		
		try{

			String us = "UPDATE stars SET x = " + (rand.nextInt(Short.MAX_VALUE*2) - Short.MAX_VALUE) + " WHERE x = y";
			System.out.println(us);
			updateStar.executeUpdate(us);
			
			us = "UPDATE stars SET x = " + (rand.nextInt(Short.MAX_VALUE*2) - Short.MAX_VALUE) + " WHERE x = -y";
			System.out.println(us);
			updateStar.executeUpdate(us);
			
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
		
	}
	
	public void deleteStar(){
		
		try{
			
			int idToUse = 0;
			ResultSet resultSet = selectStars.executeQuery();
			while (resultSet.next()){
					idToUse = resultSet.getInt(1);
			}
			
			statement.executeUpdate( ("DELETE FROM stars WHERE ID = "+idToUse) );
			
		}	catch (SQLException sqle){
			sqle.printStackTrace();
			
			System.out.println("Delete star failure");
			
		}
		
	}
	
	public Galaxy getGalaxy(){
		
		System.out.println("getGalaxy()");
		//Galaxy g;
		Star[] stars;
		ResultSet resultSet = null;
		
		
		
		try{
			
			resultSet = selectStars.executeQuery();
			
			//System.out.println("herpaderpahuu: " + resultSet.toString());
			
			int length = 0;
			while(resultSet.next()){	//size of galaxy
				length = resultSet.getInt(1)+1;
			}
			
			//resultSet.absolute(1);
			resultSet.beforeFirst();

			resultSet = null;
			resultSet = selectStars.executeQuery();
			
			//System.out.println("l:"+length);
			
			stars = new Star[length];
			int i = 0;
			while(resultSet.next()){
				/*
				stars[i]= new Star(resultSet.getShort(2),resultSet.getShort(3),resultSet.getShort(4),resultSet.getByte(5),resultSet.getByte(6),resultSet.getString(7));
				i++;
				System.out.println(" stars"+i+" to string "+stars[i].toString());
				*/
				
				//wtf:
				stars[i] = new Star(resultSet.getShort("x"),resultSet.getShort("y"),resultSet.getShort("z"),resultSet.getByte("type"),resultSet.getByte("size"),resultSet.getString("name"));
				i++;
				
			}
			
			return new Galaxy(stars);
			
		}catch (SQLException sqlException){
			System.out.println("Galaxy retrieval from db failure");
			sqlException.printStackTrace();
			return new Galaxy(0);
		}
		
		
	}
	/*
	public Player[] getPlayers(){
		
	}
	*/
}
