package fynn.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fynn.util.Util;

@SuppressLint("InflateParams")
public class PromptDialog extends Dialog {

	private Context context;
	private int margin;

	public static final int VIEW_STYLE_NORMAL = 0x00000001;
	public static final int VIEW_STYLE_TITLEBAR = 0x00000002;
	public static final int VIEW_STYLE_TITLEBAR_COLOR = 0x00000003;

	public static final int BUTTON_1 = 0x00000001;
	public static final int BUTTON_2 = 0x00000002;
	public static final int BUTTON_3 = 0x00000003;

	protected PromptDialog(Context context, int margin) {
		super(context, R.style.PromptDialogStyle);
		this.context = context;
		this.margin = margin;
	}

	protected PromptDialog(Context context) {
		this(context, R.style.PromptDialogStyle);
		this.margin = 30;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = this.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		int marginBorder = Util.dip2px(context, margin);
		params.width = Util.getScreenWidth(context) - marginBorder * 2;
		window.setAttributes(params);
	}

	@SuppressLint({ "NewApi", "InflateParams" })
	public static class Builder {

		private PromptDialog dialog;
		private Context context;

		private CharSequence title;
		private CharSequence message;
		private CharSequence button1Text;
		private CharSequence button2Text;
		private CharSequence button3Text;
		private int button1TextColor;
		private int button2TextColor;
		private int button3TextColor;
		private int titleColor;
		private int messageColor;
		private float button1Size;
		private float button2Size;
		private float button3Size;
		private float titleSize;
		private float messageSize;
		private boolean titleBold;
		private boolean messageBold;
		private ColorStateList titleColorStateList;
		private ColorStateList messageColorStateList;
		private ColorStateList button1ColorStateList;
		private ColorStateList button2ColorStateList;
		private ColorStateList button3ColorStateList;
		private int titlebarGravity;
		private int messageGravity;

		private Drawable icon;
		private boolean cancelable;
		private boolean canceledOnTouchOutside;
		private View view;
		private int viewStyle;

		private PromptDialog.OnClickListener button1Listener;
		private PromptDialog.OnClickListener button2Listener;
		private PromptDialog.OnClickListener button3Listener;

		private int button1Flag;
		private int button2Flag;
		private int button3Flag;

		private int titleBarColor;

		private ListAdapter listAdapter;
		private Bitmap bitmap;

		private PromptDialog.OnItemClickListener onItemClickListener;

		public Builder(Context context, int margin) {
			dialog = new PromptDialog(context, margin);
			this.context = context;
			initData();
		}

		public Builder(Context context) {
			dialog = new PromptDialog(context);
			this.context = context;
			initData();
		}

		private void initData() {
			this.button1TextColor = Color.parseColor("#808080");
			this.button2TextColor = Color.parseColor("#808080");
			this.button3TextColor = Color.parseColor("#808080");
			this.messageColor = Color.parseColor("#696969");
			this.titleColor = Color.parseColor("#696969");
			this.titleBarColor = Color.parseColor("#4cc05f");

			this.button1Size = 15;
			this.button2Size = 15;
			this.button3Size = 15;
			this.messageSize = 16;
			this.titleSize = 18;

			this.titlebarGravity = Gravity.CENTER;
			this.messageGravity = Gravity.LEFT;

			this.titleBold = false;
			this.messageBold = false;

			cancelable = true;
			canceledOnTouchOutside = false;
		}

		public Context getContext() {
			return context;
		}

		public Builder setTitleBarGravity(int titlebarGravity) {
			this.titlebarGravity = titlebarGravity;
			return this;
		}

		public Builder setMessageGravity(int messageGravity) {
			this.messageGravity = messageGravity;
			return this;
		}

		public Builder setTitle(CharSequence title) {
			this.title = title;
			return this;
		}

		public Builder setTitle(int titleResId) {
			this.title = context.getResources().getString(titleResId);
			return this;
		}

		public Builder setBitmap(Bitmap bitmap) {

			this.bitmap = bitmap;
			return this;
		}

		public Builder setTitleColor(int titleColor) {
			this.titleColor = titleColor;
			return this;
		}

