
package swingnerdle;
        
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class userInterface extends JPanel implements MouseListener,MouseMotionListener{
    
    private int startX;
    private int startY;
    private Color color;
    private final int squareCount;
    private final ArrayList<Squares> sqValues;
    private final ArrayList<Squares> sqInputOperand;
    private final ArrayList<Squares> sqInputOperator;
    private String tempEquation;
    private String[] EquationArr;
    private final StartGame screen;
    private String inputOperand;
    private String inputOperator;
    private int middle;
    private int endX;
    private int level;
    private long time;
    private long endTime;
    private Equation equation;
    private Statistics stat;
    private Save saveGame;
    private Thread th1;
    
    private boolean pressed1;
    private boolean isWarning1;
    private boolean gameIsOver;
    private boolean saveStatistics;
    private boolean saveAllGame;
    private boolean stopThread;
    
    public userInterface(StartGame screen,boolean isSave) {
        this.screen=screen;
        color=new Color(255,255,254);
        setBackground(color);
        startY=0;
        sqInputOperand=new ArrayList<>();
        sqInputOperator=new ArrayList<>();
        stat=new Statistics();
        stat.initializeStatistics();
        saveGame=new Save();
        int totalX=0;
        if(isSave){
            //Save Gelecek
            equation=new Equation();
            saveGame.getSave();
            tempEquation=saveGame.equation;
            time=System.currentTimeMillis()-Long.valueOf(saveGame.time)*1000;
            endTime=System.currentTimeMillis();
            level=saveGame.level;
            sqValues=saveGame.prevGuesses;
            squareCount=tempEquation.length();
        for(int i=0;i<squareCount;i++){
            totalX+=61;
        }
        middle=(600-totalX)/2;
        startX=middle;
        for(int i=0;i<6;i++){
            startX=middle;
            for(int j=0;j<squareCount;j++){
                startX+=Squares.getDistanceX();
            }
            startY+=Squares.getDistanceY();
        }
        endX=startX;
        saveGame.delete();
        }else{
           //Denklem alınacak
           sqValues=new ArrayList<>();
        equation=new Equation();
        tempEquation=equation.generateEquation(); 
        time=System.currentTimeMillis();
        endTime=0;
        squareCount=tempEquation.length();
        //level alınacak
        level=1;
        for(int i=0;i<squareCount;i++){
            totalX+=61;
        }
        middle=(600-totalX)/2;
        startX=middle;
        int tmpLevel=1;
        for(int i=0;i<6;i++){
            startX=middle;
            for(int j=0;j<squareCount;j++){
                sqValues.add(new Squares(startX, startY,"",tmpLevel));
                startX+=Squares.getDistanceX();
            }
            tmpLevel++;
            startY+=Squares.getDistanceY();
        }
        endX=startX;
        }
        inputOperator="";
        inputOperand="";
        gameIsOver=false;
        //squareCount alınacak
        EquationArr=new String[squareCount];
        EquationArr=tempEquation.split("");
        saveStatistics=false;
        saveAllGame=false;
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        inject();
    }
    
    public void inject(){
       stopThread=false;
        th1=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stopThread){
                   try {
                        repaint();
                        if(gameIsOver){
                            stopThread=true;
                        }
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        System.out.println("Program Kesintiye Ugradi!");
                    } 
                } 
            }
        });
        th1.start();
    }
    
    public void goMenu(boolean isSuccess,int time){
        //save methodu gelecek
        if(saveStatistics){
           stat.finalizeGame(isSuccess,level,gameIsOver,String.valueOf(time));
           
        }
        if(saveAllGame){
            saveGame=new Save(tempEquation,sqValues,level,String.valueOf(time));
            saveGame.writeSave();
        }
        MainScreen main=new MainScreen(screen);
        main.setFocusable(true);
        main.setFocusTraversalKeysEnabled(false);
        screen.add(main);
        screen.setVisible(true);
                setOpaque(false);
                setEnabled(false);
                setVisible(false);
    }
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        startX=0;
        startY=0;
        //System.out.println(time/1000.0);
        //Value Squares
        color=new Color(153,148,132);
        Font font=new Font("Arial",Font.PLAIN,25);
        g.setFont(font);
        g.setColor(color);
        
        for(Squares s : sqValues){
            if(s.getSituation()==0){
                color=new Color(153,148,132);
            }else if(s.getSituation()==1){
                //Doğru İnput Yanlış Yer
                color=new Color(153,153,0);
            }else if(s.getSituation()==2){
                //Doğru Yer
                color=new Color(0,102,0);
            }else if(s.getSituation()==3){
                //Yanlış Yer
                color=new Color(153,0,0);
            }
            g.setColor(color);
            g.fillRoundRect(s.getLocalX(), s.getLocalY(), s.getLengthX(), s.getLengthY(), s.getRoundDegree(), s.getRoundDegree());
            if(s.isPicked()){
                color=new Color(0,0,0);
                g.setColor(color);
                g.drawRoundRect(s.getLocalX()-1, s.getLocalY()-1, s.getLengthX()+2, s.getLengthY()+2, s.getRoundDegree(), s.getRoundDegree());
            }
            color=new Color(255,255,255);
            g.setColor(color);
            g.drawString(s.getValue(), s.getLocalX()+20, s.getLocalY()+32);
        }
        //Timer
        if(!gameIsOver){
        endTime=System.currentTimeMillis();
        endTime-=time; 
        }
        g.setColor(new Color(0,0,0));
        g.drawString("Süre: "+String.valueOf((int)endTime/1000)+" sn", 240, 630);
        //Input Squares
        startX=75;
        startY=380;
        for(int i=0; i<10;i++){
            sqInputOperand.add(new Squares(startX, startY, String.valueOf(i), 40, 55));
            startX+=Squares.getDistanceX()-15;
        }
        
        for(Squares s : sqInputOperand){
           color=new Color(153,148,132);
           g.setColor(color);
           g.fillRoundRect(s.getLocalX(), s.getLocalY(), s.getLengthX(), s.getLengthY(), s.getRoundDegree(), s.getRoundDegree());
           if(s.isPicked()){
                color=new Color(0,0,0);
                g.setColor(color);
                g.drawRoundRect(s.getLocalX()-1, s.getLocalY()-1, s.getLengthX()+2, s.getLengthY()+2, s.getRoundDegree(), s.getRoundDegree());
            }
           color=new Color(255,255,255);
            g.setColor(color);
            g.drawString(s.getValue(), s.getLocalX()+14, s.getLocalY()+35);
        }
        
        startX=190;
        startY=450;

            sqInputOperator.add(new Squares(startX, startY, "+", 40, 55));
            startX+=Squares.getDistanceX()-15;
            sqInputOperator.add(new Squares(startX, startY, "-", 40, 55));
            startX+=Squares.getDistanceX()-15;
            sqInputOperator.add(new Squares(startX, startY, "*", 40, 55));
            startX+=Squares.getDistanceX()-15;
            sqInputOperator.add(new Squares(startX, startY, "/", 40, 55));
            startX+=Squares.getDistanceX()-15;
            sqInputOperator.add(new Squares(startX, startY, "=", 40, 55));
            startX+=Squares.getDistanceX()-15;
            startY=520;
            startX=200;
            sqInputOperator.add(new Squares(startX, startY, "Sil", 55, 50));
            startX+=Squares.getDistanceX();
            sqInputOperator.add(new Squares(startX, startY, "Tahmin Et", 145, 50));
            
        
        
        for(Squares s : sqInputOperator){
           color=new Color(153,148,132);
           g.setColor(color);
           g.fillRoundRect(s.getLocalX(), s.getLocalY(), s.getLengthX(), s.getLengthY(), s.getRoundDegree(), s.getRoundDegree());
           if(s.isPicked()){
                color=new Color(0,0,0);
                g.setColor(color);
                g.drawRoundRect(s.getLocalX()-1, s.getLocalY()-1, s.getLengthX()+2, s.getLengthY()+2, s.getRoundDegree(), s.getRoundDegree());
            }
           color=new Color(255,255,255);
            g.setColor(color);
            g.drawString(s.getValue(), s.getLocalX()+14, s.getLocalY()+35);
        }
        
        if(!pressed1){
            g.setColor(new Color(255,150,80));
            }else{
                g.setColor(new Color(255,102,0));
            }
        g.drawString("Sonra Bitir", 240, 700);
   
    }
    public void playAgain(){
        userInterface usi=new userInterface(screen,false);
        usi.requestFocus();
        usi.setFocusable(true);
        usi.setFocusTraversalKeysEnabled(false);
        screen.add(usi);
        screen.setVisible(true);
                setOpaque(false);
                setEnabled(false);
                setVisible(false); 
    }
    public void changeStatistics(boolean isSuccess,int time){
        stat.finalizeGame(isSuccess,level,gameIsOver,String.valueOf(time));
    }
    public void warning1(){
        Object[] opt=new Object[1];
        opt[0]="Tamam";
        JOptionPane.showOptionDialog(this, "Yanlış Kareyi Seçtiniz.", "Uyarı", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
 
    }
    public void warning2(){
        Object[] opt=new Object[1];
        opt[0]="Tamam";
            JOptionPane.showOptionDialog(this, "Birden Fazla Kare Seçemezsiniz.", "Uyarı", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
    }
    public void warning3(){
        Object[] opt=new Object[1];
        opt[0]="Tamam";
        JOptionPane.showOptionDialog(this, "Kare Seçin.", "Uyarı", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
    }
    public void warning4(){
        Object[] opt=new Object[1];
        opt[0]="Tamam";
        JOptionPane.showOptionDialog(this, "Sıradaki Tüm Kareleri Doldurun.", "Uyarı", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
    }
    public void warning5(){
        Object[] opt=new Object[1];
        opt[0]="Tamam";
        JOptionPane.showOptionDialog(this, "Geçerli Bir Denklem Giriniz.", "Uyarı", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
    }
    public void congMessage(int time){
        Object[] opt=new Object[2];
        opt[0]="Ana Menüye Git";
        opt[1]="Yeni Oyun";
        int check=JOptionPane.showOptionDialog(this, "Tebrikler. Oyunu Kazandınız. Süre: "+time+" sn", "Oyun Bitti", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]);
        if(check==0){
            saveStatistics=true;
            goMenu(true,time);
        }else{
            saveStatistics=true;
            changeStatistics(true,time);
            playAgain();
        }
    }
    public void loserMessage(int time){
       Object[] opt=new Object[2];
        opt[0]="Ana Menüye Git";
        opt[1]="Yeni Oyun";
        int check=JOptionPane.showOptionDialog(this, "Tahmin Hakkınız Bitti. Oyunu Kazanamadınız. Süre: "+time+" sn"+"\nDenklem: "+tempEquation, "Oyun Bitti", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,opt,opt[0]); 
        
        if(check==0){
            saveStatistics=true;
            goMenu(false,time);
        }else{
            saveStatistics=true;
            changeStatistics(false,time);
            playAgain();
        }
    }
    
    public int pickedIndex(ArrayList<Squares> squares){
        for(int i=0;i<squares.size();i++){
            if(squares.get(i).isPicked())
                return i;
        }  
        return -1;
    }
    
    public void injectValue(String values,int opType){
        
        int i=0;
        boolean isFound=false;
        while(!isFound && i<sqValues.size()){
            if(sqValues.get(i).isPicked()){
                sqValues.get(i).setValue(values);
                if(i+1-((level-1)*squareCount)<squareCount){
                    sqValues.get(i+1).setPicked(true);
                    sqValues.get(i).setPicked(false);
                }else{
                    //level up
                }
                
                isFound=true;
            }
            i++;
        }
        if(!isFound){
            warning3();
        }
        
        if(opType==1){
           for(Squares s : sqInputOperand){
            if(s.isPicked()){
                s.setPicked(false);
            }
        } 
        }else if(opType==2){
            for(Squares s : sqInputOperator){
            if(s.isPicked()){
                s.setPicked(false);
            }
        }
        }
        
    }
    
    public void deleteValue(int index){
        sqValues.get(index).setValue("");
        int i=0;
        boolean isFound=false;
        while(!isFound && i<sqInputOperator.size()){
            if(sqInputOperator.get(i).isPicked()){
                sqInputOperator.get(i).setPicked(false);
                isFound=true;
            }
            i++;
        }
        if(index>getLevelIndex()){
            sqValues.get(index).setPicked(false);
            sqValues.get(index-1).setPicked(true);
        }
    }
    
    public int getLevelIndex(){
        int tmp=1,i=0;
        while(tmp!=level){
          i+=squareCount;
          tmp++;
        }
        return i;
    }

    @Override
    public void repaint() {
        super.repaint();
    }
 

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX()>=230 && e.getX()<=346 &&e.getY()>=680 && e.getY()<=700){
                pressed1=false;
                long endTime=System.currentTimeMillis();
                endTime-=time;
                saveAllGame=true;
                saveStatistics=true;
                goMenu(false,(int)endTime/1000);
            }else if(e.getX()>=75 && e.getX()<=520 &&e.getY()>=380 && e.getY()<=435){
                int i=0;
                boolean isFound=false;
                while(!isFound && i<sqInputOperand.size()){
                    if(e.getX()>=sqInputOperand.get(i).getLocalX() && e.getX()<=sqInputOperand.get(i).getLocalX()+Squares.getDistanceX()-20){
                        inputOperand=sqInputOperand.get(i).getValue();
                        injectValue(inputOperand,1);
                        sqInputOperand.get(i).setPicked(true);
                        isFound=true;
                    }
                    i++;
                }
            }else if(e.getX()>=190 && e.getX()<=410 &&e.getY()>=450 && e.getY()<=505){
                int i=0;
                boolean isFound=false;
                while(!isFound && i<sqInputOperator.size()){
                    if(e.getX()>=sqInputOperator.get(i).getLocalX() && e.getX()<=sqInputOperator.get(i).getLocalX()+40 && e.getY()>=sqInputOperator.get(i).getLocalY() && e.getY()<=sqInputOperator.get(i).getLocalY()+55){
                        inputOperator=sqInputOperator.get(i).getValue();
                        injectValue(inputOperator,2);
                        sqInputOperator.get(i).setPicked(true);
                        isFound=true;
                    }
                    i++;
                }
            }else if(e.getX()>=200 && e.getX()<=255 &&e.getY()>=520 && e.getY()<=570){
                //Sil
                inputOperator=sqInputOperator.get(sqInputOperator.size()-2).getValue();
                if(!gameIsOver){
                   int i=0;
                boolean isFound=false;
                for(Squares s : sqValues){
                    if(s.isPicked()){
                        deleteValue(i);
                        isFound=true;
                    }
                    i++;
                }
                if(!isFound){
                    warning3();
                    } 
                }
                sqInputOperator.get(sqInputOperator.size()-2).setPicked(true);

            }else if(e.getX()>=260 && e.getX()<=405 &&e.getY()>=520 && e.getY()<=570){
                //Tahmin Et
                boolean isFill=true;
                inputOperator=sqInputOperator.get(sqInputOperator.size()-1).getValue();
                sqInputOperator.get(sqInputOperator.size()-1).setPicked(true);
                int i=getLevelIndex();
                while(i<getLevelIndex()+squareCount && isFill){
                    if(sqValues.get(i).getValue().equals("")){
                        isFill=false;
                        warning4();
                    }
                    i++;
                }
                if(isFill){
                    boolean allTrue=false;
                    String tmpString="";
                    for(i=getLevelIndex();i<getLevelIndex()+squareCount;i++){
                        tmpString+=sqValues.get(i).getValue();
                    }
                    if(!equation.isEquationValid(tmpString)){
                        warning5();
                    }else{
                        i=getLevelIndex();
                        int trueCount=0;
                    HashMap<Integer,Squares> diffSquares=new HashMap<>();
                    //Tamamen Doğru mu
                    String[] tmp=EquationArr.clone();
                    int k=0;
                    for(String s : tmp){
                        if(s.equals(sqValues.get(i).getValue())){
                            sqValues.get(i).setSituation(2);
                            trueCount++;
                            tmp[k]="";
                        }else{
                            diffSquares.put(i,sqValues.get(i));
                        }
                        k++;
                        i++;
                    }
                    //Hangileri Doğru yerde
                    for(Map.Entry<Integer, Squares> entry : diffSquares.entrySet()){
                        i=0;
                        boolean isFound=false;
                        while(i<tmp.length && !isFound){
                            if(tmp[i].equals(entry.getValue().getValue())){
                                sqValues.get(entry.getKey()).setSituation(1);
                                tmp[i]="";
                                isFound=true;
                            }else{
                                //sqValues.get(entry.getKey()).setSituation(3);
                            }
                            i++;
                        }
                    }
                    //Yanlış Elemanlar
                    for(i=getLevelIndex();i<getLevelIndex()+squareCount;i++){
                        if(sqValues.get(i).getSituation()==0){
                            sqValues.get(i).setSituation(3);
                        }
                    }
                    //Level Sonuçlanması
                    long endTime=System.currentTimeMillis();
                        endTime-=time;
                    if(trueCount==squareCount){
                        allTrue=true;
                        gameIsOver=true;
                        congMessage((int)endTime/1000);
                    }else{
                        if(level<6){
                        int j=pickedIndex(sqValues);
                        sqValues.get(j).setPicked(false);
                        level++;
                        i=getLevelIndex();
                        sqValues.get(i).setPicked(true);
                        }else{
                            gameIsOver=true;
                            loserMessage((int)endTime/1000);
                        }
                        
                    }
                    } 
                    
                }
                
            }
            else if(e.getX()>=middle && e.getX()<=endX &&e.getY()>=1 && e.getY()<=325){
                int i=0;
                boolean isFound=false;
                while(!isFound && i<sqValues.size()){
                    if(e.getX()>=sqValues.get(i).getLocalX() && e.getX()<=sqValues.get(i).getLocalX()+Squares.getDistanceX()-5 && e.getY()>=sqValues.get(i).getLocalY() && e.getY()<sqValues.get(i).getLocalY()+Squares.getDistanceY()){
                        if(level==sqValues.get(i).getLevel()){
                            int index=pickedIndex(sqValues);
                            if(index==i){
                                  sqValues.get(index).setPicked(false);
                            }else if(index!=-1){
                                sqValues.get(index).setPicked(false);
                                sqValues.get(i).setPicked(true);
                            }else{
                              sqValues.get(i).setPicked(true);  
                            }  
                        }else if(!isWarning1){
                            isWarning1=true;
                        warning1();
                        }
                        isFound=true;
                    }
                    i++;
                }
                isWarning1=false;
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(e.getX()>=230 && e.getX()<=346 &&e.getY()>=680 && e.getY()<=700){
                pressed1=true;
        }else{
            pressed1=false;
        }
    }
    
    
    
    
}
