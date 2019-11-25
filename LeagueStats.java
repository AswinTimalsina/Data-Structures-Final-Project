import java.util.*;
import java.io.*;

/**
 * The LeagueStats class is used to read the file containing the soccer  
 * matches of the team. The class records the matches and according to 
 * the command by the user, returns the stat of the team using stats(String tName) method,
 * returns the best from the whole team using best() method, returns 
 * Highscoring team using highscoring() method and returns the rank of 
 * the team using rank(String tName) method.
 *
 * @author Aswin Timalsina
 */
public class LeagueStats{

   String home;
   String away;
   
   int homeGoals;
   int awayGoals;
   
   Datas dataFile;
   Map<String, Datas> soccer = new HashMap<String, Datas>();
   Map<String, Datas> soccer1 = new HashMap<String, Datas>();
   
   
   /**
	 * Constructor to read the csv file containing the matches of the team 
	 * and store them accordingly using data structure specifically map.
	 * @param csvFile the name of the file containing the datas from the matches
 	 */
   public LeagueStats(String csvFile){
      try{Scanner in = new Scanner(new File(csvFile));
         String[] eachWord = new String[10];
         String line;
         while(in.hasNextLine()){
         
            line = in.nextLine();
            eachWord = line.split(",");
         
            home = eachWord[0];
            homeGoals = Integer.parseInt(eachWord[2]);
         
            away = eachWord[1];
            awayGoals = Integer.parseInt(eachWord[3]);
         
            if(soccer.containsKey(home)){
               soccer.get(home).setDatas(homeGoals, awayGoals);
            }
            
            else{
               soccer.put(home, new Datas(homeGoals, awayGoals));
            }
         
            if(soccer.containsKey(away)){
               soccer.get(away).setDatas(awayGoals, homeGoals);
            }
            
            else{
               soccer.put(away, new Datas(awayGoals, homeGoals));
            }
         }
      } //try
      
      catch(Exception e){
         System.out.print("File Not Found!");
      }
   }

   /**
	 * Method to process a file containing a number of requests for league statistics.
    * Method to call different methods according to the commands in the file
    * Returns a concatenated String containing the result from various methods
	 * @return a concatenated String containing the result from various methods
  	 * @param statsFile the name of the file containing a number of requests
  	 */
   public String getStats(String statsFile){
      String result = "";
   // return //String
      try{
         String[] eachWord = new String[10];
         String line;
         String command = "";
        
         Scanner in = new Scanner(new File(statsFile));
         while(in.hasNextLine()){
            line = in.nextLine();
            eachWord = line.split(" "); 
         
            command = eachWord[0];
            String tName = "";
         
            for(int i = 1; i<eachWord.length; i++){
            
               tName += eachWord[i] + " ";
            }//for ends  
            if(!tName.equals("")){   
               tName = tName.substring(0, tName.length()-1);
            }
         
            if(command.equals("STATS")){
               result += stats(tName) + "\n";
            }
            
            else if(command.equals("BEST")){
               result += best() + "\n";
            }
            
            else if(command.equals("HSCORING")){
               result +=  highscoring() + "\n";
            }
            
            else if(command.equals("RANK")){
               result += rank(tName) + "\n";
            }
            
            else{
               result += "";
            }
         
         }//while end
      }//try ends
      
      catch(Exception e){
         System.out.print("Error!");
      }
      
      return result;
   }


   /**
	 * Method to return the statistics of the team in request
    * Returns a concatenated String containing the result from various methods
	 * @return string of stats of team
  	 * @param tName the name of the team whose stats is to be returned
  	 */
      public String stats(String tName){
      if(!soccer.containsKey(tName)){
         return "TEAM: " + tName + " NOT FOUND";
      }
      
      else{
         return String.format("%s%-18s%s%s%s%s%s%s%s%s%s%s%s%s","TEAM: ", tName," W: " ,
          soccer.get(tName).getWins() , " D: " , soccer.get(tName).getDraws() , " L: ", soccer.get(tName).getLosses() , 
          " GF: " , soccer.get(tName).getGoalsFor() ," GA: " , soccer.get(tName).getGoalsAgainst() , " PTS: " ,
          soccer.get(tName).getPoints());
      }
   }


