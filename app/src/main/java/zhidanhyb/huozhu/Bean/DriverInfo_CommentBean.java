package zhidanhyb.huozhu.Bean;

import java.io.Serializable;

/**
 * 司机信息-评论信息Bean
 * @author lxj
 *
 */
public class DriverInfo_CommentBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8489437088332793205L;
	private String pic;//头像
	private String cid;//评论ID
	private String mobile;//评论人手机号
	private String score;//评分
	private String comment;//评论内容
	private String created_on;//评论时间
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
	
}
