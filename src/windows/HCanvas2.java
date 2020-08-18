
package windows;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Jesús
 */
public class HCanvas2 extends java.awt.Canvas{
    
   int maxX, maxY, xCenter, yCenter;
   float pixelSize, rWidth = 30.0F, rHeight = 10.0F;
   int centerX, centerY;
    //float pixelSize, rWidth = 30.0F, rHeight = 10.0F, xP = 1000000, yP;
    int hist[];

   
   HCanvas2(){setBackground(Color.black);}
   
   void initgr(){
      Dimension d = getSize();
      maxX = d.width - 1; maxY = d.height - 1;
      xCenter = maxX/10; yCenter = maxY *9/10;   
      pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
   }
   
   public void setHist(int h[])
   {
       hist=h;
   }
   
   public float[] ajustH()
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
   public void paint(Graphics g){
      initgr();
      g.setColor(Color.white);
      float nH[] = ajustH();
        /*int left = iX(-rWidth/2), right = iX(rWidth/2),
            bottom = iY(-rHeight/2), top = iY(rHeight/2),
            xMiddle = iX (0), yMiddle = iY (0);*/ 

        g.drawLine(iX(0f), iY(0f), iX(25f), iY(0f));
        g.drawLine(iX(0f), iY(0f), iX(0f), iY(10f));
        g.setColor(Color.red);
        for(int i = 0 ; i < 255; i++)
        {
            g.drawLine(iX(i/10f), iY(nH[i]), iX((i+1)/10f), iY(nH[i+1]));       
        }

         g.drawString(""+ nH[256], iX(-1f), iY(10));
         g.drawString("0", iX(0), iY(-1f));
         g.drawString("255", iX(25), iY(-1f));

      
      /*g.drawLine(iX(0),iY(0),iX(14),iY(0));
      g.drawLine(iX(0),iY(0),iX(0),iY(10));
      g.drawString("0",iX(-0.4f),iY(-0.4f));
      for(int i=1; i<=10; i++){
         g.drawLine(iX(0),iY(i),iX(-0.2f),iY(i));
         g.drawString(""+i,iX(-0.5f),iY(i));
      }
      String[] subjects = {"S.O.","I.S.","B.D.D.","T.A.L.F.","Ingles","A.A.","M.I."};
      int s = 0;
      for(int i=2; i<=14; i+=2){
         g.drawString(subjects[s],iX(i-1),iY(-0.4f));
         g.drawLine(iX(i),iY(0),iX(i),iY(-0.2f));
         s++;
      }
      int[] score = {9,9,9,10,8,8,9};
      s = 0;/*
      int m = 11;
      for(int i=2; i<=14; i+=2){
         if(score[s]>m){
            g.drawLine(iX(i-2),iY(m),iX(i-2),iY(score[s]));
         }
         g.drawLine(iX(i-2),iY(score[s]),iX(i),iY(score[s]));
         g.drawLine(iX(i),iY(score[s]),iX(i),iY(0));
         m = score[s];
         s++;
      }*/
      /*Color[] colors = {new Color(27,79,114),
                  new Color(33,97,140),new Color(40,116,166),
                  new Color(46,134,193),new Color(40,116,166),
                  new Color(33,97,140),new Color(27,79,114)};
      for(int i=2; i<=14; i+=2){
         g.setColor(colors[s]);
         int xValues[] = {iX(i-2),iX(i-2),iX(i),iX(i)};
         int yValues[] = {iY(0),iY(score[s]),iY(score[s]),iY(0)};
         g.fillPolygon( xValues, yValues,4);
         s++;
      }
      g.setColor(Color.white);
      g.drawLine(iX(0),iY(0),iX(0),iY(10));*/
   }
}