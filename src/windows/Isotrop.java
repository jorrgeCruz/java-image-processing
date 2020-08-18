/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.*;
import java.awt.event.*;

public class Isotrop extends Frame
{  public static void main(String[] args){new Isotrop();}

   Isotrop()
   { super("Isotropic mapping mode");
     addWindowListener(new WindowAdapter()
        {public void windowClosing(WindowEvent e){System.exit(0);}});
     setSize (400, 300);
     CvIsotrop ven = new CvIsotrop();
     //vent.start();
     add("Center", ven);
     setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
     show();
   }
}

class CvIsotrop extends Canvas implements Runnable
{ int centerX, centerY;
 float pixelSize, rWidth = 20.0F, rHeight = 20.0F, xP = 1000000, yP;
 Punto2D pieza1[] = new Punto2D[6];
 Punto2D pieza2[] = new Punto2D[8];
 Punto2D pieza3[] = new Punto2D[5];
 
 

  CvIsotrop()
  { addMouseListener(new MouseAdapter()
    {  public void mousePressed(MouseEvent evt)
       {  xP = fx(evt.getX()); yP = fy(evt.getY());
          repaint();
       }
    });
  }

   public void run()  
   {
      while(true)
      {
         repaint();
         System.out.println("salida ");
         try{
         Thread.sleep(500);
         }
         catch(InterruptedException e){}
      }
   }
void initgr()
{  Dimension d = getSize();
   int maxX = d.width - 1, maxY = d.height - 1;
   pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
   centerX = maxX/2; centerY = maxY/2;
   pieza1[0]= new Punto2D(-2f, 0f);
   pieza1[1]= new Punto2D(-1f, -2f);
   pieza1[2]= new Punto2D(1f, -2f);
   pieza1[4]= new Punto2D(-2f, 0f);
 /*pieza1[0].y=0f;
 pieza1[1].x=-1f;
 pieza1[1].y=-2f;
 pieza1[2].x=1f;
 pieza1[2].y=-2f;
 pieza1[3].x=2f;
 pieza1[3].y=0f;
 pieza1[4].x=1f;
 pieza1[4].y=3f;
 pieza1[5].x=-1f;
 pieza1[5].y=3f;*/
}

  int iX(float x){return Math.round(centerX + x/pixelSize);}
  int iY(float y){return Math.round(centerY - y/pixelSize);}
  float fx(int x){return (x - centerX) * pixelSize;}
  float fy(int y){return (centerY - y) * pixelSize;}

  public void paint(Graphics g)
  { initgr();
    int left = iX(-rWidth/2), right = iX(rWidth/2),
        bottom = iY(-rHeight/2), top = iY(rHeight/2),
        xMiddle = iX (0), yMiddle = iY (0);
        
     
        
    /*g.drawLine(iX(-5f), iY(0f), iX(5f), iY(0f));
    g.drawLine(iX(0f), iY(5f), iX(0f), iY(-5f));
    
    for(int x = -5 ; x < 6; x++)
    {
      g.drawLine(iX(x), iY(5f), iX(x), iY(-5f));
      g.drawLine(iX(0), iY(x), iX(-0.2f), iY(x));
      g.drawString(""+x, iX(x), iY(-0.5f));

     
    }
     g.drawString("X", iX(4.5f), iY(-0.5f));
     
     for(int x = -25 ; x < 26; x++)
    {
           g.drawLine(iX(x*0.2f), iY(f(x*0.2f)), iX((x+1)*0.2f), iY(f((x+1)*0.2f)));
     }
    /*g.drawLine(iX(1f), iY(0.3f), iX(1f), iY(0.6f));
    g.drawLine(iX(1f), iY(0.6f), iX(0.6f), iY(1f));
    g.drawLine(iX(0.6f), iY(1f), iX(0.3f), iY(1f));
    g.drawLine(iX(0.3f), iY(1f), iX(0f), iY(0.6f));
    g.drawLine(iX(0f), iY(0.6f), iX(0f), iY(0.3f));
    g.drawLine(iX(0f), iY(0.3f), iX(0.3f), iY(0f));
    g.drawLine(iX(1f), iY(1f), iX(1f), iY(0f));*/
      
  }
  
  public void dibujaPol(Graphics g, Punto2D[] pol)
  {   
      int j= pol.length;
      
  }
  
}
class Punto2D
{
   public float x, y;
   public Punto2D(float cx, float cy)
   {
      x = cx;
      y = cy;
   }
   
}