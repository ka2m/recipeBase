/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclient;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class DeleteMeals {

    private static String table;
    private static Connection c;
    private static String meal;

    public DeleteMeals(String tbl, String entry, Connection con) {
        table = tbl;
        meal = entry;
        c = con;
    }

    public void doDelete() {
        try {
            Statement stmt = c.createStatement();
            String sql;
            sql = "DELETE FROM "+table+" WHERE displayname='"+meal+"';";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DeleteMeals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
