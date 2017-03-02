package zhidanhyb.huozhu.Bean;

/**
 * 获取用户金币、等级、星星
 * @author lxj
 *
 */
public class UserGoldBean {
	private float score;//星星数
	private String vip;

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	private String gold;//金币
	private String level;//等级  1骑士2爵士3男爵4子爵5伯爵6侯爵7公爵
	private String balance;//账户余额
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
