package com.google.android.DemoKit;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class BaseActivity extends DemoKitActivity 
{
	//�������ۂ�
	public static boolean EXPERIMENT = false;
	
	//arduino����̓��͐���N���X
	private InputController mInputController;
	
	//openGL�ł̃e�N�X�`���ό` or �e�Z���T�l�̉���
	public static boolean GL_USE = true;
	public GLView glView;

	//�����p�����[�^�̎w��
	private static final String NAME = "";//�팱�Җ�
	public static final int MAX_COUNT = 3*10*2*2;
	private static int switchCount = 0;
	//�������O�̐ݒ�
	private static boolean getLog = false;
	private static final int DATA_PER_ROW = 200;//��s���Ƃ̃f�[�^���B�G�N�Z���œǂݍ��ނ��߂ɍs���𐧌����Ă���
	private int logCount = 0;
	private boolean bkFlag = false;//�o�b�N�A�b�v�t�@�C���쐬�̗L��
	
	// �_�C�A���O�\��
	private Dialog dialog;
	
	/* ------------------------------------------------------------------------------ */
	public BaseActivity() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// ��ʂ̌��������ŌŒ�
		if(GL_USE) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		// �S��ʕ\��
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// �^�C�g���̔�\��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		super.onCreate(savedInstanceState);
		if (mAccessory != null) {
			showControls();
		} else {
			hideControls();
		}
		
		//�Z���T�[�l�Ǘ��N���X�̏�����
		SensorManager.initSensorValue();
		
		if(EXPERIMENT) FileReadWrite.setFileTitle(NAME);
	}

	/* ------------------------------------------------------------------------------ */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if(!GL_USE){
	        inflater.inflate(R.menu.option_menu_sensor, menu);
		} else if(EXPERIMENT){
	        inflater.inflate(R.menu.option_menu_experiment, menu);
		} else {
	        inflater.inflate(R.menu.option_menu, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.simulate:
			showControls();
        	return true;
        case R.id.quit:
			finish();
			System.exit(0);
        	return true;
        case R.id.start_log:
        	if(!getLog){
				getLog = true;
				FileReadWrite.WriteFile(NAME, FileReadWrite.START);
				getLogMode();
				Toast.makeText(this, getString(R.id.start_log), Toast.LENGTH_SHORT).show();
        	}
        	return true;
        case R.id.end_log:
        	if(getLog){
				getLog = false;
				FileReadWrite.WriteFile(NAME, FileReadWrite.END);
				Toast.makeText(this, getString(R.id.end_log), Toast.LENGTH_SHORT).show();
        	}
        	return true;
        case R.id.parameters:
        	seekParametersDialog();
        	return true;
        case R.id.texture:
        	selectTextureDialog();
        	return true;
        case R.id.hand:
        	selectHandDialog();
        	return true;
        case R.id.hardness:
        	//TODO
        	return true;
        case R.id.light:
        	setLightPositionDialog();
        	return true;
        case R.id.perse:
        	GLRenderer.perspectiveMode();
        	return true;
        default: break;
        }

		return true;
	}
	
	/* ------------------------------------------------------------------------------ */
	// TODO
	// this method to launch dialog has to be in the sensor manager class
	private void seekParametersDialog(){
		showDialog("Set the Deformation Amount.", getLayoutInflater().inflate(R.layout.parameters_seekbar, null));
		
		final Button ok = (Button) dialog.findViewById(R.id.button_ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final SeekBar seekDeformation = (SeekBar) dialog.findViewById(R.id.seek_deformation);
				SensorManager.setDfmAmnt((float)seekDeformation.getProgress()/100.0f * 5.0f);
				final SeekBar seekDelay = (SeekBar) dialog.findViewById(R.id.seek_delay);
				SensorManager.setDelay((float)seekDelay.getProgress()/100.0f * 1.0f);

				dismissDialog();
			}
		});
	}

	// TODO
	// this method to launch dialog has to be in the bind texture class
	private void selectTextureDialog(){
		showDialog("select the Texture.", getLayoutInflater().inflate(R.layout.texture_selection, null));
		
		final ImageView chess = (ImageView) dialog.findViewById(R.id.image_chess);
		chess.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.CHESS*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(2.0f);
				
				dismissDialog();
			}
		});
		
		final ImageView sponge = (ImageView) dialog.findViewById(R.id.image_sponge);
		sponge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.SPONGE*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(4.0f);
				
				dismissDialog();
			}
		});
		
		final ImageView stone = (ImageView) dialog.findViewById(R.id.image_stone);
		stone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.STONE*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(0.2f);
				
				dismissDialog();
			}
		});
		
		final ImageView meat_soft = (ImageView) dialog.findViewById(R.id.image_meat_soft);
		meat_soft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.MEAT_SOFT*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(4.0f);
				
				dismissDialog();
			}
		});
		
		final ImageView meat_normal = (ImageView) dialog.findViewById(R.id.image_meat_normal);
		meat_normal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.MEAT_NORMAL*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(2.0f);
				
				dismissDialog();
			}
		});
		
		final ImageView meat_hard = (ImageView) dialog.findViewById(R.id.image_meat_hard);
		meat_hard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = BindTextures.MEAT_HARD*2 + (BindTextures.getIsTransParent() ? 1 : 0);
				BindTextures.setTexId(id);
				SensorManager.setDfmAmnt(0.5f);
				
				dismissDialog();
			}
		});
	}
	
	// TODO
	// this method to launch dialog has to be in the bind texture class
	private void selectHandDialog(){
		showDialog("select the Hand.", getLayoutInflater().inflate(R.layout.hand_selection, null));
		
		//��̕\���ɍ��킹���e�N�X�`�����ߏ���
		final ImageView none = (ImageView) dialog.findViewById(R.id.image_none);
		none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BindTextures.setHandId(false, BindTextures.ALPHA);
				dismissDialog();
			}
		});
		
		final ImageView hand = (ImageView) dialog.findViewById(R.id.image_hand);
		hand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BindTextures.setHandId(true, BindTextures.ALPHA);
				dismissDialog();
			}
		});
		
		final ImageView edge = (ImageView) dialog.findViewById(R.id.image_edge);
		edge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BindTextures.setHandId(true, BindTextures.EDGE);
				dismissDialog();
			}
		});
		
		final ImageView shadow = (ImageView) dialog.findViewById(R.id.image_shadow);
		shadow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BindTextures.setHandId(true, BindTextures.SHADOW);
				dismissDialog();
			}
		});
	}
	
	// TODO
	// this method to launch dialog has to be in the gl renderer class
	private void setLightPositionDialog(){
		showDialog("select the Light Position.", getLayoutInflater().inflate(R.layout.light_pos, null));
		
		final SeekBar seekX = (SeekBar) dialog.findViewById(R.id.seek_x);
		seekX.setProgress(50);
		final SeekBar seekY = (SeekBar) dialog.findViewById(R.id.seek_y);
		seekY.setProgress(50);
		final SeekBar seekZ = (SeekBar) dialog.findViewById(R.id.seek_z);
		seekZ.setProgress(50);
		
		final Button ok = (Button) dialog.findViewById(R.id.button_ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float x = (float)(seekX.getProgress()-50f)/100.0f * 4.0f;
				float y = (float)(seekY.getProgress()-50f)/100.0f * 4.0f;
				float z = (float)seekZ.getProgress()/100.0f * 5.0f;
				LightPositionController.setLightPosition(x, y, z);
				dismissDialog();
			}
		});

		final Button dflt = (Button) dialog.findViewById(R.id.button_default);
		dflt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LightPositionController.setLightPosition(-0.5f, 0.5f, 2.0f);
				dismissDialog();
			}
		});
	}

	//dialog�̕\��
	private void showDialog(String title, View content) {
		dialog = new Dialog(this);
		dialog.setTitle(title);
		dialog.setContentView(content);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialog.show();
	}
	
	//dialog�̏���
	private void dismissDialog(){
		dialog.dismiss();
	}
	
	/* ------------------------------------------------------------------------------ */
	protected void enableControls(boolean enable) {
		if (enable) {
			showControls();
		} else {
			hideControls();
		}
	}

	protected void hideControls() {
		setContentView(R.layout.no_device);
		mInputController = null;
	}

	protected void showControls() {
		ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		glView = new GLView(this);
		setContentView(R.layout.main);
		if(GL_USE) addContentView(glView, layoutParams);
				
		mInputController = new InputController(this);
		mInputController.accessoryAttached();		
	}
	
	/* ------------------------------------------------------------------------------ */
	protected void handleSwitchMessage(SwitchMsg o) {
		if (mInputController != null) {
			byte sw = o.getSw();
			if (sw >= 0 && sw < 4) {
				mInputController.switchStateChanged(sw, o.getState() != 0);
				
				//�o�b�N�A�b�v�t�@�C���̍쐬
				if(EXPERIMENT && !bkFlag){
					String stringModeArray = "";
					//4��ނ���2��ނ̑g�ݍ��킹��12�ʂ�
					for(int i = 0; i < MAX_COUNT/2; i++){
						if(i % (MAX_COUNT/12) == 0) stringModeArray += "\n";
						stringModeArray += glView.renderer.texture.modeArray[i][0] + ","
										+  glView.renderer.texture.modeArray[i][1] + ",";
					}
					FileReadWrite.WriteBackUp(stringModeArray);
					//�o�b�N�A�b�v���쐬�t���O�𗧂Ă�
					bkFlag = true;
				}

				//surface���[�h�̕ύX
				if(sw == 0 && o.getState() != 0){
					if(EXPERIMENT){
						switchCount += (switchCount%2==0) ? 2 : 1;
						switchCount %= MAX_COUNT;
						//�e�N���X�ł̃��f���ύX
						changeView();
						Toast.makeText(this, "COUNT : " + switchCount, Toast.LENGTH_SHORT).show();
					} else {
					}
		        //���̎��s��(�����p)
				} else if(sw == 1 && o.getState() != 0){
					if(EXPERIMENT){
						switchCount += (switchCount%2==0) ? 1 : -1;
						//�e�N���X�ł̃��f���ύX
						changeView();
						Toast.makeText(this, "COUNT : " + switchCount, Toast.LENGTH_SHORT).show();
					}
				//�A���t�@�l�̕ύX
				} else if(sw == 2 && o.getState() != 0){
					if(EXPERIMENT){
						switchCount -= 2;
						//�e�N���X�ł̃��f���ύX
						changeView();
						Toast.makeText(this, "COUNT : " + switchCount, Toast.LENGTH_SHORT).show();
					} else {
					}
			    //���O�擾�̊J�n�E�I��
				} else if(sw == 3 && o.getState() != 0){
					if(EXPERIMENT){
						//Log�擾��false�Ȃ�Log�擾�J�n�Afalse�Ȃ�I��
						if(getLog){
							getLog = false;
							getLogModeArray();
							FileReadWrite.WriteFile(NAME, FileReadWrite.END);
							Toast.makeText(this, getString(R.id.start_log), Toast.LENGTH_SHORT).show();
						} else {
							getLog = true;
							getLogModeArray();
							FileReadWrite.WriteFile(NAME, FileReadWrite.START);
							getLogMode();
							Toast.makeText(this, getString(R.id.end_log), Toast.LENGTH_SHORT).show();
						}
					}
				}
			} else if (sw == 4) {
				mInputController
						.joystickButtonSwitchStateChanged(o.getState() != 0);
			}
		}
	}

	/* ------------------------------------------------------------------------------ */
	//�������̊eView�̕ύX
	private void changeView(){
		Texture.changeSurfaceExperiment(switchCount);
		Hand.changeModeExperiment(switchCount);
		glView.renderer.place100.changeNumber((switchCount/100)%10);//100�̈�
		glView.renderer.place10.changeNumber((switchCount/10)%10);//10�̈�
		glView.renderer.place1.changeNumber(switchCount%10);//1�̈�
		if(getLog) getLogMode();
	}

	/* ------------------------------------------------------------------------------ */
	//�������̔z��\�����̕ۑ�
	private void getLogModeArray(){
		String stringModeArray = null;
		for(int i = 0; i < MAX_COUNT/2; i++){
			if(i % (MAX_COUNT/12) == 0) stringModeArray += "\n";
			stringModeArray += glView.renderer.texture.modeArray[i][0] + ","
							+ glView.renderer.texture.modeArray[i][1] + ",";
		}
		FileReadWrite.WriteFile(
				stringModeArray,
				FileReadWrite.ARRAYDATA
		);
	}

	//�������̃��[�h�ؑ֎��̃w�b�_�ۑ�
	private void getLogMode(){
		FileReadWrite.WriteFile(
				"dfmAmnt:," + SensorManager.getGLpressure() +
				",texture:," + BindTextures.getTexId() + 
				",hand:," + BindTextures.getHandId() +
				",count:," + switchCount,
				FileReadWrite.HEADER
				);
		logCount = 0;
	}
	
	/* ------------------------------------------------------------------------------ */
	/**
	 * ���̓��O�ۑ��̂��߂̏����n��
	 */
	private void writeMsgToFile(float value){
		if(getLog) {
			if(logCount%DATA_PER_ROW == 0){
				FileReadWrite.WriteFile(String.valueOf(value), FileReadWrite.RETURN);
				logCount = 0;
			} else {
				FileReadWrite.WriteFile(String.valueOf(value), FileReadWrite.DATA);
			}
			logCount++;
		}
	}

	/* ------------------------------------------------------------------------------ */
	/**
	 * �����Z���T����̒l�̕ϊ�
	 */
	protected void handleFSRMessage(FSRMsg f)
	{
		if (mInputController != null) {
			float value = mInputController.setFSRValue(f.getFSR());
			sendFSRMsgToView(value);
			if(EXPERIMENT) writeMsgToFile(value);
		}
	}
	private void sendFSRMsgToView(float value)
	{
		SensorManager.updateFSRValue(value);
		SensorManager.convertSensorValueGL(GLRenderer.getAx(), GLRenderer.getAy());
		Hand.setPos(SensorManager.getGLx(), SensorManager.getGLy(), SensorManager.getGLpressure());
	}
	
	/* ------------------------------------------------------------------------------ */
	/**
	 * �^�b�`�Z���T����̒l�ϊ�
	 */
	protected void handleTouchMessage(TouchMsg tch)
	{
		if (mInputController != null) {
			//convert x:70-781 to 0-width
			float x = (tch.getX()-70f)/(781f-70f) * GLRenderer.BASE_WIDTH;
			//convert y:81-822 to 0-height
			float y = (tch.getY()-81f)/(822f-81f) * GLRenderer.BASE_HEIGHT;
			
			sendTouchMsgToView(x, y);
		}
	}
	private void sendTouchMsgToView(float xValue, float yValue)
	{
		SensorManager.updateTouchValue(xValue, yValue);
		SensorManager.convertSensorValueGL(GLRenderer.getAx(), GLRenderer.getAy());
		Hand.setPos(SensorManager.getGLx(), SensorManager.getGLy(), SensorManager.getGLpressure());
	}

	/* ------------------------------------------------------------------------------ */
	/**
	 * �^�b�`�Z���T�E�����Z���T����̒l�ϊ�
	 */
	protected void handlePanelMessage(PanelMsg p)
	{
		if(mInputController != null){
			//convert x:70-781 to 0-width
			float x = (p.getX()-70f)/(781f-70f) * GLRenderer.BASE_WIDTH;
			//convert y:81-822 to 0-height
			float y = (p.getY()-81f)/(822f-81f) * GLRenderer.BASE_HEIGHT;
			//set value at Status View
			mInputController.setPositionValue(p.getX(), p.getY());
			//convert pressure
			float value = mInputController.setFSRValue(p.getFSR());
			
			sendPanelMsgToView(value, x, y);
		}
	}
	private void sendPanelMsgToView(float pressValue, float xValue, float yValue)
	{
		SensorManager.updateSensorValue(pressValue, xValue, yValue);
		SensorManager.convertSensorValueGL(GLRenderer.getAx(), GLRenderer.getAy());
		Hand.setPos(SensorManager.getGLx(), SensorManager.getGLy(), SensorManager.getGLpressure());
	}

	/* ------------------------------------------------------------------------------ */
	public void onResume(){
		// this code may cause the error condition on the application, so if thie does not work, please delete this sentences
		super.onResume();
		selectTextureDialog();
	}


}