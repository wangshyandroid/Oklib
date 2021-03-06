package com.oklib.widget.imageloader;

import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oklib.utils.Logger.Logger;
import com.oklib.widget.imageloader.glide.CircleBorderTransformation;
import com.oklib.widget.imageloader.glide.RoundedCornersTransformation;
import com.oklib.widget.imageloader.glide.listener.ProgressLoadListener;
import com.oklib.widget.imageloader.glide.listener.ProgressModelLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Damon
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        int strategy = img.getWifiStrategy();
        if (strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            int netType = NetworkStatusUtil.getNetWorkType(ctx);
            if (netType == NetworkStatusUtil.NETWORKTYPE_WIFI) {
                loadNet(ctx, img);
            } else {
                loadCache(ctx, img);
            }
        } else {
            loadNet(ctx, img);
        }
    }

    @Override
    public void loadResource(Context context, int resId, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(resId));
    }

    @Override
    public void loadAssets(Context context, String assetName, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load("file:///android_asset/" + assetName));
    }

    @Override
    public void loadFile(Context context, File file, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(file));
    }

    @Override
    public void loadImageWithProgress(String url, ImageLoader imageLoader, final ProgressLoadListener listener) {
        Glide.with(imageLoader.getImgView().getContext()).using(new ProgressModelLoader(new ProgressLoadListener() {
            @Override
            public void update(final long bytesRead, final long contentLength) {
                imageLoader.getImgView().post(new Runnable() {
                    @Override
                    public void run() {
                        listener.update(bytesRead, contentLength);
                    }
                });
            }

            @Override
            public void onException(Exception e) {
                Logger.e("onException");
            }

            @Override
            public void onResourceReady() {
                Logger.e("onResourceReady");
            }
        })).load(url).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        listener.onException(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }
                }).into(imageLoader.getImgView());
    }


    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context.getApplicationContext()).clearMemory();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearMemory();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    private void loadNet(Context context, ImageLoader imageLoader) {
        loadImg(context, imageLoader, getRequestManager(context).load(imageLoader.getUrl()));
    }

    private void loadImg(Context context, ImageLoader imageLoader, DrawableRequestBuilder drawableRequestBuilder) {
        drawableRequestBuilder.crossFade()
                .thumbnail(imageLoader.getThumbnailSize())
                .placeholder(imageLoader.getPlaceHolder());
        if (imageLoader.isCircle()) {
            //圆形图片
            drawableRequestBuilder.bitmapTransform(
                    new CircleBorderTransformation(context, imageLoader.getBorder())).into(imageLoader.getImgView()
            );
            //FIXME默认是四个方向的圆角
        } else if (imageLoader.getRoundRadius() > 0) {
            drawableRequestBuilder.bitmapTransform(
                    new RoundedCornersTransformation(context, imageLoader.getRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL)).into(imageLoader.getImgView()
            );
        } else {
            drawableRequestBuilder.into(imageLoader.getImgView());

        }
    }

    /**
     * 从缓存中加载
     *
     * @param context
     * @param imageLoader
     */
    private void loadCache(Context context, ImageLoader imageLoader) {
        DrawableRequestBuilder drawableRequestBuilder = Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(imageLoader.getUrl())
                .crossFade()
                .placeholder(imageLoader.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        loadImg(context, imageLoader, drawableRequestBuilder);
    }
}
