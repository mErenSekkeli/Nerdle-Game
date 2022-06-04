
package swingnerdle;
import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable{
    private static final long serialVersionUID = 5860787475073472801L;
    public String equation;
    public ArrayList<Squares> prevGuesses;
    public int level;
    public String time;

    public Save(String equation , ArrayList<Squares> prevGuesses, int level,String time){
        this.equation = equation;
        this.prevGuesses = prevGuesses;
        this.level=level;
        this.time=time;
    }

    public Save() {
    }
    
    
    
    private String fileName = "save.bin";

    public void getSave(){
        FileReadWrite<Save> frw = new FileReadWrite<>();
        Save s = frw.readData(fileName);
        this.equation = s.equation;
        this.prevGuesses = s.prevGuesses;
        this.level=s.level;
        this.time = s.time;
    }
    
    public void delete(){
        FileReadWrite<Save> frw = new FileReadWrite<>();
        frw.deleteFile(this.fileName);
    }

    public void writeSave(){
        FileReadWrite<Save> frw = new FileReadWrite<>();
        frw.writeData(this.fileName, this);
    }

    public boolean isSaveExists(){
        FileReadWrite<Save> frw = new FileReadWrite<>();
       return frw.isFileExists(this.fileName);
    }

    @Override
    public String toString(){
        String a = "";
        for (Squares s : prevGuesses) {
            a += s.getValue() + "-->" + s.getSituation();
            a += " ";
        }
        return "Save: " + equation + " " + a + "Time: " + time + " Level: " + level;
    }

}

