package zhidanhyb.huozhu.Bean;

/**
 * 货主发单扣除金币调取的Bean
 * @author lxj
 *
 */
public class OwnerGoldBean {

	private int point;//每单消耗金币数      30
	private String level;//等级
	private int golds;//用户总的金币数
	private int discount;//优惠折扣  95
	private String str;//距离下一等级需要金币
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getGolds() {
		return golds;
	}
	public void setGolds(int golds) {
		this.golds = golds;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
}
