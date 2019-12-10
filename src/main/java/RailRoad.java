import java.util.ArrayList;


public final class RailRoad<T extends Station> {
    private int[][] adjency_matrix;
    private ArrayList<T> listOfStatitions;
   private ArrayList<String> route;
   private static RailRoad railRoad;
   private RailRoad(String path){
       //filling route and listOfStations from txt file
   }
   public static RailRoad getRailRoad(String path){
       // possible i have to use threads
       if(railRoad == null){
           railRoad = new RailRoad(path);
       }
       return railRoad;
   }

   public boolean isWayValid(int currentStation, int nextStation){
       return true;
   }

   private void fillMatrix(String path){

   }

   public T getStation(int stationID){
       return listOfStatitions.get(stationID-1);
   }
}


