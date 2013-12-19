/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbclient;

import dbclient.datastructs.DirectoryDataStruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad Slepukhin <slepukhinvd@sgu.ru>
 */
public class PrintDirs {

    private static Connection c;

    public PrintDirs(Connection c) {
        this.c = c;
    }

    public List<DirectoryDataStruct> getDirs() {
        List<DirectoryDataStruct> result = new ArrayList<>();

        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM dirs;");
            while (rs.next()) {
                DirectoryDataStruct dds = new DirectoryDataStruct();
                dds.id = rs.getInt("id");
                dds.displayname = rs.getString("displayname");
                dds.path = rs.getString("path");
                dds.tablename = rs.getString("tablename");

                result.add(dds);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrintDirs.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
