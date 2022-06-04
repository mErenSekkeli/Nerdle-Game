package swingnerdle;

import java.io.Serializable;

public class Statistics implements Serializable {
    private static final long serialVersionUID = 724237510432788580L;
    public int numberOfCompletedGames;
    public int numberOfUncompletedGames;
    public int numberOfSuccess;
    public int numberOfUnsuccess;
    public double averageNumberOfRows;
    public String averageTime; 
    private String fileName = "statistics.bin";

    public Statistics(){}
    
    private void increaseCompletedGameCount() {
    	this.numberOfCompletedGames++;
    }
    
    private void increaseUncompletedGameCount() {
    	this.numberOfUncompletedGames++;
    }
    
    private void increaseNumberOfSuccess() {
    	this.numberOfSuccess++;
    }
    
    private void increaseNumberOfUnsuccess() {
    	this.numberOfUnsuccess++;
    }
    
    private void changeAverageTime(String time) {
    	this.averageTime=String.valueOf((Double.valueOf(averageTime)*(numberOfSuccess-1)+Double.valueOf(time))/(numberOfSuccess));
    }
    
    private void changeAverageNumberOfRows(int rowCount) {
    	double totalRowCount = this.averageNumberOfRows * (numberOfCompletedGames-1);  
    	this.averageNumberOfRows = (double) (totalRowCount + rowCount) / numberOfCompletedGames;
    }
    
    public void initializeStatistics(){
    	FileReadWrite<Statistics> frw = new FileReadWrite<>();
    	if(frw.isFileExists(fileName)) {
            
    		Statistics s = frw.readData(fileName);
    		this.numberOfCompletedGames = s.numberOfCompletedGames;
    		this.numberOfUncompletedGames = s.numberOfUncompletedGames;
    		this.numberOfSuccess = s.numberOfSuccess;
    		this.numberOfUnsuccess = s.numberOfUnsuccess;
    		this.averageNumberOfRows = s.averageNumberOfRows;
                this.averageTime=s.averageTime;
    	}
    	else {
    		this.numberOfCompletedGames = 0;
    		this.numberOfUncompletedGames = 0;
    		this.numberOfSuccess = 0;
    		this.numberOfUnsuccess = 0;
    		this.averageNumberOfRows = 0.0;
                this.averageTime="0.0";
    	}
    }
    
    
    public void finalizeGame(boolean isEndedWithSuccess, int rowCount, boolean isCompleted, String time) {
    	FileReadWrite<Statistics> fileReadWrite  = new FileReadWrite<>();
    	if(isCompleted) {
			increaseCompletedGameCount();
    		if(isEndedWithSuccess) {
    			increaseNumberOfSuccess();
    			changeAverageNumberOfRows(rowCount);
    			changeAverageTime(time);
    		}
    		else
    			increaseNumberOfUnsuccess();	
    	}
    	else 
    		increaseUncompletedGameCount();
    	
    	fileReadWrite.writeData(this.fileName, this);
    }
    
    public String[] getString(){
        String[] str =new String[5];
        str[0]= "Yarıda Bırakılan Oyun Sayısı: " + numberOfUncompletedGames + "\n";
        str[1]= "Başarıyla Tamamlanan Oyun Sayısı: " + numberOfSuccess + "\n";
        str[2]= "Başarısızlıkla Sonuçlanan Oyun Sayısı: " + numberOfUnsuccess + "\n";
        str[3]= String.format("Başarıyla Tamamlanan Oyunların Ortalama Satır Sayısı: %.2f\n", averageNumberOfRows);
        str[4]= String.format("Başarıyla Tamamlanan Oyunların Ortalama Bitirme Süresi: %.2f \n", Double.valueOf(averageTime)); 

        return str;
    }

    
}
