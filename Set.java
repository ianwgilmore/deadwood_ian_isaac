/* 
(casting office/trailer)
Attributes
-name
-neighbors[]
for checking rank up generally false
-bool castingOffice

Methods
-getters and setters
-

Implemented by - Ian Gilmore
Last Change 02/05/26, Ian
-adding getters setters
*/

public class Set{
    String name;
    Set[] neighbors;
    Boolean castingOffice;

    public Set(String name, Set[] neighbors){
        this.name = name;
        this.neighbors = neighbors
        if (name == "Casting Office"){
            this.castingOffice = true;
        }
        else{
            this.castingOffice = false;
        }
    }

    Public String getName(){
        return this.name;
    }
    Public void setName(newname){
        this.name = newname;
    }
    Public String getNeighbors(){
        return this.neighbors;
    }
    Public void addNeighbors(neighbors){
        this.neighbors = neighbors;
    }
    Public String getcastingOffice(){
        return this.castingOffice;
    }
    Public void getcastingOffice(castingOffice){
        this.castingOffice = castingOffice;
    }




}