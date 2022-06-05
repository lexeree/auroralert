package auroralert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.*;

public class AuroraStatus {
	String RT_URL = "https://services.swpc.noaa.gov/json/planetary_k_index_1m.json";
	String PRED_URL = "https://services.swpc.noaa.gov/json/geospace/geospace_pred_est_kp_1_hour.json";
	float rtKp;
	float predKp;
    
	public AuroraStatus() {
    	rtKp = 0;
    	predKp = 0;
    }
	
	public float getRT() {
		return rtKp;
	}
	
	public float getPred() {
		return predKp;
	}
	
	public String build_note(String type, float kp) {
		String pref;
		String mod;
		if(type.equals("pred")) {
			pref = "WARNING (1 hour): ";
			mod = "predicted";
		} else {
			pref = "ALERT (NOW): ";
			mod = "estimated";
		}
		String note;
		if(kp == 0.0) {
			note = "No activity "+mod+".";
		} else if(kp>0.0 && kp<3.0){
			note = pref + "low activity "+mod+" Kp = "+String.valueOf(kp);
		} else if (kp>=3.0 && kp<5.0){
			note = pref + "moderate activity "+mod+" Kp = "+String.valueOf(kp);
		} else if (kp>=5.0 && kp<6.0) {
			note = "G1 geomagnetic storm "+mod+"!";
		} else if (kp>=6.0 && kp<7.0) {
			note = "G2 geomagnetic storm "+mod+"!";
		}else if (kp>=7.0 && kp<8.0) {
			note = "G3 geomagnetic storm "+mod+"!";
		}else if (kp>8.0 && kp<9.0) {
			note = "G4 geomagnetic storm "+mod+"!";
		}else if (kp>= 9.0) {
			note = "G5 geomagnetic storm "+mod+"!";
		} else {
			note = "";
		}
		return note;
	}
	
	public void update() throws JSONException, IOException {
		rtKp = fetchRecord(RT_URL).getLong("kp_index");
		predKp = fetchRecord(PRED_URL).getLong("k");
	}
	
	public JSONObject fetchRecord(String url) throws JSONException, IOException {
		String json = read(url);
		JSONArray list = new JSONArray(json);
		JSONObject last = (JSONObject) list.get(list.length() - 1);
		return last;
	}	
	
	public String read(String url) throws IOException {
		URL link = new URL(url);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader (new InputStreamReader(link.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1) {
	            buffer.append(chars, 0, read); 
	        }
	        return buffer.toString();
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}

}
