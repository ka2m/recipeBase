/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbclient;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class ReplaceMeals {
    private static String table;
    private static String path;
    private static String displayname;
    private static String old;
    
    private static Connection c;
    
    public ReplaceMeals(String tableName, File f, String oldFile, Connection con)
    {
            table = tableName;
            path = f.getPath().substring(f.getPath().lastIndexOf("\\")+1,f.getPath().length());
            displayname = f.getPath().substring(f.getPath().lastIndexOf("\\")+1,f.getPath().lastIndexOf("."));
            old = oldFile;
            c = con;
    }
    
    public void doReplace()
    {
        try {
            Statement stmt = c.createStatement();
            String sql = "UPDATE "+table+" SET path='"+path+"',displayname='"+displayname+"' WHERE displayname='"+old+"';";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ReplaceMeals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
