package de.tallerik.bas_vertretung;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncDownload extends AsyncTask<Object, Object, Object> {
    @Override
    protected Object doInBackground(Object[] objects) {
        String out = "";
        try {
            URL url = new URL((String)objects[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                out = readStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }


    public static String readStream(InputStream in ) throws Exception {
        byte[] contents = new byte[1024];
        String strFileContents = "";
        int bytesRead = 0;
        while((bytesRead = in.read(contents)) != -1) {
            strFileContents += new String(contents, 0, bytesRead);
        }

        return strFileContents;
    }
}
