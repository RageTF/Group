package gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Rage on 01.05.2017.
 */
public class GraphPanel extends JPanel {

    private static final int WIDTH=500;
    private static final int HEIGHT=500;

    private static final int MARGIN=10;

    public GraphPanel(){
        setPreferredSize(new Dimension(WIDTH+MARGIN,HEIGHT+MARGIN));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
