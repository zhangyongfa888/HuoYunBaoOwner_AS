package zhidanhyb.huozhu.Bean;
/**
 * 百度地图在线搜索地图结果的Bean
 * @author lxj
 *
 */
public class SuggestionInfoBean {

	private String city;//城市
	private String district;//区
	private String key;//搜索的key
	private LatLng latlng;//经纬度
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public LatLng getLatlng() {
		return latlng;
	}
	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}
	
	
}
