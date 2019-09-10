package views.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.viewphotos.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Photos;
import utils.Constant;
import widgets.GlideImageModule;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailsFragment extends Fragment {

    Photos photoDetails;
    @BindView(R.id.tv_details)
    AppCompatTextView tvDetails;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.action_toolbar)
    Toolbar toolbar;

    public PhotoDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.setTitle(getString(R.string.photo_details));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Parcelable parcelable = getArguments().getParcelable(Constant.PHOTODETAILS);
        if (parcelable != null) {
            photoDetails = Parcels.unwrap(parcelable);
            tvDetails.setText(photoDetails.getTitle());
            GlideImageModule.loadImage(getActivity(),ivPhoto,photoDetails.getUrl(), null);
        }
    }
}
