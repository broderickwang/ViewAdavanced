package marc.com.viewadavanced.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import marc.com.viewadavanced.R;

/**
 * Created by 王成达
 * Date: 2017/8/25
 * Time: 09:15
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder>{
	private Context mContext;

	private List<Drawable> mDrawables = null;

	public BannerAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.banner_item,parent,false);
		BannerViewHolder holder = new BannerViewHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(BannerViewHolder holder, int position) {
		if (mDrawables!=null){
			holder.mImageView.setBackground(mDrawables.get(position));
//			holder.mImageView.setScaleX(0.8f);
		}
	}

	@Override
	public int getItemCount() {
		return mDrawables==null?0:mDrawables.size();
	}

	class BannerViewHolder extends RecyclerView.ViewHolder{
		ImageView mImageView;
		public BannerViewHolder(View itemView) {
			super(itemView);
			mImageView = (ImageView) itemView.findViewById(R.id.banner_img);
		}
	}

	public void setDrawables(List<Drawable> list){
		this.mDrawables = list;
	}
}
