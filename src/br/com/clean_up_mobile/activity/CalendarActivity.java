package br.com.clean_up_mobile.activity;

import static android.widget.Toast.LENGTH_SHORT;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.fragment.DetalheDiaristaFragment;
import com.squareup.timessquare.CalendarPickerView;
import android.view.View;
import android.view.View.OnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends Activity {
	private static final String TAG = "CalendarFragment";
	// private static final String TAG = "SampleTimesSquareActivity";
	private CalendarPickerView calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_activity);

		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.MONTH, 1);

		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		Date today = new Date();
		calendar.init(today, nextYear.getTime()).withSelectedDate(new Date());

		Button btDone = (Button) findViewById(R.id.button_done);

		btDone.setOnClickListener(btnDoneOnClickListener);
	}

	OnClickListener btnDoneOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Date date = calendar.getSelectedDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			String novaData = new SimpleDateFormat("dd/MM/yyyy", new Locale(
					"pt", "br")).format(cal.getTime());

			Intent it = new Intent(CalendarActivity.this,
					DetalheDiaristaFragment.class);
			it.putExtra("data_servico", novaData);
			setResult(1, it);
			finish();
		}
	};
}
