package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import zhidanhyb.huozhu.Activity.Home.BannerDetailsActivity;
import zhidanhyb.huozhu.Bean.Home_AdvertiseListBean;
import zhidanhyb.huozhu.Bean.Home_PushMessageListBean;
import zhidanhyb.huozhu.HttpRequest.HttpConfigSite;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ViewUtils;

/**
 * 首页消息Adapter
 *
 * @author lxj
 */
public class Home_MessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Home_PushMessageListBean> pushlist;
    //        private List<Map<String,String>> pushlist;
    private List<Home_AdvertiseListBean> adList;
    private ViewHolder viewHolder;
    private String adPicUrl;
    private String content;
    private Home_AdvertiseListBean advertiseListBean;

    /**
     * @param context
     * @param list
     * @param advertiseListBean
     */
    public Home_MessageAdapter(Context context, List<Home_PushMessageListBean> list, Home_AdvertiseListBean advertiseListBean) {
        this.mContext = context;
        this.pushlist = list;
        this.advertiseListBean = advertiseListBean;
    }

    @Override
    public int getCount() {
        return pushlist.size();
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Home_PushMessageListBean getItem(int position) {
        return pushlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
//        if (convertView == null) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.home_messagelayout, null);
        viewHolder = new ViewHolder();
        viewHolder.content_textview = (TextView) convertView.findViewById(R.id.homemessage_content_textview);
        viewHolder.layoutImageView = (RelativeLayout) convertView.findViewById(R.id.layout_imageView);
        viewHolder.homeMessageImageViewAd = (ImageView) convertView.findViewById(R.id.homeMessage_imageView_ad);
        viewHolder.imageViewIconHome = (ImageView) convertView.findViewById(R.id.imageView_icon_home);
        ViewUtils.setViewSize(mContext, convertView.findViewById(R.id.layout2_listView), 640, 130);
        ViewUtils.setViewSize(mContext, convertView.findViewById(R.id.v2_ll_img), 99, 130);


//        viewHolder.imageViewIconHome.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext)*130/640,
//                ScreenUtils.getScreenWidth(mContext)*130/640));

        viewHolder.layoutImageView.setVisibility(View.GONE);
        convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

//        String content=pushlist.get(position).get("1");
        String content = pushlist.get(position).getContent();
        String img = pushlist.get(position).getImg();

        if (pushlist.size() > 2 && position == 2) {
            viewHolder.layoutImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(HttpConfigSite.Base_ImageView_url + advertiseListBean.getAdPicUrl()).into(viewHolder.homeMessageImageViewAd);
        }

        if (pushlist.size() == 1 && position == 0) {
            viewHolder.layoutImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(HttpConfigSite.Base_ImageView_url + advertiseListBean.getAdPicUrl()).into(viewHolder.homeMessageImageViewAd);
        }

        if (pushlist.size() == 2 && position == 1) {
            viewHolder.layoutImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(HttpConfigSite.Base_ImageView_url + advertiseListBean.getAdPicUrl()).into(viewHolder.homeMessageImageViewAd);
        }

        viewHolder.layoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, BannerDetailsActivity.class);
                intent.putExtra("url", advertiseListBean.getAdLink());
                intent.putExtra("remarks", advertiseListBean.getRemarks());
                mContext.startActivity(intent);
            }
        });
        viewHolder.content_textview.setText(content);


        if (img == null || img.equals("")) {
            ViewUtils.setViewSize(mContext, viewHolder.imageViewIconHome, 75, 75);
            Glide.with(mContext).load(R.drawable.pushicon).into(viewHolder.imageViewIconHome);
        } else {
            try {
                ViewUtils.setViewSize(mContext, viewHolder.imageViewIconHome, 75, 75);
                Glide.with(mContext)
                        .load(img)
                        .into(viewHolder.imageViewIconHome);
            } catch (Exception e) {
                ViewUtils.setViewSize(mContext, viewHolder.imageViewIconHome, 75, 75);
                Glide.with(mContext).load(R.drawable.pushicon).into(viewHolder.imageViewIconHome);
                e.printStackTrace();
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView content_textview;
        RelativeLayout layoutImageView;
        ImageView homeMessageImageViewAd;
        ImageView imageViewIconHome;
        View viewHomeAd;
    }

    public void updataList(List<Home_PushMessageListBean> list, int i) {
        if (i == 1) {
            pushlist = list;
        } else if (i == 2) {
            pushlist.addAll(list);
        }
        notifyDataSetChanged();
    }
}
