package zhidanhyb.huozhu.Bean;
/**
 * 评价列表的Bean
 * @author lxj
 *
 */
public class CommentBean {
	private String pic;//头像
	private String cid;//评论id
	private String score;//星级
	private String comment;//评论内容
	private String commented_mobile;//评论的手机号
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
	public String getCommented_mobile() {
		return commented_mobile;
	}
	public void setCommented_mobile(String commented_mobile) {
		this.commented_mobile = commented_mobile;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
}
