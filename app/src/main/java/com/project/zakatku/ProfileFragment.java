package com.project.zakatku;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewNomBayar;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Mendapatkan username pengguna yang sedang login dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("userId", ""); // Ganti dengan key yang benar
        TextView txtPassword = view.findViewById(R.id.txtpassword);
        TextView txtNama = view.findViewById(R.id.txtnama);
        TextView txtEmail = view.findViewById(R.id.txtemail);
        TextView txtUsername = view.findViewById(R.id.txtusername);
        TextView txtNotel = view.findViewById(R.id.txttelp);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference pembayaranRef = database.getReference("pembayaran");
        DatabaseReference dataUser = database.getReference("users");

        pembayaranRef.orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalNomBayar = 0;

                if (dataSnapshot.exists()) {
                    // Loop melalui hasil yang cocok dengan username
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String nomBayar = snapshot.child("nomBayar").getValue(String.class);
                        totalNomBayar += Long.parseLong(nomBayar);
                    }
                    TextView textViewTotalNomBayar = view.findViewById(R.id.textViewTotalNomBayar);
                    textViewTotalNomBayar.setText("Total Zakat : Rp " + totalNomBayar);

                } else {
                    TextView textViewTotalNomBayar = view.findViewById(R.id.textViewTotalNomBayar);
                    textViewTotalNomBayar.setText("Belum pernah berzakat");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle kesalahan jika diperlukan
            }
        });

        dataUser.orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nama = "", email = "", username = "", password = "", notel = "";

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        nama = snapshot.child("nama").getValue(String.class);
                        email = snapshot.child("email").getValue(String.class);
                        username = snapshot.child("username").getValue(String.class);
                        password = snapshot.child("password").getValue(String.class);
                        notel = snapshot.child("notel").getValue(String.class);
                    }
                    txtNama.setText(nama);
                    txtEmail.setText(email);
                    txtUsername.setText(username);
                    txtPassword.setText("*******");
                    txtNotel.setText(notel);
                    String finalPassword = password;
                    txtPassword.setOnClickListener(new View.OnClickListener() {
                        boolean isPasswordVisible = false;

                        @Override
                        public void onClick(View v) {
                            if (!isPasswordVisible) {
                                // Jika password belum terlihat, tampilkan password
                                txtPassword.setText(finalPassword);
                                isPasswordVisible = true;
                            } else {
                                // Jika password sudah terlihat, sembunyikan kembali
                                txtPassword.setText("*******");
                                isPasswordVisible = false;
                            }
                        }
                    });
                } else {
                    // Jika data pengguna tidak ditemukan, berikan pesan atau lakukan tindakan yang sesuai
                    // Misalnya:
                    txtPassword.setText("Data tidak ditemukan");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

}