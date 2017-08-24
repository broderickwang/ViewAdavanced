package marc.com.viewadavanced;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import marc.com.viewadavanced.adapter.DianDianBaseAdapter;
import marc.com.viewadavanced.view.CircleRoateView;
import marc.com.viewadavanced.view.MoMoDianDianView;

public class MainActivity extends AppCompatActivity {

	private marc.com.viewadavanced.view.CircleRoateView test;
	private MoMoDianDianView diandian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		diandian = (MoMoDianDianView) findViewById(R.id.diandian);
		diandian.setAdapter(new DianDianBaseAdapter() {
			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public View getView(int position, ViewGroup parent) {
				return LayoutInflater.from(MainActivity.this).inflate(R.layout.test,parent,false);
			}
		});
//		this.test = (CircleRoateView) findViewById(R.id.test);

		/*SpiderView spiderview = (SpiderView) findViewById(R.id.spider_view);
		List<SpiderData> datas = new ArrayList<>();
		for (int i=0;i<6;i++){
			datas.add(new SpiderData(""+i,i));
		}
		spiderview.setData(datas);*/
	}
}
