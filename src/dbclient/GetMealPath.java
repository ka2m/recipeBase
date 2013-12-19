/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class GetMealPath {

    public String get(String table, String displayname, Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT path FROM "+table+" WHERE displayname='"+displayname+"';";
            ResultSet rs =  stmt.executeQuery(sql);
            return rs.getString("path");
            
        } catch (SQLException ex) {
            Logger.getLogger(GetMealPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;

    }
}
