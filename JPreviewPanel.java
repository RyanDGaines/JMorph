import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*************
 * This class creates a panel for the preview animate window which morphs all triangles and updates the graphics
 *************/

public class JPreviewPanel extends JPanel implements ActionListener {

    private JControlPoint preP[][], preM[][], duringP[][], duringM[][], postP[][], postM[][];
    private double dxP[][], dyP[][], dxM[][], dyM[][];
    private int sec;
    private boolean end = false;
    private Timer time;
    private double frames, framesTotal;
    private JMorphPanel prePanel;

    public JPreviewPanel(JControlPoint prePoint[][], JControlPoint preMid[][], JControlPoint postPoint[][], JControlPoint postMid[][], int seconds, JMorphPanel prePanel) {
        this.prePanel = prePanel;
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        sec = seconds;
        preP = prePoint;
        preM = preMid;
        postP = postPoint;
        postM = postMid;
        duringP = new JControlPoint[preP.length][preP.length];
        duringM = new JControlPoint[preM.length][preM.length];
        for (int i = 0; i < duringP.length; i++) {
            for (int j = 0; j < duringP.length; j++) {
                duringP[i][j] = preP[i][j];
            }
        }
        for (int i = 0; i < duringM.length; i++) {
            for (int j = 0; j < duringM.length; j++) {
                duringM[i][j] = preM[i][j];
            }
        }
        findDistances();

        time = new Timer(1000 / 60, this);
        frames = 0;
        framesTotal = 60 * sec;
        time.start();

    }

    //create arrays of change in x and y
    private void findDistances() {
        dxP = new double[preP.length][preP.length];
        dyP = new double[preP.length][preP.length];
        for (int i = 0; i < preP.length; i++) {
            for (int j = 0; j < preP.length; j++) {
                dxP[i][j] = postP[i][j].getX() - preP[i][j].getX();
                dyP[i][j] = postP[i][j].getY() - preP[i][j].getY();
            }
        }
        dxM = new double[preM.length][preM.length];
        dyM = new double[preM.length][preM.length];
        for (int i = 0; i < preM.length; i++) {
            for (int j = 0; j < preM.length; j++) {
                dxM[i][j] = postM[i][j].getX() - preM[i][j].getX();
                dyM[i][j] = postM[i][j].getY() - preM[i][j].getY();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawdurP(g2d);
        drawLine(g2d);
    }

    //draw points during movement
    private void drawdurP(Graphics2D g2d) {
        for (int i = 0; i < duringP.length; i++) {
            for (int j = 0; j < duringP.length; j++) {
                g2d.draw(duringP[i][j].getCircle());
                g2d.fill(duringP[i][j].getCircle());
            }
        }
        for (int i = 0; i < duringM.length; i++) {
            for (int j = 0; j < duringM.length; j++) {
                g2d.draw(duringM[i][j].getCircle());
                g2d.fill(duringM[i][j].getCircle());
            }
        }
    }

    //draw lines during movement
    public void drawLine(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 0.2f));
        for (int i = 0; i < duringP.length - 1; i++) {
            for (int j = 0; j < duringP[i].length - 1; j++) {
                if (!duringP[i + 1][j].getEdge()) {
                    g2d.drawLine((int) duringP[i][j].getX(), (int) duringP[i][j].getY() + 3, (int) duringP[i + 1][j].getX(), (int) duringP[i + 1][j].getY() + 3);
                }
                if (!duringP[i][j + 1].getEdge()) {
                    g2d.drawLine((int) duringP[i][j].getX() + 3, (int) duringP[i][j].getY(), (int) duringP[i][j + 1].getX() + 3, (int) duringP[i][j + 1].getY());
                }
                if (i != 0) {
                    if (!duringP[i - 1][j].getEdge()) {
                        g2d.drawLine((int) duringP[i][j].getX(), (int) duringP[i][j].getY() + 3, (int) duringP[i - 1][j].getX(), (int) duringP[i - 1][j].getY() + 3);
                    }
                }
                if (j != 0) {
                    if (!duringP[i][j - 1].getEdge()) {
                        g2d.drawLine((int) duringP[i][j].getX() + 3, (int) duringP[i][j].getY(), (int) duringP[i][j - 1].getX() + 3, (int) duringP[i][j - 1].getY());
                    }
                }
            }
        }
        for (int i = 0; i < duringM.length; i++) {
            for (int j = 0; j < duringM[i].length; j++) {
                g2d.drawLine((int) duringM[i][j].getX() + 3, (int) duringM[i][j].getY() + 3, (int) duringP[i][j].getX() + 3, (int) duringP[i][j].getY() + 3);
                g2d.drawLine((int) duringM[i][j].getX() + 3, (int) duringM[i][j].getY() + 3, (int) duringP[i + 1][j].getX() + 3, (int) duringP[i + 1][j].getY() + 3);
                g2d.drawLine((int) duringM[i][j].getX() + 3, (int) duringM[i][j].getY() + 3, (int) duringP[i][j + 1].getX() + 3, (int) duringP[i][j + 1].getY() + 3);
                g2d.drawLine((int) duringM[i][j].getX() + 3, (int) duringM[i][j].getY() + 3, (int) duringP[i + 1][j + 1].getX() + 3, (int) duringP[i + 1][j + 1].getY() + 3);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updatePoints();
        if (frames == framesTotal) {
            time.stop();
            frames = 0;
        }
        frames++;
        repaint();
    }

    //update points at every timer tick
    private void updatePoints() {
        for (int i = 0; i < duringP.length; i++) {
            for (int j = 0; j < duringP.length; j++) {
                if (dxP[i][j] != 0 && dyP[i][j] != 0) {
                    JControlPoint temp = new JControlPoint((int) (preP[i][j].getX() + (frames / framesTotal * dxP[i][j])), (int) (preP[i][j].getY() + (frames / framesTotal * dyP[i][j])));
                    duringP[i][j] = temp;
                }
            }
        }
        for (int i = 0; i < duringM.length; i++) {
            for (int j = 0; j < duringM.length; j++) {
                if (dxM[i][j] != 0 && dyM[i][j] != 0) {
                    JControlPoint temp = new JControlPoint((int) (preM[i][j].getX() + (frames / framesTotal * dxM[i][j])), (int) (preM[i][j].getY() + (frames / framesTotal * dyM[i][j])));
                    duringM[i][j] = temp;
                }
            }
        }
    }
}
