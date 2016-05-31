/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicapa;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Adrian
 */
public class Perceptron {
    double rataAprendizaje;
    double [][] matrizPeso;
    double [] umbral;    
    double errorMaximo = 0;
    double [] yr;
    double [] ErrorLineal;
    double [] ErrorPatron;
    int [][] entradas;
    int [][] salidas;
    XYSeries error;
    
    /*Metodo para llenar la rata de aprendizaje, la matriz de peso y los umbrales*/
    /*public Perceptron(int[][] Salidas, int[][] Entradas, double aprendizaje)
    {
        error = new XYSeries("Error Por Iteración");
        entradas = Entradas;
        salidas = Salidas;
        ErrorPatron = new double[entradas.length];
        int fila = salidas[0].length;
        int columna = entradas[0].length;
        rataAprendizaje = aprendizaje;
        matrizPeso = new double[fila][columna];
        umbral = new double[fila];
        yr = new double [fila];
        ErrorLineal = new double[fila];
    }*/
    
    private void inicializarVariables(int[][] Salidas, int[][] Entradas, double aprendizaje){
        error = new XYSeries("Error Por Iteración");
        entradas = Entradas;
        salidas = Salidas;
        ErrorPatron = new double[entradas.length];
        int fila = salidas[0].length;
        int columna = entradas[0].length;
        rataAprendizaje = aprendizaje;
        yr = new double [fila];
        ErrorLineal = new double[fila];
        this.inicializarPesos(fila, columna);
    }
    
    private void inicializarPesos(int fila, int columna){
        matrizPeso = new double[fila][columna];
        umbral = new double[fila];
        File p = new File("MatrizDePeso.txt");
        File u = new File("Umbrales.txt");
        Archivo arc = new Archivo();
        if(p.exists() == true && u.exists() == true){
            System.out.println(p.exists()+ " " + u.exists());
            matrizPeso = arc.leerMatriz(fila, columna, "MatrizDePeso");
            umbral = arc.leerUmbrales(umbral.length, "Umbrales");
        }
        else{
            for (int i = 0; i < fila; i++) {
                umbral[i] = aleatorioTruncado(numeroAleatorio());
                for (int j = 0; j < columna; j++) {
                    matrizPeso[i][j] = aleatorioTruncado(numeroAleatorio());
                }
            }
        }
    }
    
    private double numeroAleatorio()
    {
        double aleatorio = (double) (Math.random() * -2.1+1.1);
        return aleatorio;
    }
    
    private void limpiarVariables()
    {
        int fila = salidas[0].length;
        int columna = entradas[0].length;
        ErrorPatron = new double[entradas.length];
        yr = new double [fila];
    }
    
    /*Metodo para generar numero aleatorio entre -1 y 1 truncado con 1 decimal*/
    public double aleatorioTruncado(double aleatorio)
    {
        double truncado = (double) ((int)(aleatorio * 100000)/100000.0);
        return truncado;
    }
    
    public void iniciarEntrenamiento(int[][] Salidas, int[][] Entradas, double aprendizaje, int iteraciones) throws IOException
    {
        this.inicializarVariables(Salidas, Entradas, aprendizaje);
        int i;
        Archivo a = new Archivo();
        this.inicializarPesos(salidas[0].length, entradas[0].length);
        for (i = 0; i < iteraciones; i++) {
            double suma = 0;
            for (int j = 0; j < entradas.length; j++) {
                ErrorLineal = new double[salidas[0].length];
                int entrada [] = entradas[j];
                int salida [] = salidas[j];
                this.Entrenar(entrada, salida);
                ErrorPatron[j] = this.calcularErrorPatron();
                suma = suma + ErrorPatron[j];
                this.calcularNuevaMatrizPeso(entrada);
            }
            double ErrorIteracion = suma/salidas.length;
            error.add(i+1,ErrorIteracion);
            if(ErrorIteracion <= errorMaximo){
                System.out.println("Solución Encontrada en la iteración: " + (i+1));
                JOptionPane.showMessageDialog(null, "La red ya está entrenada");
                this.limpiarVariables();
                i = iteraciones;
            }
            this.guardarUmbralesYPesosOptimos();
            this.limpiarVariables();
        }
        /*if(i == iteraciones){
            this.inicializarPesos(salidas[0].length, entradas[0].length);
            error.clear();
            //this.iniciarEntrenamiento(Salidas, Entradas, aprendizaje, iteraciones);
        }
        else{
            this.guardarUmbralesYPesosOptimos();
            //a.Escribir(matrizPeso, "MatrizDePeso");
            //a.Escribir(umbral, "Umbrales");
            
            Estadistica est = new Estadistica(error);
            JOptionPane.showMessageDialog(null, "La red ya está entrenada");
            est.GenerarEstadisticas();
        }*/
        Estadistica est = new Estadistica(error);
        est.GenerarEstadisticas();
    }
    
    public void Entrenar(int[] entrada, int[] salida)
    {
        for (int i = 0; i < salida.length; i++) {
            double suma = 0;
            for (int j = 0; j < entrada.length; j++) {
                suma = suma + (entrada[j] * matrizPeso[i][j]);
            }
            suma = suma - umbral[i];
            yr[i] = this.funcionEscalone(suma);
        }
        this.calcularErrorLineal(salida);
    }
    
    public int simular(int[] entrada, int fila)
    {
        this.inicializarPesos(fila, entrada.length);
        int respuesta = 0;
        for (int i = 0; i < fila; i++) {
            double suma = 0;
            for (int j = 0; j < entrada.length; j++) {
                suma = suma + (entrada[j] * matrizPeso[i][j]);
            }
            suma = suma - umbral[i];
            respuesta = (int)this.funcionEscalone(suma);
        }
        return respuesta;
    }
    
    private void guardarUmbralesYPesosOptimos(){
        Archivo arc = new Archivo();
        String texto = ""; 
        String textoU = "";
        for (int i = 0; i < matrizPeso.length; i++) {
            textoU = textoU + String.valueOf(umbral[i]) + ",";
            for (int j = 0; j < matrizPeso[i].length; j++) {
                texto = texto + String.valueOf(matrizPeso[i][j]) + ",";
            }
            texto = texto + ";";
        }
        arc.escribirArchivo("MatrizDePeso.txt", texto);
        arc.escribirArchivo("Umbrales.txt", textoU);
    }
    
    private double funcionEscalone(double num)
    {
        double x;
        if(num > 0){
            x = 1;
        }
        else{
            x = 0;
        }
        return x;
    }
    
    private void calcularErrorLineal(int[] salida)
    {
        for (int i = 0; i < salida.length; i++) {
            ErrorLineal[i] = salida[i] - yr[i];
        }
    }
    
    private double calcularErrorPatron()
    {
        double suma = 0;
        for (int i = 0; i < salidas[0].length; i++) {
            suma = Math.abs(suma + ErrorLineal[i]);
        }
        suma = suma/salidas[0].length;
        return suma;
    }
    
    private void calcularNuevaMatrizPeso(int[] entrada)
    {
        for (int i = 0; i < matrizPeso.length; i++) {
            //if(ErrorLineal[i] != 0){
                //umbral[i] = umbral[i] + (rataAprendizaje * ErrorLineal[i]);
            //}
            for (int j = 0; j < matrizPeso[i].length; j++) {
                if(ErrorLineal[i] != 0){
                    matrizPeso[i][j] = matrizPeso[i][j] + (rataAprendizaje * ErrorLineal[i] * entrada[j]);
                }
            }
        }
    }
}
