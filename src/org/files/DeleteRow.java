/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DeleteRow 
{
    private Connection myConn;
    private GetConnection gc;
    private GetResultSet grs;
    ResultSet rs;
    JFrame messageFrame = new JFrame();
    
    public boolean InputDataValid(java.util.Date dateToDelete, JDateChooser JDeleteDateChooser) throws SQLException
    {
        java.sql.Date sqlDateToDelete;
        String message1 = "Enter date to delete.";
        String message2 = "You are not permitted to delete 2021-03-01 as it is the first date in table.";
        String message3 = "You can only delete one row at a time.";
  
        if ( dateToDelete == null)
        {
            JOptionPane.showMessageDialog(messageFrame, message1);
            return false;
        }
        else
            sqlDateToDelete = new java.sql.Date(dateToDelete.getTime());
        
        if (sqlDateToDelete.toString().equals("2020-03-01"))
        {
            JOptionPane.showMessageDialog(messageFrame, message2);
            return false;  
        }
        else if (Reference.ROW_DELETED)
        {
            JOptionPane.showMessageDialog(messageFrame, message3);
            JDeleteDateChooser.setDate(null);
            return false;  
        }
      
        else if (Reference.SearchResultSetForRow(dateToDelete))
        {
            String message4 = "Are you sure you want to delete " + sqlDateToDelete + "?";
            int input = JOptionPane.showConfirmDialog(messageFrame, message4, "Delete", JOptionPane.YES_NO_OPTION);
           
            if ( input == 0)
            {
             //   Reference.ROW_DELETED = false;
                JDeleteDateChooser.setCalendar(null);
                return true;
            }   
            else if ( input == 1)
                JDeleteDateChooser.setCalendar(null);
         //   else if ( input == 2)
         //       JDeleteDateChooser.setCalendar(null);
            //    ;
        }
        else
        {
              sqlDateToDelete = new java.sql.Date(dateToDelete.getTime());
              String message5 = "No row exists for " + sqlDateToDelete + ".";
              JOptionPane.showMessageDialog(null, message5);
              JDeleteDateChooser.setDate(null);
        }
                
        return false;
    }
    
    public boolean DeleteRow(java.util.Date dateToDelete, JDateChooser JDeleteDateChooser) throws SQLException
    {
        JFrame messageFrame = new JFrame( ); 
        myConn = gc.init();
             
        String message1 = "Are you sure you want to delete " + dateToDelete + "?";
        String message2 = "No row exists for " + dateToDelete + ".";
       

        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        if (InputDataValid(dateToDelete, JDeleteDateChooser))
        {
            PreparedStatement stmt = myConn.prepareStatement("DELETE FROM covid19table WHERE Date = ?");
            java.sql.Date sqlDateToDelete = new java.sql.Date(dateToDelete.getTime());
            stmt.setDate(1, sqlDateToDelete);
            stmt.executeUpdate();
            Reference.UPDATE_MADE = true;
        //    Reference.ROW_DELETED = true;
            myConn.close();
            return true;
        }
    //    else
      //      JOptionPane.showMessageDialog(null, message2); 
                            
        return false;              
    }
    
}
