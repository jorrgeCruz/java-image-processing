/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package windows;

import java.awt.Color;
import java.awt.image.BufferedImage;
import Jama.*;

/**
 *
 * @author MACBOOK
 */
public class LibImg {
    
    public static float[][] potenciaImg(float[][] orig, float potencia)
    {
        float[][] sal = new float[orig.length][orig[0].length];
        
        for(int i=0; i<orig.length; i++)
         {
            for(int j=0; j<orig[0].length; j++)
            {
                sal[i][j] = (float)Math.pow(orig[i][j], (double)potencia);
            }
           
         }
        return sal;
    }
    
    
    
    public static BufferedImage manipularojos(BufferedImage img)
    {
        for(int i=100; i<120; i++)
         {
            for(int j=100; j<120; j++)
            {
                img.setRGB(j, i, 255<<16);
                
            }
         }
        return img;
    }
    
    
    public static float[][][] primerFiltro(BufferedImage img, int umbral)
    {
        int filas =  img.getHeight(), cols = img.getWidth(); 
        float [][][] arr= new float[3][filas][cols];
        float[][][] sal;
        int v, r,g,b;
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {          
               v = img.getRGB(j, i);
               r = (v>>16&255); g = (v>>8&255); b = (v&255);
               if(Math.abs(r-b)<10 && Math.abs(r-g)<10)
               {
                    arr[0][i][j] = r > umbral?  r:0; 
                    arr[1][i][j] = g > umbral?  g:0;//255:0;//
                    arr[2][i][j] = b > umbral?  b:0 ;  
               }
            }
         }
        
