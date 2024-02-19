package noommate.android.activity.signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import noommate.android.R;
import noommate.android.activity.NoommateActivity;
import noommate.android.commons.DecorationHorizontal;
import noommate.android.commons.Tools;
import noommate.android.models.BaseModel;
import timber.log.Timber;

public class AddCharActivity extends NoommateActivity {
    public interface OnAddCharListener {
        void onRefresh(String back, String face, String color);
    }
    //--------------------------------------------------------------------------------------------
    // MARK : GET START INTENT
    //--------------------------------------------------------------------------------------------
    public static Intent getStartIntent(Context context,String back,String face, String color, OnAddCharListener onAddCharListener) {
        Intent intent = new Intent(context, AddCharActivity.class);
        mOnAddCharListener = onAddCharListener;
        mBack = back;
        mFace = face;
        mColor = color;
        return intent;
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Area
    //--------------------------------------------------------------------------------------------
    @BindView(R.id.back_recycler_view)
    RecyclerView mBackRecyclerView;
    @BindView(R.id.face_recycler_view)
    RecyclerView mFaceRecyclerView;
    @BindView(R.id.color_recycler_view)
    RecyclerView mColorRecyclerView;
    @BindView(R.id.back_layout)
    RelativeLayout mBackLayout;
    @BindView(R.id.back_image_view)
    AppCompatImageView mBackImageView;
    @BindView(R.id.face_image_view)
    AppCompatImageView mFaceImageView;

    //--------------------------------------------------------------------------------------------
    // MARK : Local variables
    //--------------------------------------------------------------------------------------------
    private BackAdapter mBackAdapter;
    private FaceAdapter mFaceAdapter;
    private ColorAdapter mColorAdapter;
    private static String mBack;
    private static String mFace;
    private static String mColor;
    private static OnAddCharListener mOnAddCharListener;

    ArrayList<BaseModel> mBackImageList = new ArrayList<>();
    ArrayList<BaseModel> mFaceImageList = new ArrayList<>();
    ArrayList<BaseModel> mBackColorList = new ArrayList<>();

    //--------------------------------------------------------------------------------------------
    // MARK : Override
    //--------------------------------------------------------------------------------------------
    @Override
    protected int inflateLayout() {
        return R.layout.activty_add_char;
    }

    @Override
    protected void initLayout() {
        initToolbar("캐릭터 만들기");
        for (int i = 0; i < 6; i++) {
            mBackImageList.add(new BaseModel());
        }
        for (int i = 0; i < 6; i++) {
            mFaceImageList.add(new BaseModel());
        }
        for (int i =0; i < 6; i++) {
            mBackColorList.add(new BaseModel());
        }


        if (mBack == null) {
            mBack = "0";
            mBackImageList.get(0).setSelected(true);
        } else {
            mBackImageList.get(Integer.parseInt(mBack)).setSelected(true);
        }

        if (mFace == null) {
            mFace = "0";
            mFaceImageList.get(0).setSelected(true);
        } else {
            mFaceImageList.get(Integer.parseInt(mFace)).setSelected(true);
        }

        if (mColor ==  null) {
            mColor = "0";
            mBackColorList.get(0).setSelected(true);
        } else {
            mBackColorList.get(Integer.parseInt(mColor)).setSelected(true);
        }

        if (mBack.equals("0")) {
            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back5));
        } else if (mBack.equals("1")) {
            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back4));
        } else if (mBack.equals("2")) {
            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back3));
        } else if (mBack.equals("3")) {
            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back2));
        } else if (mBack.equals("4")) {
            mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back1));
        }
        // 표정
        if (mFace.equals("0")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_191));
        } else if (mFace.equals("1")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_197));
        } else if (mFace.equals("2")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_193));
        } else if (mFace.equals("3")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_194));
        } else if (mFace.equals("4")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_195));
        } else if (mFace.equals("5")) {
            mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_196));
        }
        // 색상
        if (mColor.equals("0")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ff6d6d));
        } else if (mColor.equals("1")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ffcd4b));
        } else if (mColor.equals("2")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_63d08f));
        } else if (mColor.equals("3")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_87b7ff));
        } else if (mColor.equals("4")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_798ed6));
        } else if (mColor.equals("5")) {
            mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_bfa0ff));
        }



    }

    @Override
    protected void initRequest() {
        initBackAdapter();
        initFaceAdapter();
        initColorAdapter();
    }

    //--------------------------------------------------------------------------------------------
    // MARK : Local functions
    //--------------------------------------------------------------------------------------------

    /**
     * 모양
     */
    private void initBackAdapter() {
        mBackAdapter = new BackAdapter(R.layout.row_char_img, mBackImageList);
        mBackAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mBack = String.valueOf(position);
                for (BaseModel value : mBackImageList) {
                    value.setSelected(false);
                }
                mBackImageList.get(position).setSelected(true);
                mBackAdapter.setNewData(mBackImageList);
                    if (position == 0) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back5));
                    } else if (position == 1) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back4));
                    } else if (position == 2) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back3));
                    } else if (position == 3) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back2));
                    } else if (position == 4) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back1));
                    } else if (position == 5) {
                        mBackImageView.setImageDrawable(mActivity.getDrawable(R.drawable.back0));
                    }
            }
        });
        mBackRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL,false));
        mBackRecyclerView.addItemDecoration(new DecorationHorizontal(mActivity, Tools.getInstance().dpTopx(mActivity, 8), Tools.getInstance().dpTopx(mActivity, 16)));
        mBackRecyclerView.setAdapter(mBackAdapter);


    }

    /**
     * 얼굴
     */
    private void initFaceAdapter() {
        mFaceAdapter = new FaceAdapter(R.layout.row_face_list, mFaceImageList);
        mFaceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mFace = String.valueOf(position);
                Timber.i("face : " + mFace);
                for (BaseModel value : mFaceImageList) {
                    value.setSelected(false);
                }
                mFaceImageList.get(position).setSelected(true);
                mFaceAdapter.setNewData(mFaceImageList);
                if (position == 0) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_191));
                } else if (position == 1) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_197));
                } else if (position == 2) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_193));
                } else if (position == 3) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_194));
                } else if (position == 4) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_195));
                } else if (position == 5) {
                    mFaceImageView.setImageDrawable(mActivity.getDrawable(R.drawable.group_196));
                }
            }
        });
        mFaceRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL,false));
        mFaceRecyclerView.addItemDecoration(new DecorationHorizontal(mActivity, Tools.getInstance().dpTopx(mActivity, 8), Tools.getInstance().dpTopx(mActivity, 16)));
        mFaceRecyclerView.setAdapter(mFaceAdapter);
    }

    /**
     * 컬러
     */
    private void initColorAdapter() {
        mColorAdapter = new ColorAdapter(R.layout.row_color, mBackColorList);
        mColorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mColor = String.valueOf(position);
                for (BaseModel value : mBackColorList) {
                    value.setSelected(false);
                }
                mBackColorList.get(position).setSelected(true);
                mColorAdapter.setNewData(mBackColorList);
                if (position == 0) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ff6d6d));
                } else if (position == 1) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_ffcd4b));
                } else if (position == 2) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_63d08f));
                } else if (position == 3) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_87b7ff));
                } else if (position == 4) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_798ed6));
                } else if (position == 5) {
                    mBackLayout.setBackgroundColor(mActivity.getColor(R.color.color_bfa0ff));
                }
            }
        });
        mColorRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL,false));
        mColorRecyclerView.addItemDecoration(new DecorationHorizontal(mActivity, Tools.getInstance().dpTopx(mActivity, 8), Tools.getInstance().dpTopx(mActivity, 16)));
        mColorRecyclerView.setAdapter(mColorAdapter);

    }

    //--------------------------------------------------------------------------------------------
    // MARK : Bind Actions
    //--------------------------------------------------------------------------------------------

    /**
     * 선택완료
     */
    @OnClick(R.id.add_button)
    public void homeTouched() {
        finishWithRemove();
        mOnAddCharListener.onRefresh(mBack, mFace, mColor);
    }



}

