package zhidanhyb.huozhu.Bean;
/**
 *  充值-微信
 * @author lxj
 *
 */
public class RechargeWxBean {
	private String prepay_id;//
	private String packagevalue;//
	private String timestamp;//
	private String partnerid;//
	private String nonceStr;//
	private String sign;//
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getPackagevalue() {
		return packagevalue;
	}
	public void setPackagevalue(String packagevalue) {
		this.packagevalue = packagevalue;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
