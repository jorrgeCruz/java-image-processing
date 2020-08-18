package windows;

import static com.sun.awt.AWTUtilities.setWindowOpacity;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;

/**
 *
 * @author Jesús
 */
public class Filters extends javax.swing.JFrame {

    private int xW, yW;
    float[][] filter, filterOld, fr, fro, fg, fgo, fb, fbo;
    float[][][] filterRGB, fil3d2;
    int opType = 0;
    final byte EFEC = 0, ARIT = 1, MAT = 2, COMP = 3, FILT = 4, SINT = 5, GEOM = 6, BINARIO = 7, APLIC = 8, PRIM = 9;
    String[] efectos = new String[]{"Grises", "Falso Color", "Umbral","UmbralMaxMin", "Negativo", "Ecualizada", "EspejoenX", "EspejoenY", "Rota180", "Rota90Der", "Rota90Izq"};
    String[] aritmeticas = new String[]{"Suma esc", "Resta esc", "Multiplicacion esc", "Division esc", "Modulo esc"};
    String[] mathematics = new String[]{"Potencia", "Tangente", "Seno", "Coseno", "Abs"};
    String[] compuestas = new String[]{"Suma", "Resta", "Multiplicacion", "Division", "And IMG", "Or IMG", "MArca Agua", "Marca Agua Escalamiento"};
    String[] filtros = new String[]{"Uniforme", "Gaussiano", "Flat Fielding", "DerX", "DerY", "Gradiente", "bordes"};
    String[] sinteticas = new String[]{"Rampa x", "Rampa y", "Ruido Uniforme", "Ruido Uniforme Rango", "Impulso"};
    String[] geometricas = new String[]{"Escala red", "escalaXY inter", "Rotar", "DeformarX", "DeformaY", "Afin", "Ajuste", "Ajuste4"};
    String[] binario = new String
            []{"Erosion", "Dilatacion", "Apertura", "Cierre", "Bordes", "Dithering", "Etiquetado"};
    String[] aplicacion = new String[]{"Primer filtro", "Segundo"};
    String[] primerosPasos = new String[]{"Rojos del 100-120", "potencia al cuadrado"};
    public int coor_a[];
    public int coor_r[];
    boolean pasasteFalsoColor = false;
    int contclick = 0;
    float puntos[];
    boolean change = false;

    private final Interface intFace;

