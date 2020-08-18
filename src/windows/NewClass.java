/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

/**
 *
 * @author MACBOOK
 */
public class NewClass {
    public static void main(String arg[])
    {
        float img[][]={{10,20},{20,30}};
        float inter = LibImg.interpolacion(img, 0.5f, 0.5f);
        
        System.out.println("inertpolado =" + inter);
    }
    
}
