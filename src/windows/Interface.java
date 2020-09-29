
package windows;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jesús
 */
public final class Interface extends javax.swing.JFrame {

    boolean maximized;
    private int xW, yW;
    int noImg;
    int conClick;
    
    private final JFileChooser opChooser, svChooser;
    private final JPanel[] miniatures;
    private int mW, mH;
    public int selected, old;
    public BufferedImage[] bffImg;
    public Filters stns;
    public Graphs graphs;
    public Graphs1 graphs1;
    public float [][][] filterArr;
    public float [][][][] filterArr2;
    public int hist[];
    public int histColor[][];
    public int coor_a[];
    public int coor_r[];

    public Interface() {
        initComponents();
        opChooser = new JFileChooser();
        initChooser(opChooser, true);
        svChooser = new JFileChooser();
        initChooser(svChooser, false);
        miniatures =new JPanel[6];
        initMiniatures();
        filterArr = new float [6][][];
        filterArr2 = new float [6][3][][];
        bffImg = new BufferedImage[6];
        old=0;
        stns = new Filters(this);
        graphs = new Graphs(this);
        graphs1 = new Graphs1(this);
        coor_a = new int[8];
        coor_r = new int[8];
        conClick =0;
          
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
                if (maximized) setExtendedState(Interface.MAXIMIZED_BOTH);
            }
        });
    }
    
    @Override
    public Image getIconImage() {
        Image image = new ImageIcon("src\\images\\icon.png").getImage();
        return image;
    }
    
    private void initChooser(JFileChooser chooser, boolean flag){
        String userhome = System.getProperty("user.home");
        chooser.setCurrentDirectory(new File(userhome+"\\Pictures"));
        chooser.setAcceptAllFileFilterUsed(false);
        if(!flag){
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setDialogTitle("Guardar Imagen");
            chooser.setFileFilter(new FileNameExtensionFilter("JPG (.jpg)","jpg"));
            chooser.setFileFilter(new FileNameExtensionFilter("JPEG (.jpeg)","jpeg"));
            chooser.setFileFilter(new FileNameExtensionFilter("PNG (.png)","png"));
            chooser.setFileFilter(new FileNameExtensionFilter("BMP (.bmp)","bmp"));
            chooser.setSelectedFile(new File("nueva_imagen"));
        }
        else{
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setDialogTitle("Abrir Imagen");
            chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Imagen","jpg","jpeg","png","bmp"));
        }
        ImagePreviewPanel preview = new ImagePreviewPanel();
        chooser.setAccessory(preview);
        chooser.addPropertyChangeListener(preview);
    }
    
    private void initMiniatures(){
        for (int i = 0; i < miniatures.length; i++) {
            miniatures[i] = (JPanel) galleryBar.getComponent(i);
            evtMiniature(i);
        }
        mW = miniatures[0].getWidth()-2;
        mH = miniatures[0].getHeight()-2;
        selected = 0;
    }
    
    private void evtMiniature(int m){
        miniatures[m].addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(miniatures[m].isEnabled()){
                    paintImage(viewPanel, new IPanel(bffImg[m]));
                    setMiniatureFocus(m);
                    selected = m;
                }
            }
        });
    }
    
    private void setMiniatureFocus(int m){
        for (int i = 0; i < miniatures.length; i++)
            if (i!=m)
                miniatures[i].setBorder(null);
        miniatures[m].setBorder(BorderFactory.createEtchedBorder(new Color(0,153,204), new Color(0,50,50)));
    }
    
    private void updateMiniatures(){
        mW = galleryBar.getComponent(0).getWidth()-2;
        mH = galleryBar.getComponent(0).getHeight()-2;
        for (int i = 0; i < miniatures.length; i++)
            if (bffImg[i] != null)
                paintImage(miniatures[i],new IPanel(bffImg[i],mW,mH));
    }
    
    private void paintImage(JPanel panel, IPanel draw){
        panel.removeAll();
        panel.add(draw);
        panel.repaint();
    }
    
    private void setImage(int i){
        paintImage(viewPanel, new IPanel(bffImg[i]));
        miniatures[i].setEnabled(true);
        miniatures[i].setBackground(Color.black);
        paintImage(miniatures[i],new IPanel(bffImg[i],mW,mH));
        setMiniatureFocus(i);
        selected = i;
    }
    
    private void onComponents(){
        save.setEnabled(true);
        filters.setEnabled(true);
        graph.setEnabled(true);
        graph1.setEnabled(true);
        //stns = new Filters(this);
        //graphs = new Graphs(this);
    }
    
    private void offComponents()
    {
        save.setEnabled(false);
        filters.setEnabled(false);
        graph.setEnabled(false);
        graph1.setEnabled(false);
    }
    
    public void setSettings(){
        //float[][] filter;
        noImg = stns.panels.getSelectedIndex();
        /*switch(stns.filters.getSelectedIndex() + 1){
            /*case 1: filter= LibImg.convierteArr(bffImg[selected]);
            break;
            case 2: filter = LibImg.umbral(filterArr[selected],stns.umbral.getValue());
            break;
            default: return;
        }*/
        bffImg[noImg] = MathImg.convierteDeArregloAImagen(filterArr2[noImg]);// cambie por selected
        setImage(noImg);
        selected = noImg;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        windowPanel = new javax.swing.JPanel();
        titleBar = new javax.swing.JPanel();
        exit = new javax.swing.JLabel();
        fullscreen = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        menuBar = new javax.swing.JPanel();
        filters = new javax.swing.JLabel();
        open = new javax.swing.JLabel();
        save = new javax.swing.JLabel();
        graph = new javax.swing.JLabel();
        graph1 = new javax.swing.JLabel();
        galleryBar = new javax.swing.JPanel();
        miniature1 = new javax.swing.JPanel();
        miniature2 = new javax.swing.JPanel();
        miniature3 = new javax.swing.JPanel();
        miniature4 = new javax.swing.JPanel();
        miniature5 = new javax.swing.JPanel();
        miniature6 = new javax.swing.JPanel();
        viewPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setName("interface"); // NOI18N
        setUndecorated(true);

        windowPanel.setBackground(new java.awt.Color(0, 0, 0));
        windowPanel.setForeground(new java.awt.Color(204, 204, 204));

        titleBar.setBackground(new java.awt.Color(10, 10, 10));
        titleBar.setForeground(new java.awt.Color(204, 204, 204));
        titleBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                titleBarMouseDragged(evt);
            }
        });
        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleBarMousePressed(evt);
            }
        });

        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });

        fullscreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fullscreen.png"))); // NOI18N
        fullscreen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fullscreen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fullscreenMouseClicked(evt);
            }
        });

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize.png"))); // NOI18N
        minimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/picture.png"))); // NOI18N
        icon.setFocusable(false);

        title.setBackground(new java.awt.Color(51, 51, 51));
        title.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        title.setForeground(new java.awt.Color(204, 204, 204));
        title.setFocusable(false);
        title.setPreferredSize(new java.awt.Dimension(100, 16));

        javax.swing.GroupLayout titleBarLayout = new javax.swing.GroupLayout(titleBar);
        titleBar.setLayout(titleBarLayout);
        titleBarLayout.setHorizontalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 456, Short.MAX_VALUE)
                .addComponent(minimize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullscreen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit)
                .addContainerGap())
        );
        titleBarLayout.setVerticalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon)
                    .addComponent(exit)
                    .addComponent(fullscreen)
                    .addComponent(minimize))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setBackground(new java.awt.Color(0, 0, 0));
        menuBar.setForeground(new java.awt.Color(255, 255, 255));
        menuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuBarMouseClicked(evt);
            }
        });

        filters.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filters.png"))); // NOI18N
        filters.setToolTipText("Filtros");
        filters.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        filters.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filters_disabled.png"))); // NOI18N
        filters.setEnabled(false);
        filters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtersMouseClicked(evt);
            }
        });

        open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open.png"))); // NOI18N
        open.setToolTipText("Abrir");
        open.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        open.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open_disabled.png"))); // NOI18N
        open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openMouseClicked(evt);
            }
        });

        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        save.setToolTipText("Guardar");
        save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_disabled.png"))); // NOI18N
        save.setEnabled(false);
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });

        graph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph.png"))); // NOI18N
        graph.setToolTipText("Graficas");
        graph.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        graph.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph_disabled.png"))); // NOI18N
        graph.setEnabled(false);
        graph.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graphMouseClicked(evt);
            }
        });

        graph1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph.png"))); // NOI18N
        graph1.setToolTipText("Graficas");
        graph1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        graph1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/graph_disabled.png"))); // NOI18N
        graph1.setEnabled(false);
        graph1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graph1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menuBarLayout = new javax.swing.GroupLayout(menuBar);
        menuBar.setLayout(menuBarLayout);
        menuBarLayout.setHorizontalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(graph1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graph)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(open)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save)
                .addContainerGap())
        );
        menuBarLayout.setVerticalGroup(
            menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graph1)
                    .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(menuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(save)
                            .addComponent(filters))
                        .addComponent(graph, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(open, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        galleryBar.setBackground(new java.awt.Color(0, 5, 5));
        galleryBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 102, 102), new java.awt.Color(0, 102, 153)));

        miniature1.setBackground(new java.awt.Color(255, 0, 0));
        miniature1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature1.setEnabled(false);
        miniature1.setMinimumSize(new java.awt.Dimension(0, 0));
        miniature1.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                miniature1FocusGained(evt);
            }
        });
        miniature1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature1Layout = new javax.swing.GroupLayout(miniature1);
        miniature1.setLayout(miniature1Layout);
        miniature1Layout.setHorizontalGroup(
            miniature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        miniature1Layout.setVerticalGroup(
            miniature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );

        miniature2.setBackground(new java.awt.Color(102, 102, 102));
        miniature2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature2.setEnabled(false);
        miniature2.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature2Layout = new javax.swing.GroupLayout(miniature2);
        miniature2.setLayout(miniature2Layout);
        miniature2Layout.setHorizontalGroup(
            miniature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        miniature2Layout.setVerticalGroup(
            miniature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        miniature3.setBackground(new java.awt.Color(102, 102, 102));
        miniature3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature3.setEnabled(false);
        miniature3.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature3Layout = new javax.swing.GroupLayout(miniature3);
        miniature3.setLayout(miniature3Layout);
        miniature3Layout.setHorizontalGroup(
            miniature3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        miniature3Layout.setVerticalGroup(
            miniature3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        miniature4.setBackground(new java.awt.Color(102, 102, 102));
        miniature4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature4.setEnabled(false);
        miniature4.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature4Layout = new javax.swing.GroupLayout(miniature4);
        miniature4.setLayout(miniature4Layout);
        miniature4Layout.setHorizontalGroup(
            miniature4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        miniature4Layout.setVerticalGroup(
            miniature4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        miniature5.setBackground(new java.awt.Color(102, 102, 102));
        miniature5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature5.setEnabled(false);
        miniature5.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature5Layout = new javax.swing.GroupLayout(miniature5);
        miniature5.setLayout(miniature5Layout);
        miniature5Layout.setHorizontalGroup(
            miniature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        miniature5Layout.setVerticalGroup(
            miniature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        miniature6.setBackground(new java.awt.Color(102, 102, 102));
        miniature6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miniature6.setEnabled(false);
        miniature6.setPreferredSize(new java.awt.Dimension(146, 35));
        miniature6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniature6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout miniature6Layout = new javax.swing.GroupLayout(miniature6);
        miniature6.setLayout(miniature6Layout);
        miniature6Layout.setHorizontalGroup(
            miniature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        miniature6Layout.setVerticalGroup(
            miniature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout galleryBarLayout = new javax.swing.GroupLayout(galleryBar);
        galleryBar.setLayout(galleryBarLayout);
        galleryBarLayout.setHorizontalGroup(
            galleryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(galleryBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(galleryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miniature1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(miniature2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(miniature3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(miniature4, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(miniature5, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(miniature6, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                .addContainerGap())
        );
        galleryBarLayout.setVerticalGroup(
            galleryBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(galleryBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(miniature1, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miniature2, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miniature3, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miniature4, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miniature5, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miniature6, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewPanel.setBackground(new java.awt.Color(0, 0, 0));
        viewPanel.setForeground(new java.awt.Color(204, 204, 204));
        viewPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout windowPanelLayout = new javax.swing.GroupLayout(windowPanel);
        windowPanel.setLayout(windowPanelLayout);
        windowPanelLayout.setHorizontalGroup(
            windowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(windowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(galleryBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(windowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(windowPanelLayout.createSequentialGroup()
                        .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        windowPanelLayout.setVerticalGroup(
            windowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, windowPanelLayout.createSequentialGroup()
                .addComponent(titleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(windowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(windowPanelLayout.createSequentialGroup()
                        .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(menuBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(windowPanelLayout.createSequentialGroup()
                        .addComponent(galleryBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(windowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(windowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void titleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleBarMousePressed
        xW = evt.getX();
        yW = evt.getY();
    }//GEN-LAST:event_titleBarMousePressed

    private void titleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleBarMouseDragged
        if(getExtendedState() != this.MAXIMIZED_BOTH){
            Point move = MouseInfo.getPointerInfo().getLocation();
            setLocation(move.x - xW, move.y - yW);
        }
    }//GEN-LAST:event_titleBarMouseDragged

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        setExtendedState(this.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void fullscreenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fullscreenMouseClicked
        if(getExtendedState() == this.MAXIMIZED_BOTH){
            setExtendedState(this.NORMAL);
            maximized = false;
        }else{
            setExtendedState(this.MAXIMIZED_BOTH);
            maximized = true;
        }
        updateMiniatures();
    }//GEN-LAST:event_fullscreenMouseClicked

    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitMouseClicked

    private void openMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openMouseClicked
        if(opChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            String filePath = opChooser.getSelectedFile().getAbsolutePath();
            try {
                File file=new File(filePath);
                if(file.exists()){
                    title.setText(filePath);
                    bffImg[selected] = ImageIO.read(file);
                    filterArr2[selected] = MathImg.convierteDeImagenaArreglo(bffImg[selected]);
                    setImage(selected);
                    old=selected;
                    onComponents();
                } else
                JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
        if (save.isEnabled()) {
            if(svChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                String filePath = svChooser.getSelectedFile().getAbsolutePath();
                try {
                    File file;
                    if(filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png") || filePath.endsWith(".bmp"))
                        file =new File(filePath);
                    else{
                        String ext = svChooser.getFileFilter().getDescription();
                        if (ext.contains(".jpg")) file =new File(filePath+".jpg");
                        else if (ext.contains(".jpeg")) file =new File(filePath+".jpeg");
                        else if (ext.contains(".png")) file =new File(filePath+".png");
                        else file =new File(filePath+".bmp");
                    }
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        ImageIO.write(bffImg[selected], "IMAGE", fos);
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_saveMouseClicked

    private void filtersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtersMouseClicked
        if (filters.isEnabled()) {
            this.setEnabled(false);
            //this.setVisible(false);
            stns.setVisible(true);
            stns.setCoordenadas(coor_r, coor_a);
        }
    }//GEN-LAST:event_filtersMouseClicked

    private void graphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graphMouseClicked
        if (graph.isEnabled()) {
            this.setEnabled(false);
            histColor = MathImg.histColor(bffImg[selected]);
            graphs.setHist(histColor);
            graphs.setVisible(true);
        }
    }//GEN-LAST:event_graphMouseClicked

    private void miniature1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_miniature1FocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_miniature1FocusGained

    private void miniature1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature1MouseClicked
        // TODO add your handling code here:
        cambiaMinions(0);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature1MouseClicked

    private void miniature2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature2MouseClicked
        // TODO add your handling code here:
        cambiaMinions(1);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature2MouseClicked

    private void miniature3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature3MouseClicked
        // TODO add your handling code here:
        cambiaMinions(2);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature3MouseClicked

    private void miniature4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature4MouseClicked
        // TODO add your handling code here:
        cambiaMinions(3);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature4MouseClicked

    private void miniature5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature5MouseClicked
        // TODO add your handling code here:
        cambiaMinions(4);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature5MouseClicked

    private void miniature6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniature6MouseClicked
        // TODO add your handling code here:
        cambiaMinions(5);
        if(bffImg[selected]==null)
            offComponents();
        else
            onComponents();
    }//GEN-LAST:event_miniature6MouseClicked

    private void viewPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewPanelMouseClicked
        // TODO add your handling code here:
        if(conClick>=8)
        {
            coor_r[conClick%8]=evt.getX();
            coor_r[conClick%8+1]=evt.getY();
        }else
        {
            coor_r[conClick]=evt.getX();
            coor_r[conClick+1]=evt.getY();
        }
       
        conClick+=2;
    }//GEN-LAST:event_viewPanelMouseClicked

    private void menuBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuBarMouseClicked
        // TODO add your handling code here:
       for(int i = 0; i<6; i++) 
       {
           System.out.println(coor_r[i]);
            System.out.println(coor_a[i]);
       }
    }//GEN-LAST:event_menuBarMouseClicked

    private void graph1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graph1MouseClicked
        // TODO add your handling code here:
        if (graph1.isEnabled()) {
            this.setEnabled(false);
            graphs1.setNoRen(10);
            graphs1.setImg(bffImg[selected]);
            graphs1.setVisible(true);
        }
    }//GEN-LAST:event_graph1MouseClicked
    
    public void cambiaMinions(int m)
    {
        //if(apply) old = selected;
        selected=m;
        for (JPanel miniature : miniatures) {
            miniature.setBackground(Color.DARK_GRAY);
        }
        miniatures[m].setBackground(Color.red);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel exit;
    private javax.swing.JLabel filters;
    private javax.swing.JLabel fullscreen;
    private javax.swing.JPanel galleryBar;
    private javax.swing.JLabel graph;
    private javax.swing.JLabel graph1;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel menuBar;
    private javax.swing.JPanel miniature1;
    private javax.swing.JPanel miniature2;
    private javax.swing.JPanel miniature3;
    private javax.swing.JPanel miniature4;
    private javax.swing.JPanel miniature5;
    private javax.swing.JPanel miniature6;
    private javax.swing.JLabel minimize;
    private javax.swing.JLabel open;
    private javax.swing.JLabel save;
    private javax.swing.JLabel title;
    private javax.swing.JPanel titleBar;
    private javax.swing.JPanel viewPanel;
    private javax.swing.JPanel windowPanel;
    // End of variables declaration//GEN-END:variables
}
