package zhidanhyb.huozhu.Activity.Message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

import zhidanhyb.huozhu.Activity.MainActivity;
import zhidanhyb.huozhu.Adapter.MessageAdaper;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Bean.MessageListBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getMessageListDataListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.View.MessageDeletePop;
import zhidanhyb.huozhu.View.MessageDeletePop.onDeleteMessageListener;
import zhidanhyb.huozhu.View.XList_View.XListView;
import zhidanhyb.huozhu.View.XList_View.XListView.IXListViewListener;

/**
 * 消息
 *
 * @author lxj
 */
public class Main_MessageActivity extends BaseFragment
        implements IXListViewListener, onDeleteMessageListener, getMessageListDataListener {
    private XListView message_listview;
    private MessageAdaper messageadapter;
    public static boolean isDelete = false;
    private MessageDeletePop messageDeletePop;
    private int page = 1;// 页数，默认是1
    private HttpController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.main_message_layout, null);
            initView();
        }
        return view;
    }

    private void initView() {
        controller = new HttpController(getActivity());
        setTitleText(R.string.message);
        setRightText(R.string.order_delete);
        setHideLeftButton();
        setHideRightButton();
        messageDeletePop = new MessageDeletePop(getActivity(), MainActivity.mTabHost);
        messageDeletePop.setOnDeleteMessageListener(this);
        message_listview = (XListView) view.findViewById(R.id.message_listview);
        message_listview.setPullLoadEnable(true);
        message_listview.setXListViewListener(this);
        //单选某条 向集合中添加要删除的某条信息
        message_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (messageadapter.getMessageList().get(position - 1).isSelector()) {
                    messageadapter.getMessageList().get(position - 1).setSelector(false);
                } else {
                    messageadapter.getMessageList().get(position - 1).setSelector(true);
                }
                messageadapter.notifyDataSetChanged();
                if (messageDeletePop != null) {
                    messageDeletePop.UpdataDeleteData(messageadapter.getMessageList());
                }
            }
        });
        if (messageadapter == null) {
            messageadapter = new MessageAdaper(getActivity());
        }
        message_listview.setAdapter(messageadapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//		page = 1;
        getData();
    }

    private void getData() {
        int paging = page;
        String type = "2";// 1.司机端 2。货主端
        if (controller != null) {
            controller.setGetMessageListDataListener(this);
            controller.getMessageListData(paging + "", type);
        }
    }

    private int editState = 0;

    private static final int NORMAL = 0;
    private static final int EDIT = 1;
    private static final int ALL = 2;

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right_rl:// 删除
                if (getRightText().contains("全选")) {
                    setRightText(R.string.not_select_all);
                    if (!messageDeletePop.isShowing()) {
                        return;
                    }
                    if (messageadapter.getMessageList() != null) {
                        for (int i = 0; i < messageadapter.getMessageList().size(); i++) {
                            messageadapter.getMessageList().get(i).setDelete(true);
                            messageadapter.getMessageList().get(i).setSelector(true);
                        }
                        messageadapter.notifyDataSetChanged();

                        if (messageDeletePop != null) {
                            messageDeletePop.UpdataDeleteData(messageadapter.getMessageList());
                        }
                    }

                } else if (getRightText().contains("编辑")) {
                    //点击编辑 出现全选按钮
                    setRightText(R.string.select_all);

                    showDeletePop();

                    //禁止刷新
                    message_listview.setPullLoadEnable(false);
                    message_listview.setPullRefreshEnable(false);
                } else if (getRightText().contains("全不选")) {
                    setRightText(R.string.select_all);
                    if (messageadapter.getMessageList() != null) {
                        for (int i = 0; i < messageadapter.getMessageList().size(); i++) {
                            messageadapter.getMessageList().get(i).setDelete(true);
                            messageadapter.getMessageList().get(i).setSelector(false);
                        }
                        messageadapter.notifyDataSetChanged();

                        if (messageDeletePop != null) {
                            messageDeletePop.UpdataDeleteData(messageadapter.getMessageList());
                        }
                    }
                }


                break;
            default:
                break;
        }
    }


    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        if (messageDeletePop != null) {
            if (messageDeletePop.isShowing()) {
                if (messageadapter.getMessageList() != null) {
                    for (int i = 0; i < messageadapter.getMessageList().size(); i++) {
                        messageadapter.getMessageList().get(i).setDelete(false);
                        messageadapter.getMessageList().get(i).setSelector(false);
                    }
                    messageadapter.notifyDataSetChanged();
                }
                messageDeletePop.colse();
                message_listview.setPullLoadEnable(true);
                message_listview.setPullRefreshEnable(true);
                setRightText(R.string.order_delete);
            }
        }
    }

    /**
     *
     */
    private void showDeletePop() {


        if (messageadapter.getMessageList() != null) {
            if (messageDeletePop != null) {
                //每一条消息都设置为编辑状态
                for (int i = 0; i < messageadapter.getMessageList().size(); i++) {
                    messageadapter.getMessageList().get(i).setDelete(true);
                }
                if (messageadapter.getMessageList().size() > 0) {
                    messageDeletePop.show();
                }
                messageadapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRefresh() {
        isDelete = false;
        page = 1;
        getData();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        page += 1;
        getData();
        onLoad();
    }

    public void onLoad() {
        message_listview.stopRefresh();
        message_listview.stopLoadMore();
        message_listview.setRefreshTime(StringUtil.getTime());
    }

    // 删除成功后刷新列表
    @Override
    public void onDeleteMess(List<String> messagelist) {
        setRightText(R.string.order_delete);
        if (messageadapter != null) {
            if (messageadapter.getMessageList() != null) {
                for (int i = 0; i < this.messageadapter.getMessageList().size(); i++) {
                    for (int j = 0; j < messagelist.size(); j++) {
                        if (messageadapter.getMessageList().get(i).getId().toString()
                                .equals(messagelist.get(j).toString())) {
                            messageadapter.getMessageList().remove(i);
                        }
                    }
                }
                messageadapter.notifyDataSetChanged();
                if (messageadapter.getMessageList().size() == 0) {
                    page = 1;
                    getData();
                }
                isDelete = false;
                onColose();
            }
        }
    }

    /**
     *
     */
    @Override
    public void onColose() {
//        showDeletePop();
        setRightText(R.string.order_delete);


        if (messageadapter.getMessageList() != null) {
            for (int i = 0; i < messageadapter.getMessageList().size(); i++) {
                messageadapter.getMessageList().get(i).setDelete(false);
                messageadapter.getMessageList().get(i).setSelector(false);
            }
            messageadapter.notifyDataSetChanged();
            if (messageDeletePop != null) {
                messageDeletePop.UpdataDeleteData(messageadapter.getMessageList());
            }
            if (messageDeletePop != null) {
                messageDeletePop.dismiss();
            }
        }


        message_listview.setPullLoadEnable(true);
        message_listview.setPullRefreshEnable(true);

    }

    // 成功得到消息列表的回调
    @Override
    public void getMeesagList(List<MessageListBean> messagelist) {
        if (messagelist == null) {
            message_listview.setVisibility(View.GONE);
            showNotData();
            return;
        } else {
            hideNotData();
            message_listview.setVisibility(View.VISIBLE);
        }
        if (page == 1) {
            if (messageadapter != null) {
                messageadapter.updataList(messagelist, 1);
            }
        } else if (page > 1) {
            if (messageadapter != null) {
                messageadapter.updataList(messagelist, 2);
            }
        }
    }
}
