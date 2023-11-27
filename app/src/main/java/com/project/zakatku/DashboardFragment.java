package com.project.zakatku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextView txtZakuTunai, txtZakuBeras, txtNamaPengguna;
    Button logoutDashboard;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private void setupRecyclerView(RecyclerView recyclerView, String[] cardContents) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cardContents);
        recyclerView.setAdapter(adapter);

        // Mengatur GridLayoutManager dengan 3 kolom
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String role = sharedPreferences2.getString("setrole", "");

        if (role.equals("admin")) {
            String[] adminArray = {"Zaku-Beras", "Zaku-Tunai", "Zaku-Verif", "Zaku-Akun"};
            setupRecyclerView(recyclerView, adminArray);
        } else {
            String[] userArray = {"Zaku-Beras", "Zaku-Tunai"};
            setupRecyclerView(recyclerView, userArray);
        }
         logoutDashboard = view.findViewById(R.id.logoutdash);
         txtNamaPengguna = view.findViewById(R.id.namaPengguna);
         SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         String displayName = sharedPreferences.getString("displayName", "");

         txtNamaPengguna.setText(displayName);

        logoutDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.remove("displayName");
                editor.remove("setrole");
                editor.apply();
                redirectToLogin();
            }
        });

        return view;
    }

    private void redirectToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish(); // Menutup MainActivity
    }


}