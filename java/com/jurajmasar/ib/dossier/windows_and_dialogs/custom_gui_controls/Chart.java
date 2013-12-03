package com.jurajmasar.ib.dossier.windows_and_dialogs.custom_gui_controls;

import java.awt.Color;

import com.jurajmasar.ib.dossier.root.Dialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import com.jurajmasar.ib.dossier.data_structures.Month;

/** DOSSIER: HL mastery 16 - use of additional libraries **/         
/**
 * Designes Chart object to be used in Profile Window.
 * 
 * @author Juraj Masar
 * @version 0.1
 */
public class Chart
{
    /**
     * Produces chart filled by information given in parameters.
     * 
     * @param months array with data
     * @return JFreeChart object
     */
    public static JFreeChart produce (Month[] months) 
    {
        //design
        JFreeChart chart = ChartFactory.createLineChart(
            "", // chart title
            "", // domain axis label
            "Points", // range axis label
            null, // data
            PlotOrientation.VERTICAL, // orientation
            true, // include legend
            true, // tooltips
            false // urls
        );       
        //set native background color
        chart.setBackgroundPaint(Dialog.mainWindow.getBackground());

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        //set background color for plot
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);        

        //set axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        try
        {
            NumberAxis rangeAxis2 = (NumberAxis)rangeAxis.clone();
            rangeAxis2.setLabel ("Distributors");
            plot.setRangeAxis(1,rangeAxis2);
            plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
            
            NumberAxis rangeAxis3 = (NumberAxis)rangeAxis.clone();
            rangeAxis3.setLabel ("Provision");
            plot.setRangeAxis(2,rangeAxis3);
            plot.setRangeAxisLocation(2, AxisLocation.TOP_OR_LEFT);
            
        } catch (java.lang.CloneNotSupportedException e) {}

        Axis axis = plot.getDomainAxis();
        axis.setTickMarksVisible(false);        
        ((CategoryAxis)axis).setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        
        //set renderer
        LineAndShapeRenderer renderer
        = (LineAndShapeRenderer) plot.getRenderer();
        //renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);

        //populate with data
        DefaultCategoryDataset disDataset = new DefaultCategoryDataset();        
        DefaultCategoryDataset pwDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset provisionDataset = new DefaultCategoryDataset();        
        for (int i=months.length-1;i>=0;i--)
        {
            pwDataset.addValue(
              months[i].getPwInt(),"PW",months[i].getMonth()+"/"+months[i].getYear()
            );
            pwDataset.addValue(
              months[i].getGwInt(),"GW",months[i].getMonth()+"/"+months[i].getYear()
            );            
            pwDataset.addValue(
              months[i].getGroupPwInt(),"Group PW",months[i].getMonth()+"/"+months[i].getYear());
            pwDataset.addValue(
              months[i].getGroupGwInt(),"Group GW",months[i].getMonth()+"/"+months[i].getYear());
           
            disDataset.addValue(
              months[i].getNumberOfAllChildren(),
              "All distributors",
              months[i].getMonth()+"/"+months[i].getYear()
            );
            disDataset.addValue(
              months[i].getNumberOfDirectChildren(),
              "Direct distributors",
              months[i].getMonth()+"/"+months[i].getYear()
            );            
           
            provisionDataset.addValue(
              months[i].getProvision(),
              "Provision",
              months[i].getMonth()+"/"+months[i].getYear()            
            );
        }
        
        //pin datasets to axes
        plot.setDataset(0, pwDataset);
        plot.setDataset(1, disDataset);
        plot.setDataset(2, provisionDataset);        
        plot.mapDatasetToRangeAxis(1,1);
        plot.mapDatasetToRangeAxis(2,2);        
        
        LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
        renderer1.setBaseToolTipGenerator(
                       new StandardCategoryToolTipGenerator());        
        //renderer1.setShapesVisible(true);
        renderer1.setDrawOutlines(true);
        renderer1.setUseFillPaint(true);                       
        plot.setRenderer(1,renderer1);

        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setBaseToolTipGenerator(
                       new StandardCategoryToolTipGenerator());        
        //renderer2.setShapesVisible(true);
        renderer2.setDrawOutlines(true);
        renderer2.setUseFillPaint(true);                       
        plot.setRenderer(2,renderer2);        
        
        return chart;
    }
}
