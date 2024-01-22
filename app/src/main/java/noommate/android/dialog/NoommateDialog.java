package noommate.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;


import noommate.android.R;

public class NoommateDialog {
  private Context context;
  private Dialog dialog;

  AppCompatTextView mDialogMessageTextView;
//  RoundRectView mDialogOkWrapView;
//  RoundRectView mDialogCancelWrapView;
  AppCompatButton mDialogOkButton;
  AppCompatButton mDialogCancelButton;

//  RoundRectView mDialogConfirmWrapView;
  AppCompatButton mDialogConfirmButton;

  RelativeLayout mOneButtonLayout;
  LinearLayout mTwoButtonLayout;



  public NoommateDialog(Context context) {
    this.context = context;
    dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setContentView(R.layout.dialog_noommate);
    dialog.setCancelable(false);

    mDialogMessageTextView = (AppCompatTextView) dialog.findViewById(R.id.dialog_message_text_view);
//    mDialogOkWrapView = (RoundRectView) dialog.findViewById(R.id.dialog_ok_wrap_view);
    mDialogOkButton = (AppCompatButton) dialog.findViewById(R.id.dialog_ok_button);

//    mDialogCancelWrapView = (RoundRectView) dialog.findViewById(R.id.dialog_cancel_wrap_view);
    mDialogCancelButton = (AppCompatButton) dialog.findViewById(R.id.dialog_cancel_button);

//    mDialogConfirmWrapView = (RoundRectView) dialog.findViewById(R.id.dialog_confirm_wrap_view);
    mDialogConfirmButton = (AppCompatButton) dialog.findViewById(R.id.dialog_confirm_button);

    mOneButtonLayout = (RelativeLayout) dialog.findViewById(R.id.one_button_layout);
    mTwoButtonLayout = (LinearLayout) dialog.findViewById(R.id.two_button_layout);

  }

  public void setDialog(String message, String okTitle) {
    mDialogMessageTextView.setText(message);
    mOneButtonLayout.setVisibility(View.VISIBLE);
    mTwoButtonLayout.setVisibility(View.GONE);
    mDialogConfirmButton.setText(okTitle);
    dialog.show();
  }

  public void setDialog(String message, String okTitle, String cancelTitle) {
    mDialogMessageTextView.setText(message);
    mOneButtonLayout.setVisibility(View.GONE);
    mTwoButtonLayout.setVisibility(View.VISIBLE);
    mDialogConfirmButton.setText(okTitle);
    mDialogOkButton.setText(okTitle);
    mDialogCancelButton.setText(cancelTitle);
    dialog.show();
  }

  public void addOKButton(View.OnClickListener clickListener) {
    mDialogOkButton.setOnClickListener(clickListener);
    mDialogConfirmButton.setOnClickListener(clickListener);
  }

  public void addCancelButton(View.OnClickListener clickListener) {
    mDialogCancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }

  public void show() {
    dialog.show();
  }

  public void dismiss() {
    dialog.dismiss();
  }
}
