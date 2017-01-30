/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable.model.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Emilia
 */
public final class Serializator {

    private static final String PATH = "src/serializable/model/data/";
    private static final Object lock = new Object();

    private Serializator() {
    }

    public static synchronized boolean writeToFile(Serializable object, String fileName) {

        try {
            File file = new File(PATH + fileName + ".ser");
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            System.out.println("Object succesfully saved as " + fileName);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public static synchronized Serializable readFromFile(String fileName) {
        Serializable obj = null;
        try {
            FileInputStream fis = new FileInputStream(PATH + fileName + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = (Serializable) ois.readObject();
            ois.close();
            System.out.println("Object succesfully read from " + fileName);
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj;
    }

    public static synchronized ArrayList<Serializable> readAllFromFolder(String folderName) {
        ArrayList<Serializable> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(PATH + folderName))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {

                    Serializable obj = null;

                    try {
                        FileInputStream fis = new FileInputStream(filePath.toString());
                        ObjectInputStream ois;
                        ois = new ObjectInputStream(fis);
                        obj = (Serializable) ois.readObject();
                        ois.close();
                        System.out.println("Object succesfully read from " + filePath.getFileName());
                    } catch (Exception ex) {
                        Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (obj != null) {
                        files.add(obj);
                    }

                }
            });

        } catch (IOException ex) {
            Logger.getLogger(Serializator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return files;
    }

}
