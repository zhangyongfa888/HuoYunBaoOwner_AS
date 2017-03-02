package zhidanhyb.huozhu.Bean;

import java.util.List;

/**
 * Created by 蒋米兰 on 2016/12/11.
 * 获取首页公告列表更多的Bean
 */
public class Home_PushMessageListMoreBean {

    public List<Home_PushMessageListBean> getPushmessageList() {
        return pushmessageList;
    }

    public void setPushmessageList(List<Home_PushMessageListBean> pushmessageList) {
        this.pushmessageList = pushmessageList;
    }

    private List<Home_PushMessageListBean> pushmessageList;// 获取首页推送消息更多的集合

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;

}
