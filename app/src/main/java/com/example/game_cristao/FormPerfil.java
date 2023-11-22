package com.example.game_cristao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormPerfil extends AppCompatActivity  implements View.OnClickListener{

    private TextView textnomeuser, textemailuser, textcpfuser, textdateuser;
    private Button bt_deslogar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_perfil);

        IniciarComponetes();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(FormPerfil.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    textnomeuser.setText(documentSnapshot.getString("nome"));
                    textemailuser.setText(email);
                    textcpfuser.setText(documentSnapshot.getString("cpf"));
                    textdateuser.setText(documentSnapshot.getString("dt"));
                }
            }
        });
    }

    private  void IniciarComponetes(){
        textnomeuser = findViewById(R.id.textnomeuser);
        textemailuser = findViewById(R.id.textemailuser);
        textcpfuser = findViewById(R.id.textcpfuser);
        textdateuser = findViewById(R.id.textdateuser);
        bt_deslogar = findViewById(R.id.bt_deslogar);
    }

    @Override
    public void onClick(View view) {

    }
}