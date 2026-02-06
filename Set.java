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
        this.neighbors = neighbors;
        if (name == "Casting Office"){
            this.castingOffice = true;
        }
        else{
            this.castingOffice = false;
        }
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Set[] getNeighbors(){
        return this.neighbors;
    }
    public void addNeighbors(Set[] neighbors){
        this.neighbors = neighbors;
    }
    public boolean getcastingOffice(){
        return this.castingOffice;
    }
    public void getcastingOffice(boolean castingOffice){
        this.castingOffice = castingOffice;
    }




}