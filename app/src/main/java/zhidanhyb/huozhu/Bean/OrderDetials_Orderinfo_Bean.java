package zhidanhyb.huozhu.Bean;

import zhidanhyb.huozhu.Utils.StringUtil;

/**
 * 订单详情-Array 订单信息
 * @author lxj
 *
 */
public class OrderDetials_Orderinfo_Bean {
	private String oid;//订单id
	private String estimate_price="0元";//估算价格
	private String goodstype;//订单详情
	private String departure_time;//发车时间
	private String origin_id;//开始点地址（详细）
	private String dest_id;//终点地址（详细）
	private String remarks;//备注
	private String price="0";//成交价格
	private String status;//状态 status:1待接单 2竞价中 3待发车 4待收货5待付款 6 完成 8 待确认收款 7取消
	private String startsite;//开始点坐标（经度,纬度）
	private String is_commen;//是否评价是否评价 0 双方都未评 1 货主已评 2 司机已评 3双方互评
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getEstimate_price() {
		return estimate_price;
	}
	public void setEstimate_price(String estimate_price) {
		this.estimate_price = estimate_price;
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
	public String getOrigin_id() {
		return origin_id;
	}
	public void setOrigin_id(String origin_id) {
		this.origin_id = origin_id;
	}
	public String getDest_id() {
		return dest_id;
	}
	public void setDest_id(String dest_id) {
		this.dest_id = dest_id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		if(StringUtil.isEmpty(price)){
			this.price = "0元";
		}else{
			this.price = price;
		}

	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartsite() {
		return startsite;
	}
	public void setStartsite(String startsite) {
		this.startsite = startsite;
	}
	public String getIs_commen() {
		return is_commen;
	}
	public void setIs_commen(String is_commen) {
		this.is_commen = is_commen;
	}
}
