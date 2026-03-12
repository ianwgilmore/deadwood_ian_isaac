

public class CastingOffice extends NonActingSet{
    int[] dolCost;
    int[] credCost;


    public CastingOffice(String name) {
        super(name);
        this.dolCost = new int[] {0,0,4,10,18,28,40};
        this.credCost = new int[] {0,0,5,10,15,20,25};
    }

    public void setDolCost(int[] cost){
        this.dolCost = cost;
    }

    public void setCredCost(int[] cost){
        this.credCost = cost;
    }

    public int getDolCost(int i){
        return this.dolCost[i];
    }

    public int getCredCost(int i){
        return this.credCost[i];
    }

}