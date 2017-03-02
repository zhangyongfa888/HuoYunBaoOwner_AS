package zhidanhyb.huozhu.Bean;

import java.io.Serializable;

import zhidanhyb.huozhu.Utils.StringUtil;

/**
 * 订单列表的Bean
 * 
 * @author lxj
 *
 */
public class OrderListBean implements Serializable {

	private String id;// 订单id
	private String price;// 价格
	private String estimate_price = "0元";// 估价
	private String status;// 订单状态 1：待接单，2：待选司机，3：等待付款，5：已完成，9：已取消]
	private String goodstype;// 货物类型
	private String departure_time;// 发车时间
	private String starCity;// 起点城市
	private String remarks;// 备注
	private String endCity;// 终点城市
	private String driver;// 司机姓名
	private String is_commen;// 是否评价是否评价 0 双方都未评 1 货主已评 2 司机已评 3双方互评

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
		if (StringUtil.isEmpty(price)) {
			this.price = "0元";
		}

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public String getStarCity() {
		return starCity;
	}

	public void setStarCity(String starCity) {
		this.starCity = starCity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getIs_commen() {
		return is_commen;
	}

	public void setIs_commen(String is_commen) {
		this.is_commen = is_commen;
	}

	public String getEstimate_price() {
		return estimate_price;
	}

	public void setEstimate_price(String estimate_price) {
		this.estimate_price = estimate_price;
	}

}
