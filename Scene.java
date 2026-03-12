/* 
Attributes
-budget
-starRoles[]
-stars[]

Methods
-getters and setters
-

Implemented by - Isaac Raven
Last Change 02/13/26, Isaac

COMPLETED
*/

import java.util.ArrayList;
import java.util.HashMap;

public class Scene{
    String name;
    int budget;
    HashMap<String, Role> starRoles;
    ArrayList<Player> stars;

    public Scene(String name, int budget, HashMap<String, Role> starRoles) {
        this.name = name;
        this.budget = budget;
        this.starRoles = starRoles;
    }

    public ArrayList<Player> getStars(){
        return this.stars;
    }

    public void addStar(Player star){
        this.stars.add(star);
    }

    public void addRole(Role role){
        this.starRoles.put(role.getTitle(),role);
    }

    public HashMap<String, Role> getRoles(){
        return this.starRoles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public int getBudget(){
        return this.budget;
    }
}