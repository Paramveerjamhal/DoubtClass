package in.app.doubtclass.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import in.app.doubtclass.R;

public class CustomTypefaceEditText extends EditText
{

	private static final String TAG = "TextView";
	private Typeface mTypeface;

	public CustomTypefaceEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	public CustomTypefaceEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomTypefaceEditText(Context context)
	{
		super(context);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs)
	{
		TypedArray fontTypedArray = ctx.obtainStyledAttributes(attrs, R.styleable.customTextView);
		String textStyle = fontTypedArray.getString(R.styleable.customTextView_font_type_typeface);
		try
		{
			if (textStyle == null)
			{
				mTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/FS Me-Bold.otf");
			}
			else
			{
				mTypeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + textStyle + ".otf");
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "Could not get typeface: " + textStyle + "&&" + e.getMessage() + " " + this.getId());
		}
		setTypeface(mTypeface, fontTypedArray.getInt(R.styleable.customTextView_android_textStyle, 0));
		fontTypedArray.recycle();
	}
}