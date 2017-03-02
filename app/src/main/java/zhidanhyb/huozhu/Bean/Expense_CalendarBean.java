package zhidanhyb.huozhu.Bean;
/**
 * 用户消费记录Bean
 * @author lxj
 *
 */
public class Expense_CalendarBean {

	private int id;//交易流水号
	private String type;//金币：1：兑换，2：奖励，3：返还，4:分享返金币11：抢单，12：下单，13：返钱  21后台充入22后台扣除14置顶推送                          账户：1充值 2提现 3兑换金币4订单支付
	private String golds;//数量 /金额
	private String created_on;//时间
	private String status;//状态 都接收，但是只有在账户历史记录有用    0审核中 1成功2失败
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGolds() {
		return golds;
	}
	public void setGolds(String golds) {
		this.golds = golds;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
