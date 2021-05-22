package com.example.hive.PostPackage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.ImageAdapter;
import com.example.hive.R;
import com.example.hive.javaClasses.Commentaire;
import com.example.hive.javaClasses.User;
import com.example.hive.utils.PermissionCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private String nomAuteur, prenomAuteur, nomPost, nomCategorie, nomTopic, UrlImage, nbLike, role;
    private int idUser;
    private TextView auteurTV, PostTV, CategorieTV, TopicTV, nbLikeTV, RoleTV;
    private ImageLoader imageLoader;
    private boolean ouvrirCommentaire;
    private NetworkImageView VollyImageView ;
    private Button buttonCommenter;
    private ImageView downloadIM;
    private String commentaire = "";
    private RecyclerView recList;
    private StringRequest RequestOfJSonArray;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/recup_liste_commentaire.php";
    private RecyclerViewCommentaire rvc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        recList = (RecyclerView) findViewById(R.id.recyclerview_commentaire);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        nomAuteur = getIntent().getExtras().getString("nomAuteur");
        prenomAuteur = getIntent().getExtras().getString("prenomAuteur");
        nomPost = getIntent().getExtras().getString("nomPost");
        nomCategorie = getIntent().getExtras().getString("nomCategorie");
        nomTopic = getIntent().getExtras().getString("nomTopic");
        UrlImage = getIntent().getExtras().getString("urlImage");
        nbLike = getIntent().getExtras().getString("nbLike");
        idUser = getIntent().getExtras().getInt("idUser");
        role = getIntent().getExtras().getString("Role");
        ouvrirCommentaire = getIntent().getExtras().getBoolean("ouvrirCommentaire");
        auteurTV = (TextView) findViewById(R.id.post_activity_auteur);

        PostTV = (TextView) findViewById(R.id.post_activity_nom_post);

        CategorieTV = (TextView) findViewById(R.id.post_activity_categorie);

        TopicTV = (TextView) findViewById(R.id.post_activity_topic);

        nbLikeTV = (TextView) findViewById(R.id.text_nb_like_post);

        VollyImageView = (NetworkImageView) findViewById(R.id.VolleyImageViewPost);

        buttonCommenter = (Button) findViewById(R.id.button_commenter_post);

        RoleTV = (TextView) findViewById(R.id.post_activity_role);

        downloadIM = (ImageView) findViewById(R.id.download_button_post);

        if(ouvrirCommentaire){
            commenter();
        }

        buttonCommenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commenter();
            }
        }
        );

                imageLoader = ImageAdapter.getInstance(this).getImageLoader();

        imageLoader.get(UrlImage,
                ImageLoader.getImageListener(
                        VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        VollyImageView.setImageUrl(UrlImage, imageLoader);
        VollyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, FullScreenImageActivity.class);
                intent.putExtra("UrlImage", UrlImage);

                startActivity(intent);
            }
        });

        downloadIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PermissionCheck.readAndWriteExternalStorage(PostActivity.this)){

                Uri uri = Uri.parse(UrlImage);
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                request.setTitle("Fichier téléchargé");
                request.setDescription("Ca télécharge");

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nomPost+".png");
                request.setMimeType("*/*");
                Toast.makeText(PostActivity.this, "Votre image se télécharge", Toast.LENGTH_SHORT).show();
                downloadManager.enqueue(request);

                }

            }
        });

        String full_name = nomAuteur+" "+prenomAuteur;
        auteurTV.setText(full_name);
        PostTV.setText(nomPost);
        CategorieTV.setText(nomCategorie);
        TopicTV.setText(nomTopic);
        nbLikeTV.setText(nbLike);
        RoleTV.setText(role);
        JSON_HTTP_CALL();
    }


    private void ajouterCommentaire(String commentaire){
        String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/ajouter_commentaire.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Failure")){
                            Toast.makeText(PostActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(PostActivity.this, "Commentaire ajouté !", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("commentaire", commentaire);
                params.put("idUser", String.valueOf(idUser));
                params.put("nomPost", nomPost);
                params.put("nomTopic", nomTopic);
                params.put("nomCategorie", nomCategorie);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PostActivity.this);
        requestQueue.add(stringRequest);
    }


    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        Log.d("RES", response);
                        try {
                            jsonArray = new JSONArray(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ParseJSonResponse(jsonArray);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                User user = (User)getIntent().getExtras().getSerializable("User");
                //nomCategorie = getIntent().getExtras().getString("nomCatagorie");

                Map<String, String> params = new HashMap<String, String>();
                params.put("nomPost", nomPost);
                params.put("nomTopic", nomTopic);
                params.put("nomCategorie", nomCategorie);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){
        ArrayList<Commentaire> mesCommentaires = new ArrayList<Commentaire>();
        for(int i = 0; i<array.length(); i++) {

            Commentaire getCommentaire = new Commentaire();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                getCommentaire.setNomAuteur(json.getString("nomAuteur"));
                getCommentaire.setPrenomAuteur(json.getString("prenomAuteur"));
                getCommentaire.setContenu(json.getString("commentaire"));

                // Adding image title name in array to display on RecyclerView click event.

            } catch (JSONException e) {

                e.printStackTrace();
            }
            mesCommentaires.add(getCommentaire);
        }

        rvc = new RecyclerViewCommentaire(mesCommentaires, this);

        recList.setAdapter(rvc);


    }

    public static class ObjectWrapperForBinder extends Binder {

        private final Object mData;

        public ObjectWrapperForBinder(Object data) {
            mData = data;
        }

        public Object getData() {
            return mData;
        }
    }

    public void commenter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
        builder.setTitle("Commentaire");
        builder.setIcon(R.drawable.ic_baseline_comment_24);

        // Set up the input
        final EditText input = new EditText(PostActivity.this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commentaire = input.getText().toString();
                ajouterCommentaire(commentaire);

                Intent intent = getIntent();

                intent.putExtra("nomAuteur", nomAuteur);
                intent.putExtra("prenomAuteur", prenomAuteur);
                intent.putExtra("nomPost", nomPost);
                intent.putExtra("nomCategorie", nomCategorie);
                intent.putExtra("nomTopic", nomTopic);
                intent.putExtra("urlImage", UrlImage);
                intent.putExtra("nbLike", nbLike);
                intent.putExtra("idUser", idUser);
                intent.putExtra("ouvrirCommentaire", false);
                finish();
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}