package com.example.hive.ComptePackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hive.AdminPackage.AdminPanelGroupe;
import com.example.hive.AdminPackage.AdminPanelPost;
import com.example.hive.R;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    private Button  bgroupe, bposts;
    private String nomTopic;
    private int idUser, idCategorie;
    private ArrayList<String> listeRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        bgroupe = (Button) findViewById(R.id.admin_panel_button_groupe);
        bposts = (Button) findViewById(R.id.admin_panel_button_posts);

        nomTopic = getIntent().getExtras().getString("nomTopic");
        idUser = getIntent().getExtras().getInt("idUser");
        listeRoles = getIntent().getExtras().getStringArrayList("listeRoles");
        idCategorie = getIntent().getExtras().getInt("idCategorie");

        bgroupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, AdminPanelGroupe.class);
                intent.putExtra("nomTopic", nomTopic);
                intent.putExtra("idUser", idUser);
                intent.putExtra("listeRoles", listeRoles);
                intent.putExtra("idCategorie", idCategorie);
                startActivity(intent);
            }
        });

        bposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, AdminPanelPost.class);
                intent.putExtra("nomTopic", nomTopic);
                intent.putExtra("idCategorie", idCategorie);
                startActivity(intent);
            }
        });
    }
}