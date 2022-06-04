
package swingnerdle;

import java.io.Serializable;


public class Squares implements Serializable{
    
    private static final long serialVersionUID = 8092006435668245133L;
    private int lengthX;
    private int lengthY;
    private int localX;
    private int localY;
    private int roundDegree;
    private static int distanceX=60;
    private static int distanceY=55;
    private String value;
    private boolean picked;
    private int level;
    private int situation;

    public Squares(int localX, int localY, String value, int level) {
        this.localX = localX;
        this.localY = localY;
        lengthX=55;
        lengthY=50;
        roundDegree=7;
        this.value=value;
        picked=false;
        this.level=level;
        situation=0;
    }
    
    public Squares(int localX, int localY, String value, int lengthX, int lengthY) {
        this.localX = localX;
        this.localY = localY;
        this.lengthX=lengthX;
        this.lengthY=lengthY;
        roundDegree=7;
        this.value=value;
        picked=false;
        level=0;
    }

    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }
    
    

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    
    
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }
    
    

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    

    public static int getDistanceX() {
        return distanceX;
    }

    public static int getDistanceY() {
        return distanceY;
    }

    public int getLengthX() {
        return lengthX;
    }

    public void setLengthX(int lengthX) {
        this.lengthX = lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public void setLengthY(int lengthY) {
        this.lengthY = lengthY;
    }

    public int getLocalX() {
        return localX;
    }

    public void setLocalX(int localX) {
        this.localX = localX;
    }

    public int getLocalY() {
        return localY;
    }

    public void setLocalY(int localY) {
        this.localY = localY;
    }

    public int getRoundDegree() {
        return roundDegree;
    }

    public void setRoundDegree(int roundDegree) {
        this.roundDegree = roundDegree;
    }
    
    
    
}
