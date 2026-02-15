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
*/

import java.util.List;

public class Scene{
    int budget;
    List<Role> starRoles;
    List<Player> stars;

    public Scene(int budget, List<Role> starRoles) {
        this.budget = budget;
        this.starRoles = starRoles;
    }

    public List<Player> getStars(){
        return this.stars;
    }

    public void addStar(Player star){
        this.stars.add(star);
    }

    public void addRole(Role role){
        this.starRoles.add(role);
    }

    public List<Role> getRoles(){
        return this.starRoles;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public int getBudget(){
        return this.budget;
    }
}