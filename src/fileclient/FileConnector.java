/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.StandardCopyOption.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fau
 */
public class FileConnector {

    private String currentDirectory;

    public void setDir(String dir) {
        currentDirectory = dir;
    }

    public void printDir() {
        System.out.println(currentDirectory);
    }

    public void deleteFile(String path) {
        String s = currentDirectory + path;
        File f = new File(s);
        f.delete();
    }

    public void addFiles(List<File> fl) {
        for (File f : fl) {
            try {
                System.out.println(f.getAbsoluteFile().toPath().toString());
                File t = new File(currentDirectory + f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\"), f.getAbsolutePath().length()));
                Files.copy(f.getAbsoluteFile().toPath(), Paths.get(t.toURI()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(FileConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void replaceFile(String old, File newf) {
        deleteFile(old + ".docx");
        List<File> f = new ArrayList<>();
        f.add(newf);
        addFiles(f);
    }
}
