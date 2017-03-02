package zhidanhyb.huozhu.Bean;

import java.io.Serializable;

public class BannerBean implements Serializable
{
    private static final long serialVersionUID = -6185359013160183811L;
    private String id;
    private String adUrl;//广告图片URL
    private String adLink;//广告链接
    private String remarks;//备注    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getAdUrl()
    {
        return adUrl;
    }
    public void setAdUrl(String adUrl)
    {
        this.adUrl = adUrl;
    }
    public String getAdLink()
    {
        return adLink;
    }
    public void setAdLink(String adLink)
    {
        this.adLink = adLink;
    }
    public String getRemarks()
    {
        return remarks;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
}
