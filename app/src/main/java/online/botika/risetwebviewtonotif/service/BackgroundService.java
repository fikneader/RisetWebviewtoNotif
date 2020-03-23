package online.botika.risetwebviewtonotif.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.RequiresApi;
import online.botika.risetwebviewtonotif.AboutActivity;
import online.botika.risetwebviewtonotif.MainActivity;

public class BackgroundService extends Service {

    MediaPlayer mp;
    Handler h = new Handler();
    int delay = 5000;
    Runnable runnable;
    String TAG = "Woke";
    String statusku;
    StatusService statusService;

    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        // do your jobs here
        startForeground(Objects.requireNonNull(intent.getAction()));

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground(String status) {
        if (status.equals("STARTSERVICE")){
                h.postDelayed( runnable = new Runnable() {
                    public void run() {
                        //do something
                        Log.i(TAG,"Service START");
//                        mp = MediaPlayer.create(getApplicationContext(), R.raw.file_example);
//                        mp.start();
//                        h.postDelayed(runnable, delay);
                    }
                }, delay);
        }
        else {
                Log.i(TAG,"Service STOP");
//                mp = MediaPlayer.create(getApplicationContext(), R.raw.file_example);
//                mp.stop();
//                mp.release();
//                h.removeCallbacks(runnable);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        statusService = SharedPreference.getInstance(this).getStatusService();
        if (statusService.getStatus().equals("STARTSERVICE")){
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, Restarter.class);
            this.sendBroadcast(broadcastIntent);
        }
        else {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("stopservice");
            broadcastIntent.setClass(this, Restarter.class);
            this.sendBroadcast(broadcastIntent);
        }
    }

//    @SuppressLint("SetJavaScriptEnabled")
//    public void loadWebview(final String auth){
////        urlku = "https://client.botmaster2.botika.online/console-botika/?auth="+auth;
//        urlku = "http://ngeartstudio.com/";
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        webView.getSettings().setSupportMultipleWindows(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl(urlku);
//
//        webView.setOnKeyListener(new View.OnKeyListener(){
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK
//                        && event.getAction() == MotionEvent.ACTION_UP
//                        && webView.canGoBack()) {
//                    webView.goBack();
//                    return true;
//                }
//                return false;
//            }
//
//        });
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (urlku.equals(Uri.parse(url).getHost())) {
//                    // This is my website, so do not override; let my WebView load the page
//                    return false;
//                }
//                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//                Toast.makeText(MainActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                startActivity(intent);
//                return true;
//            }
//        });
//
//        webView.addJavascriptInterface(new MainActivity.WebAppInterface(this), "Android");
//    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a notif from the web page */
        @JavascriptInterface
        public void showAndroidNotif(String value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotif(value);
            }
        }

//        public void showAndroidNotif() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                showNotif("Tittle");
//            }
//        }

        /** Show a notif from the web page */
        @JavascriptInterface
        public void openActivity() {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void showNotif(String value){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(value)
                .setContentTitle("subheading")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);
    }
}
