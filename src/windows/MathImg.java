package windows;


import java.awt.image.BufferedImage;
import Jama.*;
import java.util.Calendar;
//import static windows.LibImg.conectado;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MACBOOK
 */
public class MathImg {
    final static int B = 2;
    final static int G = 1;
    final static int R = 0;
    
    
    public static float [][][] convierteDeImagenaArreglo( BufferedImage bf)
    {
        float [][][] arrimg = new float[3][][];
        int ancho = bf.getWidth(), alto =bf.getHeight();
        int variable;
        
        for(int i = 0; i < 3; i++)
            arrimg[i] = new float[alto][ancho];
        
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
            {
                variable = bf.getRGB(j, i);
                arrimg[R][i][j] = (variable>>16 & 0xFF);
                arrimg[G][i][j] = (variable>>8 & 0xFF);
                arrimg[B][i][j] = (variable & 0xFF);         
            }
        
        return arrimg;
    }
    
    public static float [][][] reescalaRangoDinamico ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int alto = ent[0].length, ancho =ent[0][0].length;
        float max[], min[], factor[];
        max = new float[3];
        min = new float[3];
        factor = new float[3];
        for(int i = 0; i < 3; i++)
            sal[i] = new float[alto][ancho];
        for(int i = 0; i < 3; i++)
        {
            max[i] = ent[i][0][0];
            min[i] = ent[i][0][0];
        }
        for(int k = 0; k<3; k++)
            for(int i = 0; i < alto; i++)
                for(int j = 0; j < ancho; j++)
                {
                    max[k] = Math.max(max[k], ent[k][i][j]);
                    min[k] = Math.min(min[k], ent[k][i][j]);
                }
        
        for(int k = 0; k<3; k++)
        {
            factor[k] = 255.0f/(max[k]-min[k]);
            for(int i = 0; i < alto; i++)
                for(int j = 0; j < ancho; j++)
                {
                    sal[k][i][j] = factor[k]*(ent[k][i][j] - min[k]);     
                }
        }
    
