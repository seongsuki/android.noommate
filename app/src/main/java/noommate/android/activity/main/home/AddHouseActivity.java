package noommate.android.activity.main.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.activity.NoommateActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import noommate.android.R;
import noommate.android.commons.Constants;
import noommate.android.commons.Tools;
import noommate.android.dialog.ImagePickerDialog;
import noommate.android.models.HouseModel;
import noommate.android.models.api.BaseRouter;
import noommate.android.models.api.CommonRouter;

public class AddHouseActivity extends NoommateActivity {
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddHouseActivity.class);
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.house_image_view)
    AppCompatImageView mHouseImageView;
    @BindView(R.id.house_name_edit_text)
    AppCompatEditText mHouseNameEditText;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------

    private ImagePickerDialog mImagePickDialog;



    private String mImagePath;
    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activity_add_house;
    }

    @Override
    protected void initLayout() {
        mToolbarTitle.setText("하우스 만들기");



    }

    @Override
    protected void initRequest() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImagePickDialog.dismiss();
        Image image = ImagePicker.getFirstImageOrNull(data);
        Uri imageUri = Uri.fromFile(new File(image.getPath()));
        File file = Tools.getInstance().compressImage(imageUri);

        Tools.getInstance().fileUploadAction(file, new Tools.FileUploadListener() {
            @Override
            public void onResult(boolean isSuccess, String filePath) {

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.centerCrop();

                Glide.with(mActivity)
                        .load(BaseRouter.BASE_URL + filePath)
                        .apply(requestOptions)
                        .into(mHouseImageView);
                mImagePath = filePath;

            }
        });
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 하우스 만들기
     */
    private void houseRegInAPI() {
        HouseModel houseRequest = new HouseModel();
        houseRequest.setHouse_name(mHouseNameEditText.getText().toString());
        houseRequest.setHouse_img(mImagePath);
        houseRequest.setMember_idx(Prefs.getString(Constants.MEMBER_IDX,""));
        CommonRouter.api().house_reg_in(Tools.getInstance(mActivity).getMapper(houseRequest)).enqueue(new Callback<HouseModel>() {
            @Override
            public void onResponse(Call<HouseModel> call, Response<HouseModel> response) {
                HouseModel mHouseResponse = response.body();
                if (mHouseResponse.getCode().equals("1000")) {
                    Prefs.putString(Constants.HOUSE_CODE, mHouseResponse.getHouse_code());
                    Prefs.putString(Constants.HOUSE_IDX, mHouseResponse.getHouse_idx());
                    Intent addHouseStepTwoActivity = AddHouseStepTwoActivity.getStartIntent(mActivity);
                    startActivity(addHouseStepTwoActivity,TRANS.PUSH);
                    finishWithRemove();
                } else {
                    showAlertDialog(mHouseResponse.getCode_msg(), "확인", new DialogEventListener() {
                        @Override
                        public void onReceivedEvent() {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<HouseModel> call, Throwable t) {

            }
        });
    }




    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------
    /**
     * 하우스 이미지
     */
    @OnClick(R.id.house_image_view)
    public void houseImageTouched() {
        mImagePickDialog = new ImagePickerDialog(mActivity);
        mImagePickDialog.mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카메라 선택
                ImagePicker.create(mActivity)
                        .imageDirectory(getString(R.string.app_name))
                        .cameraOnly()
                        .start(mActivity);
            }
        });
        mImagePickDialog.mAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범에서 선택
                ImagePicker.create(mActivity)
                        .returnMode(ReturnMode.ALL)
                        .folderMode(true)
                        .toolbarFolderTitle(getString(R.string.app_name))
                        .toolbarImageTitle(getString(R.string.app_name))
                        .includeVideo(false)
                        .includeAnimation(false)
                        .imageDirectory(getString(R.string.app_name))
                        .single()
                        .theme(R.style.ImagePickerTheme)
                        .showCamera(false)
                        .start();
            }
        });
        mImagePickDialog.show();
    }

    /**
     * 하우스 만들기
     **/
    @OnClick(R.id.add_house_button)
    public void addHouseTouched() {
        houseRegInAPI();
    }
}