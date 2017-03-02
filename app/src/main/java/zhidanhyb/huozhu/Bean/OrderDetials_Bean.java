package zhidanhyb.huozhu.Bean;

import java.util.List;

/**
 * 订单详情Bean
 * @author lxj
 *
 */
public class OrderDetials_Bean {
	
	private List<OrderDetials_Driverinfo_Bean> driverinoflist;//Array 司机信息和坐标
	private OrderDetials_Orderinfo_Bean orderinfolist;//Array 订单信息
	private OrderDetials_Releaseinfo_Bean Releaseinfo;//Array 货主信息
	public List<OrderDetials_Driverinfo_Bean> getDriverinoflist() {
		return driverinoflist;
	}
	public void setDriverinoflist(List<OrderDetials_Driverinfo_Bean> driverinoflist) {
		this.driverinoflist = driverinoflist;
	}
	public OrderDetials_Orderinfo_Bean getOrderinfolist() {
		return orderinfolist;
	}
	public void setOrderinfolist(OrderDetials_Orderinfo_Bean orderinfolist) {
		this.orderinfolist = orderinfolist;
	}
	public OrderDetials_Releaseinfo_Bean getReleaseinfo() {
		return Releaseinfo;
	}
	public void setReleaseinfo(OrderDetials_Releaseinfo_Bean releaseinfo) {
		Releaseinfo = releaseinfo;
	}
}
