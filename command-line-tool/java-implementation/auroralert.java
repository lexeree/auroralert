package auroralert;

import java.util.Scanner;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

public class auroralert {

	public static void main(String[] args) throws JSONException, IOException, AWTException, InterruptedException {
		String m = args[0];
		int mins = Integer.parseInt(m);
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to auroralert!");
		
		boolean done = false;
		String mode = "";
		int thresh = 0;
		while(!done) {
			System.out.print("Please choose a mode (update, manual, geographic): ");
			mode = scan.next();
			if(mode.equals("update")) {
				done = true;
			} 
		    else if(mode.equals("manual")) {
				System.out.print("Please enter min Kp for an alert: ");
				thresh = scan.nextInt();
				done = true;
			} 
			else if(mode.equals("geographic")) {
				System.out.print("Please enter your geographic latitude: ");
				double lat = scan.nextDouble();
				System.out.print("Please enter your geographic longitude: ");
				double lon = scan.nextDouble();
				Coordinates coord = new Coordinates(lat, lon);
				thresh = coord.minKp();
				System.out.println("Minimum visible Kp is "+String.valueOf(thresh)+".");
				done = true;
			} else {
				System.out.println("Sorry, that's not an option.");
			}
		}
		System.out.println("Monitoring geomagnetic activity now...");
		scan.close();
		while(true) {
			AuroraStatus status = new AuroraStatus();
			status.update();
			float rt_kp = status.getRT();
			String rt_msg = status.build_note("rt", rt_kp);
			String pred_msg = "";
			if(mode.equals("update")) {
				float pred_kp = status.getPred();
				pred_msg = status.build_note("pred", pred_kp);
			}		
			if(rt_kp >= thresh) {
				Image image = Toolkit.getDefaultToolkit().createImage("");
				SystemTray tray = SystemTray.getSystemTray();
				TrayIcon trayIcon = new TrayIcon(image, "auroralert");
				tray.add(trayIcon);
				trayIcon.displayMessage("Auroralert!", rt_msg, MessageType.NONE);
				if(mode.equals("update")) {
					trayIcon.displayMessage("Auroralert!", pred_msg, MessageType.NONE);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
			TimeUnit.MINUTES.sleep(mins);
		}
	}

}
