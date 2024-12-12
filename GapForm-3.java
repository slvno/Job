package com.example.chuvak.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

public class GapForm extends CS_MainForm
{
	private static final String ermes = "Ощибка при вводе параметра";
	private recRast rRast = new recRast();
	private recRast rRastBack = new recRast();
	private ShowMode sMode = ShowMode.forValue(0);

	/*
	private CS_TextBox textBox4;
	private CS_TextBox textBox5;
	private CS_TextBox textBox6;
    */
	private CS_MenuItem mCloseItem;
	private CS_MenuItem mCalcHVItem;
    private CS_MainMenu mainMenu1;
	private CS_PictureBox pictureBox1;
	private CS_Label labelCD;
	private CS_Label labNumRast;
	private CS_Label label5;
	private CS_Label label4;
	private CS_Label label3;
	private CS_Label label1;
	private CS_TextBox textBoxGap3;
	private CS_TextBox textBoxGap2;
	private CS_TextBox textBoxGap1;
	private CS_Label label7;
	private CS_Label label6;
	private CS_Label label2;
	private CS_TextBox textBoxV;
	private CS_TextBox textBoxH;
	
	public GapForm(Context context, RelativeLayout layout1, String stCil, String stNumRast)
	{
        super(context, layout1);
		InitializeComponent();
		labelCD.setText(stCil);
		labNumRast.setText(stNumRast);
	  //  BackColor = Color.LightCyan;
	}
    private Handler handler = new Handler();

    RefObject<recRast> Execute_rr = null;
    RefObject<Boolean> Execute_differParam = null;
    RefObject<CS_DialogResult> Execute_result = null;
    Object ownerForm = null;

	public final void /*CS_DialogResult*/ Execute(RefObject<recRast> rr,
                                                  RefObject<Boolean> differParam, ShowMode sm,
                                                  RefObject<CS_DialogResult> result,
                                                  Object ownerForm
                                                  ) {
        AssignHardButtons();
        this.ownerForm = ownerForm;

        Execute_rr = rr;
        Execute_differParam = differParam;
        Execute_result = result;

        rRastBack.set(rr.argvalue);
        rRast.set(rr.argvalue);

        sMode = sm;
/*
        handler.post(new Runnable() {public void run() {//////////
*/
        if (sMode == ShowMode.stVstr) {
            mainMenu1.MenuItems().Remove(mCloseItem);
            mCloseItem.setText("Вперед");  //(4) ShowMode.smMeas

            CS_MenuItem mItemBack = new CS_MenuItem();
            mItemBack.setText("Назад");
            mItemBack.addClick(this, "mItemBack_Click", EventArgs.Empty());
            mainMenu1.MenuItems().Add(mItemBack);

            mainMenu1.MenuItems().Add(mCloseItem);
        }

        ShowParam(true);
        CheckCalcEnabled();

        Show();
  /*  }});
*/
	/*
			CS_DialogResult result = //ShowDialog();
                                 getResult();

        ((recRast)rr.argvalue).set(rRast);

		differParam.argvalue = IsDifferParam();
		return result;
		*/

	}
    public void Execute_Close()
    {
        ((recRast)Execute_rr.argvalue).set(rRast);
        Execute_differParam.argvalue = IsDifferParam();

        if (this.ownerForm instanceof StartForm)
            ((StartForm)this.ownerForm).ShowGapForm_Exit();
        else if (this.ownerForm instanceof ControlForm)
            ((ControlForm)this.ownerForm).ShowGapForm_Exit();
    }

    public CS_DialogResult  Execute_Result()
    {
        return getResult();
    }

	private void AssignHardButtons()
	{
		//hardwareButton1.HardwareKey = HardwareKeys.ApplicationKey1;
		//hardwareButton2.HardwareKey = HardwareKeys.ApplicationKey2;
		//hardwareButton3.HardwareKey = HardwareKeys.ApplicationKey3;
		//hardwareButton4.HardwareKey = HardwareKeys.ApplicationKey4;
	}

	private String ParToText(double v)
	{
		return (v == glob.defVal) ? "" : (new Double(v)).toString();
	}

