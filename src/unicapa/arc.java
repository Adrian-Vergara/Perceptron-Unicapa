/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicapa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Adrian
 */
public class arc {
   
    public void Escribir(Double[][] Matriz,String name_File) throws IOException{
           String Text="";
            for (int i = 0; i < Matriz.length; i++) {
                for (int j = 0; j < Matriz[i].length; j++) {
                    Text += Matriz[i][j]+",";
                }
                Text +=";";
                
            }

            File archivoPrueba = new File(name_File+".txt");
            agregaContenidoArchivo(archivoPrueba, Text);
    }
    
    public void Escribir(Double[] Matriz, String name_File) throws IOException{
           String Text="";
            for (int i = 0; i < Matriz.length; i++) {
             
                    Text += Matriz[i];
                
                Text +=",";
                
            }

            File archivoPrueba = new File(name_File+".txt");
            agregaContenidoArchivo(archivoPrueba, Text);
    }
    
    public Double[][] leerMatriz(int fila, int columna ,String name_File){
            //Creamos un String que va a contener todo el texto del archivo
            Double[][] matriz = new Double[fila][columna];
            String texto="";

            try
            {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector=new FileReader(name_File+".txt");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido=new BufferedReader(lector);

            //Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
            while((texto=contenido.readLine())!=null)
            {    int a=0;
                 int b=0;
                 String text="";
                 for (int i = 0; i < texto.length(); i++) {
                     
                     if (texto.charAt(i)!=';') {
                         
                         
                         if(texto.charAt(i)!=','){
                             text += texto.charAt(i);
                         }else {
                              matriz[a][b]= Double.parseDouble(text); 
                              b = b+1;
                              text="";
                         }
                         
                        
                         
                        
                     }else{
                         b=0;
                         a=a+1;
                     }
                }
            }
            }

            //Si se causa un error al leer cae aqui
            catch(Exception e)
            {
            System.out.println("Error al leer Matriz de pesos");
            }
      return matriz;
    }
   
    public Double[]leerUmbrales(int tamaño,String name_File){
            //Creamos un String que va a contener todo el texto del archivo
            Double[] matriz = new Double[tamaño];
            String texto="";

            try
            {
            //Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector=new FileReader(name_File+".txt");

            //El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido=new BufferedReader(lector);

            //Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
            while((texto=contenido.readLine())!=null)
            {    
                 int b=0;
                 String text="";
                 for (int i = 0; i < texto.length(); i++) {
                     
                     if (texto.charAt(i)!=';') {
                         
                         
                         if(texto.charAt(i)!=','){
                             text += texto.charAt(i);
                         }else {
                              matriz[b]= Double.parseDouble(text); 
                              b = b+1;
                              text="";
                         }
                         
                        
                         
                        
                     }
                }
            }
            }

            //Si se causa un error al leer cae aqui
            catch(Exception e)
            {
            System.out.println("Error al leer vector de umbrales");
            }
      return matriz;
    }
    
    public void Reiniciar(String name_File) throws IOException{
         String Text="";
        
            File archivoPrueba = new File(name_File+".txt");
            agregaContenidoArchivo(archivoPrueba, Text);
    }
    
    /**
  * Cambia el contenido de un archivo de testo en su totalidad.. sobreescribiendo
   * el texto existente
   **/

  static public void agregaContenidoArchivo(File archivoAbierto, String contenido) throws FileNotFoundException, IOException {
    if (archivoAbierto == null) {
      throw new IllegalArgumentException("El archivo no debe ser nulo.");
    }
    if (!archivoAbierto.exists()) {
      throw new FileNotFoundException ("el archivo no existe: " + archivoAbierto);
    }
    if (!archivoAbierto.isFile()) {
      throw new IllegalArgumentException("no debe ser un directorio: " + archivoAbierto);
    }
    if (!archivoAbierto.canWrite()) {
      throw new IllegalArgumentException("El archivo no puede ser escrito: " + archivoAbierto);
    }

    Writer output = new BufferedWriter(new FileWriter(archivoAbierto));
    try {
      output.write( contenido );
    }
    finally {
      output.close();
    }
  }
}
