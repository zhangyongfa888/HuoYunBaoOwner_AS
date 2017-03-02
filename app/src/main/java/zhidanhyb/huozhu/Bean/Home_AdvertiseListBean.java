package zhidanhyb.huozhu.Bean;
/**
 * 获取首页滚动视图数据Bean
 * @author lxj
 *
 */
public class Home_AdvertiseListBean {

	
	private String id;//id
	private String remarks;//备注
	private String adLink;//加载的网页
	private String adPicUrl;//图片地址

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	private String site;//判断是广告还是轮播图
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAdLink() {
		return adLink;
	}
	public void setAdLink(String adLink) {
		this.adLink = adLink;
	}
	public String getAdPicUrl() {
		return adPicUrl;
	}
	public void setAdPicUrl(String adPicUrl) {
		this.adPicUrl = adPicUrl;
	}
	
	
	
}
