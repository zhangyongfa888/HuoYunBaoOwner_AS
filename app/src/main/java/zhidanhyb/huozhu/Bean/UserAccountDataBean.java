package zhidanhyb.huozhu.Bean;
/**
 * 用户账户信息的Bean
 * @author lxj
 *
 */
public class UserAccountDataBean {
	private String id;//用户ID
	private String gold;//金币总数
	private String balance;//账户余额
	private String success_num;//成单总数
	private String score;//评分总数
	private String now_level;//当前级别
	private String next_level;//下一个级别
	private String need_score;//达到下一个级别所需要的评分总数
	private String content;//内容
	private int is_dealpwd;//提现密码 1:已设置 0 未设置
	private int is_bankcard;//添加银行卡1:已设置 0 未设置
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getNow_level() {
		return now_level;
	}
	public void setNow_level(String now_level) {
		this.now_level = now_level;
	}
	public String getNext_level() {
		return next_level;
	}
	public void setNext_level(String next_level) {
		this.next_level = next_level;
	}
	public String getNeed_score() {
		return need_score;
	}
	public void setNeed_score(String need_score) {
		this.need_score = need_score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIs_dealpwd() {
		return is_dealpwd;
	}
	public void setIs_dealpwd(int is_dealpwd) {
		this.is_dealpwd = is_dealpwd;
	}
	public int getIs_bankcard() {
		return is_bankcard;
	}
	public void setIs_bankcard(int is_bankcard) {
		this.is_bankcard = is_bankcard;
	}


	@Override
	public String toString() {
		return "UserAccountDataBean{" +
				"id='" + id + '\'' +
				", gold='" + gold + '\'' +
				", balance='" + balance + '\'' +
				", success_num='" + success_num + '\'' +
				", score='" + score + '\'' +
				", now_level='" + now_level + '\'' +
				", next_level='" + next_level + '\'' +
				", need_score='" + need_score + '\'' +
				", content='" + content + '\'' +
				", is_dealpwd=" + is_dealpwd +
				", is_bankcard=" + is_bankcard +
				'}';
	}
}
