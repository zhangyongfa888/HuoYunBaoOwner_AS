package zhidanhyb.huozhu.Bean;

/**
 * 更新版本的Bean
 * @author lxj
 *
 */
public class UpdataVersionBean {

	private String msg;//更新详情
	private String url;//下载地址
	private String ver;//版本号
	private String is_updata;//是否强制更新0-不强制 1-强制
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	@Override
	public String toString() {
		return "UpdataVersionBean [msg=" + msg + ", url=" + url + ", ver=" + ver + ", is_updata=" + is_updata + "]";
	}
	public String getIs_updata() {
		return is_updata;
	}
	public void setIs_updata(String is_updata) {
		this.is_updata = is_updata;
	}
}
