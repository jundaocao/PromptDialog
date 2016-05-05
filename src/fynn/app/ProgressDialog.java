package fynn.app;

import fynn.app.R;
import fynn.util.Util;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ProgressDialog extends Dialog {

	public static final int VIEW_STYLE_PROGRESS_SMALL = 0x00000001;
	public static final int VIEW_STYLE_PROGRESS_NORMAL = 0x00000002;

	protected ProgressDialog(Context context) {
		super(context, R.style.PromptDialogStyle);
	}

	@SuppressLint({ "NewApi", "InflateParams" })
	public static class Builder {

		private ProgressDialog dialog;
		private Context context;

		private int padding;
		private int margin;
		private int alpha;

		private CharSequence message;
		private int messageColor;
		private float messageSize;
		private boolean messageBold;
		private ColorStateList messageColorStateList;

		private boolean cancelable;
		private boolean canceledOnTouchOutside;

		private int viewStyle;

		public Builder(Context context) {
			dialog = new ProgressDialog(context);
			this.context = context;
			initData();
		}

		private void initData() {
			this.alpha = 240;
			this.padding = Util.dip2px(context, 8);
			this.margin = Util.dip2px(context, 4);
			this.messageColor = Color.parseColor("#696969");
			this.messageSize = 15;
			this.messageBold = false;
			cancelable = false;
			canceledOnTouchOutside = false;
		}

		public Context getContext() {
			return context;
		}

		public Builder setPadding(int padding) {
			this.padding = Util.dip2px(context, padding);
			return this;
		}

		public Builder setMessageMargin(int margin) {
			this.margin = Util.dip2px(context, margin);
			return this;
		}

		public Builder setAlpha(int alpha) {
			this.alpha = alpha;
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

		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder setCanceledOnTouchOutside(boolean canceled) {
			this.canceledOnTouchOutside = canceled;
			return this;
		}

		public Builder setViewStyle(int style) {
			this.viewStyle = style;
			return this;
		}

		public Builder setMessageBold(boolean bold) {
			this.messageBold = bold;
			return this;
		}

		@SuppressLint("InflateParams")
		public ProgressDialog create() {
			if (dialog == null) {
				return null;
			}
			View mView = null;
			LinearLayout progressdialog = null;
			TextView mMessage = null;
			switch (viewStyle) {
			case VIEW_STYLE_PROGRESS_SMALL:
			default:
				mView = LayoutInflater.from(context).inflate(
						R.layout.fynn_prompt_dialog_progress_small, null);
				break;
			case VIEW_STYLE_PROGRESS_NORMAL:
				mView = LayoutInflater.from(context).inflate(
						R.layout.fynn_prompt_dialog_progress_normal, null);
				break;
			}

			progressdialog = (LinearLayout) mView.findViewById(R.id.dialog);
			mMessage = (TextView) mView.findViewById(R.id.message);

			progressdialog.setPadding(padding, padding, padding, padding);
			progressdialog.getBackground().setAlpha(alpha);

			if (message != null) {
				mMessage.setVisibility(View.VISIBLE);
				mMessage.setText(message);
				mMessage.setTextSize(messageSize);
				mMessage.setTextColor(messageColor);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				switch (viewStyle) {
				case VIEW_STYLE_PROGRESS_SMALL:
				default:
					params.setMargins(margin, 0, 0, 0);
					break;
				case VIEW_STYLE_PROGRESS_NORMAL:
					params.setMargins(0, margin, 0, 0);
					break;
				}
				mMessage.setLayoutParams(params);
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

			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

			dialog.setContentView(mView);
			return dialog;
		}

		public ProgressDialog show() {
			create().show();
			return dialog;
		}
	}
}
