package gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rage on 18.05.2017.
 */
public class HorizontalProgressBar extends JPanel{

    private static final int WIDTH=500;
    private static final int HEIGHT=25;
    private int maxValue=100;

    private volatile boolean isStart;
    private Color mProgressColor=Color.BLUE;
    private double xD=0;
    private double x=0;
    private int y=0;
    private int value=0;
    private int WIDTH_BAR=HEIGHT*2;
    private int HEIGHT_BAR=HEIGHT;

    public HorizontalProgressBar(){
        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isStart){
            g.setColor(mProgressColor);
            g.fillRect((int) x,y,WIDTH_BAR,HEIGHT_BAR);
            if(x>=WIDTH){
                x=0;
            }else{
                x+=0.03;
            }
            repaint();
        }else{
            setBackground(Color.white);
        }
    }

    public void start(){
        isStart=true;
        repaint();
    }

    public void stop(){
        isStart=false;
        repaint();
    }

    public void plusValue(int value){
        this.value+=value;
        if(this.value>=maxValue){
            this.value=0;
            stop();
        }
    }

    public void disposeValue(){
        this.value=0;
        maxValue=100;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
