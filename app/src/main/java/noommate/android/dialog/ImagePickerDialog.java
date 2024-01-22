package noommate.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.appcompat.widget.AppCompatButton;

import noommate.android.R;

public class ImagePickerDialog {

  public interface DialogListener {
    void onResult();
  }


  private Context mContext;
  private Dialog mDialog;

  public AppCompatButton mCameraButton;
  public AppCompatButton mAlbumButton;
  public AppCompatButton mDeleteButton;

  public ImagePickerDialog(Context context) {
    this.mContext = context;
    mDialog = new Dialog(context);
    mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    mDialog.setContentView(R.layout.dialog_image_picker);
    mDialog.setCancelable(true);

    mCameraButton = (AppCompatButton) mDialog.findViewById(R.id.camera_button);
    mAlbumButton = (AppCompatButton) mDialog.findViewById(R.id.album_button);
    mDeleteButton = (AppCompatButton) mDialog.findViewById(R.id.delete_button);

  }

  public void show() {
    mDialog.show();
  }

  public void dismiss() {
    mDialog.dismiss();
  }
}
