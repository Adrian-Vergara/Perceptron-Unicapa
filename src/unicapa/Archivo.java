/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicapa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Adrian
 */
public class Archivo {
    
    public void escribirArchivo(String nombreArchivo, String texto)
    {
        File f;
        f = new File(nombreArchivo);
        try{
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(texto);
            wr.close();
            bw.close();
        }catch(IOException e){
        }

    }
    
    public void leerArchivo()
    {
        try{
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
