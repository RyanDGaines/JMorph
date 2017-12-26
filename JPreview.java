import javax.swing.*;
import java.awt.*;

/*************
 * This class creates a separate window for the preview animation
 *************/

public class JPreview extends JFrame{

    private Container c;
    private JPreviewPanel previewPanel;

    public JPreview(JMorphPanel prePanel, JMorphPanel postPanel, int row, int col, int seconds){
        super("Animate");
        previewPanel = new JPreviewPanel(prePanel.getAllPoints(), prePanel.getAllMidPoints(), postPanel.getAllPoints(), postPanel.getAllMidPoints(), seconds, prePanel);
        previewPanel.setLocation(new Point (10,10));

        // determine preview size for panel
        if(prePanel.isImgLoaded()) {
            previewPanel.setSize(new Dimension(prePanel.getImage().getWidth(),prePanel.getImage().getHeight()));
        }
        else{
            previewPanel.setSize(400, 400);
        }
        c = getContentPane();
        c.setLayout(null);
        c.add(previewPanel, SwingConstants.CENTER);

        // determine preview size for frame
        if(prePanel.isImgLoaded()) {
            setSize(new Dimension(prePanel.getImage().getWidth() + 25, prePanel.getImage().getHeight() + 50));
        }
        else{
            setSize(425, 450);
        }
        setVisible(true);
        setResizable(false);
    }
}