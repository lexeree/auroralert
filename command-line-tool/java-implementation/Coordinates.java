package auroralert;


public class Coordinates {
	double latitude;
	double longitude;
	
	//geomagnetic north coordinates (from https://wdc.kugi.kyoto-u.ac.jp/poles/polesexp.html):
	double geomN[] = {80.7, -72.7}; //in geographic coordinates
	
	//we have to translate geomN[0] to (pi/2 - geomN[0])
	//formula for the following transformation to geomagnetic coordinates can be found here: https://www.spenvis.oma.be/help/background/magfield/cd.html
	double transformation[][] = {{Math.sin(to_rad(geomN[0]))*Math.cos(to_rad(geomN[1])), Math.sin(to_rad(geomN[0]))*Math.sin(to_rad(geomN[1])), -Math.cos(to_rad(geomN[0]))},
			                     {-Math.sin(to_rad(geomN[1])), Math.cos(to_rad(geomN[1])), 0},
	                             {Math.cos(to_rad(geomN[0]))*Math.cos(to_rad(geomN[1])), Math.cos(to_rad(geomN[0]))*Math.sin(to_rad(geomN[1])), Math.sin(to_rad(geomN[0]))},
	                             };
	double cartesian[];
	
	public Coordinates(double lat, double lon) {
		latitude = lat;
		longitude = lon;
		cartesian = as_cartesian();
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double[] as_cartesian() {
		//over unit sphere
		//note that for latitude we transform theta to (pi/2 - theta)
		double x = Math.cos(to_rad(latitude))*Math.cos(to_rad(longitude));
		double y = Math.cos(to_rad(latitude))*Math.sin(to_rad(longitude));
		double z = Math.sin(to_rad(latitude));
		double c[] = {x, y, z};
		return c;
	}
	
	public double to_rad(double deg) {
		return deg*Math.PI/180.0;
	}
	
	public double to_deg(double rad) {
		return rad*180.0/Math.PI;
	}
	
	public double getGeomagneticLatitude() {
		double cart[] = new double[3];
		for(int i=0;i<3;i++) {
			cart[i] = 0;
			for(int j=0;j<3;j++) {
				cart[i] += transformation[i][j]*cartesian[j];
			}
		}
		//back to geographic spherical coordinates
		double lat = Math.PI/2 - Math.atan(Math.sqrt(cart[0]*cart[0] + cart[1]*cart[1])/cart[2]);
		return to_deg(lat);
	}
	
	public int minKp() {
		double lat = getGeomagneticLatitude();
		int kp;
		if(lat >= 66.5) {
			kp = 0;
		} else if (lat < 66.5 && lat >= 64.5) {
			kp = 1;
		} else if (lat < 64.5 && lat >= 62.4) {
			kp = 2;
		} else if (lat < 62.4 && lat >= 60.4) {
			kp = 3;
		} else if (lat < 60.4 && lat >= 58.3) {
			kp = 4;
		} else if (lat < 58.3 && lat >= 56.3) {
			kp = 5;
		} else if (lat < 56.3 && lat >= 54.2) {
			kp = 6;
		} else if (lat < 54.2 && lat >= 52.2) {
			kp = 7;
		} else if (lat < 52.2 && lat >= 50.1) {
			kp = 8;
		} else if (lat < 50.1 && lat >= 50.1) {
			kp = 9;
		} else {
			kp = 10;
		}
		return kp;
	}

}