        return sal;
    }
    
    public static BufferedImage convierteDeArregloAImagen(float [][][] ent)
    {
        BufferedImage sal;
        int alto = ent[0].length, ancho =ent[0][0].length;
            sal = new BufferedImage(  ancho, alto, BufferedImage.TYPE_INT_RGB);
        int pixel;    
        float [][][] arrsal= reescalaRangoDinamico(ent);
        for(int i = 0; i < alto; i++)
                for(int j = 0; j < ancho; j++)
                {
                    pixel = (int)arrsal[B][i][j] | (int)arrsal[G][i][j]<<8 | (int)arrsal[R][i][j]<<16;
                    sal.setRGB(j,i, pixel);
                }
        
        return sal;
    }
    
    public static BufferedImage convierteDeArregloAImagensinReescalar(float [][][] ent)
    {
        BufferedImage sal;
        int alto = ent[0].length, ancho =ent[0][0].length;
            sal = new BufferedImage(  ancho, alto, BufferedImage.TYPE_INT_RGB);
        int pixel;    
        
        for(int i = 0; i < alto; i++)
                for(int j = 0; j < ancho; j++)
                {
                    pixel = (int)ent[B][i][j] | (int)ent[G][i][j]<<8 | (int)ent[R][i][j]<<16;
                    sal.setRGB(j,i, pixel);
                }
        
        return sal;
    }
    
    public static float [][][] convertirAGris ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int alto = ent[0].length, ancho =ent[0][0].length;
        for(int i = 0; i < 3; i++)
            sal[i] = new float[alto][ancho];
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
            {
                sal[R][i][j] = (ent[R][i][j] + ent[G][i][j] + ent[B][i][j])/3;
                sal[G][i][j] = sal[R][i][j];
                sal[B][i][j] = sal[R][i][j];          
            }
        
        return sal;
    }
 
    
    public static float [][][] convertirFalsoColor ( float [][][] ent, float fR, float fG, float fB)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
            sal[i] = new float[alto][ancho];
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
            {
                sal[R][i][j] = ent[R][i][j]*fR;
                sal[G][i][j] = ent[G][i][j]*fG;
                sal[B][i][j] = ent[B][i][j]*fB;          
            }
        
        return sal;
    }
    public static float [][][] convertirAGrisUnSoloCanalB ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
            sal[i] = new float[alto][ancho];
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
            {
                sal[R][i][j] = ent[B][i][j];
                sal[G][i][j] = sal[R][i][j];
                sal[B][i][j] = sal[R][i][j];          
            }
        
        return sal;
    }
    public static float [][][] convertirAGrisUnSoloCanalG ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
            sal[i] = new float[alto][ancho];
        for(int i = 0; i < alto; i++)
            for(int j = 0; j < ancho; j++)
            {
                sal[R][i][j] = ent[G][i][j];
                sal[G][i][j] = sal[R][i][j];
                sal[B][i][j] = sal[R][i][j];          
            }
        
        return sal;
    }
    
    public static float [][][] umbralColor ( float [][][] ent, float umbr)
    {
        float [][][] sal = new float[3][][];
        for(int i = 0; i < 3; i++)
        {         
            sal[i] = umbral(ent[i], umbr); 
        }
        return sal;
    }
    
    public static float[][] umbral (float [][] arr, float umbr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {  
                asal[i][j]=  arr[i][j]>= umbr ? 1: 0;
            }
        }     
        return asal;     
    }
    
    public static float [][][] umbral01C ( float [][][] ent, float umbr)
    {
        float [][][] sal = new float[3][][];
        int filas = ent[0].length, cols =ent[0][0].length;
        sal[R]= new float [filas][cols];
        sal[G]= new float [filas][cols];
        sal[B]= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {  
                sal[R][i][j]=  ((ent[R][i][j] + ent[R][i][j] + ent[R][i][j])/3)>= umbr ? 1: 0;
            }
        }   
        sal[G] = sal[R];
        sal[B] = sal[R];
       
        return sal;
    }
    
    public static float [][][] umbralDosrangos( float [][][] ent, float rmin, float rmax)
    {
        float [][][] sal = new float[3][][];
        int filas = ent[0].length, cols =ent[0][0].length;
        sal[R]= new float [filas][cols];
        //sal[G]= new float [filas][cols];
        //sal[B]= new float [filas][cols];
        float prom;
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {  
                prom=(ent[R][i][j] + ent[R][i][j] + ent[R][i][j])/3f; 
                if(prom<= rmin && prom>=rmax)          
                    sal[R][i][j] = 1;  
            }
        }   
        sal[G] = sal[R];
        sal[B] = sal[R];
       
        return sal;
    }
    
    /*public static float[][] umbral01 (float [][] arr, float umbr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {  
                asal[i][j]=  arr[i][j]>= umbr ? 1: 0;
            }
        }     
        return asal;     
    }*/
    
    public static float [][][] sumaEscalarColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = sumaEsc(ent[i], esc);
        }
        return sal;
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
    
    public static float [][][] restaEscalarColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = restaEsc(ent[i], esc);
        }
        return sal;
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
    
    public static float [][][] multiplicacionEscalarColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //mercsal[i] = new float[alto][ancho];
            sal[i] = multiplicacionEsc(ent[i], esc);
        }
        return sal;
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
    
    public static float [][][] divisionEscalarColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {     
            sal[i] = divisionEsc(ent[i], esc);
        }
        return sal;
    }
    
    public static float[][] divisionEsc(float [][] arr, float esc)
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
    
    public static float [][][] moduloEscalarColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {     
            sal[i] = moduloEsc(ent[i], esc);
        }
        return sal;
    }
    
    public static float[][] moduloEsc(float [][] arr, float esc)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (int)arr[i][j]%(int)esc;               
            }
         }     
        return asal;     
    }
    
    
    public static float [][][] potenciaColor ( float [][][] ent, float pot)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = potencia(ent[i], pot);
        }
        return sal;
    }
    
    public static float[][] potencia(float [][] arr, float pot)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.pow(arr[i][j], pot);
            }
         }     
        return asal;     
    }
    
    public static float [][][] absColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = abs(ent[i]);
        }
        return sal;
    }
    
    public static float[][] abs(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.abs(arr[i][j]);
            }
         }     
        return asal;     
    }
    
    
    public static float [][][] tanColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = tan(ent[i]);
        }
        return sal;
    }
    
    public static float[][] tan(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = (float)Math.tan(arr[i][j]);
            }
         }     
        return asal;     
    }
    
    public static float [][][] sinColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = sin(ent[i]);
        }
        return sal;
    }
    
    public static float[][] sin(float [][] arr)
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
    
    public static float [][][] cosColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = cos(ent[i]);
        }
        return sal;
    }
    
    public static float[][] cos(float [][] arr)
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
    
    public static float [][][] negativoColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = negativo(ent[i], i);
        }
        return sal;
    }
    
    public static float[][] negativo(float [][] arr, int indice)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [filas][cols];  
        float max = arr[0][0];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  max = Math.max(arr[i][j], max);               
            }
         } 
        if(indice<2)
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = max-arr[i][j];               
            }
         }     
        return asal;     
    }
    
    public static float [][][] espejoYColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = espejoY(ent[i]);
        }
        return sal;
    }
    
     public static float[][] espejoY(float [][] arr)
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
     
     public static float [][][] espejoXColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = espejoX(ent[i]);
        }
        return sal;
    }
     
    public static float[][] espejoX(float [][] arr)
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
    public static float [][][] giro90IzqColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[ancho][alto];
            sal[i] = rota90Izq(ent[i]);
        }
        return sal;
    }
    
    public static float[][] rota90Izq(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [cols][filas];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[cols-1-j][i] = arr[i][j];               
            }
         }     
        return asal;//espejoX(asal);     
    }
    
    public static float [][][] giro90DerColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[ancho][alto];
            sal[i] = rota90Der(ent[i]);
        }
        return sal;
    }
    
    public static float[][] rota90Der(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal= new float [cols][filas];  
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[j][filas-1-i] = arr[i][j];               
            }
         }     
        return asal;//espejoX(asal);     
            
    }
    
    public static float [][][] giro180Color ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
        //int ancho = ent[0].length, alto =ent[0][0].length;
        for(int i = 0; i < 3; i++)
        {
            //sal[i] = new float[alto][ancho];
            sal[i] = rota180(ent[i]);
        }
        return sal;
    }
    
    
    public static float[][] rota180(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        float [][] asal = new float [filas][cols];  
        //asal = rota90Izq(rota90Izq(arr));
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[cols-1-i][filas-1-j] = arr[i][j];               
            }
         } 
        return asal;     
    }
    
    public static float [][][] sumaImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = sumaImagenes(img1[i], img2[i]);
        }
        return sal;
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
    
    public static float [][][] restaImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = restaImagenes(img1[i], img2[i]);
        }
        return sal;
    }
    
    public static float[][] restaImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = im1[i][j] - im2[i][j];               
            }
         }     
        return asal;
    }
    public static float [][][] multiplicacionImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = multiplicacionImagenes(img1[i], img2[i]);
        }
        return sal;
    }
    
    public static float[][] multiplicacionImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = im1[i][j] * im2[i][j];               
            }
         }     
        return asal;
    }
    public static float [][][] divisionImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = divisionImagenes(img1[i], img2[i]);
        }
        return sal;
    }
    
    public static float[][] divisionImagenes(float [][] im1, float [][] im2)
    {
        int filas = Math.min(im1.length, im2.length);
        int cols = Math.min(im1[0].length,im2[0].length);
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = im1[i][j] / (im2[i][j]==0?0.01f:im2[i][j]);               
            }
         }     
        return asal;
    }
    
    public static float [][][] marcaAguaImagenesColor ( float [][][] img1,  float [][][] img2, float porc )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = marcaAgua(img1[i], img2[i], porc);
        }
        return sal;
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
    
    public static float [][][] marcaAguaImagenesColorEsc ( float [][][] img1,  float [][][] img2, float porc )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = marcaAguaEsc(img1[i], img2[i], porc);
        }
        return sal;
    }
    
    public static float[][] marcaAguaEsc(float [][] im1, float [][] im2, float porc)
    {
        int filas = im1.length;
        int cols = im1[0].length;
        int filch =  im2.length;
        int colch =  im2[0].length;
        float [][] asal;//= new float [filas][cols];
        float escy = filas/(float)filch;
        float escx = cols/(float)colch;
        asal = escalaXYInterpolacion(im2, escx, escy);
        filas = asal.length;
        cols = asal[0].length;
        for (int i = 0 ; i < filas; i++)
            for( int j = 0; j < cols; j++)
            {                 
                
                asal[i][ j] = im1[ i][ j] + porc*asal[i][j];               
            }
              
        return asal;
    }
    
    public static float [][][] andImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = andImagenes(img1[i], img2[i]);
        }
        return sal;
    }
    
    public static float[][] andImagenes(float [][] im1, float [][] im2)
    {
        int filas = im1.length;
        int cols = im1[0].length;
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = ((im2[i][j]>0) && (im1[i][j]>0))?im1[i][j]:0;               
            }
         }     
        return asal;
    }
    
    public static float [][][] orImagenesColor ( float [][][] img1,  float [][][] img2 )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = orImagenes(img1[i], img2[i]);
        }
        return sal;
    }
    
    public static float[][] orImagenes(float [][] im1, float [][] im2)
    {
        int filas = im1.length;
        int cols = im1[0].length;
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {                 
                  asal[i][j] = ((im2[i][j]>0) || (im1[i][j]>0))?im1[i][j]:0;               
            }
         }     
        return asal;
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
    
     public static int[][] histColor(BufferedImage img)
    {
        int hist[][] = new int[3][256];
        int filas =  img.getHeight(), cols = img.getWidth();
        int v;
         for(int i=0; i<filas; i++)
         {
            for(int j=0; j<cols; j++)
            {  
                v = img.getRGB(j, i);
                hist [R][ (v>>16&255) ]++; 
                hist [G][ (v>>8&255) ]++;
                hist [B][ (v&255) ]++;
            }
         }
        return hist;
    }
     
     
     
     public static int[][] renglonColor(BufferedImage img, int nR)
    {
        int filas =  img.getHeight(), cols = img.getWidth();
        int renglon[][] = new int[3][cols];
        int v;
         for(int j=0; j<cols; j++)
         {        
                v = img.getRGB(j, nR);
                renglon [R][j] = (v>>16&255); 
                renglon [G][j] = (v>>8&255);
                renglon [B][j] = (v&255);        
         }
        return renglon;
    } 
     
      public static int[][] columnaColor(BufferedImage img, int nR)
    {
        int filas =  img.getHeight(), cols = img.getWidth();
        int renglon[][] = new int[3][cols];
        int v;
         for(int j=0; j<filas; j++)
         {        
                v = img.getRGB(nR, j);
                renglon [R][j] = (v>>16&255); 
                renglon [G][j] = (v>>8&255);
                renglon [B][j] = (v&255);       
         }
        return renglon;
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
    
    public static int[][] histAcumColor(int h[][])
    {
        int hist[][] = new int[3][256];
     
        hist[R][0]=h[R][0];
        hist[G][0]=h[G][0];
        hist[B][0]=h[B][0];
         for(int i=1; i<h[0].length; i++)
         { 
                hist[R][i] = hist[R][i-1]+ h[R][i];
                hist[G][i] = hist[G][i-1]+ h[G][i];
                hist[B][i] = hist[B][i-1]+ h[B][i];
         }
        return hist;
    }
    
    public static int[] histNorm(int h[])
    {
        int hist[] = new int[256];
       
        float max = h[255];
      
         for(int i=0; i<h.length; i++)
         { 
                hist[i] = (int)(h[i]/max*255f);           
         }
        return hist;
    }
    
    public static int[][] histNormColor(int h[][])
    {
        int hist[][] = new int[3][256];
       
        float max[] = {h[R][255], h[G][255], h[B][255]};
      
         for(int i=0; i<h[0].length; i++)
         { 
                hist[R][i] = (int)(h[R][i]/max[R]*255f);  
                hist[G][i] = (int)(h[G][i]/max[G]*255f);
                hist[B][i] = (int)(h[B][i]/max[B]*255f);
         }
        return hist;
    }
    
    public static float [][][] ecualizaImagenesColor ( float [][][] img1,  int [][] func )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = ecualiza(img1[i], func[i]);
        }
        return sal;
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
    public static float [][][] escalaXYColor ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = escalaXY(ent[i], esc);
        }
        return sal;
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
        
        
       /* for(int i=0; i<im1.length; i++)
        {
            for(int j=0; j<im1[0].length; j++)
            {                 
                asal[(int)(i*esc)][(int)(j*esc)] = im1[i][j];               
            }
        }  */
        return asal ;    
    }
    
    public static float [][][] escalaColorInterp ( float [][][] ent, float esc)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = escalaInterpolacion(ent[i], esc);
        }
        return sal;
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
    
    public static  float[][] escalaInterpolacion(float [][] im1, float esc)
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
    
    public static float [][][] escalaXYColorInterp ( float [][][] ent, float escx, float escy)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = escalaXYInterpolacion(ent[i], escx, escy);
        }
        return sal;
    }
     public static  float[][] escalaXYInterpolacion(float [][] im1, float escx, float escy)
    {
        int filas = (int)(im1.length*escy), cols = (int)(im1[0].length*escx);
        
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = interpolacion(im1, i/escy,j/escx);               
            }
        }      
        return asal ;    
    }
    
    
    public static float [][][] rotaColor ( float [][][] ent, float ang)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = rotarAngInv(ent[i], ang);
        }
        return sal;
    }
    
    public static  float[][] rotar(float [][] im1, float ang)
    {
       int filas = im1.length, cols = im1[0].length;
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
                if(nX>-1 && nY>-1)
                    asal[nY][nX] = im1[i][j];               
            }
        }      
        return asal ;      
    }
    public static  float[][] rotarAngInv(float [][] im1, float ang)
    {
        int filas = im1.length, cols = im1[0].length;
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
                if((nX>=0 && nX< cols ) && (nY>=0 && nY < filas))
                    asal[i][j] = im1[nY][nX];   
            } 
        }
        return asal;
    }

    public static float [][][] defXColor ( float [][][] ent, float fac)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = defX(ent[i], fac);
        }
        return sal;
    }
    
    public static  float[][] defX(float [][] im1, float fac)
    {
        int filas = im1.length, cols = im1[0].length;
        
        float [][] asal= new float [filas][(int)(cols + fac*filas)];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][(int)(j + i*fac)] = im1[i][j];               
            }
        }      
        return asal ;    
    }
    public static float [][][] defYColor ( float [][][] ent, float fac)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = defY(ent[i], fac);
        }
        return sal;
    }
    
    public static  float[][] defY(float [][] im1, float fac)
    {
        int filas = im1.length, cols = im1[0].length;
        
        float [][] asal= new float [(int)(cols*fac + filas)][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[(int)(i + j*fac)][j] = im1[i][j];               
            }
        }      
        return asal ;    
    }

    public static float [][][] tAfinColor ( float [][][] ent, //int x1a, int y1a, int x2a, int y2a, int x3a, int y3a,
                                    int x1r, int y1r, int x2r, int y2r, int x3r, int y3r)
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = afin(ent[i],// x1a, y1a, x2a, y2a, x3a, y3a,
                                  x1r, y1r, x2r, y2r, x3r, y3r);
        }
        return sal;
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
        
    public static float [][][] tAfinColorFondo ( float [][][] sal, float [][][] ent,// int x1a, int y1a, int x2a, int y2a, int x3a, int y3a,
                                                    int x1r, int y1r, int x2r, int y2r, int x3r, int y3r)
    {
        //float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = afinFondo(sal[i], ent[i],// x1a, y1a, x2a, y2a, x3a, y3a,
                                  x1r, y1r, x2r, y2r, x3r, y3r);
        }
        return sal;
    }
    
     public static float[][] afinFondo(float [][] asal, float [][] arr, 
                                        float xd0, float yd0, float xd1, float yd1, float xd2, float yd2)
    {
        int xSI=0, xSD=arr[0].length, xII=0;
        int ySI=0, ySD=0, yII=arr.length;
      
        int filas = arr.length, cols = arr[0].length;   
        //int ancho = (int)((xd1-xd0) + Math.abs(xd0-xd2))+1;
        //int alto  = (int)((yd2-yd0) + Math.abs(yd0-yd1))+1;
        //float [][] asal= new float [alto][ancho]; 
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
    
    public static float [][][] tBilinealColorFondo ( float [][][] sal, float [][][] ent,// int x1a, int y1a, int x2a, int y2a, int x3a, int y3a,
                                                    int x1r[])
    {
        //float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[i] = transfBilinealInv(sal[i],  x1r, ent[i]);// x1a, y1a, x2a, y2a, x3a, y3a,                          
        }
        return sal;
    } 
     
     public static float[][] transfBilineal(float [][] orig, int puntos[], float[][] arr)
    {
        int x1a=0, x2a=arr[0].length, x4a=0,          x3a=arr[0].length;
        int y1a=0, y2a=0,             y4a=arr.length, y3a=arr.length;
        float xr1=puntos[0],  yr1=puntos[1], xr2=puntos[2], yr2=puntos[3],
              xr3=puntos[4], yr3=puntos[5], xr4 = puntos[6], yr4=puntos[7];
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
     
     static int conX(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
 	static int conY(int i, int j, double cs[])
	{
		return (int)(cs[0] * j + cs[1] * i + cs[2]*i*j + cs[3]);
	}
        
    public static float[][] transfBilinealInv(float [][] orig, int puntos[], float[][] arr)
    {
        int x1r=0, x2r=arr[0].length, x4r=0,          x3r=arr[0].length;
        int y1r=0, y2r=0,             y4r=arr.length, y3r=arr.length;
        int x1a=puntos[0],  y1a=puntos[1], x2a=puntos[2], y2a=puntos[3], 
            x3a=puntos[4],  y3a=puntos[5], x4a = puntos[6], y4a=puntos[7];
        int filas = arr.length, cols = arr[0].length;
        int minfilas, mincols, maxfilas, maxcols;
        minfilas= (Math.min(y1a, Math.min(y2a,Math.min(y3a,y4a))));
        mincols= (Math.min(x1a, Math.min(x2a,Math.min(x3a,x4a))));
        maxfilas= (Math.max(y1a, Math.max(y2a,Math.max(y3a,y4a))));
        maxcols= (Math.max(x1a, Math.max(x2a,Math.max(x3a,x4a))));
        //int ancho = orig[0].length;
        //int alto  = orig.length;
        double [][] matA =      {{x1a, y1a, x1a*y1a, 1},
                               {x2a, y2a, x2a*y2a, 1},
                               {x3a, y3a, x3a*y3a, 1},
                               {x4a, y4a, x4a*y4a, 1}};
        Matrix A = new Matrix(matA);
        double vecX[] = {x1r, x2r, x3r, x4r};
        double vecY[] = {y1r, y2r, y3r, y4r};
        Matrix X = new Matrix(vecX, vecX.length);
        Matrix Y = new Matrix(vecY, vecY.length);
        Matrix SolX, SolY;
        
        SolX = A.solve(X);
        SolY = A.solve(Y);
        //float [][] asal= new float [alto][ancho]; 
        double posi, posj;
        //float coef[][] = matrizDeC(xd0, yd0, xd1, yd1, xd2, yd2, xSI,ySI, xSD, ySD, xII, yII );
        
        for(int i=minfilas; i<maxfilas; i++)     
        {
            for(int j=mincols; j<maxcols; j++)
            {                                
                posj = conX(i,j, SolX.getColumnPackedCopy());
                posi = conY(i,j, SolY.getColumnPackedCopy());                
                if((posj>=0 && posj<= cols-1) && (posi>=0 && posi<= filas-1))
                    orig[i][j] = arr[(int)posi][(int)posj];               
            }
        }     
        return orig;  
    }
    
    public static float [][][] ruidoColor ( int filas, int cols)
    {
        float [][][] sal = new float[3][][];
        
        //for(int i = 0; i < 3; i++)
        {
            sal[0] = ruido01(filas, cols);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    
    public static  float[][] ruido01(int filas, int cols)
    {
        //int filas = im1.length, cols = im1[0].length;
        
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = (int)(Math.round(Math.random()));               
            }
        }      
        return asal ;    
    }
    
    public static float [][][] ruidoColorRango ( int filas, int cols, int min, int max)
    {
        float [][][] sal = new float[3][][];
        
        //for(int i = 0; i < 3; i++)
        {
            sal[0] = ruidorango(filas, cols, min, max);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    public static  float[][] ruidorango(int filas, int cols, int min, int max)
    {
        //int filas = im1.length, cols = im1[0].length;
        
        float [][] asal= new float [filas][cols];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<cols; j++)
            {                 
                asal[i][j] = (int)(Math.round(Math.random()*(max-min) + min));               
            }
        }      
        return asal ;    
    }
    
    public static float [][][] rampaXColor ( int filas )
    {
        float [][][] sal = new float[3][][];
        {
            sal[0] = rampaX(filas);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    public static  float[][] rampaX(int filas)
    { 
        float [][] asal= new float [filas][filas];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<filas; j++)
            {                 
                asal[i][j] = j/(float)filas;               
            }
        }      
        return asal ;    
    }
      
    public static float [][][] rampaYColor ( int filas )
    {
        float [][][] sal = new float[3][][];     
        {
            sal[0] = rampaY(filas);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    public static  float[][] rampaY(int filas)
    {
        //int filas = im1.length, cols = im1[0].length;
        
        float [][] asal= new float [filas][filas];
        for(int i=0; i<filas; i++)
        {
            for(int j=0; j<filas; j++)
            {                 
                asal[i][j] = i/(float)filas;               
            }
        }      
        return asal ;    
    }
    
    public static float [][][] impulsoColor ( int filas, int posx, int posy )
    {
        float [][][] sal = new float[3][][];
        
        //for(int i = 0; i < 3; i++)
        {
            sal[0] = impulso(filas, posx, posy);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    public static  float[][] impulso(int filas, int posx, int posy)
    {  
        float [][] asal= new float [filas][filas];
        
        asal[posy][posx] = 1f;               
    
        return asal ;    
    }
    
    public static float [][][] erosionColor ( float [][][] ent, boolean fac)
    {
        float [][][] sal = new float[3][][];
        
        //for(int i = 0; i < 3; i++)
        {
            sal[0] = erosion(ent[0], fac);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
    }
    
    public static float [][][] dilatacionColor ( float [][][] ent, boolean fac)
    {
        float [][][] sal = new float[3][][];
        
        //for(int i = 0; i < 3; i++)
        {
            sal[0] = dilatacion(ent[0], fac);
            sal[1] = sal[0];
            sal[2] = sal[0];
        }
        return sal;
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
                //if(i>0 && i<filas || j)
                
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
                if(arr[i][j]==1 || vecinos>0)  
                    asal[i][j] = 1;  
            }
        }     
        return asal;     
    }
    
    public static float [][][] aperturaColor ( float [][][] ent, boolean fac)
    {
        float [][][] sal = new float[3][][];
            sal[0] = apertura(ent[0], fac);
            sal[1] = sal[0];
            sal[2] = sal[0];
        return sal;
    }
     
    public static float [][][] cierreColor ( float [][][] ent, boolean fac)
    {
        float [][][] sal = new float[3][][];
            sal[0] = cierre(ent[0], fac);
            sal[1] = sal[0];
            sal[2] = sal[0];
        return sal;
    }
     
    public static float[][] apertura(float [][] arr, boolean vec8)
    { 
        return dilatacion(erosion(arr, vec8), vec8);
    }
    
    public static float[][] cierre(float [][] arr, boolean vec8)
    {           
        return erosion(dilatacion(arr, vec8), vec8);
    }
    
    public static float [][][] bordesBinColor ( float [][][] ent, boolean fac)
    {
        float [][][] sal = new float[3][][];
            sal[0] = bordesBin(ent[0], fac);
            sal[1] = sal[0];
            sal[2] = sal[0];
        return sal;
    }
     
    public static float[][] bordesBin(float [][] arr, boolean vec8)
    {
        return restaImagenes(arr, erosion(arr, vec8));
       // return restaImagenes( dilatacion(arr, vec8), arr);
    }
    
    public static float [][][] dithColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
            sal[0] = dith2(ent[0]);
            sal[1] = sal[0];
            sal[2] = sal[0];
        return sal;
    }
    
    public static float[][] dith2(float [][] arr)
    {
        int filas = (arr.length), cols = (arr[0].length);
        float [][] asal= new float [filas][cols]; 
        int temp[] = new int[10];
        for(int i=1; i<filas-1; i+=3)     
        {
            for(int j=1; j<cols-1; j+=3)
            {
                temp[1]= (int)(arr[i][j]/28f);
                temp[2]= (int)(arr[i][j+1]/28f);
                temp[3]= (int)(arr[i-1][j]/28f);
                temp[4]= (int)(arr[i-1][j+1]/28f);
                temp[5]= (int)(arr[i+1][j]/28f);
                temp[6]= (int)(arr[i][j-1]/28f);
                temp[7]= (int)(arr[i+1][j-1]/28f);
                temp[8]= (int)(arr[i][j-1]/28f);
                temp[9]= (int)(arr[i+1][j+1]/28f);
                
                for(int k=1; k<10; k++)
                {
                    switch(k)
                    {
                        case 1:
                            if(temp[k]>=k)
                                asal[i][j] = 1;
                            break;
                        case 2:
                            if(temp[k]>=k)
                                asal[i][j+1] = 1;
                            break;
                        case 3:
                            if(temp[k]>=k)
                                asal[i-1][j] = 1;
                            break;
                        case 4:
                            if(temp[k]>=k)
                                asal[i-1][j+1] = 1;
                            break;
                            
                        case 5:
                            if(temp[k]>=k)
                                asal[i+1][j] = 1;
                            break;
                        case 6:
                            if(temp[k]>=k)
                                asal[i][j-1] = 1;
                            break;
                            
                        case 7:
                            if(temp[k]>=k)
                                asal[i+1][j-1] = 1;
                            break;
                            
                        case 8:
                            if(temp[k]>=k)
                                asal[i][j-1] = 1;
                            break;
                        case 9:
                            if(temp[k]>=k)
                                asal[i+1][j+1] = 1;
                            break;
                    }
                }       
                
            }
        }
        return asal;
    }
    
    public static float[][] dith1(float [][] arr)
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
                    asal[i][j] = 1; 
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
                    asal[i][j] = 1; 
                    sig =-1;
                    err = 255-valorpix;
                    err*=sig;
                }
            }
        }     
        return asal;  
    }

    public static float [][][] etiquetadoColor ( float [][][] ent)
    {
        float [][][] sal = new float[3][][];
            sal[0] = etiquetado(ent[0]);
            sal[1] = sal[0];
            sal[2] = sal[0];
        return sal;
    }
    public static float[][] etiquetado(float [][] arr)
    {
        int filas = arr.length, cols = arr[0].length;
        
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
        System.out.println("metodo terminado, numero de regiones es: " + (cont-2));
        return arr;
    }
     public static void conectado(float [][] arr, int i, int j)
    {
        //if((i>=1 && i<= arr.length-1) && (j>=1 && j<= arr[0].length-1))
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
     
    public static float [][][] convolucionColor ( float [][][] arr, float kernel[][] )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[R] = convolucion(arr[R], kernel);
            sal[G] = convolucion(arr[G], kernel);
            sal[B] = convolucion(arr[B], kernel);
        }
        return sal;
    }
    
    public static float[][] convolucion(float [][] arr, float kernel[][])
    {     
        int filas = arr.length, cols = arr[0].length;       
        float [][] asal= new float [filas][cols];   
  
        for(int i=0; i<filas; i++)     
        {
            for(int j=0; j<cols; j++)
            {                                 
                asal[i][j] = peso(arr, kernel, i,j);               
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
    public static float [][][] derXColor ( float [][][] arr )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[R] = derX(arr[R]);
            sal[G] = derX(arr[G]);
            sal[B] = derX(arr[B]);
        }
        return sal;
    }
    public static float [][][] derYColor ( float [][][] arr )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[R] = derY(arr[R]);
            sal[G] = derY(arr[G]);
            sal[B] = derY(arr[B]);
        }
        return sal;
    }
    public static float[][] derX(float [][] arr)
    {     
        float kernel[][] ={{0,0,0},
                           {-1,1,0},
                           {0,0,0}};

        return convolucion(arr, kernel);  
    }
    
    public static float[][] derY(float [][] arr)
    {     
        float kernel[][] ={{0,-1,0},
                           {0,1,0},
                           {0,0,0}};
        

        return convolucion(arr, kernel);  
    }
    public static float [][][] gradienteColor ( float [][][] arr )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[R] = gradiente(arr[R]);
            sal[G] = gradiente(arr[G]);
            sal[B] = gradiente(arr[B]);
            
        }
        return sal;
    }
    public static float[][] gradiente(float [][] arr)
    {     
        return sumaImagenes(abs(derX(arr)), abs(derY(arr)));  
    }
    
    public static float [][][] bordesColor ( float [][][] arr )
    {
        float [][][] sal = new float[3][][];
        
        for(int i = 0; i < 3; i++)
        {
            sal[R] = bordes(arr[R]);
            sal[G] = bordes(arr[G]);
            sal[B] = bordes(arr[B]);
            
        }
        return sal;
    }
    public static float[][] bordes(float [][] arr)
    {
        return umbral(abs(derX(arr)), 30);
    }
           
}


