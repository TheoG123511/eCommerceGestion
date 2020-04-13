package com.example.ecommercegestion.outils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.example.ecommercegestion.R;
import java.io.InputStream;

/**
 * Classe technique de connexion distante HTTP qui permet de telechargé un fichier de type image
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView bmImage;
    private Context context;

    /**
     * Constructeur de la classe
     * @param bmImage un object de type ImageView pour y afficher l'image télécharger
     * @param context le context
     */
    public DownloadImageTask(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            mIcon11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.take_screen);
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}