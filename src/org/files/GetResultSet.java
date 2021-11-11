/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetResultSet
{
    Connection myCon;
    ResultSet rs;
    
    public ResultSet getResultSet ( ) throws SQLException
    {
        GetConnection newConnection = new GetConnection();
        myCon = newConnection.init();
        PreparedStatement stmt = myCon.prepareStatement("SELECT * FROM covid19table", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
        rs = stmt.executeQuery();
       
        return rs;
    } 
    
}
