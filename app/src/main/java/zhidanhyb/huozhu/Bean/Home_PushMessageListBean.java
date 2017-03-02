package zhidanhyb.huozhu.Bean;
/**
 * 获取首页推送消息的Bean
 * @author lxj
 *
 */
public class Home_PushMessageListBean {

	
	private String content;//内容

	public String getImg() {
		return img;
	}
public  String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setImg(String img) {
		this.img = img;
	}

	private String img;
//	private String id;//id
//	private String type;//type1:跳URL type2 :跳订单详情
//	private String link;//type =1 为链接   type = 2 为订单id
//	private String status;//订单状态
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getLink() {
//		return link;
//	}
//	public void setLink(String link) {
//		this.link = link;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	
}
