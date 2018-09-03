import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play=false;
    private int score=0;
    private MapGenerator map;
    private int totalBricks=21;

    //to set the speed of the ball//
    private Timer timer;
    private int delay=5;

    private int playerX=310;

    private int ballX=120;
    private int ballY=350;
    private int ballXCh=-1;
    private int ballYCh=-2;

    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay, this);
        timer.start();
    }

    private static void pause(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //draws the slider and tile//
    public void paint(Graphics g){
        //background//
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        map.draw((Graphics2D)g);      

        //border//
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(0,0,3,592);

        if(ballY<570){
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD,25));
            g.drawString("Score:"+score,560,30);
        }
        //pedal//
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);//playerX gives where it is present imagine it as the x-axis

        //ball//
        g.setColor(Color.yellow);
        g.fillOval(ballX,ballY,20,20);

        if(ballY>570){
            play=false;
            ballXCh=0;
            ballYCh=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over, Score: "+score,180,300);
            g.setFont(new Font("serif", Font.BOLD,25));
            g.drawString("Press enter to restart",210,350);

        }

        if(totalBricks==0){
            play=false;
            ballXCh=0;
            ballYCh=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("CONGRATULATION YOU WON ",100,300);
            g.setFont(new Font("serif", Font.BOLD,25));
            g.drawString("Press enter to restart",210,350);

        }

        g.dispose();
    }

    @Override 
    public void actionPerformed(ActionEvent e) { 
        timer.start();
        if(play){
            if(new Rectangle(ballX,ballY,20,20).intersects(new Rectangle(playerX,550,100,8)))ballYCh=-ballYCh;
            ballX+=ballXCh;
            ballY+=ballYCh;
            //check if the ball has reached the border and thus needs to change direction //
            if(ballX<0)ballXCh=-ballXCh;
            if(ballY<0)ballYCh=-ballYCh;
            if(ballX>670)ballXCh=-ballXCh;
        }
        A:  for(int i=0;i<map.map.length;i++){
            for(int j=0;j<map.map[0].length;j++){
                if(play){
                    if(map.map[i][j]>0){
                        //location of the brick to find the intersection//
                        int brickX=j*map.brickWidth+80;
                        int brickY=i*map.brickHeight+50;
                        Rectangle brickRect=new Rectangle(brickX,brickY,map.brickWidth,map.brickHeight);
                        if(new Rectangle(ballX,ballY,20,20).intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;

                            if(ballX+19<= brickRect.x ||ballX+1>=brickRect.x+brickRect.width)ballXCh=-ballXCh;
                            else ballYCh=-ballYCh;
                            break A;
                        }
                    }
                }
                repaint();//like refreshing the window//
                //code that reacts to the action... 
            }
        }
    }

    @Override 
    public void keyTyped(KeyEvent e){}

    @Override 
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=600)playerX=600;
            else moveRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10)playerX=10;
            else moveLeft();  
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                score=0;
                map = new MapGenerator(3,7);
                totalBricks=21;
                //to set the speed of the ball//
                playerX=310;
                ballX=120;
                ballY=350;
                ballXCh=-1;
                ballYCh=-2;
                repaint();
            }
        }
    }

    //moiving by 20pixel//
    public void moveRight(){
        play=true;
        playerX+=20;
    }

    public void moveLeft(){
        play=true;
        playerX-=20;
    }

    @Override 
    public void keyReleased(KeyEvent e)
    {
    }
}
