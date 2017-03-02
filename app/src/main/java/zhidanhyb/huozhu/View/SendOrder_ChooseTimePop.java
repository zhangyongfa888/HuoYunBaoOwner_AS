package zhidanhyb.huozhu.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.ViewUtils;

/**
 * 发订单时选择发货时间的pop
 * 
 * @author lxj
 *
 */
public class SendOrder_ChooseTimePop extends PopupWindow {
	private TextView cancle_textview;
	private TextView sure_textview;
	private WheelView date_wheelview;
	private WheelView time_wheelview;
	private WheelView ampm_wheelview;
	private String[] STR_DAY = new String[3];
	private String[] STR_DAYS = new String[3];
	private String[] STR_YEAR = new String[3];
	private String[] STR_TIME = { "上午", "下午" };
	private String[] STR_HOUR = { "1点", "2点", "3点", "4点", "5点", "6点", "7点", "8点", "9点", "10点", "11点", "12点" };
	private Date nowDate;
	public List<String> hours = new ArrayList<String>();
	public List<String> times = new ArrayList<String>();
	private View popview;
	private boolean isDay = true;// 选择是是不是时间
	private String chooseDay = "明天";// 默认明天
	private String chooseAmPm = "上午";// 默认上午
	private String chooseTime = "1点";// 默认1点
	private Calendar cal;
	private int selectedIndex_1 = 0;

	/**
	 * @param context
	 * @param view
	 */
	@SuppressLint("SimpleDateFormat")
	public SendOrder_ChooseTimePop(Context context, View view) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat sdyear = new SimpleDateFormat("yyyy");
		for (int i = 0; i < 3; i++) {
			Date date = new Date();
			if (i == 0) {
				STR_DAY[i] = "今天" + sdf.format(date);
				STR_DAYS[i] = sdf.format(date);
				STR_YEAR[i] = sdyear.format(date);
			} else if (i == 1) {
				STR_DAY[i] = "明天" + sdf.format(new Date(date.getTime() + 86400000));
				STR_DAYS[i] = sdf.format(new Date(date.getTime() + 86400000));
				STR_YEAR[i] = sdyear.format(new Date(date.getTime() + 86400000));
				chooseDay = STR_DAY[1];
			} else if (i == 2) {
				STR_DAY[i] = "后天" + sdf.format(new Date(date.getTime() + 172800000));
				STR_DAYS[i] = sdf.format(new Date(date.getTime() + 172800000));
				STR_YEAR[i] = sdyear.format(new Date(date.getTime() + 172800000));
			}
		}

		popview = LayoutInflater.from(context).inflate(R.layout.sendorder_choosetimelayout, null);
		ViewUtils.setViewSize(context,popview.findViewById(R.id.v2_dialog_choose_time),640,75);
		cancle_textview = (TextView) popview.findViewById(R.id.sendorder_cancle_textview);
		sure_textview = (TextView) popview.findViewById(R.id.sendorder_sure_textview);
		date_wheelview = (WheelView) popview.findViewById(R.id.sendorder_date_wheelview);// 日期
		ampm_wheelview = (WheelView) popview.findViewById(R.id.sendorder_ampm_wheelview);// 上午下午
		time_wheelview = (WheelView) popview.findViewById(R.id.sendorder_time_wheelview);// 几点

