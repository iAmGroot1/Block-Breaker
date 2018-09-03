import javax.swing.JFrame; 

public class Main{
    public static void main(String[]args){
        JFrame obj= new JFrame();
        Gameplay gameplay=new Gameplay();
        gameplay.setDoubleBuffered(false);
        obj.setLocationRelativeTo(null);
        obj.setBounds(10,10,700,600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.add(gameplay); 
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    String audioFilePath = "/home/iamgroot/Downloads/Six.wav";
        //    AudioPlayerExample1 player = new AudioPlayerExample1();
        //    player.play(audioFilePath);
        //    obj.add(gameplay); 
    }
}