   /**
	 * Method to return the best team from the whole collection of team
    * Returns the name and stats of the best team
	 * @return string of the name and stats of the best team
  	 */
   String bestTeam= "";
   public String best(){
      int initial = 0;
      int max = -400;
      int maxCount = 0;
   
      for(String a: soccer.keySet()){
         initial = soccer.get(a).getPoints();
      
         if(initial > max){
            max = initial;
            maxCount = 1;
         }
         
         else if(max == initial){
            maxCount++;
         }
      
      } //for ends
      
      
      if(maxCount>1){  
         Map<String, Integer> goalDiff = new HashMap<>();
         Map<String, Integer> tieGoalDiff = new HashMap<>();
      
         for(String c: soccer.keySet()){
            if(soccer.get(c).getPoints() == max){
               goalDiff.put(c, soccer.get(c).getGoalsFor() - soccer.get(c).getGoalsAgainst());                  
            }
         }//for ends
        
         int max1 = -400;
         int max1Count= 0;
      
         for(String a:goalDiff.keySet()){
         
            int first = goalDiff.get(a);
         
         
            if(first> max1){
               max1 = first;
               max1Count = 1;
            }
            
            else if(max1 == first){
               max1Count++;
            }      
         } //for loop for getting the best from the draws
      
         if(max1Count > 1){
         
            for(String a: goalDiff.keySet()){
               if(goalDiff.get(a) == max1){
                  tieGoalDiff.put(a, soccer.get(a).getGoalsFor());
               }
            
               int max2 = -400;
            
               for(String b : tieGoalDiff.keySet()){
                  int first = tieGoalDiff.get(b);
               
               
                  if(first> max2){
                     max2 = first;
                  
                  }
               } //finding the max goalfor
               for(String b:tieGoalDiff.keySet()){
                  if(tieGoalDiff.get(b) == max2){
                     bestTeam = b;
                  }
               }  //if the goal difference are same, the best team is the one with the greatest number of goals
            }//for putting in a goalDiff map
         
         }//if max1Count > 1 ends
         
         else{
         
            for(String a:goalDiff.keySet()){
            
               if(goalDiff.get(a) == max1){
                  bestTeam = a;
               }
            
            }//for
          
         }//else
      
      }//if maxCount>1 ends
      
      
      else{
         for(String a: soccer.keySet()){
            if(soccer.get(a).getPoints() == max){
               bestTeam = a;        
            }
         }//for ends
      
      }
      return String.format("%s%-18s%s%s%s%s%s%s%s%s%s%s%s%s","BEST: ", bestTeam, " W: " , soccer.get(bestTeam).getWins() , " D: " , soccer.get(bestTeam).getDraws() , " L: ", soccer.get(bestTeam).getLosses() , " GF: " , soccer.get(bestTeam).getGoalsFor() ," GA: " , soccer.get(bestTeam).getGoalsAgainst() , " PTS: " ,soccer.get(bestTeam).getPoints());
   }



   /**
	 * Method to return the names of any teams that have scored more goals than the BEST team
    * The method uses the best() method
    * Returns a concatenated String of the teams separated by comma
	 * @return the teams that have scored more goals than the BEST team
  	 * @param tName the name of the team whose stats is to be returned
  	 */
   public String highscoring(){
      String highscoringResult = "";
      best();
      for(String a : soccer.keySet()){
         if(!a.equals(bestTeam)){
            if(soccer.get(a).getGoalsFor() >= soccer.get(bestTeam).getGoalsFor()){
               highscoringResult += a + ", ";          
            }        
         }
      }//for ends
   
      //highscoringResult =  highscoringResult.substring(0, highscoringResult.length()-2);
   
      if(highscoringResult.equals("")){
         return "HIGH SCORERS: NONE";
      }
   
      return "HIGH SCORERS: " + highscoringResult;
   }


