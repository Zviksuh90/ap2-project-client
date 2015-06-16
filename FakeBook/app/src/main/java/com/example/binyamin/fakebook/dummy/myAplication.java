package com.example.binyamin.fakebook.dummy;

import android.app.Application;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by binyamin on 15-Jun-15.
 */
public class myAplication extends Application{
    /*
    @Override
    public void onCreate() {
        super.onCreate();
        new DownloadFilesTask().execute();
    }
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            showDialog("Downloaded " + result + " bytes");
        }
    }
    */
}