	private void ShowParam(boolean fullShow)
	{
		textBoxGap1.setText(ParToText(rRast.gap1));
		textBoxGap2.setText(ParToText(rRast.gap2));
		textBoxGap3.setText(ParToText(rRast.gap3));

		if (fullShow)
		{
			textBoxH.setText(ParToText(rRast.hor));
			textBoxV.setText(ParToText(rRast.vert));
		}
	}

	private boolean IsDifferParam()
	{
		return (rRast.hor != rRastBack.hor) || (rRast.vert != rRastBack.vert) || (rRast.gap1 != rRastBack.gap1) || (rRast.gap2 != rRastBack.gap2) || (rRast.gap3 != rRastBack.gap3);
	}

	private boolean IsCorrectGapText(CS_TextBox tb, RefObject<Double> buf)
	{
		boolean result = true;
		if (tb.getText().equals(""))
		{
			buf.argvalue = glob.defVal;
		}
		else
		{
			result = CommonDop.IsCorrectTextEdit(tb, false, ermes, true, buf);
			buf.argvalue = (double)Func.Round(buf.argvalue, glob.rn);
		}
		return result;
	}

	private boolean IsCorrectRastInfo(boolean showMessage)
	{
		RefObject<Double> tempRef_gap1 = new RefObject<Double>(rRast.gap1);
		RefObject<Double> tempRef_gap2 = new RefObject<Double>(rRast.gap2);
		RefObject<Double> tempRef_gap3 = new RefObject<Double>(rRast.gap3);
		RefObject<Double> tempRef_hor = new RefObject<Double>(rRast.hor);
		RefObject<Double> tempRef_vert = new RefObject<Double>(rRast.vert);
		boolean result = IsCorrectGapText(textBoxGap1, tempRef_gap1) &&
                IsCorrectGapText(textBoxGap2, tempRef_gap2) &&
                IsCorrectGapText(textBoxGap3, tempRef_gap3) &&
                (CommonDop.IsCorrectTextEdit(textBoxH, false, ermes, showMessage, tempRef_hor)) &&
                (CommonDop.IsCorrectTextEdit(textBoxV, false, ermes, showMessage, tempRef_vert));
		rRast.gap1 = tempRef_gap1.argvalue;
		rRast.gap2 = tempRef_gap2.argvalue;
		rRast.gap3 = tempRef_gap3.argvalue;
		rRast.hor = tempRef_hor.argvalue;
		rRast.vert = tempRef_vert.argvalue;

		if (result)
		{
			rRast.hor = (double)Func.Round(rRast.hor, glob.rn);
			rRast.vert = (double)Func.Round(rRast.vert, glob.rn);
		}
		return result;
	}

	private boolean IsCalcHV()
	{
		RefObject<Double> tempRef_gap1 = new RefObject<Double>(rRast.gap1);
		RefObject<Double> tempRef_gap2 = new RefObject<Double>(rRast.gap2);
		RefObject<Double> tempRef_gap3 = new RefObject<Double>(rRast.gap3);
		boolean tempVar = (CommonDop.IsCorrectTextEdit(textBoxGap1, false, ermes, true, tempRef_gap1)) && (CommonDop.IsCorrectTextEdit(textBoxGap2, false, ermes, true, tempRef_gap2)) && (CommonDop.IsCorrectTextEdit(textBoxGap3, false, ermes, true, tempRef_gap3));
			rRast.gap1 = tempRef_gap1.argvalue;
		rRast.gap2 = tempRef_gap2.argvalue;
		rRast.gap3 = tempRef_gap3.argvalue;
		if (tempVar)
		{
		   rRast.hor = -(rRast.gap1 - rRast.gap2) / 2;
		   rRast.vert = (rRast.gap1 + rRast.gap2) / 2 - rRast.gap3;

		   rRast.hor = (double)Func.Round(rRast.hor, glob.rn);
		   rRast.vert = (double)Func.Round(rRast.vert, glob.rn);

		   textBoxH.setText((new Double(rRast.hor)).toString());
		   textBoxV.setText((new Double(rRast.vert)).toString());
		   return true;
		}
		else
		{
			return false;
		}
	}

