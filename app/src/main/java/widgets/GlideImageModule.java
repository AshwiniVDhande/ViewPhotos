package widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.viewphotos.R;

/**
 * Middleware GlideImageModule Class to render all types of images
 */
public class GlideImageModule {

    /**
     * Download and render images
     *
     * @param context        current context
     * @param imageView      view to load images
     * @param imageUrl       source url of image
     * @param progressBar    progressBar instance
     *
     */
    public static void loadImage(Context context, ImageView imageView, String imageUrl, ProgressBar progressBar) {
        try {
            // Get Access token
            String domainURL = imageUrl;


            // Set placeholder
            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.placeholder(R.drawable.ic_post_image_placeholder);
            requestOptions.error(R.drawable.no_image);

            // Load Image
            if (context != null) {
                // Display imageView
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(domainURL)
                        .apply(requestOptions)
                        .thumbnail(0.5f)
                        .listener(new RequestListener<Drawable>() {

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                                        boolean isFirstResource) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                }
                                return false;
                            }
                        })
                        .apply(requestOptions)
                        .into(imageView);
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }
}
