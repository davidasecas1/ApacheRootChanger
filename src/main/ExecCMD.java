package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecCMD {
	public static void ejecutarCMD(String[] cmd){
	    Process p;
	    try {
	      p = Runtime.getRuntime().exec(cmd);
	      p.waitFor();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	      String line = "";
	      while ((line = reader.readLine())!= null) {
	        System.out.println(line);
	      }
	      reader.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
