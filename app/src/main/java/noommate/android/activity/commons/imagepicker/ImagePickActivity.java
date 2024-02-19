package noommate.android.activity.commons.imagepicker;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.dialog.ImagePickerDialog;


/**
 * TODO : Image Crop 을 사용하기 위해서는 다음을 추가 해주어야 한다.
 * <!-- 이미지 크롭-->
 * <activity
 * android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
 * android:theme="@style/Base.Theme.AppCompat" />
 */
public class ImagePickActivity extends NoommateActivity {
  //--------------------------------------------------------------------------------------------
  // MARK : GET START INTENT
  //--------------------------------------------------------------------------------------------
  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, ImagePickActivity.class);
    return intent;
  }

  //--------------------------------------------------------------------------------------------
  // MARK : Bind Area
  //--------------------------------------------------------------------------------------------

  @BindView(R.id.test_image_view)
  AppCompatImageView mTestImageView;
  @BindView(R.id.image_view)
  AppCompatImageView mImageView;
  //--------------------------------------------------------------------------------------------
  // MARK : Local variables
  //--------------------------------------------------------------------------------------------
  private ImageAdapter mImageAdapter;
  private RequestOptions requestOptions = new RequestOptions();
  ArrayList<Uri> mImageList = new ArrayList<>();
  private ImagePickerDialog mImagePickerDialog;
  private String mImagePath;

  //--------------------------------------------------------------------------------------------
  // MARK : Override
  //--------------------------------------------------------------------------------------------
  @Override
  protected int inflateLayout() {
    return R.layout.activity_image_pick;
  }

  @Override
  protected void initLayout() {
//    initToolbar("이미지 선택", getDrawable(R.drawable.ef_ic_arrow_back));
  }

  @Override
  protected void initRequest() {

  }

//  @Override
//  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//    // 이미지 선택 결과
//    if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//      Image image = ImagePicker.getFirstImageOrNull(data);
//      Uri imageUri = FileProvider.getUriForFile(mActivity, getString(R.string.fileprovider), new File(image.getPath()));
//
//
//      Tools.getInstance(mActivity).cropImage(imageUri, CropImageView.CropShape.OVAL, new ActivityResultCallback<CropImageView.CropResult>() {
//        @Override
//        public void onActivityResult(CropImageView.CropResult result) {
//          if (result.isSuccessful()) {
//            String uriFilePath = result.getUriFilePath(mActivity, false);
//            File file = new File(uriFilePath);
//
//            Tools.getInstance().fileUploadAction(file, new Tools.FileUploadListener() {
//              @Override
//              public void onResult(boolean isSuccess, String filePath) {
//                if (isSuccess) {
//                  // 이미지 업로드 성공
//                  requestOptions.centerCrop();
//                  Glide.with(mActivity)
//                      .load(BaseRouter.BASE_URL + filePath)
//                      .apply(requestOptions)
//                      .into(mImageView);
//                  mImagePath = filePath;
//                }
//
//                if (mImagePickerDialog != null) {
//                  mImagePickerDialog.dismiss();
//                }
//              }
//            });
//          }
//        }
//      });
//    }
//
//
//    super.onActivityResult(requestCode, resultCode, data);
//
//  }

  //--------------------------------------------------------------------------------------------
  // MARK : Local functions
  //--------------------------------------------------------------------------------------------


  //--------------------------------------------------------------------------------------------
  // MARK : Bind Actions
  //--------------------------------------------------------------------------------------------

  /**
   * 이미지 수정 버튼
   */
  @OnClick(R.id.image_layout)
  public void editImageTouched() {

    mImagePickerDialog = new ImagePickerDialog(mActivity);
    mImagePickerDialog.mCameraButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        ImagePicker.create(mActivity)
//            .imageDirectory(getString(R.string.app_name))
//            .cameraOnly()
//            .start(mActivity);
      }
    });
    mImagePickerDialog.mAlbumButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        ImagePicker.create(mActivity)
//            .returnMode(ReturnMode.ALL)
//            .folderMode(true)
//            .toolbarFolderTitle(getString(R.string.app_name))
//            .toolbarImageTitle(getString(R.string.app_name))
//            .includeVideo(false)
//            .includeAnimation(false)
//            .imageDirectory(getString(R.string.app_name))
//            .single()
//            .theme(R.style.ImagePickerTheme)
//            .showCamera(false)
//            .start();
      }
    });
    mImagePickerDialog.show();
  }

  /**
   * 앨범에서 다중 선택
   */
  @OnClick(R.id.multi_album_button)
  public void multiAlbumTouched() {
//    ImagePicker.create(mActivity)
//        .returnMode(ReturnMode.CAMERA_ONLY)
//        .folderMode(false)
//        .toolbarFolderTitle("감동팁")
//        .toolbarImageTitle("감동팁")
//        .includeVideo(false)
//        .includeAnimation(false)
//        .imageDirectory(getString(R.string.app_name))
//        .multi()
//        .theme(R.style.ImagePickerTheme)
//        .showCamera(false)
//        .start();
  }
}
