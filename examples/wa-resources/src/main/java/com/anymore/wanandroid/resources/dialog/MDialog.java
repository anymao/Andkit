package com.anymore.wanandroid.resources.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.anymore.wanandroid.resources.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ygj on 2018/7/31.
 * 对话框
 */
public class MDialog {
    private boolean mCancel;
    private boolean mCancelable = true;
    private Context mContext;
    private AlertDialog mAlertDialog;
    private Builder mBuilder;
    private int mTitleResId;
    private CharSequence mTitle;
    private int mMessageResId;
    private CharSequence mMessage;
    private TextView mPositiveButton;
    private boolean positiveButtonExist;
    private TextView mNegativeButton;
    private boolean negativeButtonExist;
    private TextView mNeutralButton;
    private boolean neutralButtonIsShow;
    private View viewSpace;
    private LinearLayout mainView;
    private View mDialogView;
    private int messageGravity = Gravity.CENTER;
    private int messageTextAlignment = View.TEXT_ALIGNMENT_TEXT_START;
    private int gravity = Gravity.CENTER;
    private boolean mHasShow = false;
    private int width;
    private int height;
    private @DrawableRes
    int bgResId;
    private DialogInterface.OnDismissListener mOnDismissListener;

    private DialogInterface.OnKeyListener onKeyListener;

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    private String mPositiveButtonTitle;
    private View.OnClickListener mPositiveButtonClick;

    private String mNegativeButtonTitle;
    private View.OnClickListener mNegativeButtonClick;

    private String mNeutralButtonTitle;
    private View.OnClickListener mNeutralButtonClick;
    private DialogInterface.OnShowListener onShowListener;

    public MDialog(Context context) {
        this.mContext = context;
        this.width = (int) (mContext.getResources().getDisplayMetrics().density * 300 + 0.5f);
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.bgResId = R.drawable.wa_dialog_bg_shape;
    }

