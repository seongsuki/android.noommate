package noommate.android.commons;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.core.widget.NestedScrollView;

//import com.canhub.cropper.CropImageContract;
//import com.canhub.cropper.CropImageContractOptions;
//import com.canhub.cropper.CropImageOptions;
//import com.canhub.cropper.CropImageView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import noommate.android.activity.NoommateActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.models.BaseModel;
import noommate.android.models.FileModel;
import noommate.android.models.api.BaseRouter;
import noommate.android.models.api.CommonRouter;
import timber.log.Timber;

public class Tools {

  private static final Tools ourInstance = new Tools();
  private static NoommateActivity mActivity;

  public static Tools getInstance() {
    return ourInstance;
  }

  public static Tools getInstance(NoommateActivity activity) {
    mActivity = activity;
    return ourInstance;
  }

  public interface FileUploadListener {
    void onResult(boolean isSuccess, String filePath);
  }

  private Tools() {
  }

  /**
   * API 성공 유무를 판단
   *
   * @return
   */
  public boolean isSuccessResponse(Response<?> response) {
    ArrayList<String> successCode = new ArrayList<String>() {{
      add("1000");
    }};
    ArrayList<String> messageCode = new ArrayList<String>() {{
      add("2001");
    }};

    String code = "";
    String code_msg = "";

    if (response != null && response.isSuccessful()) {
      code = ((BaseModel) response.body()).getCode();
      code_msg = ((BaseModel) response.body()).getCode_msg();
    } else {
      showToast("알 수 없는 오류가 발생하였습니다.");
      return false;
    }

    if (successCode.contains(code)) { // 성공
      return true;
    } else if (messageCode.contains(code)) { // 성공 후 메세지 표현
      showToast(code_msg);
      return true;
    } else { // 실패
      return false;
    }
  }

