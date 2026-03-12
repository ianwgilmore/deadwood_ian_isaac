/* 
(casting office/trailer)
Attributes
-name
-name
-neighbors[]
for checking rank up generally false
-bool castingOffice
-actingset associated, null for casting office and trailer

Methods
-getters and setters
-

Implemented by - Ian Gilmore
Last Change 02/05/26, Ian
-adding getters setters

*/

import java.util.ArrayList;

public class Set{
    String name;
    ArrayList<String> neighbors;

    public Set(String name){
        this.name = name;
        this.neighbors = new ArrayList<String>();
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public ArrayList<String> getNeighbors(){
        return this.neighbors;
    }
    public void addNeighbors(String neighbor){
        this.neighbors.add(neighbor);
    }




}