		public Builder setTitleColor(ColorStateList titleColor) {
			this.titleColorStateList = titleColor;
			return this;
		}

		public Builder setTitleSize(float titleSize) {
			this.titleSize = titleSize;
			return this;
		}

		public Builder setIcon(Drawable icon) {
			this.icon = icon;
			return this;
		}

		public Builder setIcon(int iconResId) {
			this.icon = context.getResources().getDrawable(iconResId);
			return this;
		}

		public Builder setMessage(CharSequence message) {
			this.message = message;
			return this;
		}

		public Builder setMessage(int messageResId) {
			this.message = context.getResources().getString(messageResId);
			return this;
		}

		public Builder setMessageColor(int color) {
			this.messageColor = color;
			return this;
		}

		public Builder setMessageColor(ColorStateList color) {
			this.messageColorStateList = color;
			return this;
		}

		public Builder setMessageSize(float size) {
			this.messageSize = size;
			return this;
		}

		public Builder setButton1(CharSequence text,
				PromptDialog.OnClickListener listener) {
			this.button1Text = text;
			this.button1Listener = listener;
			button1Flag = 1;
			return this;
		}

		public Builder setButton1(int textId,
				PromptDialog.OnClickListener listener) {
			this.button1Text = context.getResources().getString(textId);
			this.button1Listener = listener;
			button1Flag = 1;
			return this;
		}

		public Builder setButton1TextColor(int color) {
			this.button1TextColor = color;
			return this;
		}

		public Builder setButton1TextColor(ColorStateList color) {
			this.button1ColorStateList = color;
			return this;
		}

		public Builder setButton1Size(float button1Size) {
			this.button1Size = button1Size;
			return this;
		}

		public Builder setButton2(CharSequence text,
				PromptDialog.OnClickListener listener) {
			this.button2Text = text;
			this.button2Listener = listener;
			button2Flag = 2;
			return this;
		}

		public Builder setButton2(int textId,
				PromptDialog.OnClickListener listener) {
			this.button2Text = context.getResources().getString(textId);
			this.button2Listener = listener;
			button2Flag = 2;
			return this;
		}

		public Builder setButton2TextColor(int color) {
			this.button2TextColor = color;
			return this;
		}

		public Builder setButton2TextColor(ColorStateList color) {
			this.button2ColorStateList = color;
			return this;
		}

		public Builder setButton2Size(float button2Size) {
			this.button2Size = button2Size;
			return this;
		}

		public Builder setButton3(CharSequence text,
				PromptDialog.OnClickListener listener) {
			this.button3Text = text;
			this.button3Listener = listener;
			button3Flag = 4;
			return this;
		}

		public Builder setButton3(int textId,
				PromptDialog.OnClickListener listener) {
			this.button3Text = context.getResources().getString(textId);
			this.button3Listener = listener;
			button3Flag = 4;
			return this;
		}

		public Builder setButton3TextColor(int color) {
			this.button3TextColor = color;
			return this;
		}

		public Builder setButton3TextColor(ColorStateList color) {
			this.button3ColorStateList = color;
			return this;
		}

		public Builder setButton3Size(float button3Size) {
			this.button3Size = button3Size;
			return this;
		}

		public Builder setAdapter(ListAdapter listAdapter,
				PromptDialog.OnItemClickListener onItemClickListener) {
			this.listAdapter = listAdapter;
			this.onItemClickListener = onItemClickListener;
			return this;
		}

		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder setCanceledOnTouchOutside(boolean canceled) {
			this.canceledOnTouchOutside = canceled;
			return this;
		}

		public Builder setView(View view) {
			this.view = view;
			return this;
		}

		public Builder setViewStyle(int style) {
			this.viewStyle = style;
			return this;
		}

		public Builder setTitleBold(boolean bold) {
			this.titleBold = bold;
			return this;
		}

		public Builder setMessageBold(boolean bold) {
			this.messageBold = bold;
			return this;
		}

		public Builder setTitleBarColor(int color) {
			this.titleBarColor = color;
			return this;
		}