  /**
   * 데이터 맵퍼
   *
   * @param object
   * @return
   */
  public Map<String, Object> getMapper(Object object) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    Map<String, Object> map = mapper.convertValue(object, Map.class);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Timber.d("PARAMETER :" + gson.toJson(map));
    return map;
  }

  /**
   * 토스트 표시
   *
   * @param message - 메세지
   */
  public void showToast(String message) {
    Snackbar mSnackbar;
    mSnackbar = Snackbar.make(mActivity.getWindow().getDecorView().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
    mSnackbar.show();
  }


  /**
   * 숫자 String 에 ",(콤마)" 표시 추가
   *
   * @param value 숫자 String
   * @return 결과값
   */
  public String numberPlaceValue(String value) {
    String ret = "0";
    if (value == null) {
      return ret;
    } else {
      long val = Long.parseLong(value);
      try {
        DecimalFormat format = new DecimalFormat("#,###");
        ret = format.format(val);
      } catch (NumberFormatException nfe) {
        ret = "0";
      } finally {
        return ret;
      }
    }
  }

  /**
   * 시스템 바 색상 지정
   *
   * @param act
   * @param color
   */
  public void setSystemBarColor(Activity act, @ColorRes int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = act.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(act.getResources().getColor(color));
    }
  }

  /**
   * 시스템 바 색상 Light 버전
   *
   * @param act
   */
  public void setSystemBarLight(Activity act) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      View view = act.findViewById(android.R.id.content);
      int flags = view.getSystemUiVisibility();
      flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      view.setSystemUiVisibility(flags);
    }
  }

  /**
   * Notification 바 투명하게
   */
  public void setSystemBarTransparent(Activity act) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = act.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
    }
  }

  /**
   * DP to PX
   *
   * @param c
   * @param dp
   * @return
   */
  public int dpTopx(Context c, int dp) {
    Resources r = c.getResources();
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
  }

  /**
   * PX to DP
   *
   * @param context
   * @param pxValue
   * @return
   */
  public int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 스크롤
   *
   * @param nested
   * @param targetView
   */
  public void nestedScrollTo(final NestedScrollView nested, final View targetView) {
    nested.post(new Runnable() {
      @Override
      public void run() {
        nested.scrollTo(500, targetView.getBottom());
      }
    });
  }

  /**
   * 전화 걸기
   *
   * @param tel
   */
  public void openPhone(String tel) {
    try {
      Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
      mActivity.startActivity(tt);
    } catch (Exception e) {
      showToast("전화를 연결할 수 없습니다.");
    }

  }

  /**
   * 외부 링크 열기
   *
   * @param url
   */
  public void openBrowser(String url) {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      mActivity.startActivity(intent);
    } catch (Exception e) {
      showToast("링크를 연결할 수 없습니다.");
    }
  }

  /**
   * 클립 보드에 복사
   *
   * @param context
   * @param text
   */
  public void copyToClipboard(Context context, String text) {
    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    ClipData clip = ClipData.newPlainText("clipboard", text);
    clipboard.setPrimaryClip(clip);
  }

  /**
   * 파일 업로드
   * @param file
   */
  public void fileUploadAction(File file, FileUploadListener fileUploadListener) {
    RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fbody);
    CommonRouter.api().fileUpload_action(body).enqueue(new Callback<FileModel>() {
      @Override
      public void onResponse(Call<FileModel> call, Response<FileModel> response) {
        FileModel fileUploadResponse = response.body();

        if (Tools.getInstance().isSuccessResponse(response)) {
          fileUploadListener.onResult(true, fileUploadResponse.getFile_path());
        } else {
//          showToast(mActivity.getString(R.string.api_error));
          fileUploadListener.onResult(false, "");
        }
      }
      @Override
      public void onFailure(Call<FileModel> call, Throwable t) {
//        showToast(mActivity.getString(R.string.api_error));
        fileUploadListener.onResult(false, "");
      }
    });
  }


  /**
   * 이미지 압축
   * @param imageUri
   * @return
   */
  public File compressImage(Uri imageUri) {
    Bitmap loadedBitmap = loadBitmap(String.valueOf(imageUri));
    Bitmap mResizeImageBitmap = bitmapResizePrc(loadedBitmap).getBitmap();

    ExifInterface exif = null;
    try {
      exif = new ExifInterface(imageUri.getPath());

    } catch (IOException e) {
      Timber.e(e.getLocalizedMessage());
    }
    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
    Bitmap mRotateBitmap = rotateBitmap(mResizeImageBitmap, orientation);
    String mGetBitmapFile = saveBitmapToJpeg(mActivity, mRotateBitmap, UUID.randomUUID().toString());
    File imageFile = new File(mGetBitmapFile);
    return imageFile;
  }


  /**
   * Image -> Bitmap 변경
   *
   * @author khh
   * @since 4/16/21
   **/
  public Bitmap loadBitmap(String url) {
    Bitmap bm = null;
    InputStream is = null;
    BufferedInputStream bis = null;
    try {
      URLConnection conn = new URL(url).openConnection();
      conn.connect();
      is = conn.getInputStream();
      bis = new BufferedInputStream(is, 8192);
      bm = BitmapFactory.decodeStream(bis);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return bm;
  }

  /**
   * 이미지 사이즈 변경
   *
   * @author khh
   * @since 4/16/21
   **/
  public BitmapDrawable bitmapResizePrc(Bitmap mSrc) {
    BitmapDrawable bitmapResult = null;
    int newHeight, newWidth;

    Timber.i("ORIGINAL IMAGE WIDTH SIZE === " + mSrc.getWidth());
    Timber.i("ORIGINAL IMAGE HEIGHT SIZE === " + mSrc.getHeight());

    int ratio = 1;
    if (mSrc.getWidth() > 1500) {  //이미지 크기가 1000 보다 클때
      ratio = mSrc.getWidth() / 1500;
    } else { //이미지 크기가 1000 보다 작을때 이미지 크기 변경 비율을 Default로 1을 설정한다.
      ratio = 1;
    }

    Timber.i("RATION === " + ratio);
    newHeight = mSrc.getHeight() / ratio;
    newWidth = mSrc.getWidth() / ratio;

    int width = mSrc.getWidth();
    int height = mSrc.getHeight();

    // calculate the scale - in this case = 0.4f
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;

    // createa matrix for the manipulation
    Matrix matrix = new Matrix();
    // resize the bit map

    matrix.postScale(scaleWidth, scaleHeight);

    // rotate the Bitmap  회전
//    matrix.postRotate(90);

    // recreate the new Bitmap
    Bitmap resizedBitmap = Bitmap.createBitmap(mSrc, 0, 0, width, height, matrix, true);

    // check
    width = resizedBitmap.getWidth();
    height = resizedBitmap.getHeight();

    Timber.i("AFTER IMAGE WIDTH SIZE === " + width);
    Timber.i("AFTER IMAGE HEIGHT SIZE === " + height);

//    Log.i("ImageResize", "Image Resize Result : " + Boolean.toString((newHeight==height)&&(newWidth==width)) );
    Timber.i("ImageResize" + "Image Resize Result : " + Boolean.toString((newHeight == height) && (newWidth == width)));

    // make a Drawable from Bitmap to allow to set the BitMap
    // to the ImageView, ImageButton or what ever
    bitmapResult = new BitmapDrawable(resizedBitmap);

    return bitmapResult;
  }

  /**
   * Bitmap -> File 변환
   *
   * @author khh
   * @since 4/15/21
   **/
  public String saveBitmapToJpeg(Context context, Bitmap bitmap, String name) {
    File storage = context.getCacheDir();
    String fileName = name + ".jpg";
    File tempFile = new File(storage, fileName);
    try {
      tempFile.createNewFile();
      FileOutputStream out = new FileOutputStream(tempFile);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tempFile.getAbsolutePath();
  }

  /**
   * 이미지 회전
   *
   * @author khh
   * @since 4/29/21
   **/
  public Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
    Matrix matrix = new Matrix();
    switch (orientation) {
      case ExifInterface.ORIENTATION_NORMAL:
        return bitmap;
      case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
        matrix.setScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_180:
        matrix.setRotate(180);
        break;
      case ExifInterface.ORIENTATION_FLIP_VERTICAL:
        matrix.setRotate(180);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_TRANSPOSE:
        matrix.setRotate(90);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_90:
        matrix.setRotate(90);
        break;

      case ExifInterface.ORIENTATION_TRANSVERSE:
        matrix.setRotate(-90);
        matrix.postScale(-1, 1);
        break;

      case ExifInterface.ORIENTATION_ROTATE_270:
        matrix.setRotate(-90);
        break;
      default:
        return bitmap;
    }

    try {
      Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
      bitmap.recycle();
      return bmRotated;
    } catch (OutOfMemoryError e) {
      Timber.e(e.getLocalizedMessage());
      return null;
    }
  }

//  @RequiresApi(api = Build.VERSION_CODES.N)
//  public static void cropImage(Uri imageUri, CropImageView.CropShape cropShape , ActivityResultCallback<CropImageView.CropResult> resultListener) {
//
//    int initRotate = 0;
//    try {
//      InputStream in = mActivity.getContentResolver().openInputStream(imageUri);
//      ExifInterface exif = new ExifInterface(in);
//      in.close();
//
//      int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//      if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
//        initRotate = 90;
//      } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
//        initRotate = 180;
//      } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
//        initRotate = 270;
//      }
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    CropImageContractOptions options = new CropImageContractOptions(imageUri, new CropImageOptions())
//        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
//        .setCropShape(cropShape)
//        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
//        .setAspectRatio(1, 1)
//        .setMaxZoom(4)
//        .setAutoZoomEnabled(true)
//        .setMultiTouchEnabled(true)
//        .setCenterMoveEnabled(true)
//        .setShowCropOverlay(true)
//        .setAllowFlipping(true)
//        .setSnapRadius(3f)
//        .setTouchRadius(48f)
//        .setInitialCropWindowPaddingRatio(0.1f)
//        .setBorderLineThickness(3f)
//        .setBorderLineColor(Color.argb(170, 255, 255, 255))
//        .setBorderCornerThickness(2f)
//        .setBorderCornerOffset(5f)
//        .setBorderCornerLength(14f)
//        .setBorderCornerColor(WHITE)
//        .setGuidelinesThickness(1f)
//        .setGuidelinesColor(R.color.white)
//        .setBackgroundColor(Color.argb(119, 0, 0, 0))
//        .setMinCropWindowSize(24, 24)
//        .setMinCropResultSize(20, 20)
//        .setMaxCropResultSize(99999, 99999)
//        .setActivityTitle("")
//        .setActivityMenuIconColor(0)
//        .setOutputUri(null)
//        .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
//        .setOutputCompressQuality(100)
//        .setRequestedSize(0, 0)
//        .setRequestedSize(0, 0, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
//        .setInitialCropWindowRectangle(null)
//        .setInitialRotation(initRotate)
//        .setAllowCounterRotation(false)
//        .setFlipHorizontally(false)
//        .setFlipVertically(false)
//        .setCropMenuCropButtonTitle("등록")
//        .setCropMenuCropButtonIcon(0)
//        .setAllowRotation(true)
//        .setNoOutputImage(false)
//        .setFixAspectRatio(true);
//
////    ComponentActivity
//    ActivityResultLauncher<CropImageContractOptions> cropImage = mActivity.registerForActivityResult(new CropImageContract(), resultListener);
//    cropImage.launch(options);
//  }


  /**
   * 이미지 경로
   *
   * @param imgPath
   */
  public String getImagePath(String imgPath) {
    return BaseRouter.BASE_URL + imgPath;
  }

  /**
   * 썸네일 이미지 경로
   * @param imgPath
   * @return
   */
  public String thumbnailImageUrl(String imgPath) {
    String[] split = imgPath.split("\\.");
    if (split.length > 1) {
      split[0] += "_s.";
      return BaseRouter.BASE_URL + String.join("", split);
    } else {
      return BaseRouter.BASE_URL + imgPath;
    }
  }
}