	public void mCloseItem_Click(EventArgs e)
	{
        if (CS_ProjectOptions.getMainTimer(getContext()).isEnabled() )
            return;

        CS_ProjectOptions.getMainTimer(getContext()).setDelay(2000);
        CS_ProjectOptions.getMainTimer(getContext()).setOneShot(true);
        CS_ProjectOptions.getMainTimer(getContext()).setEnabled(true);

		if (IsCorrectRastInfo(true))
		{
			setResult(CS_DialogResult.OK);
			Close();
            Execute_result.argvalue = CS_DialogResult.OK;
            Execute_Close();
		}
	}

	public void mItemBack_Click(EventArgs e)
	{
        if (CS_ProjectOptions.getMainTimer(getContext()).isEnabled() )
            return;

        CS_ProjectOptions.getMainTimer(getContext()).setDelay(2000);
        CS_ProjectOptions.getMainTimer(getContext()).setOneShot(true);
        CS_ProjectOptions.getMainTimer(getContext()).setEnabled(true);

        IsCorrectRastInfo(false);
	   // if (IsCorrectRastInfo(false))
	  //  {
			setResult(CS_DialogResult.Cancel);
			Close();
            Execute_result.argvalue = CS_DialogResult.Cancel;
            Execute_Close();
	  //  }
	}

	public void mCalcHVItem_Click(EventArgs e)
	{
        if (CS_ProjectOptions.getMainTimer(getContext()).isEnabled() )
            return;

        CS_ProjectOptions.getMainTimer(getContext()).setDelay(2000);
        CS_ProjectOptions.getMainTimer(getContext()).setOneShot(true);
        CS_ProjectOptions.getMainTimer(getContext()).setEnabled(true);

        IsCalcHV();
	}

	private void CheckCorrectNumText()
	{
	//    if (sMode == ShowMode.stVstr)
		mCloseItem.setEnabled((CommonDop.IsCorrectNumText(textBoxH.getText()) && CommonDop.IsCorrectNumText(textBoxV.getText())));
	}

	public void textBoxH_TextChanged(EventArgs e)
	{
		CheckCorrectNumText();

		rRast.gap1 = glob.defVal;
		rRast.gap2 = glob.defVal;
		rRast.gap3 = glob.defVal;
		ShowParam(false);
	}

	public void GapForm_KeyDown(EventArgs e1)
	{
        if ((e1 == null) ||
                (!(e1 instanceof CS_KeyEventArgs))
                )
            return;

        CS_KeyEventArgs e = (CS_KeyEventArgs)e1;

        switch (e.KeyCode()) //Hardware
		{
			case CS_Keys.D1 : //ApplicationKey1 - Hardware
				if (mCalcHVItem.Enabled())
				{
				mCalcHVItem_Click(null);
				}
				break;
			case CS_Keys.D2 : //ApplicationKey2 - Hardware
				if (sMode != ShowMode.stVstr)
				{
				mCloseItem_Click(null);  //(4) ShowMode.smMeas
				}
				break;

			default:
				break;
		}
	}

	private void CheckCalcEnabled()
	{
		mCalcHVItem.setEnabled((CommonDop.IsCorrectNumText(textBoxGap1.getText()) && CommonDop.IsCorrectNumText(textBoxGap2.getText()) && CommonDop.IsCorrectNumText(textBoxGap3.getText())));
	}

	public void textBoxGap1_TextChanged(EventArgs e)
	{
		CheckCalcEnabled();
	}




