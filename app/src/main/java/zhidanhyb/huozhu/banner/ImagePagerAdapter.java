package zhidanhyb.huozhu.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.List;

import zhidanhyb.huozhu.Bean.Home_AdvertiseListBean;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.MyBitmapUtil;

/**
 * ViewPager的适配器
 * 
 * @author LWK
 * 
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter

{
    
    private Context context;
    
    private List<Home_AdvertiseListBean> mList;
    
    private boolean isInfiniteLoop;
    
    ViewHolder holder;
    
    public ImagePagerAdapter(Context context, List<Home_AdvertiseListBean> list)
    {
        this.context = context;
        this.mList = list;
        isInfiniteLoop = false;
    }
    
    @Override
    public int getCount()
    {
        return mList == null ? 0 : mList.size();
    }
    
    private int getPosition(int position)
    {
        return position;
    }
    
    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View view, ViewGroup container)
    {
        
        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = holder.imageView = (ImageView)LayoutInflater.from(context).inflate(R.layout.item_image, null);
            holder.imageView.setScaleType(ScaleType.FIT_XY);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }

        MyBitmapUtil.getInstance(context).setBitmap(holder.imageView, mList.get(position).getAdPicUrl());
        return view;
    }
    
    private class ViewHolder
    {
        
        ImageView imageView;
    }
    
    public boolean isInfiniteLoop()
    {
        return isInfiniteLoop;
    }
    
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop)
    {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
    
}