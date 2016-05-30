/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unicapa;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Erley
 */
public class Estadistica {
    
    XYSeries error;
    
  public Estadistica(XYSeries e){
      this.error = e;
  }
  
  public void GenerarEstadisticas(){
       XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(error);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Error Por iteracion", // TÃ­tulo
                "Iteraciones", // Etiqueta Coordenada X
                "Error", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );
        
         // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Ejemplo Grafica Lineal", chart);
        frame.pack();
        frame.setVisible(true);
  }
}
