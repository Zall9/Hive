package com.example.hive.UploadPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hive.CategoriePackage.CategorieFragment;
import com.example.hive.R;
import com.example.hive.javaClasses.Categorie;
import com.example.hive.javaClasses.User;
import com.example.hive.utils.VolleyCallBack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class AddFragment extends Fragment {

    Bitmap bitmap;

    boolean check = true;

    Button SelectImageGallery, UploadImageServer;

    ImageView imageView;

    EditText imageName;

    ProgressDialog progressDialog ;

    String GetImageNameEditText, GetTopicNameEditText, GetCategorieNameSpinner = "orientation";

    String ImageName = "image_name" ;

    String ImagePath = "image_path" ;

    Spinner spinnerCategorie;

    EditText Topics;

    String ServerUploadPath ="http://os-vps418.infomaniak.ch:1180/l2_gr_8/img_upload_to_server.php" ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.imageView);

        imageName = (EditText)rootView.findViewById(R.id.editTextImageName);

        Topics = (EditText)rootView.findViewById(R.id.text_topic);

        SelectImageGallery = (Button)rootView.findViewById(R.id.buttonSelect);

        UploadImageServer = (Button)rootView.findViewById(R.id.buttonUpload);

        SelectImageGallery.setOnClickListener(selectListener);

        UploadImageServer.setOnClickListener(uploadListener);

        spinnerCategorie = (Spinner) rootView.findViewById(R.id.spinner_categorie);
        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetCategorieNameSpinner = String.valueOf(spinnerCategorie.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("ALED", GetCategorieNameSpinner);

        CategorieFragment.recupCategorie(getActivity(), new VolleyCallBack() {
            @Override
            public void onSuccess(ArrayList<Categorie> result) {
                String[] lCategorie = new String[result.size()];
                for(int i=0; i<result.size();i++){
                    lCategorie[i] = result.get(i).toString();
                }
                ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lCategorie);
                dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategorie.setAdapter(dataAdapterR);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Ajout du post au serveur","Veuillez patienter",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                Toast.makeText(getActivity(), string1, Toast.LENGTH_SHORT).show();

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                String messageSucces = "Votre post à bien été envoyer au serveur";
                if(!(string1.equals("Vous êtes banni de ce Topic !"))){
                    // Printing uploading success message coming from server on android app.
                    Toast.makeText(getActivity(),messageSucces,Toast.LENGTH_LONG).show();
                }

                // Setting image as transparent after done uploading.
                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {
                User user = (User)getActivity().getIntent().getExtras().getSerializable("User");

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageName, GetImageNameEditText);

                HashMapParams.put(ImagePath, ConvertImage);

                HashMapParams.put("idUser", String.valueOf(user.getIdUser()));

                HashMapParams.put("nomCategorie", GetCategorieNameSpinner);

                HashMapParams.put("nomTopic", GetTopicNameEditText);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
                /*TODO Dans les Catégorie il faut toujours avoir orientation en premier -> valeur par défaut
                TODO Sinon modifer la valeur par défaut de GetCategorieNameSpinner*/
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                Log.d("ERREUR","marche pas", e);
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    private final View.OnClickListener selectListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    intent.setType("image/*");

                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
                }
            };

    private final View.OnClickListener uploadListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetImageNameEditText = imageName.getText().toString();
                    GetTopicNameEditText = Topics.getText().toString();
                    //Spinner
                    ImageUploadToServerFunction();
                }
            };
}
