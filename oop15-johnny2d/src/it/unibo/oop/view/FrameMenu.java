package it.unibo.oop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Paolo
 *
 * Custom JFrame used as template for game's menu.
 * It defines standard JButtons layout and the LayoutManager.
 * 
 */
public class FrameMenu extends JFrame {
    
    private static final long serialVersionUID = -6224390548062243879L;
    private static final Color COMPONENTS_COLOR = new Color(255, 220, 130);
    private final Dimension prefButtonSize = new Dimension(160, 40);
    private final GridBagConstraints cnst = new GridBagConstraints();

    public FrameMenu(final String label) {
        super(label);

        this.cnst.insets = new Insets(5,5,5,5);
        this.cnst.gridy = 0;
        this.setSize(500, 500); 
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        final URL imgURL = Launcher.class.getResource("/background.jpg");
        final ImageIcon img = new ImageIcon(imgURL);
        
        final JPanel menuPanel = new ImagePanel(img.getImage()); 
        menuPanel.setLayout(new GridBagLayout());

        this.setContentPane(menuPanel);
    }

    public void addComponent(final JComponent cmp, final boolean customize) {
        if (customize) {
            cmp.setPreferredSize(this.prefButtonSize);
            cmp.setBackground(COMPONENTS_COLOR);
        }
        this.getContentPane().add(cmp, cnst);
        cnst.gridy++;
            
    }
}
