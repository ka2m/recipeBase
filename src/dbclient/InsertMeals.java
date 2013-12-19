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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class InsertMeals {

    private String table;
    private List<File> files;
    private Connection c;

    public InsertMeals(String tableName, List<File> files, Connection c) {
        table = tableName;
        this.files = files;
        this.c = c;
    }

    public void doInsert() {
        for (File f : files) {
            try {
                Statement stmt = c.createStatement();
                String sql;

                sql = "INSERT INTO " + table + " (displayname,path) " + "VALUES('"
                        + f.getPath().substring(f.getPath().lastIndexOf('\\') + 1, f.getPath().lastIndexOf(".")).toString() + "','"
                        + f.getPath().substring(f.getPath().lastIndexOf('\\') + 1).toString() + "');";
                stmt.executeUpdate(sql);
                c.commit();
            } catch (SQLException ex) {
                Logger.getLogger(InsertMeals.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
