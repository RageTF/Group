package gui;

import algorithms.AlgorithmResult;
import algorithms.DynamicAlgorithm;
import algorithms.RecursiveAlgorithm;
import generator.GraphGenerator;
import gui.panel.HorizontalProgressBar;
import gui.panel.StatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rage on 01.05.2017.
 */
public class MainFrame extends JFrame {

    private volatile boolean mFirstIsComplete = true;
    private volatile boolean mSecondIsComplete = true;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private Thread mFirstThread;
    private Thread mSecondThread;

    private StatisticsPanel mStatisticsPanel;
    private HorizontalProgressBar mProgressBar;

    private JButton mStart;
    private JButton mStop;
    private JTextField mCount;

    private JCheckBox mFirst = new JCheckBox("Динамическое программирование: ", true);
    private JCheckBox mSecond = new JCheckBox("Полный перебор: ", true);

    public MainFrame() {

        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BorderLayout());
        mStart = new JButton("Начать");
        mStart.setPreferredSize(new Dimension(WIDTH / 2, 50));
        mStop = new JButton("Остановить");
        mStop.setPreferredSize(new Dimension(WIDTH / 2, 50));

        mCount = new JTextField();
        mCount.setHorizontalAlignment(SwingConstants.CENTER);
        mCount.setPreferredSize(new Dimension(WIDTH / 2, 50));

        toolbar.add(mCount, "North");

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BorderLayout());
        panelButton.add(mStart, "South");
        panelButton.add(mStop, "Center");

        toolbar.add(panelButton, "Center");

        mStart.addActionListener(e -> {
            try {
                int count = Integer.parseInt(mCount.getText());
                if (count >= 3) {
                    startStatisticTest(count);
                }
            } catch (Exception a) {
                return;
            }
        });

        mStop.addActionListener(e -> {
            stop();
        });

        JPanel check = new JPanel();
        check.add(mFirst);
        check.add(mSecond);

        toolbar.add(check, "South");

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        mStatisticsPanel = new StatisticsPanel();
        mProgressBar = new HorizontalProgressBar();

        panel.add(mStatisticsPanel);

        add(toolbar, "North");
        add(panel, "Center");
        add(mProgressBar, "South");
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void stop() {

        mProgressBar.stop();
        mStatisticsPanel.clear();
        mProgressBar.disposeValue();

        if (mFirstThread != null && mFirstThread.isAlive()) {
            mFirstThread.stop();
        }
        if (mSecondThread != null && mSecondThread.isAlive()) {
            mSecondThread.stop();
        }
    }

    public void startStatisticTest(int size) {
        GraphGenerator graphGenerator = new GraphGenerator();
        int[][] graph = graphGenerator.getGraph(size, 25);

        stop();
        if (mFirst.isSelected() && mSecond.isSelected()) {
            mProgressBar.setMaxValue(100);
        } else if (mFirst.isSelected() || mSecond.isSelected()) {
            mProgressBar.setMaxValue(50);
        } else {
            return;
        }
        mProgressBar.start();
        if (mFirst.isSelected()) {
            mFirstThread = new Thread(() -> {
                mFirstIsComplete = false;
                AlgorithmResult algorithmResult = new DynamicAlgorithm().getHamiltonianPath(graph);
                mStatisticsPanel.setFirstAlgorithmResult(algorithmResult);
                mProgressBar.plusValue(50);
                mFirstIsComplete = true;
            });
            mFirstThread.start();
        }
        if (mSecond.isSelected()) {
            mSecondThread = new Thread(() -> {
                mSecondIsComplete = false;
                AlgorithmResult algorithmResult = new RecursiveAlgorithm().getHamiltonianPath(graph);
                mStatisticsPanel.setSecondAlgorithmResult(algorithmResult);
                mProgressBar.plusValue(50);
                mSecondIsComplete = true;
            });
            mSecondThread.start();
        }
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

    }

}
