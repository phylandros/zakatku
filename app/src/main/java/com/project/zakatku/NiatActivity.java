package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NiatActivity extends AppCompatActivity {

    Button btnLanjutPembayaran, btnBackInputZakat;
    String serverKey = BuildConfig.SERVER_KEY;
    String clientKey = BuildConfig.CLIENT_KEY;
    String apiSandbox = BuildConfig.API_SANDBOX;
    String authString = Base64.encodeToString((serverKey).getBytes(), Base64.NO_WRAP);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niat);

        btnLanjutPembayaran = findViewById(R.id.lanjutPembayaran);
        btnBackInputZakat = findViewById(R.id.backDashboard);


        btnBackInputZakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NiatActivity.this, InputZakatActivity.class);
                startActivity(intent);
            }
        });

        btnLanjutPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kodePembayaran = getIntent().getStringExtra("KODE_PEMBAYARAN");
                String Nama = getIntent().getStringExtra("NAMA");
                String JumlahJiwa = getIntent().getStringExtra("JUMLAH_JIWA");

                createPaymentLink("ZaKu-Tunai "+Nama, 16000, kodePembayaran, Integer.valueOf(JumlahJiwa));
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    private void createPaymentLink(String productName, int productPrice, String orderId, int quantity) {
        try {
            JSONObject jsonObject = new JSONObject();

            // Transaction Details
            JSONObject transactionDetails = new JSONObject();
            transactionDetails.put("order_id", orderId);
            transactionDetails.put("gross_amount", productPrice * quantity);
            jsonObject.put("transaction_details", transactionDetails);

            // Item Details
            JSONArray itemDetails = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("id", orderId);
            item.put("name", productName);
            item.put("price", productPrice);
            item.put("quantity", quantity);
            itemDetails.put(item);

            jsonObject.put("item_details", itemDetails);
            String jsonData = jsonObject.toString();
            new CreatePaymentLinkTask().execute(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class CreatePaymentLinkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String paymentLinkUrl = "";

            if (params.length > 0) {
                String jsonData = params[0];

                try {
                    URL url = new URL(apiSandbox + "/v1/payment-links");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Basic " + authString);
                    conn.setDoOutput(true);
                    try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        wr.writeBytes(jsonData);
                    }
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Mendapatkan URL pembayaran dari respons
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        paymentLinkUrl = jsonResponse.optString("payment_url");
                    } else {
                        // Tangani respons tidak sukses
                        paymentLinkUrl = "Failed to create payment link. Response code: " + responseCode;
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    paymentLinkUrl = "Failed to create payment link: " + e.getMessage();
                }
            }

            return paymentLinkUrl;
        }

        @Override
        protected void onPostExecute(String paymentLinkUrl) {
            if (paymentLinkUrl != null && !paymentLinkUrl.isEmpty() && !paymentLinkUrl.startsWith("Failed")) {
                openWebView(paymentLinkUrl);
            } else if (paymentLinkUrl.startsWith("Failed to create payment link. Response code: 409")) {
                showError("Order Sudah pernah dibuat");
            } else {
                showError("Gagal membuat order");
            }
        }
    }
    private void openWebView(String url) {
        Intent intent = new Intent(NiatActivity.this, PembayaranActivity.class);
        intent.putExtra("URL_TO_LOAD", url);
        startActivity(intent);
    }

    private void showError(String errorMessage) {
        Toast.makeText(NiatActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}