	private void InitializeComponent()
	{
        this.setLocation(new Point(0, 0));

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int displayHeight = metrics.heightPixels;
        int displayWidth = metrics.widthPixels;

        this.setSize(new Size(displayWidth, displayHeight));
        this.SuspendLayout();
        this.setMargin(new CS_Padding(2));

		//this.components = new System.ComponentModel.Container();
		//System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(GapForm.class);
		this.mainMenu1 = new CS_MainMenu(getContext(), getFormLayout());
		this.mCalcHVItem = new CS_MenuItem();
		this.mCloseItem = new CS_MenuItem();

        /*
		this.textBox4 = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
		this.textBox5 = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100 );
		this.textBox6 = new CS_TextBox(getContext(), getClientLayout(), 0,0, 100, 100);
        */
		this.pictureBox1 = new CS_PictureBox(getContext());
		this.labelCD = new CS_Label(getContext());
		this.labNumRast = new CS_Label(getContext());
		this.label5 = new CS_Label(getContext());
		this.label4 = new CS_Label(getContext());
		this.label3 = new CS_Label(getContext());
		this.label1 = new CS_Label(getContext());
		this.textBoxGap3 = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
        this.textBoxGap3.setTextType(TextType.NumberDecimal);

		this.textBoxGap2 = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
        this.textBoxGap2.setTextType(TextType.NumberDecimal);

		this.textBoxGap1 = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
        this.textBoxGap1.setTextType(TextType.NumberDecimal);

		this.label7 = new CS_Label(getContext());
		this.label6 = new CS_Label(getContext());
		this.label2 = new CS_Label(getContext());
		this.textBoxV = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
        this.textBoxV.setTextType(TextType.NumberDecimal);

		this.textBoxH = new CS_TextBox(getContext(), getClientLayout(), 0,0 , 100, 100);
        this.textBoxH.setTextType(TextType.NumberDecimal);
		//((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
		//
		// 
		// mainMenu1
		// 
		this.mainMenu1.MenuItems().AddRange(new CS_MenuItem[] { this.mCalcHVItem, this.mCloseItem});
		// 
		// mCalcHVItem
		// 
		//this.mCalcHVItem.Index = 0;
		this.mCalcHVItem.setText("Расчитать В,Г (1)");
		this.mCalcHVItem.addClick(this, "mCalcHVItem_Click", EventArgs.Empty());
		// 
		// mCloseItem
		// 
		//this.mCloseItem.Index = 1;
		this.mCloseItem.setText("Закрыть (4)");
		this.mCloseItem.addClick(this, "mCloseItem_Click", EventArgs.Empty());

        /*
		// 
		// textBox4
		// 
		this.textBox4.setLocation(new Point(0, 0));
		this.textBox4.setName("textBox4");
		this.textBox4.setSize(new Size(100, 20));
		//this.textBox4.TabIndex = 0;
		// 
		// textBox5
		// 
		this.textBox5.setLocation(new Point(0, 0));
		this.textBox5.setName("textBox5");
		this.textBox5.setSize(new Size(100, 20));
		//this.textBox5.TabIndex = 0;
		// 
		// textBox6
		// 
		this.textBox6.setLocation(new Point(0, 0));
		this.textBox6.setName("textBox6");
		this.textBox6.setSize(new Size(100, 20));
		//this.textBox6.TabIndex = 0;
        */
		// 
		// pictureBox1
		// 
		this.pictureBox1.setBackColor(CS_Color.Silver);
		//this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
        this.pictureBox1.Assign(CS_Image.getBitmapFromAsset(getContext(), "img/panel_1.png", 0,0));

		this.pictureBox1.setLocation(new Point(187+103, 156));
		//this.pictureBox1.setMargin(new CS_Padding(2, 2, 2, 2);
		this.pictureBox1.setName("pictureBox1");
		this.pictureBox1.setSize(new Size(292*6/10, 217*6/10));
		//this.pictureBox1.TabIndex = 29;
		//this.pictureBox1.TabStop = false;
		// 
		// labelCD
		// 
		this.labelCD.setFont("Tahoma", 20F);
		this.labelCD.setLocation(new Point(20, 320));
		this.labelCD.setMargin( new CS_Padding(2, 0, 2, 0));
		this.labelCD.setName("labelCD");
		this.labelCD.setSize(new Size(178, 34));
		//this.labelCD.TabIndex = 27;
		this.labelCD.setText("Ц");
		// 
		// labNumRast
		// 
		this.labNumRast.setFont("Tahoma", 20F);
		this.labNumRast.setLocation(new Point(624, 320));
		this.labNumRast.setMargin(new CS_Padding(2, 0, 2, 0));
		this.labNumRast.setName("labNumRast");
		this.labNumRast.setSize(new Size(175, 34));
		//this.labNumRast.TabIndex = 28;
		this.labNumRast.setText("P№");
		//this.labNumRast.TextAlign = System.Drawing.ContentAlignment.TopRight;
		// 
		// label5
		// 
		this.label5.setFont("Tahoma", 18F);
		this.label5.setLocation(new Point(11+103, 173));
		this.label5.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label5.setName("label5");
		this.label5.setSize(new Size(40, 34));
		//this.label5.TabIndex = 23;
		this.label5.setText("в");
		// 
		// label4
		// 
		this.label4.setFont("Tahoma", 18F);
		this.label4.setLocation(new Point(11+103, 132));
		this.label4.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label4.setName("label4");
		this.label4.setSize(new Size(40, 34));
		//this.label4.TabIndex = 24;
		this.label4.setText("б");
		// 
		// label3
		// 
		this.label3.setFont("Tahoma", 18F);
		this.label3.setLocation(new Point(11+103, 91));
		this.label3.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label3.setName("label3");
		this.label3.setSize(new Size(40, 34));
		//this.label3.TabIndex = 25;
		this.label3.setText("а");
		// 
		// label1
		// 
		this.label1.setLocation(new Point(20, 20));
        this.label1.setFont("Tahoma", 18F);
		this.label1.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label1.setName("label1");
		this.label1.setSize(new Size(370, 88));
		//this.label1.TabIndex = 26;
		this.label1.setText("Зазоры между валопроводом и базовой расточкой, мм");
        //
        // label2
        //
        this.label2.setLocation(new Point(410, 20));
        this.label2.setFont("Tahoma", 18F);
        this.label2.setMargin(new CS_Padding(2, 0, 2, 0));
        this.label2.setName("label2");
        this.label2.setSize(new Size(370, 88));
        //this.label2.TabIndex = 2;
        this.label2.setText("Смещение оси валопровода относительно базовых расточек, мм");
        //
		// textBoxGap3
		// 
		this.textBoxGap3.setFont("Tahoma", 18F);
		this.textBoxGap3.setLocation(new Point(50+103, 170));
		this.textBoxGap3.setMargin(new CS_Padding(2, 2, 2, 2));
		this.textBoxGap3.setName("textBoxGap3");
		this.textBoxGap3.setSize(new Size(120,34));
		//this.textBoxGap3.TabIndex = 13;
		this.textBoxGap3.addTextChanged(this, "textBoxGap1_TextChanged", EventArgs.Empty());
		// 
		// textBoxGap2
		// 
		this.textBoxGap2.setFont("Tahoma", 18F);
		this.textBoxGap2.setLocation(new Point(50+103, 128));
		this.textBoxGap2.setMargin(new CS_Padding(2, 2, 2, 2));
		this.textBoxGap2.setName("textBoxGap2");
		this.textBoxGap2.setSize(new Size(120,34));
		//this.textBoxGap2.TabIndex = 12;
		this.textBoxGap2.addTextChanged(this, "textBoxGap1_TextChanged", EventArgs.Empty());
		// 
		// textBoxGap1
		// 
		this.textBoxGap1.setFont("Tahoma", 18F);
		this.textBoxGap1.setLocation(new Point(50+103, 88));
		this.textBoxGap1.setMargin(new CS_Padding(2, 2, 2, 2));
		this.textBoxGap1.setName("textBoxGap1");
		this.textBoxGap1.setSize(new Size(120,34));
		//this.textBoxGap1.TabIndex = 11;
		this.textBoxGap1.addTextChanged(this, "textBoxGap1_TextChanged", EventArgs.Empty());
		// 
		// label7
		// 
		this.label7.setFont("Tahoma", 18F);
		this.label7.setLocation(new Point(366+103, 129+20));
		this.label7.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label7.setName("label7");
		this.label7.setSize(new Size(40, 34));
		//this.label7.TabIndex = 0;
		this.label7.setText("В");
		// 
		// label6
		// 
		this.label6.setFont("Tahoma", 18F);
		this.label6.setLocation(new Point(366+103, 88+20));
		this.label6.setMargin(new CS_Padding(2, 0, 2, 0));
		this.label6.setName("label6");
		this.label6.setSize(new Size(40, 34));
		//this.label6.TabIndex = 1;
		this.label6.setText("Г");

		// 
		// textBoxV
		// 
		this.textBoxV.setFont("Tahoma", 18F);
		this.textBoxV.setLocation(new Point(408+103, 126+20));
		this.textBoxV.setMargin(new CS_Padding(2, 2, 2, 2));
		this.textBoxV.setName("textBoxV");
		this.textBoxV.setSize(new Size(120,34));
		//this.textBoxV.TabIndex = 22;
		this.textBoxV.addTextChanged(this, "textBoxH_TextChanged", EventArgs.Empty());
		// 
		// textBoxH
		// 
		this.textBoxH.setFont("Tahoma", 18F);
		this.textBoxH.setLocation(new Point(408+103, 86+20));
		this.textBoxH.setMargin(new CS_Padding(2, 2, 2, 2));
		this.textBoxH.setName("textBoxH");
		this.textBoxH.setSize(new Size(120,34));
		//this.textBoxH.TabIndex = 21;
		this.textBoxH.addTextChanged(this, "textBoxH_TextChanged", EventArgs.Empty());
		// 
		// GapForm
		// 
		//this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
		//this.AutoScaleMode = CS_AutoScaleMode.Dpi;
		this.setBackColor(CS_Color.Silver);
		//this.setSize(new Size(320, 195));
		//this.ControlBox = false;
        this.Controls().Add(this.pictureBox1);
        this.Controls().Add(this.label7);
		this.Controls().Add(this.label6);
		this.Controls().Add(this.label2);
		this.Controls().Add(this.textBoxV);
		this.Controls().Add(this.textBoxH);
		this.Controls().Add(this.label5);
		this.Controls().Add(this.label4);
		this.Controls().Add(this.label3);
		this.Controls().Add(this.label1);
		this.Controls().Add(this.textBoxGap3);
		this.Controls().Add(this.textBoxGap2);
		this.Controls().Add(this.textBoxGap1);
		this.Controls().Add(this.labelCD);
		this.Controls().Add(this.labNumRast);

		//this.setFont("Tahoma", 8F);
		//this.setLocation(new Point(0, 0));
		//this.setMargin(new CS_Padding(2, 2, 2, 2));
		//this.Menu = this.mainMenu1;
		//this.MinimizeBox = false;
		this.setName("GapForm");
		this.setText("Зазоры и смещения");
		this.addKeyDown(this, "GapForm_KeyDown", EventArgs.Empty());
		//((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
		this.ResumeLayout();
		this.PerformLayout();

	}


