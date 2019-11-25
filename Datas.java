import java.util.*;
import java.io.*;

/**
 * The Datas class uses goalsFor and goalsAgainst as argument 
 * The class stores wins, draws, losses, and calculates points
 * The class has the method to retrive goalsFor, goalsAgainst, 
 * wins, losses, draws and points
 *
 * @author Aswin Timalsina
 */

public class Datas{

   int wins = 0;
   int draws = 0;
   int losses= 0;
   int points = 0;
   int Gfor = 0;
   int Gagainst = 0;

   /**
	 * Constructor to save the wins, losses and draws from the arguments according to the conditions
	 * Initializes the goalsFor and goalsAgainst  The Id consists of the 
	 * @param goalsFor goals scored by the team
	 * @param goalsAgainst goals scored against the team
 	 */
   public Datas(int goalsFor, int goalsAgainst){
   
      Gfor = goalsFor;
      Gagainst = goalsAgainst;
   
      if(goalsFor > goalsAgainst){
         wins++;;
      }
      
      
      else if(goalsFor<goalsAgainst){
         losses++;
      }
      
      else if(goalsFor==goalsAgainst){
         draws++;
      }
   }
   
   /**
	 * Method to modify the stored goalsFor and goalsAgainst specially for
    * the teams which are already in the map
    * Method increases the goalsFor and goalsAgainst from the previous saved value
	 * @param goalsFor goals scored by the team
	 * @param goalsAgainst goals scored against the team
 	 */
   public void setDatas(int goalsFor, int goalsAgainst){
   
      Gfor += goalsFor;
      Gagainst += goalsAgainst;
   
      if(goalsFor > goalsAgainst){
         wins++;;
      }
      
      
      else if(goalsFor<goalsAgainst){
         losses++;
      }
      
      else if(goalsFor==goalsAgainst){
         draws++;
      }
   }
   
	/**
	 * Accessor that returns the goals scored by the team.
	 * @return the the total goals scored by the team.
 	 */
   public int getGoalsFor(){
      return Gfor;
   }
   
   /**
	 * Accessor that returns the goals scored against the team.
	 * @return the the total goals scored against the team.
 	 */
   public int getGoalsAgainst(){
      return Gagainst;
   }
   
   /**
	 * Accessor that returns the number of wins by the team.
	 * @return the total wins by the team.
 	 */
   public int getWins(){
      return wins;
   }
   
   /**
	 * Accessor that returns the number of losses by the team.
	 * @return the total losses by the team.
 	 */
   public int getLosses(){
      return losses;
   }
   
   /**
	 * Accessor that returns the draws by the team.
	 * @return the total draws by the team.
 	 */
   public int getDraws(){
      return draws;
   }
   
   /**
	 * Accessor that returns the points of the team.
	 * @return the total points of the team.
 	 */
   public int getPoints(){
      return 3*getWins() + getDraws();
   }
}