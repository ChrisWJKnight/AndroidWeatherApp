package com.example.weatherhistory.ui.historicalgraphs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherhistory.R;

public class GraphsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_graphs, container, false);
        final TextView mText = root.findViewById(R.id.text_graphs);
        mText.setText(R.string.under_construction);
        return root;
    }
}