

public class CastingOffice extends NonActingSet{
    int[] dolCost;
    int[] credCost;


    public CastingOffice(String name) {
        super(name);
    }

    public void setDolCost(int[] cost){
        this.dolCost = cost;
    }

    public void setCredCost(int[] cost){
        this.CredCost = cost;
    }

    public int[] getDolCost(){
        return this.dolCost;
    }

    public int[] getCredCost(){
        return this.CredCost;
    }

}