
package windows;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class IPanel extends javax.swing.JPanel {
    
    BufferedImage img;
    
    public IPanel(BufferedImage img) {
        this.img = img;
        this.setSize(img.getWidth(), img.getHeight());
    }
    
    public IPanel(BufferedImage img, int width, int height) {
        this.img = img;
        this.setSize(width, height);
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }
    
    @Override
    public void update(Graphics g){
        Dimension d = getSize();
        g.drawImage(img, 2, 2, d.width, d.height, null);
        setOpaque(false);
        super.paintComponent(g);
    }
    
}