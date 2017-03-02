package zhidanhyb.huozhu.Bean;

import java.util.List;

/**
 * 获取首页Bean
 * @author lxj
 *
 */
public class HomeBean {
	
	private List<Home_PushMessageListBean> pushmessageList;// 获取首页推送消息的集合
	private List<Home_NoticeListBean> noticeList;//获取首页公告集合
	private List<Home_AdvertiseListBean> advertiseList;//获取首页滚动视图数据集合
	public List<Home_PushMessageListBean> getPushmessageList() {
		return pushmessageList;
	}
	public void setPushmessageList(List<Home_PushMessageListBean> pushmessageList) {
		this.pushmessageList = pushmessageList;
	}
	public List<Home_NoticeListBean> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(List<Home_NoticeListBean> noticeList) {
		this.noticeList = noticeList;
	}
	public List<Home_AdvertiseListBean> getAdvertiseList() {
		return advertiseList;
	}
	public void setAdvertiseList(List<Home_AdvertiseListBean> advertiseList) {
		this.advertiseList = advertiseList;
	}
}
