package com.example.firebase29;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateProductActivity extends AppCompatActivity {

    Button btn_add_Prod;
    EditText nombre, talla, color;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        this.setTitle("Crear producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = findViewById(R.id.nombre);
        talla = findViewById(R.id.talla);
        color = findViewById(R.id.color);
        btn_add_Prod = findViewById(R.id.btn_add_Prod);
        mfirestore = FirebaseFirestore.getInstance();

        btn_add_Prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreProduct = nombre.getText().toString().trim();
                String tallaProduct = talla.getText().toString().trim();
                String colorProduct = color.getText().toString().trim();

                if (nombreProduct.isEmpty() && tallaProduct.isEmpty() && colorProduct.isEmpty() ) {
                    Toast.makeText(CreateProductActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    postProduct(nombreProduct, tallaProduct, colorProduct);
                }
            }
        });

    }

    private void postProduct(String nombreProduct, String tallaProduct, String colorProduct) {
        Map <String, Object>  map = new HashMap<>();
        map.put("nombre", nombreProduct);
        map.put("talla", tallaProduct);
        map.put("color", colorProduct);

        mfirestore.collection("product").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateProductActivity.this, "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateProductActivity.this, "Error al ingresar ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}