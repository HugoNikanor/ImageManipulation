package imageStitcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.exec.ExecuteWatchdog;

public class PowershellJava {

	public static ArrayList<String> powershell(String[] command) {
		
		ArrayList<String> output = new ArrayList<String>();
		try {
			
            //String command = "powershell.exe \"C:\\users\\hugo.hornquist\\script.ps1\"";
			//String[] command = {"powershell.exe", "(get-childitem).count"};
            ExecuteWatchdog watchdog = new ExecuteWatchdog(10000);
            Process powerShellProcess = Runtime.getRuntime().exec(command);
            if (watchdog != null) {
                watchdog.start(powerShellProcess);
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
            String line;
            System.out.println("Output :");
            while ((line = stdInput.readLine()) != null) {
                System.out.println(line);
            	output.add(line);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//System.out.println("output.get(0): " + output.get(0));
		//System.out.println("testMarker");
		
		return output;
		
	}
}
