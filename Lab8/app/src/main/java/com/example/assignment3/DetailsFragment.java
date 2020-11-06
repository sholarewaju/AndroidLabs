package com.example.assignment3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String MESSAGE = "message";
    public static final String MESSAGE_ID = "id";
    public static final String MESSAGE_TYPE = "type";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvmsg, tvid;
    CheckBox checkMsgType;
    Button btnHide;
    private String message;
    private Long id;
    private boolean msgType;

    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(MESSAGE);
            id = getArguments().getLong(MESSAGE_ID);
            msgType = getArguments().getBoolean(MESSAGE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_details, container, false);
        tvmsg=view.findViewById(R.id.tvMessage);
        tvid=view.findViewById(R.id.tvID);
        checkMsgType=view.findViewById(R.id.checkboxMsgType);
        btnHide=view.findViewById(R.id.btnHide);

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.remove(DetailsFragment.this);
                ft.commit();
            }
        });

        tvmsg.setText(message);
        tvid.setText("ID = "+id);
        checkMsgType.setChecked(msgType);
        return view;
    }
}