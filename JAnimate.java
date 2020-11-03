import javax.swing.*;
import java.awt.*;

/*************
 * This class creates a separate window for the animation
 *************/

public class JAnimate extends JFrame{

    private Container c;
    JAnimatePanel animatePanel;
    private JMorphPanel prePanel;

    public JAnimate(JMorphPanel prePanel, JMorphPanel postPanel, int seconds){
        super("JMorpher");
        this.prePanel = prePanel;
        animatePanel = new JAnimatePanel(prePanel, postPanel, seconds);

        c = getContentPane();
        c.add(animatePanel);
        setVisible(true);
        setResizable(false);
        pack();
    }
}
