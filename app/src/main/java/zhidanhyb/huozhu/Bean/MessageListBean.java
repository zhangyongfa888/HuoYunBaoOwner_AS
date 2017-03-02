package zhidanhyb.huozhu.Bean;
/**
 * 消息列表的Bean
 * @author lxj
 *
 */
public class MessageListBean {
	private String content;//内容
	private String id;//消息ID
	private String title;//标题
	private String Link_id;//关联的id号，如果是订单消息则关联订单ID其他则关联自他相关ID
	private String type;//类型
	private String created_on;//创建的时间
	private boolean isDelete;//是否显示删除图标     本地属性
	private boolean isSelector;//是否选择     本地属性
 	public boolean isDelete() { 
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public boolean isSelector() {
		return isSelector;
	}
	public void setSelector(boolean isSelector) {
		this.isSelector = isSelector;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink_id() {
		return Link_id;
	}
	public void setLink_id(String link_id) {
		Link_id = link_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
}
