package zhidanhyb.huozhu.Bean;

/**
 * 所有司机的经纬度
 * @author lxj
 *
 */
public class AllDriverLocationBean {
	private double lat;//纬度
	private double lng;//经度
	private String name;//车牌号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
}
