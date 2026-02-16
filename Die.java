/* 

Attributes
-none

Methods
-roll()

Implemented by - 
Last Change mm/dd/yy, first
*/


import java.util.Random;
public class Die {
    public int roll() {
        Random roll = new Random();
        return roll.nextInt(7);
    }
}