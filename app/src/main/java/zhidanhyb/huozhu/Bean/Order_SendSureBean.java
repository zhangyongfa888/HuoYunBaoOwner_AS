package zhidanhyb.huozhu.Bean;

import java.io.Serializable;

/**
 * 货主发货前确认订单信息的Bean
 * 
 * @author lxj
 *
 */
public class Order_SendSureBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2533262009694494528L;
	private String choosetime;// 本地字段-选择的发车时间（这个字段主要是为了修改订单的时候需要填到textview上
								// 例：明天上午10点）
	private String time;// 发车时间 （这个字段是传到后台的发车时间 例：2016-4-1 10：00）
	private String starAdr;// 起点位置
	private String endAdr;// 终点位置
	private String starLat;// 起点纬度
	private String starLng;// 起点经度
	private String endLat;// 终点纬度
	private String endLng;// 终点经度
	private String goodsType;// 货物类型
	private String evaluate;// 估价
	private String orderDetail;// 订单详情
	private String id = "";

	public Order_SendSureBean() {
		super();
	}

	/**
	 * @param choosetime
	 *            //本地字段-选择的发车时间（这个字段主要是为了修改订单的时候需要填到textview上 例：明天上午10点）
	 * @param time
	 *            //发车时间 （这个字段是传到后台的发车时间 例：2016-4-1 10：00）
	 * @param starAdr
	 *            //起点位置
	 * @param endAdr;//终点位置
	 * @param starLat;//起点纬度
	 * @param starLng;//起点经度
	 * @param endLat;//终点纬度
	 * @param endLng;//终点经度
	 * @param goodsType;//货物类型
	 * @param evaluate;//估价
	 * @param orderDetail
	 * @param isPush;//
	 *            是否首页推送
	 * @param starCity;//
	 *            起点城市
	 * @param endCity;//
	 *            终点城市
	 * 
	 */
	public Order_SendSureBean(String choosetime, String time, String starAdr, String endAdr, String starLat,
			String starLng, String endLat, String endLng, String goodsType, String evaluate, String orderDetail,
			String isPush, String starCity, String endCity) {
		super();
		this.choosetime = choosetime;
		this.time = time;
		this.starAdr = starAdr;
		this.endAdr = endAdr;
		this.starLat = starLat;
		this.starLng = starLng;
		this.endLat = endLat;
		this.endLng = endLng;
		this.goodsType = goodsType;
		this.evaluate = evaluate;
		this.orderDetail = orderDetail;
		this.isPush = isPush;
		this.starCity = starCity;
		this.endCity = endCity;
	}

	private String isPush="0";// 是否首页推送
	private String starCity;// 起点城市
	private String endCity;// 终点城市

	public String getChoosetime() {
		return choosetime;
	}

	public void setChoosetime(String choosetime) {
		this.choosetime = choosetime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStarAdr() {
		return starAdr;
	}

	public void setStarAdr(String starAdr) {
		this.starAdr = starAdr;
	}

	public String getEndAdr() {
		return endAdr;
	}

	public void setEndAdr(String endAdr) {
		this.endAdr = endAdr;
	}

	public String getStarLat() {
		return starLat;
	}

	public void setStarLat(String starLat) {
		this.starLat = starLat;
	}

	public String getStarLng() {
		return starLng;
	}

	public void setStarLng(String starLng) {
		this.starLng = starLng;
	}

	public String getEndLat() {
		return endLat;
	}

	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}

	public String getEndLng() {
		return endLng;
	}

	public void setEndLng(String endLng) {
		this.endLng = endLng;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}

	public String getStarCity() {
		return starCity;
	}

	public void setStarCity(String starCity) {
		this.starCity = starCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
}
