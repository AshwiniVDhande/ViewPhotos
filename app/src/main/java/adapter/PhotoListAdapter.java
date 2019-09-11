package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewphotos.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import models.Photos;
import widgets.GlideImageModule;

/**
 * Set the PhototListAdapter
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> implements Filterable {
    ArrayList<Photos> photoList;
    private List<Photos> photoListFiltered;
    private IActivityFeedCallback m_callback;
    private Context m_context;
    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;
    private ProgressBar m_footerProgressBar;
    private AppCompatTextView m_footerEndOfListText;
    private static int mPosition;

    /**
     * public constructor to intitialze context , interface
     */
    public PhotoListAdapter(@NonNull AppCompatActivity context, @Nullable IActivityFeedCallback callback) {
        m_callback = callback;
        m_context = new WeakReference<>(context).get();
        this.photoList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType == TYPE_ITEM ? R.layout.item_photolist : R.layout.list_footer, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mPosition = position;
        if (getItemViewType(position) == TYPE_ITEM) {
            holder.setItem(photoListFiltered.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return photoListFiltered.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    photoListFiltered = photoList;
                } else {
                    List<Photos> filteredList = new ArrayList<>();
                    for (Photos row : photoList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    photoListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = photoListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                photoListFiltered = (ArrayList<Photos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * ViewHolder for custome adater to set the item view
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_photo)
        CardView cardPhoto;
        @BindView(R.id.iv_photo_even)
        ImageView ivPhotoEven;
        @BindView(R.id.tv_phototitle_even)
        AppCompatTextView tvPhototitleEven;
        @BindView(R.id.iv_photo_odd)
        ImageView ivPhotoOdd;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {
                ButterKnife.bind(this, itemView);
            } else if (viewType == TYPE_FOOTER) {
                m_footerProgressBar = itemView.findViewById(R.id.footerProgressBar);
                m_footerEndOfListText = itemView.findViewById(R.id.footerEndText);
            }
        }

        /**
         * set photoList item data as per even/odd roe
         */
        void setItem(@NonNull Photos photoItem) {
            if (mPosition % 2 == 0) {
                GlideImageModule.loadImage(m_context, ivPhotoEven, photoItem.getThumbnailUrl(), null);
                ivPhotoOdd.setVisibility(View.GONE);
                ivPhotoEven.setVisibility(View.VISIBLE);
            } else {
                GlideImageModule.loadImage(m_context, ivPhotoOdd, photoItem.getThumbnailUrl(), null);
                ivPhotoOdd.setVisibility(View.VISIBLE);
                ivPhotoEven.setVisibility(View.GONE);
            }
            tvPhototitleEven.setText(photoItem.getTitle());
            cardPhoto.setAnimation(AnimationUtils.loadAnimation(m_context, R.anim.fade_scale_animation));
        }

        @OnClick(R.id.card_photo)
        public void onViewClicked() {
            m_callback.getItemDetails(photoListFiltered.get(getAdapterPosition()));
        }
    }

    /**
     * Reset the updated photo list to recycler view
     */
    public void setPhotoList(@NonNull ArrayList<Photos> items) {
        photoList.addAll(items);
        photoListFiltered = photoList;
        notifyDataSetChanged();
    }

    public ArrayList<Photos> getPhotoList() {
        return photoList;
    }

    /**
     * Handles Footer child's visibility.
     *
     * @param result If true then visible progress bar, And if false then hide progress bar.
     */
    public void setFooterProgressBarVisibility(boolean result) {
        if (m_footerProgressBar != null) {
            m_footerProgressBar.setVisibility(result ? View.VISIBLE : View.GONE);
            m_footerEndOfListText.setVisibility(result ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Add new items in the photoList
     */
    public void addNewItems() {
        try {
            if (getItemCount() != 0) {
                notifyItemInserted(getItemCount() - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            e.getCause();
        }
    }


    /**
     * Create PhotoItem Callback interface
     * Handle all onClick calls
     */
    public interface IActivityFeedCallback {

        void getItemDetails(@NonNull Photos photoDetails);
    }
}
