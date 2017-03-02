package zhidanhyb.huozhu.Bean;
/**
 * 银行卡列表Bean
 * @author lxj
 *
 */
public class BankListBean {
	
	private String id;//银行卡id
	private String card_no;//银行卡号
	private String card_bank;//归属银行
	private String card_name;//持卡人姓名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_bank() {
		return card_bank;
	}
	public void setCard_bank(String card_bank) {
		this.card_bank = card_bank;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
}