   // private Microsoft.WindowsCE.Forms.HardwareButton hardwareButton1;
   // private Microsoft.WindowsCE.Forms.HardwareButton hardwareButton4;
   // private Microsoft.WindowsCE.Forms.HardwareButton hardwareButton2;
   // private Microsoft.WindowsCE.Forms.HardwareButton hardwareButton3;

    public void Destroy()
    {


        if (mCloseItem != null)
        {
            mCloseItem.Dispose();
        }

        if (mCalcHVItem != null)
        {
            mCalcHVItem.Dispose();
        }

        if (mainMenu1 != null)
        {
            mainMenu1.Dispose();
        }

        if (pictureBox1 != null)
        {
            pictureBox1.Dispose();
        }

        if (labelCD != null)
        {
            labelCD.Dispose();
        }

        if (labNumRast != null)
        {
            labNumRast.Dispose();
        }

        if (label5 != null)
        {
            label5.Dispose();
        }

        if (label4 != null)
        {
            label4.Dispose();
        }

        if (label3 != null)
        {
            label3.Dispose();
        }

        if (label1 != null)
        {
            label1.Dispose();
        }

        if (textBoxGap3 != null)
        {
            textBoxGap3.Dispose();
        }

        if (textBoxGap2 != null)
        {
            textBoxGap2.Dispose();
        }

        if (textBoxGap1 != null)
        {
            textBoxGap1.Dispose();
        }

        if (label7 != null)
        {
            label7.Dispose();
        }

        if (label6 != null)
        {
            label6.Dispose();
        }

        if (label2 != null)
        {
            label2.Dispose();
        }

        if (textBoxV != null)
        {
            textBoxV.Dispose();
        }

        if (textBoxH != null)
        {
            textBoxH.Dispose();
        }

        //
        rRast = null;
        rRastBack = null;
        sMode = null;
        Execute_rr = null;
        Execute_differParam = null;
        Execute_result = null;
        ownerForm = null;
        handler = null;
        //

        mCloseItem  = null;
        mCalcHVItem  = null;
        mainMenu1  = null;
        pictureBox1  = null;
        labelCD  = null;
        labNumRast  = null;
        label5  = null;
        label4  = null;
        label3  = null;
        label1  = null;
        textBoxGap3  = null;
        textBoxGap2  = null;
        textBoxGap1  = null;
        label7  = null;
        label6  = null;
        label2  = null;
        textBoxV  = null;
        textBoxH  = null;
    }
}