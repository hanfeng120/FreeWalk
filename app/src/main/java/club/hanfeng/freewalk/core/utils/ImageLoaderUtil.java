package club.hanfeng.freewalk.core.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.environment.DebugEnv;

public class ImageLoaderUtil {

    private static SparseArray<DisplayImageOptions> cache = new SparseArray<DisplayImageOptions>(10);
    private static DisplayImageOptions rectOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_default_rect).showImageForEmptyUri(R.mipmap.ic_default_rect)
            .showImageOnFail(R.mipmap.ic_default_rect).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    private static DisplayImageOptions bannerOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.default_banner).showImageForEmptyUri(R.mipmap.default_banner)
            .showImageOnFail(R.mipmap.default_banner).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    private static DisplayImageOptions portraitOptions;

    public static DisplayImageOptions getRoundOption(int corner) {
        if (cache.get(corner) == null) {
            DisplayImageOptions tmp = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_default_round).showImageForEmptyUri(R.mipmap.ic_default_round)
                    .showImageOnFail(R.mipmap.ic_default_round).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(corner))
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            cache.put(corner, tmp);
        }
        return cache.get(corner);
    }

    public static void dispalyImageFromBundle(Activity activity, Bundle bundle, String key, String picFileFullName, ImageView portrait) {
        Bitmap photo = bundle.getParcelable(key);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        FileOutputStream fileos = null;
        try {
            if (!TextUtils.isEmpty(picFileFullName)) {
                fileos = new FileOutputStream(picFileFullName);
                fileos.write(baos.toByteArray());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileos != null) {
                try {
                    fileos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void displayImageInRound(final String uri, final ImageView imageview) {
        int width = imageview.getWidth();
        if (width == 0) {
            imageview.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    ImageLoader.getInstance().displayImage(uri, imageview, getRoundOption(imageview.getWidth()));
                    imageview.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        } else {
            ImageLoader.getInstance().displayImage(uri, imageview, getRoundOption(width));
        }

    }

    public static void displayImageInRect(String uri, ImageView imageview) {
        ImageLoader.getInstance().displayImage(uri, imageview, rectOptions);
    }

    public static void displayImageBanner(String uri, ImageView imageview) {
        ImageLoader.getInstance().displayImage(uri, imageview, bannerOptions);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new WeakMemoryCache());
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        if (DebugEnv.DEBUG) {
            config.writeDebugLogs();
        }
        ImageLoader.getInstance().init(config.build());
    }
}
