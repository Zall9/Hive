package com.example.hive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {
    String ServerUploadPath ="http://os-vps418.infomaniak.ch:1180/l2_gr_8/img_display.php";
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize your view here for use view.findViewById("your view id")
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Picasso.get()
                .load("https://square.github.io/picasso/static/saasdasdmple.png")
                .into(imageView);
    }
}
