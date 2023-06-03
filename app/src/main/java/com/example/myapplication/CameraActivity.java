package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private IntentIntegrator qrScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        qrScanner = new IntentIntegrator(this);
        qrScanner.setOrientationLocked(false); // Permitir rotación de pantalla
        qrScanner.setBeepEnabled(false); // Desactivar sonido de escaneo
        qrScanner.setDesiredBarcodeFormats(getSupportedBarcodeFormats()); // Establecer los formatos de código de barras admitidos
        qrScanner.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String barcode = result.getContents();


            redirectToPage(barcode);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    private void redirectToPage(String barcode) {
        Intent intent = new Intent();
        intent.putExtra("barcode", barcode);
        setResult(RESULT_OK, intent);

        finish();
    }

    private List<String> getSupportedBarcodeFormats() {
        // Obtener todos los formatos de códigos de barras admitidos por ZXing
        List<BarcodeFormat> formats = Arrays.asList(
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR,
                BarcodeFormat.RSS_14,
                BarcodeFormat.RSS_EXPANDED
        );

        // Obtener los nombres de los formatos de códigos de barras manualmente
        List<String> formatStrings = new ArrayList<>();
        for (BarcodeFormat format : formats) {
            formatStrings.add(format.name());
        }

        return formatStrings;
    }
}
