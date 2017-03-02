package zhidanhyb.huozhu.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 司机信息的Bean
 * @author lxj
 *
 */
public class DriverInfoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3623024425569814215L;
	private String driver_id;//司机ID
	private String name;//姓名
	private String pic;//头像
	private String plate_num;//车牌号
	private String mobile;//电话
	private String vehicle_id;//车型载重
	private String level;//级别    1骑士2爵士3男爵4子爵5伯爵6侯爵7公爵
	private String rate;//好评率
	private String success_num;//成单数
	private String score;//星级
	private List<DriverInfo_CommentBean>commentlist;//评价列表commentlist
	public String getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPlate_num() {
		return plate_num;
	}
	public void setPlate_num(String plate_num) {
		this.plate_num = plate_num;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
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
	public List<DriverInfo_CommentBean> getCommentlist() {
		return commentlist;
	}
	public void setCommentlist(List<DriverInfo_CommentBean> commentlist) {
		this.commentlist = commentlist;
	}
}
