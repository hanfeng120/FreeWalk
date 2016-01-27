package club.hanfeng.freewalk.studio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileNotFoundException;
import java.util.List;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.activity.PictureViewerActivity;
import club.hanfeng.freewalk.adapter.DirectRecyclerAdapter;
import club.hanfeng.freewalk.core.studio.StudioManager;
import club.hanfeng.freewalk.core.studio.data.StudioInfo;
import club.hanfeng.freewalk.framework.BaseActivity;
import club.hanfeng.freewalk.interfaces.studio.OnRecyclerItemClickListener;
import club.hanfeng.freewalk.utils.FreeWalkProgress;
import cn.bmob.v3.listener.FindListener;

public class StudioActivity extends BaseActivity {

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private RecyclerView recycleView;
    private DirectRecyclerAdapter adapter;
    private String[] items = new String[]{"拍照", "从相册选择"};

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void refreshView(int viewId) {

    }

    @Override
    protected boolean initBackActionBar() {
        return true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_studio;
    }

    @Override
    protected View getContentRootView() {
        return null;
    }

    @Override
    protected void initTopBar() {
        FreeWalkProgress.show(getContext(), "正在努力加载...");
    }

    @Override
    protected void initContent() {
        recycleView = (RecyclerView) findViewById(R.id.rv_direct);

        adapter = new DirectRecyclerAdapter(this);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);
        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                startActivity(new Intent(StudioActivity.this, PictureViewerActivity.class));
            }
        });
    }

    @Override
    protected void initBottomBar() {

    }

    @Override
    protected void initData() {
        StudioManager.getInstance().getAllStudios(getContext(), new FindListener<StudioInfo>() {
            @Override
            public void onSuccess(List<StudioInfo> list) {
                adapter.setData(list);
                FreeWalkProgress.dismiss(getContext());
            }

            @Override
            public void onError(int i, String s) {
                FreeWalkProgress.dismiss(getContext());
            }
        });
    }

    private void sendStudio() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    break;
                case CAMERA_REQUEST_CODE:
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(StudioManager.getInstance().getCacheFile()));
        }
    }

    /**
     * 静态从文件中获取图片的方法
     *
     * @param uri
     * @return
     */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(getContext())
                .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intentCapture = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        intentCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(StudioManager.getInstance().getCacheFile()));
                                        startActivityForResult(intentCapture,
                                                CAMERA_REQUEST_CODE);
                                        break;
                                    case 1:
                                        Intent intentGallery = new Intent();
                                        intentGallery.setType("image/*");
                                        intentGallery
                                                .setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(intentGallery,
                                                IMAGE_REQUEST_CODE);
                                        break;
                                }
                            }
                        }
                )
                .setCancelable(true)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_studio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            showDialog();
        }

        return super.onOptionsItemSelected(item);
    }

}
