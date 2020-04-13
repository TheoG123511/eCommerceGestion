package com.example.ecommercegestion.outils;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Classe technique de connexion distante HTTP qui permet d'envoyer des fichier
 */
public class AccesHttpUpload extends AsyncTask<String, Integer, Long> {
    // constante
    private static final String LINE_FEED = "\r\n";
    private static final String boundary = "WebKitFormBoundary7MA4YWxkTrZu0gW" + LINE_FEED;

    // propriétés
    public String ret; // information retournée par le serveur
    public String url; // url correspondant au information retourner par la serveur
    private String method; // permet de stocker la methode a utiliser dans la requete
    private String token; // permet de stocker le token d'autentifiquation a l'api
    private String charset;
    public AsyncResponse delegate=null; // gestion du retour asynchrone
    private JSONObject parameter = new JSONObject(); // contient tous les parametre
    private JSONObject file = new JSONObject(); // contient tous les fichier a envoyer
    private JSONObject typeFile = new JSONObject(); // contient le type de fichier correspondant au fichier a envoyer

    /**
     * Constructeur de la classe
     * @param url l'url a utilisé
     * @param method la methode a utilisé (POST, PUT)
     * @param token le token d'authentification a utilisé
     * @param charset l'encode a utilise (ex: UTF-8)
     */
    public AccesHttpUpload(String url, String method, String token, String charset) {
        super();
        this.url = url;
        this.method = method;
        this.token = token;
        this.charset = charset;
    }

    /**
     * Méthode appelée par la méthode execute
     * permet d'envoyer au serveur une liste de paramètres en POST ou PUT
     * @return null
     */
    @Override
    protected Long doInBackground(String... param) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod(this.method);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "eGestion Android Agent");
            httpConn.setRequestProperty("Authorization", "Token " + this.token);
            OutputStream outputStream = httpConn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
            // ajout des parametre a la requete
            addAllParameter(writer);
            // ajout des fichier a la requete
            addAllFile(writer, outputStream);
            // on ecrit la fin de la requete
            writer.write("--WebKitFormBoundary7MA4YWxkTrZu0gW--" + LINE_FEED);
            writer.flush();
            // on envoie la requete
            writer.close();
            Log.d("AccesHttpUpload", "ok on essai de recuperer le retour serveur");
            // Récupération du retour du serveur
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            Log.d("AccesHttp", "acesshttpUpload result");
            ret = reader.readLine();
            Log.v("AccesHttpUpload", "SERVER REPLIED:");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sur le retour du serveur, envoi l'information retournée à processFinish
     * @param result le retour du serveur
     */
    @Override
    protected void onPostExecute(Long result) {
        // ret contient l'information récupérée
        try
        {
            delegate.processFinish(this.ret, this.url, this.method);
        }catch (Exception e){
            Log.d("AcceshttpUpload", "Erreur dans la fonction onPostExecute() " + e.getMessage());
        }
    }

    /**
     * Ajoute un paramtere (de type donnes)
     * @param name Le nom du parametre
     * @param value La valeur a y associé
     */
    public void addParam(String name, String value) {
        try {
            parameter.put(name, value);
        }catch (Exception e) {
            Log.d("AcceshttpUpload", "Erreur dans la fonction addParam() " + e.getMessage());
        }
    }

    /**
     * Ajoute un parametre (de type fichier)
     * @param name le nom du parametre
     * @param value le chemin d'acces vers le fichier
     * @param typeFile le type de fichier (ex: image/jpeg)
     */
    public void addFile(String name, String value, String typeFile) {
        try {
            file.put(name, value);
            addTypeFile(name, typeFile);
        }catch (Exception e) {
            Log.d("AcceshttpUpload", "Erreur dans la fonction addFile() " + e.getMessage());
        }
    }

    /**
     * Ajoute le type de fichier correspondant a un fichier
     * @param key le nom du parametre
     * @param contentType le type de fichier (ex: image/jpeg)
     */
    private void addTypeFile(String key, String contentType) {
        try {
            typeFile.put(key, contentType);
        }catch (Exception e) {
            Log.d("AcceshttpUpload", "Erreur dans la fonction addTypeFile() " + e.getMessage());
        }
    }

    /**
     * Permet d'ecrire tous les parametre de type donnes a la requete
     * @param writer la requete
     */
    private void addAllParameter(PrintWriter writer) {
        Iterator<String> keys = parameter.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                writer.write("--" + boundary);
                writer.write("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_FEED + LINE_FEED);
                writer.write(parameter.getString(key) + LINE_FEED);
            } catch (Exception e) {
                Log.d("AcceshttpUpload", "Erreur dans la fonction addAllParameter() " + e.getMessage());
            }
        }
    }

    /**
     * Permet d'ajouter un fichier a la requete
     * @param writer la requete
     * @param outputStream 'la connection'
     */
    private void addAllFile(PrintWriter writer, OutputStream outputStream) {
        Iterator<String> keys = file.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                File uploadFile = new File(file.getString(key));
                writer.write("--" + boundary);
                writer.write("Content-Disposition: form-data; name=\"" + "image" + "\"; filename=\"" + uploadFile.getName() + "\"" + LINE_FEED);
                writer.write("Content-Type: " + typeFile.getString(key) + LINE_FEED + LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(uploadFile);
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();
                writer.flush();
            } catch (Exception e) {
                Log.d("AcceshttpUpload", "Erreur dans la fonction addAllFile() " + e.getMessage());
            }

        }
    }

}