    /**
     * 设置对话框显示监听
     *
     * @param onShowListener 对话框显示监听
     */
    public void setOnShowListener(DialogInterface.OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    /**
     * 对话框是否显示
     *
     * @return 是否显示
     */
    public boolean isShowing() {
        return mAlertDialog != null && mAlertDialog.isShowing();
    }

    /**
     * 显示对话框
     */
    public void show() {
        if (mHasShow) {
            mAlertDialog.show();
        } else {
            mBuilder = new Builder();
        }
        mHasShow = true;
    }

    /**
     * 设置对话框局部View(该方法可设置出了底部按钮布局以上的View)
     *
     * @param view 要设置显示的view
     * @return 当前对话框
     */
    public MDialog setView(View view) {
        mDialogView = view;
        if (mBuilder != null) {
            mBuilder.setView(view);
        }
        return this;
    }

    /**
     * 取消提示框
     */
    public void dismiss() {
        mAlertDialog.dismiss();
    }

    /**
     * 设置标题
     *
     * @param resId 标题资源id
     * @return 当前对话框
     */
    public MDialog setTitle(int resId) {
        mTitleResId = resId;
        if (mBuilder != null) {
            mBuilder.setTitle(resId);
        }
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题字符串
     * @return 当前对话框
     */
    public MDialog setTitle(CharSequence title) {
        mTitle = title;
        if (mBuilder != null) {
            mBuilder.setTitle(title);
        }
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param resId 提示信息资源id
     * @return 当前对话框
     */
    public MDialog setMessage(int resId) {
        mMessageResId = resId;
        if (mBuilder != null) {
            mBuilder.setMessage(resId);
        }
        return this;
    }

    /**
     * 设置提示信息
     *
     * @param message 提示信息字符串
     * @return 当前对话框
     */
    public MDialog setMessage(CharSequence message) {
        mMessage = message;
        if (mBuilder != null) {
            mBuilder.setMessage(message);
        }
        return this;
    }

    public MDialog setMessageGravity(@GravityType int gravity) {
        this.messageGravity = gravity;
        if (mBuilder != null) {
            mBuilder.setMessageGravity(gravity);
        }
        return this;
    }

    public MDialog setMessageTextAlignment(@TextAlignment int textAlignment) {
        this.messageTextAlignment = textAlignment;
        if (mBuilder != null) {
            mBuilder.setMessageTextAlignment(textAlignment);
        }
        return this;
    }

    /**
     * 设置对话框展示位置
     */
    public MDialog setGravity(@GravityType int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 中间按钮
     *
     * @param resId    显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */
    public MDialog setNeutralButton(@StringRes int resId, final View.OnClickListener listener) {
        return setNeutralButton(mContext.getString(resId), listener);
    }

    /**
     * 中间按钮
     *
     * @param text     显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */
    public MDialog setNeutralButton(String text, final View.OnClickListener listener) {
        neutralButtonIsShow = true;
        mNeutralButtonTitle = text;
        mNeutralButtonClick = listener;
        if (mBuilder != null) {
            mBuilder.setNeutralButton(text, listener);
        }
        return this;
    }

    /**
     * 右边按钮
     *
     * @param resId    显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */

    public MDialog setPositiveButton(@StringRes int resId, final View.OnClickListener listener) {
        return setPositiveButton(mContext.getString(resId), listener);
    }

    /**
     * 右边按钮
     *
     * @param text     显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */
    public MDialog setPositiveButton(String text, final View.OnClickListener listener) {
        positiveButtonExist = true;
        mPositiveButtonTitle = text;
        mPositiveButtonClick = listener;
        if (mBuilder != null) {
            mBuilder.setPositiveButton(text, listener);
        }
        return this;
    }

    /**
     * 左边按钮
     *
     * @param resId    显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */
    public MDialog setNegativeButton(@StringRes int resId, final View.OnClickListener listener) {
        return setNegativeButton(mContext.getString(resId), listener);
    }

    /**
     * 左边按钮
     *
     * @param text     显示文字
     * @param listener 点击监听事件
     * @return 当前对话框
     */
    public MDialog setNegativeButton(String text, final View.OnClickListener listener) {
        negativeButtonExist = true;
        mNegativeButtonTitle = text;
        mNegativeButtonClick = listener;
        if (mBuilder != null) {
            mBuilder.setNegativeButton(text, listener);
        }
        return this;
    }

    /**
     * 设置是否外部点击可取消
     *
     * @param cancel 是否可取消
     * @return 当前对话框
     */
    public MDialog setCanceledOnTouchOutside(boolean cancel) {
        this.mCancel = cancel;
        if (mBuilder != null) {
            mBuilder.setCanceledOnTouchOutside(mCancel);
        }
        return this;
    }

    /**
     * 设置返回键是否可取消
     *
     * @param cancelable 是否可取消
     * @return 当前对话框
     */
    public MDialog setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        if (mBuilder != null) {
            mBuilder.setCancelable(mCancelable);
        }
        return this;
    }

    /**
     * 设置对话框消失监听事件
     *
     * @param onDismissListener 对话框消息监听事件
     * @return 当前对话框
     */
    public MDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    /**
     * 设置对话框宽高
     *
     * @param width  宽度
     * @param height 高度
     */
    public void setLayout(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 设置对话框背景
     *
     * @param bgResId 背景
     */
    public void setBackgroundResource(int bgResId) {
        this.bgResId = bgResId;
    }

    private class Builder {

        private TextView mTitleView;
        private TextView mMessageView;
        private View contentView;

        private Builder() {
            mAlertDialog = new AlertDialog.Builder(mContext, R.style.wa_dialog_transparent).create();
            mAlertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            contentView = LayoutInflater.from(mContext).inflate(R.layout.wa_mdialog, null);
            mAlertDialog.setView(contentView);
            LinearLayout parent = contentView.findViewById(R.id.ll_parent);
            parent.setBackgroundResource(bgResId);
            mTitleView = contentView.findViewById(R.id.mDialog_title);
            mMessageView = contentView.findViewById(R.id.mDialog_message);
            mPositiveButton = contentView.findViewById(R.id.mDialog_positive);
            mNegativeButton = contentView.findViewById(R.id.mDialog_negative);
            mNeutralButton = contentView.findViewById(R.id.mDialog_neutral);
            viewSpace = contentView.findViewById(R.id.view_space);
            LinearLayout buttonLayout = contentView.findViewById(R.id.mDialog_btn_layout);
            mainView = contentView.findViewById(R.id.mDialog_view);
//            mainView.setBackgroundResource(bgResId);

            if (mTitleResId != 0) {
                setTitle(mTitleResId);
                mTitleView.setVisibility(View.VISIBLE);
            }
            if (mTitle != null) {
                setTitle(mTitle);
                mTitleView.setVisibility(View.VISIBLE);
            }
            if (mMessageResId != 0) {
                setMessage(mMessageResId);
                mMessageView.setVisibility(View.VISIBLE);
            }
            if (mMessage != null) {
                setMessage(mMessage);
                mMessageView.setVisibility(View.VISIBLE);
            }
            setMessageGravity(messageGravity);
            setMessageTextAlignment(messageTextAlignment);
            if (positiveButtonExist) {
                setPositiveButton(mPositiveButtonTitle, mPositiveButtonClick);
                mPositiveButton.setVisibility(View.VISIBLE);
            }
            if (negativeButtonExist) {
                setNegativeButton(mNegativeButtonTitle, mNegativeButtonClick);
                mNegativeButton.setVisibility(View.VISIBLE);
            }
            if (positiveButtonExist && negativeButtonExist) {
                viewSpace.setVisibility(View.VISIBLE);
            }
            if (neutralButtonIsShow) {
                setNeutralButton(mNeutralButtonTitle, mNeutralButtonClick);
                mNeutralButton.setVisibility(View.VISIBLE);
                mNeutralButton.setBackgroundResource(R.drawable.wa_mdialog_neutral_btn_bg_selector);
            }
            if (positiveButtonExist && !negativeButtonExist) {
                mPositiveButton.setBackgroundResource(R.drawable.wa_mdialog_one_btn_bg_selector);
            }
            if (negativeButtonExist && !positiveButtonExist) {
                mNegativeButton.setBackgroundResource(R.drawable.wa_mdialog_one_btn_bg_selector);
            }
            if (neutralButtonIsShow && !positiveButtonExist && !negativeButtonExist) {
                mNeutralButton.setBackgroundResource(R.drawable.wa_mdialog_one_btn_bg_selector);
            }

            if (negativeButtonExist || positiveButtonExist || neutralButtonIsShow) {
                buttonLayout.setVisibility(View.VISIBLE);
            }
            if (mDialogView != null) {
                this.setView(mDialogView);
            }
            mAlertDialog.setCanceledOnTouchOutside(mCancel);
            if (mOnDismissListener != null) {
                mAlertDialog.setOnDismissListener(mOnDismissListener);
            }
            if (onKeyListener != null) {
                mAlertDialog.setOnKeyListener(onKeyListener);
            }
            if (onShowListener != null) {
                mAlertDialog.setOnShowListener(onShowListener);
            }
            mAlertDialog.setCancelable(mCancelable);
            mAlertDialog.show();

            Window window = mAlertDialog.getWindow();
            WindowManager.LayoutParams layoutParams;
            window.setWindowAnimations(R.style.wa_dialog_anim);
            window.setGravity(gravity);
            layoutParams = window.getAttributes();
            layoutParams.width = width;
            layoutParams.height = height;
            layoutParams.dimAmount = 0.65f;
            int dimensionPixelSize = mContext.getResources().getDimensionPixelSize(R.dimen.wa_mdialog_padding);
            layoutParams.y = layoutParams.y + dimensionPixelSize;
            window.setAttributes(layoutParams);
        }

        private void setTitle(int resId) {
            mTitleView.setText(resId);
        }

        private void setTitle(CharSequence title) {
            mTitleView.setText(title);
        }

        private void setMessage(int resId) {
            mMessageView.setText(resId);
        }

        private void setMessage(CharSequence message) {
            mMessageView.setText(message);
        }

        private void setMessageGravity(@GravityType int gravity) {
            mMessageView.setGravity(gravity);
        }

        private void setMessageTextAlignment(@TextAlignment int textAlignment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mMessageView.setTextAlignment(textAlignment);
            }
        }

        private void setPositiveButton(String text, final View.OnClickListener listener) {
            mPositiveButton.setText(text);
            mPositiveButton.setOnClickListener(listener);
        }

        private void setNegativeButton(String text, final View.OnClickListener listener) {
            mNegativeButton.setText(text);
            mNegativeButton.setOnClickListener(listener);
        }

        private void setNeutralButton(String text, final View.OnClickListener listener) {
            mNeutralButton.setText(text);
            mNeutralButton.setOnClickListener(listener);
        }

        private void setView(View view) {
            mainView.removeAllViews();
            mainView.addView(view);
        }

        private void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }

        private void setCancelable(boolean cancelable) {
            mAlertDialog.setCancelable(cancelable);
        }
    }

    /**
     * 排版类型 定义
     */
    @IntDef({Gravity.END, Gravity.START, Gravity.CENTER, Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityType {

    }

    /**
     * 排版类型 定义
     */
    @IntDef({View.TEXT_ALIGNMENT_TEXT_END, View.TEXT_ALIGNMENT_TEXT_START, View.TEXT_ALIGNMENT_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TextAlignment {

    }
}