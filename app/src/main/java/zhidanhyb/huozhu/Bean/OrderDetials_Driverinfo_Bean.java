package zhidanhyb.huozhu.Bean;
/**
 * 订单详情-Array 司机信息和坐标
 * @author lxj
 *
 */
public class OrderDetials_Driverinfo_Bean {

	private String id;//用户id
	private String score;//星级
	private String price;//司机的报价（如果报价了显示，否则为’’）
	private String level;//司机等级
	private String mobile;//司机手机号
	private String lat;//纬度
	private String lng;//经度
	private String name;//司机姓名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
