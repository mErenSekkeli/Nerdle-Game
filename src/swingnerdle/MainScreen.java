
package swingnerdle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainScreen extends JPanel implements ActionListener,MouseListener,MouseMotionListener{

    private Timer timer;
    private Color color;
    private StartGame screen;
    private Save save;
    private Statistics stat;
    private Equation eq;
    private String equationMes;
    
    //Boolean Değişkenler
    private boolean goStatistics;
    private boolean pressed1;
    private boolean pressed2;
    private boolean pressed3;
    private boolean pressed4;
    private boolean hasGame;
    private boolean takeSavedGame;
    private boolean isVisible;
    private boolean applyTest;
    
    public MainScreen(StartGame screen) {
        timer=new Timer(50, this);
        timer.start();
        color=new Color(255,255,254);
        this.screen=screen;
        setBackground(color);
        pressed1=false;
        pressed2=false;
        pressed3=false;
        pressed4=false;
        goStatistics=false;
        hasGame=false;
        takeSavedGame=false;
        applyTest=false;
        save=new Save();
        if(save.isSaveExists()){
            isVisible=true;
        }else{
            isVisible=false;
        }
        stat=new Statistics();
        eq=new Equation();
        stat.initializeStatistics();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    public void exitGame(){
        StartGame.closeMenu();
        System.exit(0);
    }
   
    
    public void goGame(){
        if(hasGame){
            userInterface usi;
            if(takeSavedGame){
                usi=new userInterface(screen,true);
            }else{
                usi=new userInterface(screen,false);
            }
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usi.requestFocus();
        usi.setFocusable(true);
        usi.setFocusTraversalKeysEnabled(false);
        screen.add(usi);
        screen.setVisible(true);
                setOpaque(false);
                setEnabled(false);
                setVisible(false);
                hasGame=false;
        }        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font font=new Font("Arial",Font.PLAIN,25);
        g.setFont(font);
        if(goStatistics){
            //İstatistikler
        }else{
            if(isVisible){
                String newGame="Devam Et";
            
            if(!pressed1){
            g.setColor(new Color(255,150,80));
            }else{
                g.setColor(new Color(255,102,0));
            }
            g.drawString(newGame, 225, 120);
            }
            
            String keepGame="Yeni Oyun";
            
            if(!pressed4){
                g.setColor(new Color(255,150,80));
            }else{
                g.setColor(new Color(255,102,0));
            }
            g.drawString(keepGame, 225, 180);
            
            String test="Test";
            
            if(!pressed2){
                g.setColor(new Color(255,150,80));
            }else{
                g.setColor(new Color(255,102,0));
            }
            g.drawString(test, 225, 240);
            
            String close="Oyunu Kapat";
            
            if(!pressed3){   
            g.setColor(new Color(255,150,80));
            }else{
                g.setColor(new Color(255,102,0));
            }
            g.drawString(close, 225, 300);
            
            g.setColor(new Color(0,0,0));
            g.drawString("İstatistikler: ", 20, 370);
            g.setFont(new Font("Arial",Font.PLAIN,16));
            
            String[] tmp=stat.getString();
            int Y=400;
            for(int i=0;i<tmp.length;i++){
                g.drawString(tmp[i], 50, Y);
                Y+=30;
            }     
            
        }
        
        //Test Yazdırma
        if(applyTest){
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("Denklem: "+equationMes, 20, 40);
        }
        
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
        goGame();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX()>=225 && e.getX()<=335 &&e.getY()>=100 && e.getY()<=120){
                pressed1=false;
                if(save.isSaveExists()){
                hasGame=true;
                takeSavedGame=true;
                StartGame.closeMenu();
                }
            }else if(e.getX()>=225 && e.getX()<=340 && e.getY()>=215 && e.getY()<=241){
                pressed2=false;
                equationMes=eq.generateEquation();
                applyTest=true;
            }else if(e.getX()>=225 && e.getX()<=370 && e.getY()>=280 && e.getY()<=305){
                pressed3=false;
                exitGame();
            }else if(e.getX()>=230 && e.getX()<=340 && e.getY()>=162 && e.getY()<=185){
                pressed4=false;
                hasGame=true;
                takeSavedGame=false;
                StartGame.closeMenu();
                //goGame();
                
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
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(e.getX()>=225 && e.getX()<=335 &&e.getY()>=100 && e.getY()<=120){
                pressed1=true;
            }else if(e.getX()>=225 && e.getX()<=340 && e.getY()>=215 && e.getY()<=241){
                pressed2=true;
                
            }else if(e.getX()>=225 && e.getX()<=370 && e.getY()>=280 && e.getY()<=305){
                pressed3=true;
            }else if(e.getX()>=230 && e.getX()<=340 && e.getY()>=162 && e.getY()<=185){
                pressed4=true;
                
            }else{
                pressed1=false;
                pressed2=false;
                pressed3=false;
                pressed4=false;
            }
    }
    
    
    
}
