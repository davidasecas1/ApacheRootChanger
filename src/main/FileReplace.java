package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileReplace {
    

    public void  replace(String fileRoute, String dirRoot) {
        try {
        	ArrayList<String> lines = new ArrayList<String>();
            String line = null;
        	copyFile(fileRoute, String.format("%s.backup",fileRoute));
            File f1 = new File(fileRoute);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            boolean found1 = false; boolean found2 = false;
            while ((line = br.readLine()) != null) {
                if (!found1 && line.contains("DocumentRoot \"")) {
                    line = String.format("DocumentRoot \"%s\"", dirRoot);
                    found1 = true;
                }
                if (!found2 && line.contains("<Directory \"")) {
                    line = String.format("<Directory \"%s\">", dirRoot);
                    found2 = true;
                }
                lines.add(line+"\n");
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                 out.write(s);
            out.flush();
            out.close();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public boolean copyFile(String fromFile, String toFile) {
        File origin = new File(fromFile);
        File destination = new File(toFile);
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                // We use a buffer for the copy (Usamos un buffer para la copia).
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}