		@SuppressLint("InflateParams")
		public PromptDialog create() {
			if (dialog == null) {
				return null;
			}
			View mView = null;
			LinearLayout mTitleBar = null;
			TextView mTitle = null;
			TextView mMessage = null;
			TextView btnLeft = null;
			TextView btnCenter = null;
			TextView btnRight = null;
			LinearLayout addView = null;
			LinearLayout btnView = null;
			View btnDivider1 = null;
			View btnDivider2 = null;
			View msgBtnDivider = null;
			ListView listView = null;
			ImageView imageView = null;
			switch (viewStyle) {
			case VIEW_STYLE_NORMAL:
			default:
				mView = LayoutInflater.from(context).inflate(
						R.layout.fynn_prompt_dialog_normal, null);
				break;
			case VIEW_STYLE_TITLEBAR:
				mView = LayoutInflater.from(context).inflate(
						R.layout.fynn_prompt_dialog_normal, null);
				mView.findViewById(R.id.title_msg_divider).setVisibility(
						View.VISIBLE);
				break;
			case VIEW_STYLE_TITLEBAR_COLOR:
				mView = LayoutInflater.from(context).inflate(
						R.layout.fynn_prompt_dialog_titlebar_color, null);
				titleColor = Color.WHITE;
				break;
			}

			mTitleBar = (LinearLayout) mView.findViewById(R.id.titlebar);
			mTitle = (TextView) mView.findViewById(R.id.title);
			mMessage = (TextView) mView.findViewById(R.id.message);
			addView = (LinearLayout) mView.findViewById(R.id.layout_addview);
			btnLeft = (TextView) mView.findViewById(R.id.button_left);
			btnCenter = (TextView) mView.findViewById(R.id.button_center);
			btnRight = (TextView) mView.findViewById(R.id.button_right);
			btnDivider1 = (View) mView.findViewById(R.id.btn_divider1);
			btnDivider2 = (View) mView.findViewById(R.id.btn_divider2);
			msgBtnDivider = (View) mView.findViewById(R.id.msg_btn_divider);
			btnView = (LinearLayout) mView.findViewById(R.id.btn_view);
			listView = (ListView) mView.findViewById(R.id.listview);
			imageView = (ImageView) mView.findViewById(R.id.imageview);
			if (viewStyle == VIEW_STYLE_TITLEBAR_COLOR) {
				GradientDrawable myGrad = (GradientDrawable) mTitleBar
						.getBackground();
				myGrad.setColor(titleBarColor);
			}

			if (listAdapter != null) {
				listView.setVisibility(View.VISIBLE);
				listView.setAdapter(listAdapter);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						onItemClickListener.onClick(dialog, arg2);
					}
				});
				if (message == null && viewStyle == VIEW_STYLE_NORMAL) {
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(0, Util.dip2px(context, 10), 0, 0);
					listView.setLayoutParams(params);
				}
			}
			if (bitmap != null) {
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageBitmap(bitmap);
			}
			if ((title != null) || (icon != null)) {
				mTitle.setVisibility(View.VISIBLE);
				mTitle.setText(title);
				mTitle.setTextSize(titleSize);
				mTitle.setTextColor(titleColor);
				if (titleColorStateList != null) {
					mTitle.setTextColor(titleColorStateList);
				}
				if (titleBold) {
					TextPaint textPaint = mTitle.getPaint();
					textPaint.setFakeBoldText(true);
				}
				mTitle.setCompoundDrawables(icon, null, null, null);
				mTitleBar.setGravity(titlebarGravity);
			} else {
				mTitleBar.setVisibility(View.GONE);
			}

			if (message != null) {
				mMessage.setVisibility(View.VISIBLE);
				mMessage.setText(message);
				mMessage.setTextSize(messageSize);
				mMessage.setTextColor(messageColor);
				mMessage.setGravity(messageGravity);
				if (messageColorStateList != null) {
					mMessage.setTextColor(messageColorStateList);
				}
				if (messageBold) {
					TextPaint textPaint = mMessage.getPaint();
					textPaint.setFakeBoldText(true);
				}
			} else {
				mMessage.setVisibility(View.GONE);
			}

			if (view != null) {
				addView.removeAllViews();
				addView.addView(view);
				addView.setGravity(Gravity.CENTER);
			}

			int btnCountFlag = button1Flag + button2Flag + button3Flag;
			switch (btnCountFlag) {
			case 1:
			case 5:
				btnCenter.setVisibility(View.VISIBLE);
				btnLeft.setVisibility(View.GONE);
				btnRight.setVisibility(View.GONE);
				btnCenter
						.setBackgroundResource(R.drawable.fynn_prompt_dialog_btn_single_selector);
				if (button1Text != null) {
					btnCenter.setText(button1Text);
					btnCenter.setTextSize(button1Size);
					btnCenter.setTextColor(button1TextColor);
					if (button1ColorStateList != null) {
						btnCenter.setTextColor(button1ColorStateList);
					}
					if (button1Listener != null) {
						btnCenter
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View arg0) {
										button1Listener.onClick(dialog,
												BUTTON_1);
									}
								});
					}
				}
				break;

			case 3:
				btnLeft.setVisibility(View.VISIBLE);
				btnRight.setVisibility(View.VISIBLE);
				btnCenter.setVisibility(View.GONE);
				btnDivider1.setVisibility(View.VISIBLE);
				btnDivider2.setVisibility(View.GONE);

				if (button1Text != null) {
					btnLeft.setText(button1Text);
					btnLeft.setTextSize(button1Size);
					btnLeft.setTextColor(button1TextColor);

					if (button1ColorStateList != null) {
						btnLeft.setTextColor(button1ColorStateList);
					}

					if (button1Listener != null) {
						btnLeft.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								button1Listener.onClick(dialog, BUTTON_1);
							}
						});
					}
				}

				if (button2Text != null) {
					btnRight.setText(button2Text);
					btnRight.setTextSize(button2Size);
					btnRight.setTextColor(button2TextColor);

					if (button2ColorStateList != null) {
						btnRight.setTextColor(button2ColorStateList);
					}

					if (button2Listener != null) {
						btnRight.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								button2Listener.onClick(dialog, BUTTON_2);
							}
						});
					}
				}
				break;
			case 7:
				btnLeft.setVisibility(View.VISIBLE);
				btnCenter.setVisibility(View.VISIBLE);
				btnRight.setVisibility(View.VISIBLE);
				btnDivider1.setVisibility(View.VISIBLE);
				btnDivider2.setVisibility(View.VISIBLE);

				if (button1Text != null) {
					btnLeft.setText(button1Text);
					btnLeft.setTextSize(button1Size);
					btnLeft.setTextColor(button1TextColor);

					if (button1ColorStateList != null) {
						btnLeft.setTextColor(button1ColorStateList);
					}

					if (button1Listener != null) {
						btnLeft.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								button1Listener.onClick(dialog, BUTTON_1);
							}
						});
					}
				}
				if (button2Text != null) {
					btnCenter.setText(button2Text);
					btnCenter.setText(button2Text);
					btnCenter.setTextSize(button2Size);
					btnCenter.setTextColor(button2TextColor);

					if (button2ColorStateList != null) {
						btnCenter.setTextColor(button2ColorStateList);
					}

					if (button2Listener != null) {
						btnCenter
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View arg0) {
										button2Listener.onClick(dialog,
												BUTTON_2);
									}
								});
					}
				}

				if (button3Text != null) {
					btnRight.setText(button3Text);
					btnRight.setTextSize(button3Size);
					btnRight.setTextColor(button3TextColor);

					if (button3ColorStateList != null) {
						btnRight.setTextColor(button3ColorStateList);
					}

					if (button3Listener != null) {
						btnRight.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								button3Listener.onClick(dialog, BUTTON_3);
							}
						});
					}
				}
				break;

			default:
				btnView.setVisibility(View.GONE);
				msgBtnDivider.setVisibility(View.GONE);
				break;
			}

			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

			dialog.setContentView(mView);
			return dialog;
		}

		public PromptDialog show() {
			create().show();
			return dialog;
		}
	}

	public interface OnClickListener {
		void onClick(Dialog dialog, int which);
	}

	public interface OnItemClickListener {
		void onClick(Dialog dialog, int which);
	}
}
