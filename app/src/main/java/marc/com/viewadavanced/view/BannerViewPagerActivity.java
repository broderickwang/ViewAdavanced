package marc.com.viewadavanced.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import marc.com.viewadavanced.R;

import static android.R.id.list;

public class BannerViewPagerActivity extends AppCompatActivity {
	private ScaleViewPager viewPager;
	private List<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_view_pager);

		init();

		viewPager = (ScaleViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View v = views.get(position);
				container.addView(v);
				return v;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view==object;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(views.get(position));
			}
		});
	}

	private void init() {
		views = new ArrayList<>();

		for(int i=0;i<2;i++){
			View v = LayoutInflater.from(this).inflate(R.layout.red,null);
			View v2 = LayoutInflater.from(this).inflate(R.layout.green,null);
			View v3 = LayoutInflater.from(this).inflate(R.layout.blue,null);

			views.add(v);
			views.add(v2);
			views.add(v3);
		}
	}
}
