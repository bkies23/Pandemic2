/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import com.toedter.calendar.JDateChooser;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class InsertRow 
{
    private Connection myConn;
    private GetConnection gc;
    private GetResultSet grs;
    private ResultSet rs;
    Date currentDate = null, lastDate;
    int newCases, newDeaths, correctedNewCases = 0, correctedNewDeaths = 0, currentRow, lastRow;
    JFrame messageFrame = new JFrame();
    
    public boolean InputDataValid(String newCasesStr, String newDeathsStr,JDateChooser JInsertDateChooser ) throws SQLException
    {
        java.util.Date dateToInsert = JInsertDateChooser.getDate();
        if (JInsertDateChooser.getDate() == null)
         {
            JOptionPane.showMessageDialog(messageFrame, "Enter Date To Insert.");
            return false;
         }
        else if (Reference.SearchResultSetForRow(dateToInsert))
        {
            JOptionPane.showMessageDialog(messageFrame, "A Row For This Date Already Exists.");
            return false;
        }
        else if (newCasesStr.trim().isEmpty() && newDeathsStr.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter New Cases And Deaths.");
            return false;
        }
        else if ( newCasesStr.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter New Cases.");
            return false;
        }
        else if ( newDeathsStr.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter New Deaths.");
            return false;
        }
        
        return true;
    }
    
    public boolean InsertRow(String newCasesStr, String newDeathsStr, JDateChooser JInsertDateChooser) throws SQLException, ParseException
    {
        gc = new GetConnection();
        myConn = gc.init( );
        java.util.Date dateToInsert = JInsertDateChooser.getDate();
        int prevTotalCases, prevTotalDeaths, prevDayCases, prevDayDeaths;
        int prevRow, totalCases, totalDeaths;
        long diff;
        java.util.Date dayBefore;
        java.sql.Date sqlDate;
        double percentChangeInCases, percentChangeInDeaths, deathPercent;
        String strPercentChangeInCases, strPercentChangeInDeaths, strDeathPercent, prevDate;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        
       
        grs = new GetResultSet();
        rs = grs.getResultSet();
    
        if (InputDataValid(newCasesStr, newDeathsStr, JInsertDateChooser ))
        {
            newCases = Integer.parseInt(newCasesStr.trim());
            newDeaths = Integer.parseInt(newDeathsStr.trim());
            diff = dateToInsert.getTime();
            diff -= 1 * 24 * 60 * 60 * 1000;
            dayBefore = new java.util.Date(diff);
           // convert java.util.date to java.sql.date for database table
            sqlDate = new java.sql.Date(dayBefore.getTime());
            prevRow = Reference.FindCurrentRow(sqlDate);
            rs.absolute(prevRow);
        }
        
        // convert java.util.date to java.sql.date for database table
        sqlDate = new java.sql.Date(dateToInsert.getTime());
        prevTotalCases = rs.getInt(2);
        totalCases = prevTotalCases + newCases;
        prevTotalDeaths = rs.getInt(3);
        totalDeaths = prevTotalDeaths + newDeaths;
        prevDayCases = rs.getInt(4);
        prevDayDeaths = rs.getInt(5);
        percentChangeInCases = ((float)newCases / prevDayCases - 1) * 100;
        strPercentChangeInCases = decimalFormat.format(percentChangeInCases);
        strPercentChangeInCases += "%";
          
        if (newDeaths == 0)
            strPercentChangeInDeaths = "0.00%";
        else
        {
            percentChangeInDeaths = ((float)newDeaths / prevDayDeaths - 1) * 100;
            strPercentChangeInDeaths = decimalFormat.format(percentChangeInDeaths);
            strPercentChangeInDeaths += "%";
        }
                
        deathPercent = ((float)totalDeaths / totalCases)*100.0;
        strDeathPercent = decimalFormat.format(deathPercent);
        strDeathPercent += "%";
     
        PreparedStatement stmt = myConn.prepareStatement("INSERT INTO covid19table (Date, Total_Cases, Total_Deaths, New_Cases, New_Deaths, "
                                                         + "Change_In_Cases, Change_In_Deaths, Death_Percent) VALUE (?, ?, ?, ?, ?, ?, ?, ?)");
        
        stmt.setDate(1, sqlDate);
        stmt.setInt(2, totalCases);
        stmt.setInt(3, totalDeaths);
        stmt.setInt(4, newCases);
        stmt.setInt(5, newDeaths);
        stmt.setString(6, strPercentChangeInCases);
        stmt.setString(7, strPercentChangeInDeaths);
        stmt.setString(8, strDeathPercent);   
        stmt.execute();
        Reference.UPDATE_MADE = true;
        
        myConn.close();
        rs.close();               
        return true;     
    }                                 
}
