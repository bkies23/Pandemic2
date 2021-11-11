/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static org.files.Reference.SearchResultSetForRow;


public class ModifyRow 
{
      int choice = 0, correctedNewCases = 0, correctedNewDeaths = 0, currentRow = 0, lastRow = 0;
      ResultSet rs;
      Date firstDate = null, lastDate = null;
      java.util.Date dateToModify;
      SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
      GetConnection newConnection = new GetConnection();
      GetResultSet grs = new GetResultSet();
     
    public boolean InputDataValid (String casesToModify, String deathsToModify, JDateChooser JNewDateChooser) throws SQLException
    {
        dateToModify = JNewDateChooser.getDate();
        // for various message dialog boxes
        JFrame messageFrame = new JFrame();
        grs = new GetResultSet();
        rs = grs.getResultSet();
        rs.first();
        firstDate = rs.getDate(1);
        dateToModify = Reference.RemoveTime(dateToModify);
        rs.last();
        lastDate = rs.getDate(1);
    
        String message1 = "Enter Date.";
        String message2 = "Modify Date cannot be before " + firstDate + ".";
        String message3 = "Modify Date cannot be after " + lastDate + ".";
        String message4 = "Enter New Cases or New Deaths or both.";
        String message5 = "Input data must be integer.";
        String message6 = "Row does not exist."; 
        
       
        if (dateToModify == null)
        {
            JOptionPane.showMessageDialog(messageFrame, message1);
            return false;
        }
        
        else if (!SearchResultSetForRow(dateToModify))
        {
             JOptionPane.showMessageDialog(messageFrame, message6);
             return false;
        }
        
        else if (dateToModify.before(firstDate))
        {
            JOptionPane.showMessageDialog(null, message2);
            JNewDateChooser.setCalendar(null);
            return false;
        }
        
        else if (dateToModify.after(lastDate))
        {
            JOptionPane.showMessageDialog(null, message3);
            JNewDateChooser.setCalendar(null);
            return false;    
        }
        
        else if (casesToModify.isEmpty() && deathsToModify.isEmpty())
        {
            JOptionPane.showMessageDialog(null, message4);
            return false;
        }
        
        else
            return true;
    }
    
    public boolean ModifyRow (String casesToModify, String deathsToModify, JDateChooser JModifyDateChooser, JTextField JModifyCasesTF, JTextField JModifyDeathsTF) throws ParseException, SQLException
    {
       
        if (InputDataValid(casesToModify, deathsToModify, JModifyDateChooser ))
        {
            java.util.Date dateToModify = JModifyDateChooser.getDate();
            java.sql.Date sqlDateToModify = new java.sql.Date (dateToModify.getTime());
            if (!casesToModify.isEmpty() && !deathsToModify.isEmpty() )
            {
                correctedNewCases = Integer.parseInt(casesToModify);
                correctedNewDeaths = Integer.parseInt(deathsToModify);
                choice = 1;
                Reference.UPDATE_MADE = true;
            }
            else if (!casesToModify.isEmpty())
            {
                correctedNewCases = Integer.parseInt(casesToModify);
                choice = 2;
                Reference.UPDATE_MADE = true;
            }
            else if (!deathsToModify.isEmpty() )
            {
                correctedNewDeaths = Integer.parseInt(deathsToModify);
                choice = 3;
                Reference.UPDATE_MADE = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No changes entered.");
            }
       
            switch (choice)
            {
                case 1:
                try
                {
                    rs = grs.getResultSet();
                    currentRow = Reference.FindCurrentRow(dateToModify);
                    rs.absolute(currentRow);
                    int currentNewCases = rs.getInt(4);
                    int currentNewDeaths = rs.getInt(5);

                    JModifyCasesTF.setText("");
                    JModifyDeathsTF.setText("");
                    JModifyDateChooser.setCalendar(null);

                    Reference.UpdateCaseColumns(currentRow, sqlDateToModify, lastRow, currentNewCases, correctedNewCases);
                    Reference.UpdateDeathColumns(currentRow, sqlDateToModify, lastRow, currentNewDeaths, correctedNewDeaths );
                    return true;
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
                case 2:
                try
                {
                    rs = grs.getResultSet();
                    currentRow = Reference.FindCurrentRow(dateToModify);
                    rs.absolute(currentRow);
                    int currentNewCases = rs.getInt(4);

                    JModifyCasesTF.setText("");
                    JModifyDateChooser.setCalendar(null);

                    Reference.UpdateCaseColumns(currentRow, sqlDateToModify, lastRow, currentNewCases, correctedNewCases);
                    return true;
                }
                catch (SQLException | ParseException ex)
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
                case 3:
                try
                {
                    rs = grs.getResultSet();
                    currentRow = Reference.FindCurrentRow(dateToModify);
                    rs.absolute(currentRow);
                    int currentNewDeaths = rs.getInt(5);

                    JModifyDeathsTF.setText("");
                    JModifyDateChooser.setCalendar(null);

                    Reference.UpdateDeathColumns(currentRow, sqlDateToModify, lastRow, currentNewDeaths, correctedNewDeaths);
                    return true;
                }
                catch (SQLException | ParseException ex)
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            
        return false;
    }
   
}
