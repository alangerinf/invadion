package com.bitlicon.purolator.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitlicon.purolator.R;

public class ImageFragment extends Fragment {


    private int position;
    private int[] logos = {R.drawable.ic_purolator, R.drawable.ic_filtech};

    public static Fragment getInstance(int position) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setPosition(position);
        return imageFragment;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = (ImageView) root.findViewById(R.id.logo);
        imageView.setImageDrawable(getActivity().getResources().getDrawable(logos[position]));
        return root;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
