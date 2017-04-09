package com.example.gor.myhomies2;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class DownloadJSONAsync extends AsyncTask<String, Integer, JSONArray> {
    public static final String JSON_URL = "http://188.166.49.215/tech/imglist.json";
    private static final String TAG = "myLogs";

    public static int countOfImages = 0;

    @Override
    protected JSONArray doInBackground(String... params) {
        if (params != null && params.length > 0) {
            //Скачиваем файл
            HttpRequest request = new HttpRequest(params[0]);
            int status = request.makeRequest("JSON");

            Log.d(TAG, "request");

            //Получаем из файла массив ссылок
            if (status == HttpRequest.REQUEST_OK) {
                JSONTokener jtk = new JSONTokener(request.getContent());
                Log.d(TAG, "Status is OK");
                try {
                    JSONArray jsonArray = (JSONArray)jtk.nextValue();
                    countOfImages = jsonArray.length();
                    Log.d(TAG, "lenghtPUT = "+countOfImages);
                    
                    return jsonArray;
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
            /*else {
                mErrorStringID = request.getErrorStringId();
            }*/
        }
        /*else {
            mErrorStringID = R.string.too_few_params;
        }*/

        Log.d(TAG, "out");


        return null;
    }

    @Override
    protected void onPostExecute(JSONArray linksArray) {
        ImageCache imageCache = ImageCache.getInstance();
        Log.d(TAG, "lenghtGET = ");
        for(int i = 0; i < linksArray.length(); i++){
            try {
                imageCache.setUrl(linksArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