		ViewUtils.setViewSize(context,date_wheelview,0,208);
		ViewUtils.setViewSize(context,ampm_wheelview,0,208);
		ViewUtils.setViewSize(context,time_wheelview,0,208);
//		ViewUtils.setViewSize(context,popview,640,283);
		setContentView(popview);
		this.setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent_dialog_color));
		this.setOutsideTouchable(false);
		this.setFocusable(true);
		this.setHeight(ScreenUtils.getScreenHeight(context));
		this.setWidth(ScreenUtils.getScreenWidth(context));
		showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

		date_wheelview.setOffset(1);
		date_wheelview.setItems(Arrays.asList(STR_DAY));
		date_wheelview.setSeletion(1);
		date_wheelview.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				isDay = true;// 选择的是第一项时间
				Log.e("selectedIndex_date_wheelview", selectedIndex + "");

				selectedIndex_1 = selectedIndex;

				if (selectedIndex == 1) {
					setTodaySpinner();
				} else {
					setOtherSpinner();
				}
				chooseDay = item;
			}
		});
		ampm_wheelview.setOffset(1);
		ampm_wheelview.setItems(Arrays.asList(STR_TIME));
		ampm_wheelview.setSeletion(0);
		ampm_wheelview.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				isDay = false;
				Log.e("selectedIndex_ampm_wheelview", selectedIndex_1 + "," + selectedIndex + "");
				ampm_wheelview.setSeletion(selectedIndex - 1);

				if (selectedIndex_1 == 1) {             //check by 张永发 1.选择默认时间问题+上下午时间不匹配问题
					// 今天
					Log.e("selectedIndex_hour", Calendar.getInstance().get(Calendar.HOUR)+"");
					if (selectedIndex == 1 && Calendar.getInstance().get(Calendar.HOUR) < 12) {// 今天的上午
						setTodaySpinner();
					} else if (selectedIndex == 2 && Calendar.getInstance().get(Calendar.HOUR) >= 12) {// 今天的上午
						setTodaySpinner();
					} else {
						setOtherSpinner();

					}

				} else {
					setOtherSpinner();
				}
				chooseAmPm = item;
			}
		});
		time_wheelview.setOffset(1);
		time_wheelview.setItems(Arrays.asList(STR_HOUR));
		time_wheelview.setSeletion(0);
		time_wheelview.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				chooseTime = item;
				L.i("选择的小时==" + chooseTime);
			}
		});

		cancle_textview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		sure_textview.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				String day = null;
				int time = 0;
				int ampm = 0;
				String hour = "";
				String year = null;
				if (chooseDay.contains("今天")) {
					day = STR_DAYS[0];
					year = STR_YEAR[0];
				} else if (chooseDay.contains("明天")) {
					day = STR_DAYS[1];
					year = STR_YEAR[1];
				} else if (chooseDay.contains("后天")) {
					day = STR_DAYS[2];
					year = STR_YEAR[2];
				}

				if (chooseAmPm.contains("上午")) {
					ampm = 0;
					hour = "0";
				} else if (chooseAmPm.contains("下午")) {
					ampm = 12;
					hour = "";
				}
				if (chooseTime.toString().equals("1点")) {
					time = 1;
				} else if (chooseTime.toString().equals("2点")) {
					time = 2;
				} else if (chooseTime.toString().equals("3点")) {
					time = 3;
				} else if (chooseTime.toString().equals("4点")) {
					time = 4;
				} else if (chooseTime.toString().equals("5点")) {
					time = 5;
				} else if (chooseTime.toString().equals("6点")) {
					time = 6;
				} else if (chooseTime.toString().equals("7点")) {
					time = 7;
				} else if (chooseTime.toString().equals("8点")) {
					time = 8;
				} else if (chooseTime.toString().equals("9点")) {
					time = 9;
				} else if (chooseTime.toString().equals("10点")) {
					time = 10;
				} else if (chooseTime.toString().equals("11点")) {
					time = 11;
				} else if (chooseTime.toString().equals("12点")) {
					time = 12;
				}
				// "03月17日 15:00";
				String choosetime = year + "年" + day + " " + hour + (time + ampm) + "时00分";

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String choosedate = null;
				try {
					choosedate = sdfs.format(sdf.parse(choosetime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String times = chooseDay + chooseAmPm + chooseTime;
				L.i("选择的时间是ampm=" + ampm);
				L.i("选择的时间是=" + choosedate);
				if (choosedate != null) {
					try {
						chooseTimeListener.getChooseTime(times, choosedate, sdfs.parse(choosedate).getTime() / 1000);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				dismiss();
			}
		});

		nowDate = new Date();
		cal = Calendar.getInstance();
		cal.setTime(nowDate);
		initHour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		am_pm = cal.get(Calendar.AM_PM);
	}

	private int initHour;
	private int minute;
	private int am_pm;

	private void setTodaySpinner() {
		hours.clear();
		times.clear();
		int hourIndex = 0;
		int timeIndex = am_pm;
		int hourAdd = 0;
		if (minute >= 50) {
			hourAdd = 2;
		} else {
			hourAdd = 1;
		}
		if (initHour > 12) {

			hourIndex = initHour - 12 - 1 + hourAdd;
		} else if (initHour < 12) {
			hourIndex = initHour - 1 + hourAdd;
		}

		for (; hourIndex < STR_HOUR.length; hourIndex++) {
			hours.add(STR_HOUR[hourIndex]);// 几点
		}
		for (; timeIndex < STR_TIME.length; timeIndex++) {
			chooseAmPm = STR_TIME[timeIndex];
			times.add(STR_TIME[timeIndex]);// 上下午
		}
		if (isDay) {// 默认为u上午
			ampm_wheelview.setItems(times);
			ampm_wheelview.setSeletion(0);
		}

		time_wheelview.setItems(hours);
		time_wheelview.setSeletion(0);
		chooseAmPm = times.get(0);

		chooseTime = hours.get(0);

	}

	private void setOtherSpinner() {
		hours.clear();
		times.clear();
		int hourIndex = 0;
		int timeIndex = 0;
		for (; hourIndex < STR_HOUR.length; hourIndex++) {
			hours.add(STR_HOUR[hourIndex]);
		}
		for (; timeIndex < STR_TIME.length; timeIndex++) {
			times.add(STR_TIME[timeIndex]);
		}
		if (isDay) {
			ampm_wheelview.setItems(times);
			ampm_wheelview.setSeletion(0);
		}
		time_wheelview.setItems(hours);
		time_wheelview.setSeletion(0);

		chooseAmPm = times.get(0);
		chooseTime = hours.get(0);
	}

	private OnChooseTimeListener chooseTimeListener;

	public void setOnChooseTimeListener(OnChooseTimeListener onChooseTimeListener) {
		this.chooseTimeListener = onChooseTimeListener;
	}

	public interface OnChooseTimeListener {
		void getChooseTime(String time, String l, long m);
	}
}
