package gui.panel;

import algorithms.AlgorithmResult;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Created by Rage on 01.05.2017.
 */
public class StatisticsPanel extends JPanel {

    private Color mFirstColor;
    private Color mSecondColor;

    private  String mTitile;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final int MARGIN = 10;

    private static final int WIDTH_RECT = 100;

    private List<AlgorithmResult> mAlgorithmResults;

    public StatisticsPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH + MARGIN, HEIGHT + MARGIN));
        mAlgorithmResults = new LinkedList<>();
        mAlgorithmResults.add(null);
        mAlgorithmResults.add(null);
        mFirstColor = Color.RED;
        mSecondColor = Color.darkGray;

    }

    public void setTitle(String title){
        mTitile=title;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(0, HEIGHT, WIDTH, HEIGHT);

        if(mTitile!=null){
            g.drawString(mTitile,10,10);
        }

        if (mAlgorithmResults.size() != 0) {
            long maxTime = -1;
            for (AlgorithmResult algorithmResult : mAlgorithmResults) {
                if (algorithmResult != null
                        && algorithmResult.getTime() != -1
                        && (maxTime == -1 || (algorithmResult.getTime() > maxTime))) {
                    maxTime = algorithmResult.getTime();
                }
            }

            if (maxTime != -1) {
                double k = maxTime / (0.8 * HEIGHT);

                for (int i = 0; i < mAlgorithmResults.size(); i++) {

                    if (i == 0) {
                        g.setColor(mFirstColor);
                    } else if (i == 1) {
                        g.setColor(mSecondColor);
                    }

                    AlgorithmResult algorithmResult = mAlgorithmResults.get(i);

                    int xColor=10;
                    int yColor=(i+1)*15 + 10;

                    if(algorithmResult!=null) {
                        g.fillRect(xColor,yColor, 10 ,10);
                        g.drawString(" - "+algorithmResult.getName()+ " , длина пути "+algorithmResult.getLength()+ " , путь "+Arrays.toString(algorithmResult.getPath()), xColor + 25, yColor + 7);

                        int width = WIDTH_RECT;
                        int height = (int) (algorithmResult.getTime() / k);

                        int x = i * WIDTH_RECT + WIDTH/2-WIDTH_RECT;
                        int y = HEIGHT - height;
                        g.fillRect(x, y, width, height);

                        int xText = x + width / 4;
                        int yText = y - 10;

                        g.setColor(Color.BLACK);
                        g.drawString(getTime(algorithmResult.getTime()), xText, yText);
                    }
                }
            }
        }
    }

    private static String getTime(long nano) {
        double s = (double) nano / 1000000000;
        return Double.toString(s);
    }

    public synchronized void setFirstAlgorithmResult(AlgorithmResult algorithmResult) {
        mAlgorithmResults.set(0,algorithmResult);
        repaint();
    }

    public synchronized void setSecondAlgorithmResult(AlgorithmResult algorithmResult) {
        mAlgorithmResults.set(1,algorithmResult);
        repaint();
    }

    public void clear(){
        mAlgorithmResults.clear();
        mAlgorithmResults.add(null);
        mAlgorithmResults.add(null);
        repaint();
    }
}
