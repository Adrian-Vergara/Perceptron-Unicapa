/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicapa;

/**
 *
 * @author Adrian
 */
public class Unicapa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int [][] entradas = {{1,1},{1,0},{0,1},{0,0}};
        int [][] salidas = {{1}, {0}, {0}, {0}};
        Perceptron p = new Perceptron(salidas, entradas);
        p.iniciarEntrenamiento(500);
        /*for (int i = 0; i < entradas.length; i++) {
            for (int j = 0; j < entradas[i].length; j++) {
                System.out.print(entradas[i][j] + " ");
            }
            System.out.println(" ");
        }*/
    }
    
}