    public Filters(Interface principal) {
        initComponents();
        puntos = new float[8];
        setWindowOpacity(this, 0.95f);
        intFace = principal;
        panels.setSelectedIndex(intFace.selected);
        jSliderR.setVisible(false);
        jSliderG.setVisible(false);
        jSliderB.setVisible(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
                //paintFilter();
                //intFace.filterArr[intFace.selected] = LibImg.convierteArr(intFace.bffImg[intFace.selected]);
            }
        });
    }

    public void setCoordenadas(int r[], int a[]) {
        coor_r = r;
        coor_a = a;
    }

    private void paintFilter() {
        preview.removeAll();
        pasasteFalsoColor = false;
        jSliderR.setVisible(false);
        jSliderG.setVisible(false);
        jSliderB.setVisible(false);
        change = false;
        switch (opType) {
            case EFEC:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        filterRGB = MathImg.convertirAGris(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    case 1:
                        if(!jSliderR.isVisible())
                        {
                            jSliderR.setVisible(true);
                            jSliderG.setVisible(true);
                            jSliderB.setVisible(true);
                        }
                        pasasteFalsoColor = true;
                        filterRGB = MathImg.convertirFalsoColor(intFace.filterArr2[intFace.selected], jSliderR.getValue()/100f, jSliderG.getValue()/100f,jSliderB.getValue()/100f);
                        
                        System.out.println("factor" + (jSliderR.getValue()/100f));
                        //filterRGB = MathImg.umbralColor(intFace.filterArr2[intFace.selected], umbral.getValue());
                        stateComponents(true);
                        break;
                    case 2:
                        filterRGB = MathImg.umbralColor(intFace.filterArr2[intFace.selected], umbral.getValue());
                        stateComponents(true);
                        break;
                    case 3:
                        filterRGB = MathImg.umbralDosrangos(intFace.filterArr2[intFace.selected], umbral.getValue(), umbral1.getValue());
                        stateComponents(true);
                        break;
                    case 4:
                        filterRGB = MathImg.negativoColor(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    case 5:
                        int h[][] = MathImg.histColor(intFace.bffImg[intFace.selected]);
                        int hA[][] = MathImg.histAcumColor(h);
                        int hNorm[][] = MathImg.histNormColor(hA);
                        filterRGB = MathImg.ecualizaImagenesColor(intFace.filterArr2[intFace.selected], hNorm);
                        stateComponents(false);
                        break;
                    case 6:
                        filterRGB = MathImg.espejoXColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 7:
                        filterRGB = MathImg.espejoYColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 8:
                        filterRGB = MathImg.giro180Color(intFace.filterArr2[intFace.selected]);
                        break;
                    case 9:
                        filterRGB = MathImg.giro90DerColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 10:
                        filterRGB = MathImg.giro90IzqColor(intFace.filterArr2[intFace.selected]);
                        break;
                    default:
                        return;
                }
                break;
            case ARIT:
                setTf();
                switch (filters.getSelectedIndex() + 1) {
                    case 1:
                        filterRGB = MathImg.sumaEscalarColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    case 2:
                        filterRGB = MathImg.restaEscalarColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    case 3:
                        filterRGB = MathImg.multiplicacionEscalarColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    case 4:
                        filterRGB = MathImg.divisionEscalarColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    case 5:
                        filterRGB = MathImg.moduloEscalarColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    default:
                        return;
                }
                break;
            case MAT:
                setTf();
                switch (filters.getSelectedIndex() + 1) {
                    case 1:
                        filterRGB = MathImg.potenciaColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        break;
                    case 2:
                        filterRGB = MathImg.tanColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 3:
                        filterRGB = MathImg.sinColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 4:
                        filterRGB = MathImg.cosColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 5:
                        filterRGB = MathImg.absColor(intFace.filterArr2[intFace.selected]);
                        break;
                    default:
                        return;
                }
                
                break;
            case COMP:
                if (intFace.filterArr2[intFace.selected] == null) {
                    System.out.println("sin memoria" + intFace.selected);
                } else if (intFace.filterArr2[panels.getSelectedIndex()] == null) {
                    System.out.println("sin memoria selector" + panels.getSelectedIndex());
                } else {
                    switch (filters.getSelectedIndex() + 1) {
                        case 1:
                            filterRGB = MathImg.sumaImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 2:
                            filterRGB = MathImg.restaImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 3:
                            filterRGB = MathImg.multiplicacionImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 4:
                            filterRGB = MathImg.divisionImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 5:
                            filterRGB = MathImg.andImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 6:
                            filterRGB = MathImg.orImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()]);
                            break;
                        case 7:
                            filterRGB = MathImg.marcaAguaImagenesColor(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()], Float.parseFloat(tfValor.getText()));
                            break;
                        case 8:
                            filterRGB = MathImg.marcaAguaImagenesColorEsc(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()], Float.parseFloat(tfValor.getText()));
                            break;  
                          
                        default:
                            return;
                    }
                }
                //intFace.filterArr[panels.getSelectedIndex()] = filter;
                break;
            case FILT:
                switch (filters.getSelectedIndex()) {

                    case 0:
                        float k[][] = MathImg.kernelUniforme(Integer.parseInt(tfValor.getText()));
                        filterRGB = MathImg.convolucionColor(intFace.filterArr2[intFace.selected], k);
                        stateComponents(false);
                        break;
                    case 1:

                        //umbral.setMaximum(10);
                        //umbral.setValue(1);
                        float kGauss[][] = MathImg.kernelUGaussiano(Integer.parseInt(tfValor.getText()), 3);
                        filterRGB = MathImg.convolucionColor(intFace.filterArr2[intFace.selected], kGauss);
                        stateComponents(true);
                        break;
                    case 2:
                        float kernel[][] =      { {1,1,1,1,1},
                                                { 1,1,1,1,1},
                                                { 1,1,1,1,1},
                                                { 1,1,1,1,1},
                                                { 1,1,1,1,1}};
                        filterRGB = MathImg.convolucionColor(intFace.filterArr2[intFace.selected], kernel);
                        stateComponents(false);

                        break;
                    case 3:
                        filterRGB = MathImg.derXColor(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    case 4:
                        filterRGB = MathImg.derYColor(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    case 5:
                        filterRGB = MathImg.gradienteColor(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    case 6:
                        filterRGB = MathImg.bordesColor(intFace.filterArr2[intFace.selected]);
                        stateComponents(false);
                        break;
                    default:
                        return;
                }
                break;
            case SINT:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        filterRGB = MathImg.rampaXColor(Integer.parseInt(tfValor.getText()));
                        break;
                    case 1:
                        filterRGB = MathImg.rampaYColor(Integer.parseInt(tfValor.getText()));
                        break;
                    case 2:
                        filterRGB = MathImg.ruidoColor(200, 200);
                        break;
                    case 3:
                        filterRGB = MathImg.ruidoColorRango(200, 200, 0, 255);
                                
                        break;
                    case 4:    
                        filterRGB = MathImg.impulsoColor(100, 50, 50);
                        break;

                    default:
                        return;
                }
                break;
            case GEOM:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        filterRGB = MathImg.escalaXYColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        stateComponents(false);
                        break;
                    case 1:
                        filterRGB = MathImg.escalaXYColorInterp(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()),
                                Float.parseFloat(jTFEscala2.getText()));
                        stateComponents(true);
                        break;
                    case 2:
                        filterRGB = MathImg.rotaColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        stateComponents(false);
                        break;
                    case 3:
                        filterRGB = MathImg.defXColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        stateComponents(false);
                        break;
                    case 4:
                        filterRGB = MathImg.defYColor(intFace.filterArr2[intFace.selected], Float.parseFloat(tfValor.getText()));
                        stateComponents(false);
                        break;
                    case 5:
                        filterRGB = MathImg.tAfinColor(intFace.filterArr2[intFace.selected], 50,50, 350,0, 0,250);
                                //intFace.filterArr[panels.getSelectedIndex()],
                                //coor_a, //[0], coor_a[1], coor_a[2], coor_a[3], coor_a[4], coor_a[5],
                                //coor_r); //[0], coor_r[1], coor_r[2], coor_r[3], coor_r[4], coor_r[5]); stateComponents(false);
                        break;
                        
                    case 6:
                        filterRGB = MathImg.tAfinColorFondo(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()],
                                intFace.coor_r[0],intFace.coor_r[1],intFace.coor_r[2],intFace.coor_r[3],intFace.coor_r[4],intFace.coor_r[5]);
                        stateComponents(false);
                        break;
                    case 7:
                        
                        filterRGB = MathImg.tBilinealColorFondo(intFace.filterArr2[intFace.selected], intFace.filterArr2[panels.getSelectedIndex()],
                                                                intFace.coor_r ); //stateComponents(false);
                         
                        break;
                    default:
                        return;
                }
                break;
            case BINARIO:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        filterRGB = MathImg.erosionColor(intFace.filterArr2[intFace.selected], jCB8Vec.isSelected());
                        break;
                    case 1:
                        filterRGB = MathImg.dilatacionColor(intFace.filterArr2[intFace.selected], jCB8Vec.isSelected());
                        break;
                    case 2:
                        filterRGB = MathImg.aperturaColor(intFace.filterArr2[intFace.selected], jCB8Vec.isSelected());
                        break;
                    case 3:
                        filterRGB = MathImg.cierreColor(intFace.filterArr2[intFace.selected], jCB8Vec.isSelected());
                        break;
                    case 4:
                        filterRGB = MathImg.bordesBinColor(intFace.filterArr2[intFace.selected], jCB8Vec.isSelected());
                        break;
                    case 5:
                        filterRGB = MathImg.dithColor(intFace.filterArr2[intFace.selected]);
                        break;
                    case 6:
                        filterRGB = MathImg.etiquetadoColor(intFace.filterArr2[intFace.selected]);
                        break;

                    default:
                        return;
                }
                break;
            case APLIC:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        change = true;
                       // filter3D = LibImg.primerFiltro(intFace.bffImg[intFace.selected], 200);//umbral.getValue()); stateComponents(true);
                        /*    break;
                    case 1:  */
                        //filter3D = LibImg.segundoFiltro(filter3D, intFace.bffImg[intFace.selected]);
                        break;
                }
            case PRIM:
                switch (filters.getSelectedIndex()) {
                    case 0:
                        filter = LibImg.convierteArr(LibImg.manipularojos(intFace.bffImg[intFace.selected]));
                        break;
                    case 1:
                        filter = LibImg.potenciaImg(intFace.filterArr[intFace.selected], 2);

                }

        }
        if (change) {
            filter = filterRGB[0];
        }
        //intFace.filterArr[intFace.selected] = filter;

        //modificar para imagenes 2d y 3d
        filterOld = filter;
        if (change) {
            
            preview.add(new IPanel(LibImg.convierteImgCol(filterRGB)));
        } else {
            if(pasasteFalsoColor)
                preview.add(new IPanel(MathImg.convierteDeArregloAImagensinReescalar(filterRGB)));
            else
                preview.add(new IPanel(MathImg.convierteDeArregloAImagen(filterRGB)));
        }
        preview.repaint();
    }

    private void stateComponents(boolean value) {
        umbral.setEnabled(value);
        umbral1.setEnabled(value);
    }

    private void setTf() {
        labelUS.setText("Valor: ");

    }

    public void setOperations(String[] op) {
        filters.setModel(new javax.swing.DefaultComboBoxModel<>(op));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        framePanel = new javax.swing.JPanel();
        labelSF = new javax.swing.JLabel();
        filters = new javax.swing.JComboBox<>();
        labelSP = new javax.swing.JLabel();
        panels = new javax.swing.JComboBox<>();
        accept = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        preview = new javax.swing.JPanel();
        controls = new javax.swing.JPanel();
        labelUS = new javax.swing.JLabel();
        umbral = new javax.swing.JSlider();
        tfValor = new javax.swing.JTextField();
        jCB8Vec = new javax.swing.JCheckBox();
        umbral1 = new javax.swing.JSlider();
        jSliderR = new javax.swing.JSlider();
        jSliderG = new javax.swing.JSlider();
        jSliderB = new javax.swing.JSlider();
        jTFEscala2 = new javax.swing.JTextField();
        jCBoperations = new javax.swing.JComboBox<>();
        jLback = new javax.swing.JLabel();
        jLfoward = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        framePanel.setBackground(new java.awt.Color(0, 0, 0));
        framePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), new java.awt.Color(0, 153, 204)));
        framePanel.setForeground(new java.awt.Color(255, 255, 255));
        framePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                framePanelMouseDragged(evt);
            }
        });
        framePanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                framePanelFocusGained(evt);
            }
        });
        framePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                framePanelMousePressed(evt);
            }
        });
        framePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                framePanelComponentShown(evt);
            }
        });
        framePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                framePanelPropertyChange(evt);
            }
        });

        labelSF.setForeground(new java.awt.Color(0, 153, 204));
        labelSF.setText("Seleccione un Filtro:");

        filters.setForeground(new java.awt.Color(0, 102, 153));
        filters.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grises", "Falso Color", "Umbral", "UmbralMaxMin", "Negativo", "Ecualizada", "Espejo En X", "Espejo En Y", "Rota180", "Rota90Der", "Rota90Izq" }));
        filters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtersActionPerformed(evt);
            }
        });

        labelSP.setForeground(new java.awt.Color(0, 153, 204));
        labelSP.setText("Seleccione donde colocar la imagen resulado:");

        panels.setForeground(new java.awt.Color(0, 102, 153));
        panels.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Panel 1", "Panel 2", "Panel 3", "Panel 4", "Panel 5", "Panel 6" }));
        panels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelsActionPerformed(evt);
            }
        });

        accept.setForeground(new java.awt.Color(0, 102, 153));
        accept.setText("Aceptar");
        accept.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accept.setDoubleBuffered(true);
        accept.setFocusCycleRoot(true);
        accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptActionPerformed(evt);
            }
        });

        cancel.setForeground(new java.awt.Color(0, 102, 153));
        cancel.setText("Cancelar");
        cancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancel.setDoubleBuffered(true);
        cancel.setFocusCycleRoot(true);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        preview.setBackground(new java.awt.Color(0, 0, 0));
        preview.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), new java.awt.Color(0, 102, 153)));
        preview.setForeground(new java.awt.Color(255, 255, 255));
        preview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previewMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                previewMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout previewLayout = new javax.swing.GroupLayout(preview);
        preview.setLayout(previewLayout);
        previewLayout.setHorizontalGroup(
            previewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        previewLayout.setVerticalGroup(
            previewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        controls.setBackground(new java.awt.Color(0, 0, 0));
        controls.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), new java.awt.Color(0, 102, 153)));
        controls.setForeground(new java.awt.Color(255, 255, 255));

        labelUS.setBackground(new java.awt.Color(0, 0, 0));
        labelUS.setForeground(new java.awt.Color(0, 153, 204));
        labelUS.setText("Umbral:");

        umbral.setBackground(new java.awt.Color(0, 0, 0));
        umbral.setMajorTickSpacing(1);
        umbral.setMaximum(255);
        umbral.setToolTipText("");
        umbral.setValue(100);
        umbral.setEnabled(false);
        umbral.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                umbralStateChanged(evt);
            }
        });

        tfValor.setText("2");

        jCB8Vec.setForeground(new java.awt.Color(0, 255, 255));
        jCB8Vec.setText("8 Vecinos");

        umbral1.setBackground(new java.awt.Color(0, 0, 0));
        umbral1.setMajorTickSpacing(1);
        umbral1.setMaximum(255);
        umbral1.setToolTipText("");
        umbral1.setValue(127);
        umbral1.setEnabled(false);
        umbral1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                umbral1StateChanged(evt);
            }
        });

        jSliderR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderRStateChanged(evt);
            }
        });

        jSliderG.setValue(100);
        jSliderG.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderGStateChanged(evt);
            }
        });

        jSliderB.setValue(0);
        jSliderB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderBStateChanged(evt);
            }
        });

        jTFEscala2.setText("2");
        jTFEscala2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFEscala2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlsLayout = new javax.swing.GroupLayout(controls);
        controls.setLayout(controlsLayout);
        controlsLayout.setHorizontalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsLayout.createSequentialGroup()
                .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSliderG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(controlsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlsLayout.createSequentialGroup()
                                .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSliderR, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(umbral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(controlsLayout.createSequentialGroup()
                                        .addComponent(labelUS)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTFEscala2)
                                            .addComponent(tfValor, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCB8Vec)))
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addComponent(umbral1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))))
                .addContainerGap())
        );
        controlsLayout.setVerticalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUS)
                    .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCB8Vec))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFEscala2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(umbral1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(umbral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSliderR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSliderG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCBoperations.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectos", "Aritmeticas", "Matematicas", "Compuestas", "Filtros", "Sinteticas", "Geometricas", "Analisis Binario", "Aplicacion", "Primeros Pasos" }));
        jCBoperations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBoperationsActionPerformed(evt);
            }
        });

        jLback.setForeground(new java.awt.Color(153, 0, 0));
        jLback.setText("<");
        jLback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbackMouseClicked(evt);
            }
        });

        jLfoward.setForeground(new java.awt.Color(204, 0, 0));
        jLfoward.setText(">");
        jLfoward.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLfowardMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout framePanelLayout = new javax.swing.GroupLayout(framePanel);
        framePanel.setLayout(framePanelLayout);
        framePanelLayout.setHorizontalGroup(
            framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(framePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(framePanelLayout.createSequentialGroup()
                        .addComponent(labelSF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBoperations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(filters, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(framePanelLayout.createSequentialGroup()
                        .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(framePanelLayout.createSequentialGroup()
                                .addComponent(labelSP)
                                .addGap(98, 98, 98)
                                .addComponent(panels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(framePanelLayout.createSequentialGroup()
                                .addComponent(jLback, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(preview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLfoward)
                                .addGap(12, 12, 12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(controls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(framePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(accept)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancel)))))
                .addContainerGap())
        );
        framePanelLayout.setVerticalGroup(
            framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(framePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSF)
                    .addComponent(filters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBoperations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(framePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(framePanelLayout.createSequentialGroup()
                                .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(preview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(controls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, framePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLback)
                                .addGap(158, 158, 158))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, framePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLfoward)
                        .addGap(154, 154, 154)))
                .addGroup(framePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accept)
                    .addComponent(cancel)
                    .addComponent(panels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSP))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(framePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(framePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void acceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptActionPerformed
        //intFace.filterArr[intFace.selected] = filter;
        intFace.filterArr2[panels.getSelectedIndex()] = filterRGB;
        intFace.setSettings();
        intFace.setEnabled(true);
        //this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_acceptActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        intFace.setEnabled(true);
        //this.dispose();
        this.setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

    private void framePanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_framePanelMousePressed
        xW = evt.getX();
        yW = evt.getY();
    }//GEN-LAST:event_framePanelMousePressed

    private void framePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_framePanelMouseDragged
        Point move = MouseInfo.getPointerInfo().getLocation();
        setLocation(move.x - xW, move.y - yW);
    }//GEN-LAST:event_framePanelMouseDragged

    private void filtersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtersActionPerformed
        paintFilter();
        contclick = 0;
    }//GEN-LAST:event_filtersActionPerformed

    private void umbralStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_umbralStateChanged
        paintFilter();
    }//GEN-LAST:event_umbralStateChanged

    private void panelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panelsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelsActionPerformed

    private void jCBoperationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBoperationsActionPerformed
        // TODO add your handling code here:
        switch (jCBoperations.getSelectedIndex()) {
            case 0:
                setOperations(efectos);
                opType = EFEC;
                break;
            case 1:
                setOperations(aritmeticas);
                opType = ARIT;
                break;
            case 2:
                setOperations(mathematics);
                opType = MAT;
                break;
            case 3:
                setOperations(compuestas);
                opType = COMP;
                break;
            case 4:
                setOperations(filtros);
                opType = FILT;
                break;
            case 5:
                setOperations(sinteticas);
                opType = SINT;
                break;
            case 6:
                setOperations(geometricas);
                opType = GEOM;
                break;
            case 7:
                setOperations(binario);
                opType = BINARIO;
                break;
            case 8:
                setOperations(aplicacion);
                opType = APLIC;
                break;
            case 9:
                setOperations(primerosPasos);
                opType = PRIM;
            default:
        }
    }//GEN-LAST:event_jCBoperationsActionPerformed

    private void framePanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_framePanelComponentShown
        // TODO add your handling code here:

    }//GEN-LAST:event_framePanelComponentShown

    public void sop(String cad) {
        System.out.println(cad);
    }
    private void jLfowardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLfowardMouseClicked
        // TODO add your handling code here:
        if(isValidbffImgfow(intFace.selected+1))
        {
            intFace.selected++;
            panels.setSelectedIndex(intFace.selected);
            // preview.add(new IPanel(intFace.bffImg[intFace.selected]));
            preview.removeAll();
            preview.add(new IPanel(intFace.bffImg[intFace.selected]));
            preview.repaint();
            //jLfoward.setForeground(Color.black);
            checkArrows();
        }
        //preview.repaint();
        //paintFilter();
    }//GEN-LAST:event_jLfowardMouseClicked

    private void framePanelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_framePanelFocusGained
        // TODO add your handling code here:
        sop("focus gained ");
    }//GEN-LAST:event_framePanelFocusGained

    private boolean isValidbffImgbac(int n) {
        return (intFace.bffImg[n] != null);
    }

    private boolean isValidbffImgfow(int n) {
        return (intFace.bffImg[n] != null);
    }
    private void framePanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_framePanelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_framePanelPropertyChange

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        checkArrows();
        preview.removeAll();
        preview.add(new IPanel(intFace.bffImg[intFace.selected]));
        preview.repaint();
        sop("Componente show");
    }//GEN-LAST:event_formComponentShown

    private void checkArrows() {
        if ((intFace.selected+1)<6 && isValidbffImgfow(intFace.selected+1)) {
            //if (intFace.bffImg[intFace.selected + 1] != null) {
                jLfoward.setForeground(Color.red);
                //jLfoward.setVisible(true);
            //}
        }
        else
            jLfoward.setForeground(Color.black);
        if ((intFace.selected-1)>-1 && isValidbffImgbac(intFace.selected-1)) {
            //if (intFace.bffImg[intFace.selected - 1] != null) {
                //jLback.setForeground(Color.red);
                jLback.setForeground(Color.red);
           // }
        }
        else
            jLback.setForeground(Color.black);
    }
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
        //jLfoward.setForeground(Color.black);
        //jLback.setForeground(Color.black);
        //jLfoward.setVisible(false);
        //jLback.setVisible(false);
    }//GEN-LAST:event_formComponentHidden

    private void jLbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbackMouseClicked
        // TODO add your handling code here:
        if(isValidbffImgfow(intFace.selected-1))
        {
            intFace.selected--;
            panels.setSelectedIndex(intFace.selected);
            // preview.add(new IPanel(intFace.bffImg[intFace.selected]));
            preview.removeAll();
            preview.add(new IPanel(intFace.bffImg[intFace.selected]));
            preview.repaint();
            //jLfoward.setForeground(Color.black);
            checkArrows();
        }
        /*intFace.selected--;
        panels.setSelectedIndex(intFace.selected);
        //preview.add(new IPanel(intFace.bffImg[intFace.selected]));
        jLback.setForeground(Color.black);
        jLback.setVisible(false);
        checkArrows();
        //preview.repaint();
        paintFilter();*/
    }//GEN-LAST:event_jLbackMouseClicked

    private void previewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previewMouseClicked
        // TODO add your handling code here:
        float esc = Float.parseFloat(tfValor.getText());
        /*if(contclick ==0)
        {
            puntos[0]=evt.getX()/esc;
            puntos[1]=evt.getY()/esc;
        }
        if(contclick ==1)
        {
            puntos[2]=evt.getX()/esc;
            puntos[3]=evt.getY()/esc;
        }
        if(contclick ==2)
        {
            puntos[4]=evt.getX()/esc;
            puntos[5]=evt.getY()/esc;
        }*/
        guardaPar(evt, esc);
        contclick++;
    }//GEN-LAST:event_previewMouseClicked

    public void guardaPar(java.awt.event.MouseEvent evt, float esc) {
        puntos[contclick * 2] = evt.getX() / esc;
        puntos[contclick * 2 + 1] = evt.getY() / esc;
    }
    private void previewMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previewMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_previewMousePressed

    private void umbral1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_umbral1StateChanged
        // TODO add your handling code here:
        paintFilter();
    }//GEN-LAST:event_umbral1StateChanged

    private void jSliderRStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderRStateChanged
        // TODO add your handling code here:
        paintFilter();//filterRGB = MathImg.convertirFalsoColor(intFace.filterArr2[intFace.selected], jSliderR.getValue()/100f, jSliderG.getValue()/100f,jSliderB.getValue()/100f);
    }//GEN-LAST:event_jSliderRStateChanged

    private void jSliderBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderBStateChanged
        // TODO add your handling code here:
        paintFilter();//filterRGB = MathImg.convertirFalsoColor(intFace.filterArr2[intFace.selected], jSliderR.getValue()/100f, jSliderG.getValue()/100f,jSliderB.getValue()/100f);
    }//GEN-LAST:event_jSliderBStateChanged

    private void jSliderGStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderGStateChanged
        // TODO add your handling code here:
       paintFilter();// filterRGB = MathImg.convertirFalsoColor(intFace.filterArr2[intFace.selected], jSliderR.getValue()/100f, jSliderG.getValue()/100f,jSliderB.getValue()/100f);
    }//GEN-LAST:event_jSliderGStateChanged

    private void jTFEscala2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFEscala2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFEscala2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept;
    private javax.swing.JButton cancel;
    private javax.swing.JPanel controls;
    public javax.swing.JComboBox<String> filters;
    private javax.swing.JPanel framePanel;
    private javax.swing.JCheckBox jCB8Vec;
    private javax.swing.JComboBox<String> jCBoperations;
    private javax.swing.JLabel jLback;
    private javax.swing.JLabel jLfoward;
    private javax.swing.JSlider jSliderB;
    private javax.swing.JSlider jSliderG;
    private javax.swing.JSlider jSliderR;
    private javax.swing.JTextField jTFEscala2;
    private javax.swing.JLabel labelSF;
    private javax.swing.JLabel labelSP;
    private javax.swing.JLabel labelUS;
    public javax.swing.JComboBox<String> panels;
    private javax.swing.JPanel preview;
    private javax.swing.JTextField tfValor;
    public javax.swing.JSlider umbral;
    public javax.swing.JSlider umbral1;
    // End of variables declaration//GEN-END:variables
}
