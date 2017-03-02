package zhidanhyb.huozhu.Bean;
/**
 * 充值-支付宝
 * @author lxj
 *
 */
public class RechargeAliPayBean {

	private String account_id;//支付订单号（并不是支付宝的是本项目的支付）
	private String money;//金额
	private String notify_url;//支付宝回调地址
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
}
