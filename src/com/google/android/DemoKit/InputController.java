package com.google.android.DemoKit;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class InputController extends AccessoryController {
	/*
	 * TODO
	private TextView mTemperature;
	private TextView mLightView;
	private TextView mLightRawView;
	*/
	private TextView xView;
	private TextView yView;

	private JoystickView mJoystickView;
	private TextView mFSRView;
	ArrayList<SwitchDisplayer> mSwitchDisplayers;
	/*
	 * TODO
	private final DecimalFormat mLightValueFormatter = new DecimalFormat("##.#");
	*/
	private final DecimalFormat mPositionFormatter = new DecimalFormat ("####");
	private final DecimalFormat mTemperatureFormatter = new DecimalFormat(
			"###" + (char)0x00B0);
	private final DecimalFormat mFSRValueFormatter = new DecimalFormat("#.####");

	InputController(DemoKitActivity hostActivity) {
		super(hostActivity);
		/*
		 * TODO
		mTemperature = (TextView) findViewById(R.id.tempValue);
		mLightView = (TextView) findViewById(R.id.lightPercentValue);
		mLightRawView = (TextView) findViewById(R.id.lightRawValue);
		*/
		xView = (TextView) findViewById(R.id.tempValue);
		yView = (TextView) findViewById(R.id.lightPercentValue);
		
		mJoystickView = (JoystickView) findViewById(R.id.joystickView);
		mFSRView = (TextView) findViewById(R.id.fsrVoltValue);
	}

	protected void onAccesssoryAttached() {
		mSwitchDisplayers = new ArrayList<SwitchDisplayer>();
		for (int i = 0; i < 4; ++i) {
			SwitchDisplayer sd = new SwitchDisplayer(i);
			mSwitchDisplayers.add(sd);
		}
	}
	
	/*
	 * TODO	
	public void setTemperature(int temperatureFromArduino) {
		 * Arduino board contains a 6 channel (8 channels on the Mini and Nano,
		 * 16 on the Mega), 10-bit analog to digital converter. This means that
		 * it will map input voltages between 0 and 5 volts into integer values
		 * between 0 and 1023. This yields a resolution between readings of: 5
		 * volts / 1024 units or, .0049 volts (4.9 mV) per unit.

		double voltagemv = temperatureFromArduino * 4.9;
		
		 * The change in voltage is scaled to a temperature coefficient of 10.0
		 * mV/degC (typical) for the MCP9700/9700A and 19.5 mV/degC (typical)
         * for the MCP9701/9701A. The out- put voltage at 0 degC is also scaled
         * to 500 mV (typical) and 400 mV (typical) for the MCP9700/9700A and
		 * MCP9701/9701A, respectively. VOUT = TC¥TA+V0degC

		double kVoltageAtZeroCmv = 400;
		double kTemperatureCoefficientmvperC = 19.5;
		double ambientTemperatureC = ((double) voltagemv - kVoltageAtZeroCmv)
				/ kTemperatureCoefficientmvperC;
		double temperatureF = (9.0 / 5.0) * ambientTemperatureC + 32.0;
		mTemperature.setText(mTemperatureFormatter.format(temperatureF));
	}

	public void setLightValue(int lightValueFromArduino) {
		mLightRawView.setText(String.valueOf(lightValueFromArduino));
		mLightView.setText(mLightValueFormatter
				.format((100.0 * (double) lightValueFromArduino / 1024.0)));
	}
	*/
	public void setPositionValue(int x, int y){
		xView.setText(mPositionFormatter.format(x));
		yView.setText(mPositionFormatter.format(y));
	}
	
	public float setFSRValue(int fsrValueFromArduino) {
		/*
		 * The value returned from Arduino is 0 to 1023, and the range of voltage
		 * is 0 to 5 V. So in this method, convert the returned value to the range
		 * of volts by the equation below.
		 * 			value / range_of_value * range_of_volts
		 */
		double valRange = 1024.0;
		double maxVoltage = 5.0;
		/*
		 * Value returned from arduino is volt value, so we need to convert it to force value
		 * or pressure value. The next equation is derived from a circuit and equation 
		 * between force value and resistance value. 1inch = 25.4mm
		 * 			Force/square-half-inch = 40 / [50/Volt - 10] 
		 */
		float Ans = (float)(fsrValueFromArduino / valRange * maxVoltage);
		mFSRView.setText(mFSRValueFormatter
				.format((double)Ans));
		return Ans;
	}
	
	public void setTouchValue(int xFromArduino, int yFromArduino){
		
	}

	public void switchStateChanged(int switchIndex, boolean switchState) {
		if (switchIndex >= 0 && switchIndex < mSwitchDisplayers.size()) {
			SwitchDisplayer sd = mSwitchDisplayers.get(switchIndex);
			sd.onSwitchStateChange(switchState);
		}
	}

	public void joystickButtonSwitchStateChanged(boolean buttonState) {
		mJoystickView.setPressed(buttonState);
	}

	public void joystickMoved(int x, int y) {
		mJoystickView.setPosition(x, y);
	}

	/*
	 * TODO
	public void onTemperature(int temperature) {
		setTemperature(temperature);
	}

	public void onLightChange(int lightValue) {
		setLightValue(lightValue);
	}
	*/
	public void onPositionChange(int x, int y){
		setPositionValue(x, y);
	}

	public void onSwitchStateChange(int switchIndex, Boolean switchState) {
		switchStateChanged(switchIndex, switchState);
	}

	public void onButton(Boolean buttonState) {
		joystickButtonSwitchStateChanged(buttonState);
	}

	public void onStickMoved(int x, int y) {
		joystickMoved(x, y);
	}

	public void onFSRChanged(int FSRValue){
		setFSRValue(FSRValue);
	}

	class SwitchDisplayer {
		private final ImageView mTargetView;
		private final Drawable mOnImage;
		private final Drawable mOffImage;

		SwitchDisplayer(int switchIndex) {
			int viewId, onImageId, offImageId;
			switch (switchIndex) {
			default:
				viewId = R.id.Button1;
				onImageId = R.drawable.indicator_button1_on_noglow;
				offImageId = R.drawable.indicator_button1_off_noglow;
				break;
			case 1:
				viewId = R.id.Button2;
				onImageId = R.drawable.indicator_button2_on_noglow;
				offImageId = R.drawable.indicator_button2_off_noglow;
				break;
			case 2:
				viewId = R.id.Button3;
				onImageId = R.drawable.indicator_button3_on_noglow;
				offImageId = R.drawable.indicator_button3_off_noglow;
				break;
			case 3:
				viewId = R.id.Button4;
				onImageId = R.drawable.indicator_button_capacitive_on_noglow;
				offImageId = R.drawable.indicator_button_capacitive_off_noglow;
				break;
			}
			mTargetView = (ImageView) findViewById(viewId);
			mOffImage = mHostActivity.getResources().getDrawable(offImageId);
			mOnImage = mHostActivity.getResources().getDrawable(onImageId);
		}

		public void onSwitchStateChange(Boolean switchState) {
			Drawable currentImage;
			if (!switchState) {
				currentImage = mOffImage;
			} else {
				currentImage = mOnImage;
			}
			mTargetView.setImageDrawable(currentImage);
		}

	}
}