   /*
      Self-designed method that uses "soccer1" map that is the copy of "soccer" map
      Method returns the BEST team from the soccer1 map which is modified in rank(String tName) method
      @return the best team specially for the rank method
   */
   String bestTeam1= "";
   public String bestForRank(){
      int initial = 0;
      int max = -400;
      int maxCount = 0;
   
      for(String a: soccer1.keySet()){
         initial = soccer1.get(a).getPoints();
      
         if(initial > max){
            max = initial;
            maxCount = 1;
         }
         
         else if(max == initial){
            maxCount++;
         }
      
      } //for ends
   
   
      if(maxCount>1){  
      
         Map<String, Integer> goalDiff = new HashMap<>();
         Map<String, Integer> tieGoalDiff = new HashMap<>();
      
         for(String c: soccer1.keySet()){
            if(soccer1.get(c).getPoints() == max){
               goalDiff.put(c, soccer1.get(c).getGoalsFor() - soccer1.get(c).getGoalsAgainst());                  
            }
         }//for ends
        
         int max1 = -400;
         int max1Count= 0;
      
         for(String a:goalDiff.keySet()){ 
         
            int first = goalDiff.get(a);
         
         
            if(first> max1){      //0>-1
               max1 = first;
               max1Count = 1;
            }
            
            else if(max1 == first){
               max1Count++;
            }      
         } //for loop for getting the best from the draws
      
         if(max1Count > 1){     
         
            for(String a: goalDiff.keySet()){
               if(goalDiff.get(a) == max1){
                  tieGoalDiff.put(a, soccer1.get(a).getGoalsFor());
               }
            
               int max2 = 0;
            
               for(String b : tieGoalDiff.keySet()){
                  int first = tieGoalDiff.get(b);
               
               
                  if(first> max2){
                     max2 = first;
                  
                  }
               } //finding the max goalfor
               for(String b:tieGoalDiff.keySet()){
                  if(tieGoalDiff.get(b) == max2){
                     bestTeam = b;
                  }
               }  //if the goal difference are same, the best team is the one with the greatest number of goals
            }//for putting in a goalDiff map
         
         }//if max1Count > 1 ends
         
         else{
         
            for(String a:goalDiff.keySet()){
            
               if(goalDiff.get(a) == max1){
                  bestTeam = a;
                  break;
               }
            
            }//for
          
         }//else
      
      }//if maxCount>1 ends
      
      
      else{
         for(String a: soccer1.keySet()){
            if(soccer1.get(a).getPoints() == max){
               bestTeam = a; 
               break;       
            }
         }//for ends
      
      }
      return bestTeam;
   }//bestForRank ends



   /**
	 * Method to return the rank of the team out of all the teams who plays the matches
    * Returns the team name and the rank if it exists otherwise returns NONE
	 * @return the rank of the team
  	 * @param tName the name of the team whose stats is to be returned
  	 */
   public String rank(String tName){
      soccer1.putAll(soccer);
      int totalTeams = 0;
      int position = 0;
      List<String> positionList = new ArrayList<>();
   
      for(String b:soccer.keySet()){
         totalTeams++;
      }
   
      List<String> keys = new ArrayList<>();
      for(String b: soccer.keySet()){
         keys.add(b);
      }
   
   if(soccer.keySet().contains(tName)){
   
      for(int i = 0; i < keys.size(); i++){
      String bestFromRank = bestForRank();
         positionList.add(bestFromRank);
         soccer1.remove(bestFromRank);
      }
   
      for(int i = 0; i<positionList.size(); i++){
         if(positionList.get(i).equals(tName)){
            position = i+1;
            break;
         }
      }
   
      return "RANK: " + tName + " is ranked " + position + " out of " + totalTeams;
      }
      
      else{
      return "RANK: " + tName + " NOT FOUND";
      }    
      
   }//rank ends 

}//leagueStats ends