package marc.com.viewadavanced.data;

/**
 * Created by 王成达
 * Date: 2017/8/9
 * Time: 16:00
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class SpiderData {
	private String name;
	private float value;

	public SpiderData(String name, float value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