        //sal = segundoFiltro(arr, img); 
        return arr;
    }
    
    public static float[][][] segundoFiltro(float [][][] prev, BufferedImage img)
    {
        int filas =  img.getHeight(), cols = img.getWidth(); 
        float [][][] arr= new float[3][filas][cols];
        int v;
        arr[0] = LibImg.dilatacion(prev[0], 8);
        //arr[1] = LibImg.dilatacion(prev[1], 8);
        //arr[2] = LibImg.dilatacion(prev[2], 8);
        
        arr[0] = LibImg.cierre(arr[0], 8);
        //arr[0] = LibImg.dilatacion(arr[0], 8);
        //arr[2] = LibImg.dilatacion(arr[2], 8);
        
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {          
               v = img.getRGB(j, i);
               if(arr[0][i][j]==255)
               {
                    arr[0][i][j] = (v>>16&255); 
                    arr[1][i][j] = (v>>8&255);
                    arr[2][i][j] = (v&255);
               }
            }
        }
        
        return arr;       
    }
    
    public static float[][] convierteArr(BufferedImage img)
    {
        int filas =  img.getHeight(), cols = img.getWidth(); 
        float [][] arr= new float[filas][cols];
        int v;
        Color c;
        
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {          
               //c = new Color(img.getRGB(j, i));
               //arr[i][j]= (c.getRed() + c.getGreen() + c.getBlue())/3;
               v = img.getRGB(j, i);
               arr[i][j] = ((v>>16&255) + (v>>8&255) + (v&255)) / 3;              
            }
         }
        
        return arr;
    }
    public static float[][] convierteRangoDin (float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];
        float max=arr[0][0], min=max;
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                max = Math.max(max, arr[i][j]);
                min = Math.min(min, arr[i][j]);
            }
         }
         float denominador = 255/(max-min);
          for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                asal[i][j] = (arr[i][j]-min)*denominador;
            }
         }     
        return asal;     
    }
            
            
            
    public static BufferedImage convierteImg(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        BufferedImage img= new BufferedImage(  cols, filas, BufferedImage.TYPE_INT_RGB);
        float [][] asal=convierteRangoDin(arr);
        Color gr;
        //for(int k = 0 ; k<3; k++)
        int valor;
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {  
                //gr = new Color( (int)(asal[i][j]),(int)(asal[i][j]), (int)(asal[i][j]));
               valor =  (int)asal[i][j];
                valor = valor | valor<<8 | valor <<16;
               //gr = new Color( (int)(arr[i][j] *255 /32),(int)(arr[i][j]*255 /32), (int)(arr[i][j]*255 /32));
               img.setRGB(j, i, valor);//gr.getRGB());
            }
         }
        return img;
    }
    
    public static BufferedImage convierteImgCol(float [][][] asal)
    {
        int filas = asal[0].length, cols = asal[0][0].length;
        BufferedImage img= new BufferedImage(  cols, filas, BufferedImage.TYPE_INT_RGB);
        //float [][] asal=convierteRangoDin(arr);
        Color gr;
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                gr = new Color( (int)(asal[0][i][j]),(int)(asal[1][i][j]), (int)(asal[2][i][j]));
               //gr = new Color( (int)(arr[i][j] *255 /32),(int)(arr[i][j]*255 /32), (int)(arr[i][j]*255 /32));
               img.setRGB(j, i, gr.getRGB());
            }
         }
        return img;
    }
    
    public static int[] hist(BufferedImage img)
    {
        int hist[] = new int[256];
        int filas =  img.getHeight(), cols = img.getWidth();
        int v;
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                v = img.getRGB(j, i);
                hist [ (int)(((v>>16&255) + (v>>8&255) + (v&255)) / 3)]++; 
            }
         }
        return hist;
    }
    
    public static int[] histAcum(int h[])
    {
        int hist[] = new int[256];
     
        hist[0]=h[0];
         for(int i=1; i<h.length; i++)
         { 
                hist[i] = hist[i-1]+ h[i];           
         }
        return hist;
    }
    
    public static int[] histNorm(int h[])
    {
        int hist[] = new int[256];
        //int filas =  img.getHeight(), cols = img.getWidth();
        //int v;
        //hist[0]=h[0];
        float max = h[255];
       /* for(int i = 1 ; i < 256; i++)
      {
          max = Math.max(max, h[i]);
      }*/
      //nH[256]=max;
         for(int i=0; i<h.length; i++)
         { 
                hist[i] = (int)(h[i]/max*255f);           
         }
        return hist;
    }
    
    public static float[][] ecualiza(float [][] arr, int [] func)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        
     
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                asal[i][j] = func[(int)arr[i][j]];
            }
            
         }
         return asal;
    }
    
    public static float[][] umbral (float [][] arr, float umbral)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                /*if(arr[i][j]> umbral)
                        asal[i][j] = 255;
                else
                        asal[i][j] = 0;*/
                asal[i][j]=  arr[i][j]> umbral ? 1: 0;
            }
         }     
        return asal;     
    }
    
     public static float[][] convierte01 (float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
               
                        asal[i][j] = arr[i][j]/255;
               
            }
         }     
        return asal;     
    }
     
      public static float[][] raizCuadrada (float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {        
                        asal[i][j] = (float)Math.sqrt(arr[i][j]);              
            }
         }     
        return asal;     
    }
      
       public static float[][] potencia (float [][] arr, float pot)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.pow(arr[i][j],pot);               
            }
         }     
        return asal;     
    }
      
       
       public static float[][] sumaEsc(float [][] arr, float esc)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[i][j]+esc;               
            }
         }     
        return asal;     
    }
       public static float[][] restaEsc(float [][] arr, float esc)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[i][j]-esc;               
            }
         }     
        return asal;     
    }
    public static float[][] multiplicacionEsc(float [][] arr, float esc)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[i][j]*esc;               
            }
         }     
        return asal;     
    }
    public static float[][] divEsc(float [][] arr, float esc)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[i][j]/esc;               
            }
         }     
        return asal;     
    }
    public static float[][] seno(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.sin(arr[i][j]);               
            }
         }     
        return asal;     
    }
    public static float[][] coseno(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.cos(arr[i][j]);               
            }
         }     
        return asal;     
    }
    public static float[][] negativo(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = 255-arr[i][j];               
            }
         }     
        return asal;     
    }
    
    
    public static float[][] espejoX(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[i][filas-1-j];               
            }
         }     
        return asal;     
    }
    
    public static float[][] espejoY(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[cols-1-i][j];               
            }
         }     
        return asal;     
    }
    
    public static float[][] rota180(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = arr[cols-1-i][filas-1-j];               
            }
         }     
        return asal;     
    }
    
    public static float[][] rota90Izq(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [cols][filas];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[cols-1-j][filas-1-i] = arr[i][j];               
            }
         }     
        return espejoX(asal);     
            
    }
    
    public static float[][] rota90Der(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [cols][filas];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[j][i] = arr[i][j];               
            }
         }     
        return espejoX(asal);     
    }
        public static float[][] abs(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = Math.abs(arr[i][j]);               
            }
         }     
        return asal;     
    }
    
    public static float[][] marcaAgua(float [][] im1, float [][] im2, float porc)
    {
        int filas = im1.length;
        int cols = im1[0].length;
        int filch =  im2.length;
        int colch =  im2[0].length;
        float [][] asal= new float [filas][cols];
        int vecesfila = filas/filch;
        int vecescol = cols/colch;
        for (int fg = 0; fg<= vecesfila; fg++)
            for( int cg = 0; cg<=vecescol; cg++)
                for(int i=0; i<filch; i++)
                 {
                    for(int j=0; j<colch; j++)
                    {                 
                        if((filch*fg+i)<filas && (colch*cg  + j) < cols)  
                        asal[filch*fg + i][colch*cg  + j] = im1[filch*fg + i][colch*cg  + j] + porc*im2[i][j];               
                    }
                 }     
        return asal;
    }
    
    public static float[][] sumaImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = im1[i][j] + im2[i][j];               
            }
         }     
        return asal;
    }
    
    
     public static float[][] restaImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int minf = Math.min(im1.length, im2.length);
        int minc = Math.min(im1[0].length,im2[0].length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<minf; i++)
         {
            for(int j=0; j<minc; j++)
            {                 
                  asal[i][j] = im1[i][j] - im2[i][j];               
            }
         }     
        return asal;
    }
    
      public static float[][] multImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int minf = Math.min(im1.length, im2.length);
        int minc = Math.min(im1[0].length,im2[0].length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<minf; i++)
         {
            for(int j=0; j<minc; j++)
            {                 
                  asal[i][j] = im1[i][j] * im2[i][j];               
            }
         }     
        return asal;
    }
      
    public static float[][] divImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int minf = Math.min(im1.length, im2.length);
        int minc = Math.min(im1[0].length,im2[0].length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<minf; i++)
         {
            for(int j=0; j<minc; j++)
            {                 
                  asal[i][j] = im1[i][j] / (im2[i][j]+0.1f);               
            }
         }     
        return asal;
    }
    
     public static float[][] andImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                if(im1[i][j]>0 && im2[i][j] ==1)  
                    asal[i][j] = im1[i][j];   
                else
                    asal[i][j] = 0;
            }
         }     
        return asal;
    }
     
     public static float[][] orImagenes(float [][] im1, float [][] im2)
    {
        int minf = Math.min(im1.length, im2.length);
        int minc = Math.min(im1[0].length,im2[0].length);     
        float [][] asal= new float [minf][minc];
        for(int i=0; i<minf; i++)
         {
            for(int j=0; j<minc; j++)
            {                 
                if(im1[i][j]>0 || im2[i][j] ==1)  
                    asal[i][j] = im1[i][j];   
                else
                    asal[i][j] = 0;
            }
         }     
        return asal;
    }
     
    public static  float[][] rotarAng(float [][] im1, float ang)
    {
        int filas = (int)(im1.length), cols = (int)(im1[0].length);
        int xIzq = 0, yIzq= filas, xDer = cols, yDer=0;
        int xSup = 0, ySup = 0, xInf = cols, yInf = filas;
        int nX, nY;      
        float c = (float)Math.cos(Math.toRadians(ang)), s = (float)Math.sin(Math.toRadians(ang)),
         xP = cols/2, yP = filas/2,
         r11 = c, r12 = s,
         r21 = -s, r22 = c,
         r31 = -xP * c + yP * s + xP, r32 = -xP * s - yP * c + yP;
         int x1 = (int)(xIzq * r11 + yIzq * r21 + r31);   
         int x2 = (int)(xDer * r11 + yDer * r21 + r31);
         
         int y1 = (int)(xSup * r12 + ySup * r22 + r32);   
         int y2 = (int)(xInf * r12 + yInf * r22 + r32);
         
         int ancho = (int)Math.abs(x2-x1);
         int alto = (int)Math.abs(y2-y1);
         
        float [][] asal= new float [alto][ancho];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                nX = (int)(j * r11 + i * r21 + r31)-x1;
                nY = (int)(j * r12 + i * r22 + r32)-y1;
                asal[nY][nX] = im1[i][j];               
            }
        }      
        return asal ;    
    }
     
    public static  float[][] rotarAngInv(float [][] im1, float ang)
    {
        int filas = (int)(im1.length), cols = (int)(im1[0].length);
        int xIzq = 0, yIzq= filas, xDer = cols, yDer=0;
        int xSup = 0, ySup = 0, xInf = cols, yInf = filas;
        int nX, nY;      
        float c = (float)Math.cos(Math.toRadians(ang)), s = (float)Math.sin(Math.toRadians(ang)),
         xP = cols/2, yP = filas/2,
         r11 = c, r12 = s,
         r21 = -s, r22 = c,
         r31 = -xP * c + yP * s + xP, r32 = -xP * s - yP * c + yP;
         int x1 = (int)(xIzq * r11 + yIzq * r21 + r31);   
         int x2 = (int)(xDer * r11 + yDer * r21 + r31);
         
         int y1 = (int)(xSup * r12 + ySup * r22 + r32);   
         int y2 = (int)(xInf * r12 + yInf * r22 + r32);
         
         int ancho = (int)Math.abs(x2-x1);
         int alto = (int)Math.abs(y2-y1);
         
        float [][] asal= new float [alto][ancho];
        
        c = (float)Math.cos(Math.toRadians(-ang));
        s = (float)Math.sin(Math.toRadians(-ang));
         xP = ancho/2; yP = alto/2;
         r11 = c; r12 = s;
         r21 = -s; r22 = c;
         r31 = -xP * c + yP * s + xP;
         r32 = -xP * s - yP * c + yP;
        
        for(int i=0; i<alto; i++)
        {
            for(int j=0; j<ancho; j++)
            {    
                nX = (int)(j * r11 + i * r21 + r31)+x1;
                nY = (int)(j * r12 + i * r22 + r32)+y1;
                //if((nX>=0 && nX< cols ) && (nY>=0 && nY < filas))
                    asal[i][j] = im1[nY][nX];   
            } 
        }
        return asal;
    }
    
    public static  float[][] escalaXY(float [][] im1, float esc)
    {
        int filas = (int)(im1.length*esc), cols = (int)(im1[0].length*esc);
        
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = im1[(int)(i/esc)][(int)(j/esc)];               
            }
        }      
        return asal ;    
    }
    
    public static float interpolacion(float I[][], float y, float x)
    {
        float i1, i2, i3=0;
        int filas = I.length, cols = I[0].length;
        int i = (int) y, j = (int) x ;
        float deltax = x-j, deltay = y - i;
        if(j < cols-1 && i < filas-1)
        {
            i1 = (I[i][j+1]-I[i][j])*deltax + I[i][j];
            i2 = (I[i+1][j+1]-I[i+1][j])*deltax + I[i+1][j];
            i3 = (i2-i1)*deltay + i1;
        }
        return i3;
    }
    
    public static  float[][] escalaXYInterpolacion(float [][] im1, float esc)
    {
        int filas = (int)(im1.length*esc), cols = (int)(im1[0].length*esc);
        
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = interpolacion(im1, i/esc,j/esc);               
            }
        }      
        return asal ;    
    }
    
    public static  float[][] shearingX(float [][] im1, float a)
    {
        int filas = im1.length, cols = im1[0].length;
        int cols2 = (int)(im1[0].length +a*filas);
        float [][] asal= new float [filas][cols2];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][(int)(j+a*i)] = im1[i][j];
            }
        }      
        return asal ;    
    }
    
     public static  float[][] shearingY(float [][] im1, float b)
    {
        int filas = im1.length, cols = im1[0].length;
        int filas2 = (int)(im1.length +b*cols);
        float [][] asal= new float [filas2][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[(int)(i+b*j)][j] = im1[i][j];
            }
        }      
        return asal ;    
    }
    
    
     public static  float[][] tAfin(float [][] im1, float [][] im2,
                                    int x1a, int y1a, int x2a, int y2a, int x3a, int y3a,
                                    int x1r, int y1r, int x2r, int y2r, int x3r, int y3r)
    {
        int filas = im1.length, cols = im1[0].length;
        float [][] asal= new float [filas][cols];
        float cc[][] = new float[2][3];
        int nx, ny;
        float det = (x1a*y2a + x2a*y3a + x3a*y1a) - (x3a*y2a + x2a*y1a + x1a*y3a);
        float detc11 = (x1r*y2a + x2r*y3a + x3r*y1a) - (x3r*y2a + x2r*y1a + x1r*y3a);
        float detc12 = (x1a*x2r + x2a*x3r + x3a*x1r) - (x3a*x2r + x2a*x1r + x1a*x3r);
        float detc13 = (x1a*y2a*x3r + x2a*y3a*x1r + x3a*y1a*x2r) - (x3a*y2a*x1r + x2a*y1a*x3r + x1a*y3a*x2r);
        
        float detc21 = (y1r*y2a + y2r*y3a + y3r*y1a) - (y3r*y2a + y2r*y1a + y1r*y3a);
        float detc22 = (x1a*y2r + x2a*y3r + x3a*y1r) - (x3a*y2r + x2a*y1r + x1a*y3r);
        float detc23 = (x1a*y2a*y3r + x2a*y3a*y1r + x3a*y1a*y2r) - (x3a*y2a*y1r + x2a*y1a*y3r + x1a*y3a*y2r);
        
        cc[0][0] = detc11/det;
        cc[0][1] = detc12/det;
        cc[0][2] = detc13/det;
        cc[1][0] = detc21/det;
        cc[1][1] = detc22/det;
        cc[1][2] = detc23/det;
        
        for(int i=y1a; i<y3a; i++)
        {
            for(int j=x1a; j<x2a; j++)
            {                 
                nx = (int)calculaX(i, j, cc);
                ny = (int)calculaY(i, j, cc);
                //if((nx>=0 && < cols)&&(ny>=0 && ))
                {
                
                asal[ny][nx] = im1[i][j];
                }
            }
        }     
        return asal;
    }
        
     public static float calculaX(int i, int j, float cc[][])
     {
         return cc[0][0]*j * cc[0][1]*i + cc[0][0];
     }
     
     public static float calculaY(int i, int j, float cc[][])
     {
         return cc[1][0]*j * cc[1][1]*i + cc[1][0];
     }
    /*
    
    c = (float)Math.cos(Math.toRadians(-ang)); 
         s = (float)Math.sin(Math.toRadians(-ang));
         xP = ancho/2; yP = alto/2;
         r11 = c; r12 = s;
         r21 = -s; r22 = c;
         r31 = -xP * c + yP * s + xP; r32 = -xP * s - yP * c + yP;
        
         for(int i=0; i<alto; i++)
        {
            for(int j=0; j<ancho; j++)
            {                 
                nX = (int)(j * r11 + i * r21 + r31)+x1;
                nY = (int)(j * r12 + i * r22 + r32)+y1;
                if((nX>=0 && nX<= cols-1) && (nY>=0 && nY<= filas-1))
                    asal[i][j] = im1[nY][nX];               
            }
        }     
    */
    
       
    public static  float[][] zoomRedondeo(float [][] im1, float factor)
    {
        int filas = (int)(im1.length*factor), cols = (int)(im1[0].length*factor);
        float [][] asal= new float [filas][cols];
        
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = im1[(int)(i/factor)][(int)(j/factor)];               
            }
        }            
        return asal ;        
    }
    
    public static float interpolacionBilineal(float iy, float jx, float [][] I)
    {
        float val;
        int j = (int)Math.floor(jx), i=(int)Math.floor(iy);  
        float b = jx - j;
        float a = iy - i;
        
        
        if(i>=I.length-2) i=I.length-2;
        if(j>=I[0].length-2) j=I[0].length-2;
                val = (1-a)*(1-b)*I[i][j] + (1-b)*a*I[i][j+1] + b*(1-a)*I[i+1][j] + a*b*I[i+1][j+1];
        
        return val;
    }
    
    public static float[][] zoom(float [][] arr, float factor)
    {
        int filas = (int)(arr.length* factor), cols = (int)(arr[0].length* factor);
        //int fim = im1.length, cim = arr[0].length;
        float [][] asal= new float [filas][cols]; 
        float posi, posj;
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                 
                posi=(i/factor);
                posj=(j/factor);
                asal[i][j] = interpolacionBilineal(posi, posj, arr);               
            }
        }     
        return asal;  
    }
    public static float[][] rotar(float [][] arr, float angulo)
    {
        int xSI=0, xSD=arr[0].length, xII=0, xID=arr[0].length;
        int ySI=0, ySD=0, yII=arr.length, yID=arr.length;
        
        double xrSI, xrSD, xrII, xrID;
        double yrSI, yrSD, yrII, yrID;
        double cA=Math.cos(Math.toRadians(angulo)), sA=Math.sin(Math.toRadians(angulo));
        int filas = (int)(arr.length), cols = (int)(arr[0].length);
        double xP=filas/2, yP=cols/2;
        
        double r11 = cA, r12 = sA,
                r21 = -sA, r22 = cA,
                r31 = -xP * cA + yP * sA + xP, r32 = -xP * sA - yP * cA + yP;
        xrSI = xSI * r11 + ySI * r21 + r31;//xSI *  cA+ ySI * sA;
        yrSI = xSI * r12 + ySI * r22 + r32;//xSI * -sA + ySI * cA;
        
        xrSD = xSD * r11 + ySD * r21 + r31;//xSD * cA + ySD * sA;
        yrSD = xSD * r12 + ySD * r22 + r32;////xSD * -sA + ySD * cA;
        
        xrII = xII * r11 + yII * r21 + r31;//xII * cA + yII * sA;
        yrII = xII * r12 + yII * r22 + r32;//xII * -sA + yII * cA;
        
        xrID = xID * r11 + yID * r21 + r31;//xID * cA + yID * sA;
        yrID = xID * r12 + yID * r22 + r32;//xID * -sA + yID * cA;
        
        int ancho = (int)Math.abs(xrII - xrSD);
        int alto  = (int)Math.abs(yrSI - yrID);
        
        
        float [][] asal= new float [alto][ancho]; 
        double posi, posj;
        
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                
                posj = j * r11 + i * r21 + r31;
                posi = j * r12 + i * r22 + r32;
                posj+= Math.abs(xrII);
                posi+= Math.abs(yrSI);        
                //posj = j * Math.cos(Math.toRadians(angulo)) + i * Math.sin(Math.toRadians(angulo));
                //posi = j *-Math.sin(Math.toRadians(angulo)) + i * Math.cos(Math.toRadians(angulo));
                if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    asal[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return asal;  
    }
    
    
    public static float[][] deformarX(float [][] arr, float a)
    {     
        int ancho = (int)(arr[0].length+ arr.length*a);
        int alto  = arr.length;
        int filas = (arr.length), cols = (arr[0].length);
        float [][] asal= new float [alto][ancho]; 
        double posi, posj;
  
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                 
                posj = j + i*a;
                posi = i;      
               // if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    asal[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return asal;  
    }
    
    public static float[][] afin(float [][] arr, float xd0, float yd0, float xd1, float yd1, float xd2, float yd2)
    {
        int xSI=0, xSD=arr[0].length, xII=0;
        int ySI=0, ySD=0, yII=arr.length;
      
        int filas = arr.length, cols = arr[0].length;
         
        int ancho = (int)((xd1-xd0) + Math.abs(xd0-xd2))+1;
        int alto  = (int)((yd2-yd0) + Math.abs(yd0-yd1))+1;
        
        
        float [][] asal= new float [alto][ancho]; 
        double posi, posj;
        float coef[][] = matrizDeC(xd0, yd0, xd1, yd1, xd2, yd2, xSI,ySI, xSD, ySD, xII, yII );
        
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                
                posj = convierteX(i,j, coef);
                posi = convierteY(i,j, coef);
                     
                //if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    asal[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return asal;  
    }
    
    
    
    public static float[][] afinAjuste(float [][] orig,  float[][] arr, int c_a[], int c_r[])
    {
        /*int xSI=c_a[0], xSD=c_a[2], xII=c_a[4];
        int ySI=c_a[1], ySD=c_a[3], yII=c_a[5];*/
        int xSI=0, xSD=arr[0].length, xII=0;
        int ySI=0, ySD=0, yII=arr.length;
        float xd0=c_r[0],  yd0=c_r[1], xd1=c_r[2], yd1=c_r[3], xd2=c_r[4], yd2=c_r[5];
        int filas = arr.length, cols = arr[0].length;
         
        //int ancho = orig[0].length;
       // int alto  = orig.length;
          
        //float [][] asal= new float [alto][ancho]; 
        double posi, posj;
        float coef[][] = matrizDeC(xd0, yd0, xd1, yd1, xd2, yd2, xSI,ySI, xSD, ySD, xII, yII );
        
        for(int i=ySI; i<yII; i++)     
        {
            for(int j=xSI; j<xSD; j++)
            {                                
                posj = convierteX(i,j, coef);
                posi = convierteY(i,j, coef);
                     
                //if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    orig[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return orig;  
    }
    
    public static float[][] transfAjuste(float [][] orig, int puntos[], float[][] arr)
    {
        int x1a=0, x2a=arr[0].length, x4a=0,          x3a=arr[0].length;
        int y1a=0, y2a=0,             y4a=arr.length, y3a=arr.length;
        float xr1=puntos[0],  yr1=puntos[1], xr2=puntos[2], yr2=puntos[3], xr3=puntos[4], yr3=puntos[5], xr4 = puntos[6], yr4=puntos[7];
        int filas = arr.length, cols = arr[0].length;
         
        //int ancho = orig[0].length;
        //int alto  = orig.length;
        double [][] matA =      {{x1a, y1a, x1a*y1a, 1},
                               {x2a, y2a, x2a*y2a, 1},
                               {x3a, y3a, x3a*y3a, 1},
                               {x4a, y4a, x4a*y4a, 1}};
        Matrix A = new Matrix(matA);
        double vecX[] = {xr1, xr2, xr3, xr4};
        double vecY[] = {yr1, yr2, yr3, yr4};
        Matrix X = new Matrix(vecX, vecX.length);
        Matrix Y = new Matrix(vecY, vecY.length);
        Matrix SolX, SolY;
        
        SolX = A.solve(X);
        SolY = A.solve(Y);
        //float [][] asal= new float [alto][ancho]; 
        double posi, posj;
        //float coef[][] = matrizDeC(xd0, yd0, xd1, yd1, xd2, yd2, xSI,ySI, xSD, ySD, xII, yII );
        
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                
                posj = conX(i,j, SolX.getColumnPackedCopy());
                posi = conY(i,j, SolY.getColumnPackedCopy());                
                //if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    orig[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return orig;  
    }
    
  
    static float [][]  matrizDeC (float x1, float y1, float x2, float y2, float x3, float y3, 
			   float xa1, float ya1, float xa2, float ya2, float xa3, float ya3)
	{
		float[][] cs = new float[2][3];
		float det1;
		
		det1 = xa1*ya2 + ya1*xa3 + xa2*ya3 - (xa3*ya2 + xa1*ya3 + xa2*ya1);
		
		cs[0][0] =  x3*ya1 + x2*ya3 + x1*ya2 - (x1*ya3 + x2*ya1 + x3*ya2);
		cs[0][0] /= det1;
		cs[0][1] =  x3*xa2 + x2*xa1 + x1*xa3 - (x1*xa2 + x2*xa3 + x3*xa1);
		cs[0][1] /= det1;
		cs[0][2] =  x3*xa1*ya2 + x2*xa3*ya1 + x1*xa2*ya3 - (x1*ya2*xa3 + x2*ya3*xa1 + x3*ya1*xa2);
                cs[0][2] /= det1;
	
		cs[1][0] =  y3*ya1 + y2*ya3 + y1*ya2 - (y1*ya3 + y2*ya1 + y3*ya2);
		cs[1][0] /= det1;
		cs[1][1] =  y3*xa2 + y2*xa1 + y1*xa3 - (y1*xa2 + y2*xa3 + y3*xa1);
		cs[1][1] /= det1;
		cs[1][2] =  y3*xa1*ya2 + y2*xa3*ya1 + y1*xa2*ya3 - (y1*ya2*ya3 + y2*ya3*xa1 + y3*ya1*xa2);
		cs[1][2] /= det1;
		
		return cs;
	}
	
	static int convierteX(int i, int j, float cs[][])
	{
		return (int)(cs[0][0] * j + cs[0][1] * i + cs[0][2]);
	}
 	static int convierteY(int i, int j, float cs[][])
	{
		return  (int)(cs[1][0] * j + cs[1][1] * i + cs[1][2]);
	}
        
        static int conX(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
 	static int conY(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
        
        
 /* public static float[][] transformacionAjuste(float [][] orig, float puntos[], float[][] arr)
    {
        int x1a=0, x2a=arr[0].length, x4a=0, x3a=arr[0].length;
        int y1a=0, y2a=0, y4a=arr.length, y3a=arr.length;
        float xd0=puntos[0],  yd0=puntos[1], xd1=puntos[2], yd1=puntos[3], xd2=puntos[4], yd2=puntos[5], xd3=puntos[6], yd3=puntos[7];
        int filas = arr.length, cols = arr[0].length;
         
        int ancho = orig[0].length;
        int alto  = orig.length;
        double[][] matrix = {{x1a, y1a, x1a*y1a, 1},{x2a, y2a, x2a*y2a, 1},{x3a, y3a, x3a*y3a, 1},{x4a, y4a, x4a*y4a, 1}};
        double[] xar={xd0,xd1,xd2,xd3};
        double[] yar={yd0,yd1,yd2,yd3};
        Matrix A=new Matrix(matrix);
        Matrix X=new Matrix(xar, xar.length);
        Matrix Y=new Matrix(yar, yar.length);
        Matrix Bx = A.solve(X);
        Matrix By = A.solve(Y);
        
        double posi, posj;
        //float coef[][]=//matrizDeC(xd0, yd0, xd1, yd1, xd2, yd2, xSI,ySI, xSD, ySD, xII, yII );
        
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                
                posj = cX(i,j, Bx.getColumnPackedCopy());
                posi = cY(i,j, By.getColumnPackedCopy());
                     
                if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    orig[(int)posi][(int)posj] = arr[i][j];               
            }
        }     
        return orig;  
    }*/
        static int cX(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
 	static int cY(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
   public static float[][] ruidoUniforme(int t, int max)
    {
        int filas = t, cols = t;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<t; i++)
        {
            for(int j=0; j<t; j++)
                
            {
                asal[i][j] = (int)(Math.random()*max+0.5);
            }
        }
        return asal;
    }
        
    
    public static float[][] rampaX(int t)
    {
        int filas = t, cols = t;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<t; i++)
        {
            for(int j=0; j<t; j++)
            {                 
                  asal[i][j] = t-j;               
            }
        }     
        return asal;     
    }
    
    public static float[][] rampaY(int t)
    {
        int filas = t, cols = t;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<t; i++)
        {
            for(int j=0; j<t; j++)
            {                 
                  asal[i][j] = i;               
            }
        }     
        return asal;     
    }
    
    public static float[][] impulso(int t, int x)
    {
        int filas = t, cols = t;
        float [][] asal= new float [filas][cols];  
                       
                  asal[x][x] = 255;               
      
        return asal;     
    }
    
    public static float[][] erosion(float [][] arr, boolean vec8)
    {
        int filas = arr.length, cols = arr[0].length; 
        float [][] asal= new float [filas][cols];  
        int vecinos, valorComp=4;
        if(vec8) valorComp=8;
        
        for(int i=1; i<filas-1; i++)
        {
            for(int j=1; j<cols-1; j++)
            {                 
                vecinos = 0;
                vecinos += arr[i+1][j];
                vecinos += arr[i-1][j];
                vecinos += arr[i][j+1];
                vecinos += arr[i][j-1];
                if(vec8)
                {
                    vecinos += arr[i+1][j-1];
                    vecinos += arr[i+1][j+1];
                    vecinos += arr[i-1][j+1];
                    vecinos += arr[i-1][j-1];
                }
                if(arr[i][j]==1 && vecinos == valorComp)  
                    asal[i][j] = 1;  
            }
        }     
        return asal;     
    }
    
    public static float[][] dilatacion(float [][] arr, boolean vec8)
    {
        int filas = arr.length, cols = arr[0].length; 
        float [][] asal= new float [filas][cols];  
        int vecinos, valorComp=4;
        if(vec8) valorComp=8;
        
        for(int i=1; i<filas-1; i++)
        {
            for(int j=1; j<cols-1; j++)
            {                 
                vecinos = 0;
                vecinos += arr[i+1][j];
                vecinos += arr[i-1][j];
                vecinos += arr[i][j+1];
                vecinos += arr[i][j-1];
                if(vec8)
                {
                    vecinos += arr[i+1][j-1];
                    vecinos += arr[i+1][j+1];
                    vecinos += arr[i-1][j+1];
                    vecinos += arr[i-1][j-1];
                }
                if(arr[i][j]==1 || vecinos== valorComp)  
                    asal[i][j] = 1;  
            }
        }     
        return asal;     
    }
    
    public static float[][] apertura(float [][] arr, boolean vec8)
    {
        return dilatacion(erosion(arr, vec8), vec8);
    }
    
    public static float[][] cierre(float [][] arr, boolean vec8)
    {
        return erosion(dilatacion(arr, vec8), vec8);
    }
    
    public static float[][] dith(float [][] arr)
    {     
        int filas = (arr.length), cols = (arr[0].length);
        float [][] asal= new float [filas][cols]; 
        int valorpix, err =0, sig;
  
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                 
                valorpix = (int)arr[i][j] + err;
                //sig =1;
                if(valorpix >= 255) 
                {   
                    //valorpix=255;
                    asal[i][j] = 255; 
                    err=0;
                }
                else if(valorpix <= 0) 
                {   
                   // valorpix=0;
                    asal[i][j] = 0; 
                    err=0;
                }else if((255- valorpix) > 127 )
                {
                    asal[i][j] = 0; 
                    sig =1;
                    err = valorpix*sig;
                }
                else
                {
                    asal[i][j] = 255; 
                    sig =-1;
                    err = 255-valorpix;
                    err*=sig;
                }
            }
        }     
        return asal;  
    }
    
    public static float[][] convolucion(float [][] arr, float kernel[][])
    {     
        int filas = arr.length, cols = arr[0].length;       
        float [][] asal= new float [filas][cols];   
  
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                 
                asal[i][j] = (peso(arr, kernel, i,j));               
            }
        }     
        return asal;  
    }
    
    public static float peso(float [][] arr, float kernel[][], int posy, int posx)
    {
       float peso=0; 
       int altk=kernel.length;
       int anck=altk;
       int ancho = arr[0].length, alto = arr.length;
       int posj,  posi;
       for(int i=0; i<altk; i++)     
        {
            for(int j=0; j<anck; j++)
            {                                 
                posi=posy-altk/2+i;
                posj=posx-altk/2+j;
                if((posj>=0 && posj<= ancho-1) && (posi>=0 && posi<= alto-1))
                    peso += arr[posi][posj]*kernel[i][j];               
            }
        }  
       return peso;
    }
    
    public static float[][] kernelUniforme(int k)
    {
        float filtro[][] = new float [k][k];
        float den = k*k;
        for(int i=0; i<k; i++)     
        {
            for(int j=0; j<k; j++)
            {              
                filtro[i][j] = 1/den;
            }
        }
        return filtro;
    }
    
    public static float[][] kernelUGaussiano(int k, float desvStd)
    {
        float filtro[][] = new float [k][k];
        int elk = (k-1)/2;
        float d;
        
        for(int i=-elk; i<elk; i++)     
        {
            for(int j=-elk; j<elk; j++)
            {              
                d = (float)Math.sqrt(i*i+j*j);
                filtro[i+elk][j+elk] = (float)Math.exp(-0.5*Math.pow(d/desvStd,2));
            }
        }
        return filtro;
    }
    public static float[][] derX(float [][] arr)
    {     
        float kernel[][] ={{0,0,0},{-1,0,1},{0,0,0}};

        return convolucion(arr, kernel);  
    }
    
    public static float[][] derY(float [][] arr)
    {     
        float kernel[][] ={{0,-1,0},{0,0,0},{0,1,0}};

        return convolucion(arr, kernel);  
    }
    
    public static float[][] gradiente(float [][] arr)
    {     
        return sumaImagenes(abs(derX(arr)), abs(derY(arr)));  
    }
    
     public static float[][] erosion(float [][] arr, int tipo)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols]; 
        boolean salida;
        for(int i=1; i<filas-1; i++)
         {
            for(int j=1; j<cols-1; j++)
            {                 
                salida = false;
                if(arr[i][j]>0) 
                {
                     salida=true; 
                     salida = salida && (arr[i-1][j]>0); 
                     salida = salida && (arr[i+1][j]>0);
                     salida = salida && (arr[i][j-1]>0);
                     salida = salida && (arr[i][j+1]>0);
                     if(tipo == 8)
                     {
                        salida = salida && (arr[i-1][j-1]>0); 
                        salida = salida && (arr[i+1][j-1]>0);
                        salida = salida && (arr[i+1][j+1]>0);
                        salida = salida && (arr[i-1][j+1]>0);                   
                     }
                     asal[i][j] = salida? 255: 0;
                }              
            }
         }     
        return asal;     
    }
     
     
     
     
     
     
     
     
     
     
    public static float[][] dilatacion(float [][] arr, int tipo)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols]; 
        boolean salida;
        for(int i=1; i<filas-1; i++)
         {
            for(int j=1; j<cols-1; j++)
            {                 
                salida = false;
                if(arr[i][j]==0) 
                {
                     //salida=true; 
                     salida = salida || (arr[i-1][j]>0); 
                     salida = salida || (arr[i+1][j]>0);
                     salida = salida || (arr[i][j-1]>0);
                     salida = salida || (arr[i][j+1]>0);
                     if(tipo == 8)
                     {
                        salida = salida || (arr[i-1][j-1]>0); 
                        salida = salida || (arr[i+1][j-1]>0);
                        salida = salida || (arr[i+1][j+1]>0);
                        salida = salida || (arr[i-1][j+1]>0);                   
                     }
                     asal[i][j] = salida? 255: 0;
                } 
                else
                    asal[i][j] = 255;
            }
         }     
        return asal;     
    }
    
    public static float[][] bordesdilatacion(float [][] arr, int tipo)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols]; 
        boolean salida;
        for(int i=1; i<filas-1; i++)
         {
            for(int j=1; j<cols-1; j++)
            {                 
                salida = false;
                if(arr[i][j]==0) 
                {
                     //salida=true; 
                     salida = salida || (arr[i-1][j]>0); 
                     salida = salida || (arr[i+1][j]>0);
                     salida = salida || (arr[i][j-1]>0);
                     salida = salida || (arr[i][j+1]>0);
                     if(tipo == 8)
                     {
                        salida = salida || (arr[i-1][j-1]>0); 
                        salida = salida || (arr[i+1][j-1]>0);
                        salida = salida || (arr[i+1][j+1]>0);
                        salida = salida || (arr[i-1][j+1]>0);                   
                     }
                     asal[i][j] = salida? 255: 0;
                }              
            }
         }     
        return asal;     
    }
    
    public static float [][] apertura(float [][] arr, int tipo)
    {
        float asal[][] = erosion(arr, tipo);
        return dilatacion(asal, tipo);
    }
    
    public static float [][] cierre(float [][] arr, int tipo)
    {
        float asal[][] = dilatacion(arr, tipo);
        return erosion(asal, tipo);
    }
    
    public static float[][] etiquetado(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        //float [][] asal= new float [filas][cols]; 
        //boolean salida;
        
        int cont = 2;
        for(int i=1; i<filas-1; i++)
         {
             for(int j=1; j<cols-1; j++)
            {    
                if(arr[i][j]==1)
                {                  
                    arr[i][j] = cont;
                    conectado(arr, i, j);
                    cont+=1;
                }               
            }
         }
        System.out.println("metodo et terminado");
        return arr;
    }
    
    public static void conectado(float [][] arr, int i, int j)
    {
        if((j>=1 && j<= arr.length-2) && (i>=1 && i<= arr[0].length-2))
        {
            if(arr[i-1][j-1] == 1)
            {
                arr[i-1][j-1] = arr[i][j];
                conectado(arr, i-1, j-1);
            } 
            if(arr[i][j-1] == 1)
            {
                arr[i][j-1] = arr[i][j];
                conectado(arr, i, j-1);
            } 
            if(arr[i+1][j-1] == 1)
            {
                arr[i+1][j-1] = arr[i][j];
                conectado(arr, i+1, j-1);
            } 
            if(arr[i+1][j] == 1)
            {
                arr[i+1][j] = arr[i][j];
                conectado(arr, i+1, j);
            } 
            if(arr[i+1][j+1] == 1)
            {
                arr[i+1][j+1] = arr[i][j];
                conectado(arr, i+1, j+1);
            } 
            if(arr[i][j+1] == 1)
            {
                arr[i][j+1] = arr[i][j];
                conectado(arr, i, j+1);
            } 
            if(arr[i-1][j+1] == 1)
            {
                arr[i-1][j+1] = arr[i][j];
                conectado(arr, i-1, j+1);
            } 
            if(arr[i][j-1] == 1)
            {
                arr[i][j-1] = arr[i][j];
                conectado(arr, i, j-1);
            } 
        }
    }
}
