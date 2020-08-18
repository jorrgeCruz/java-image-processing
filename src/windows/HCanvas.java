
package windows;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import static windows.MathImg.R;


/**
 *
 * @author Jesús
 */
public class HCanvas extends java.awt.Canvas implements java.io.Serializable{
    private String titulo;
    private Color color;
    private static final int MARGEN_X=12;
    private static final int MARGEN_Y=8;
    private transient Vector actionListeners;
    boolean bPulsado=false;
   int maxX, maxY, xCenter, yCenter;
   float pixelSize, rWidth = 70.0F, rHeight = 20.0F;
   int centerX, centerY;
    //float pixelSize, rWidth = 30.0F, rHeight = 10.0F, xP = 1000000, yP;
    int hist[][];
    BufferedImage img;
    int nRen;
    boolean renCol;
    boolean his = true;

   
   public HCanvas()
   {
        setBackground(Color.black);
        try  
        {
            jbInit();
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }        
   }
   private void jbInit() throws Exception {
    setSize(60,40);
    this.titulo="Bean";
    color=Color.yellow;
    setFont(new Font("Dialog", Font.BOLD, 12));
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        this_mousePressed(e);
      }
      public void mouseReleased(MouseEvent e) {
        this_mouseReleased(e);
      }
    });
  }
   void initgr(){
      Dimension d = getSize();
      maxX = d.width - 1; maxY = d.height - 1;
      xCenter = maxX/10; yCenter = maxY *9/10;   
      pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
   }
   
   public void setHist(int h[][])
   {
       hist=h;
   }
   
    public void setImg(BufferedImage i)
   {
       img= i;
   }
    public void setNoren(int nr)
    {
        nRen = nr;
    }
    
    public void setRenCol(boolean re)
    {
        renCol = re;
    }
    
    public void setHisVF(boolean h)
    {
        his = h;
    }
   
   public float[] ajustH(int hist[])
  {
      float max = hist[0];
      float nH[] = new float[257];
      for(int i = 1 ; i < 256; i++)
      {
          max = Math.max(max, hist[i]);
      }
      nH[256]=max;
      for(int i = 0 ; i < 256; i++)
      {
          nH[i] = hist[i]*10f/max;
      }    
          
      return nH;    
  }

   int iX(float x){return Math.round(xCenter + x/pixelSize);}
   int iY(float y){return Math.round(yCenter - y/pixelSize);}
  
   @Override
   public synchronized void paint(Graphics g){
      initgr();
      g.setColor(Color.white);
      if(his)
      {
            float nH[] = ajustH(hist[0]);

            dibujaHist(g, nH, 0, 0, "canal R");
            nH = ajustH(hist[1]);
            dibujaHist(g, nH, 30, 0, "canal G");
            nH = ajustH(hist[2]);
            dibujaHist(g, nH, 0, 13, "canal B");
      }
      else
      {
          int RC[][] = renCol?MathImg.renglonColor(img, nRen):MathImg.columnaColor(img, nRen);
           g.setColor(Color.red);
          dibujaRenglonColumna(g, RC[0], 0, 0, "Rojo", renCol);
           g.setColor(Color.green);
          dibujaRenglonColumna(g, RC[1], 30, 0, "Verde", renCol);
           g.setColor(Color.blue);
          dibujaRenglonColumna(g, RC[2], 0, 13, "Azul", renCol);
      }
   }
   
   public void dibujaHist(Graphics g, float nH[], float ox, float oy, String cad)
   {
       g.drawLine(iX(ox), iY(oy), iX(ox+25f), iY(oy));
        g.drawLine(iX(ox), iY(oy), iX(ox), iY(oy+10f));
        g.setColor(Color.red);
        for(int i = 0 ; i < 255; i++)
        {
            g.drawLine(iX(ox+i/10f), iY(oy+nH[i]), iX(ox+(i+1)/10f), iY(oy+nH[i+1]));       
        }

         g.drawString(""+ nH[256], iX(ox-1f), iY(oy+10));
         g.drawString("0", iX(ox), iY(oy-1f));
         g.drawString(cad, iX(ox+2), iY(oy-2f));
         g.drawString("255", iX(ox+25), iY(oy-1f));
   }
   
   
   public void dibujaRenglonColumna(Graphics g, int nRC[], float ox, float oy, 
                                    String cad, boolean ren)
   {
       int lim = ren?  img.getWidth():img.getHeight();
       g.drawLine(iX(ox), iY(oy), iX(ox+25f), iY(oy));
        g.drawLine(iX(ox), iY(oy), iX(ox), iY(oy+10f));
       
        
        
        for(int i = 0 ; i < lim-1; i++)
        {  
            g.drawLine(iX(ox+i/10f), iY(oy+nRC[i]/25.6f), iX(ox+(i+1)/10f), iY(oy+nRC[i+1]/25.6f));       
        }

        /* g.drawString(""+ nH[256], iX(ox-1f), iY(oy+10));
         g.drawString("0", iX(ox), iY(oy-1f));
         g.drawString(cad, iX(ox+2), iY(oy-2f));
         g.drawString("255", iX(ox+25), iY(oy-1f));*/
   }
   
   
   public void setTitulo(String newTitulo) {
    titulo = newTitulo;
    Dimension d = getPreferredSize();
    setSize(d.width, d.height);
    invalidate();
  }
  public String getTitulo() {
    return titulo;
  }

  public void setColor(Color newColor) {
    color = newColor;
    repaint();
  }
  public Color getColor() {
    return color;
  }
  /*public synchronized void paint(Graphics g) {
    int ancho=getSize().width;
    int alto=getSize().height;

    g.setColor(color);
    g.fillRect(1, 1, ancho-2, alto-2);
    g.draw3DRect(0, 0, ancho-1, alto-1, false);

    g.setColor(getForeground());
    g.setFont(getFont());

    g.drawRect(2, 2, ancho-4, alto-4);
    FontMetrics fm = g.getFontMetrics();
    g.drawString(titulo, (ancho-fm.stringWidth(titulo))/2, (alto+fm.getMaxAscent()-fm.getMaxDescent())/2);
 }*/

   public Dimension getPreferredSize() {
    FontMetrics fm=getFontMetrics(getFont());
    return new Dimension(fm.stringWidth(titulo)+MARGEN_X, fm.getMaxAscent()+fm.getMaxDescent()+MARGEN_Y);
  }
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  void this_mousePressed(MouseEvent e) {
    bPulsado=true;
  }
  void this_mouseReleased(MouseEvent e) {
    if(bPulsado){
        bPulsado=false;
        ActionEvent ev=new ActionEvent(e.getSource(), e.getID(), titulo);
        fireActionPerformed(ev);
    }
  }

  public synchronized void removeActionListener(ActionListener l) {
    if (actionListeners != null && actionListeners.contains(l)) {
      Vector v = (Vector) actionListeners.clone();
      v.removeElement(l);
      actionListeners = v;
    }
  }
  public synchronized void addActionListener(ActionListener l) {
    Vector v = actionListeners == null ? new Vector(2) : (Vector) actionListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      actionListeners = v;
    }
  }

  protected void fireActionPerformed(ActionEvent e) {
    if (actionListeners != null) {
      Vector listeners = actionListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
        ((ActionListener) listeners.elementAt(i)).actionPerformed(e);
    }
  }
}