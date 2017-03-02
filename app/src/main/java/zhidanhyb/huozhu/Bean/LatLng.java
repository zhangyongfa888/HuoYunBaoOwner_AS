package zhidanhyb.huozhu.Bean;

/**
 * 百度地图在线搜索地图结果的经纬度Bean
 * @author lxj
 *
 */
public class LatLng {

	private double latitude; //纬度
	private double longitude;//经度
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
