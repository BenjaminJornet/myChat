package comeb.come.identity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends Activity {

    private AsyncTask<Void, Integer, Void> async;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        async.cancel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = MainActivity.this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = (TextView) findViewById(R.id.text);
        String userName=getUsername(context);
        t.setText(userName);

       async = new MyAsync(MainActivity.this,userName,(ProgressBar)findViewById(R.id.progress_bar)).execute();
    }


    public String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

}
class MyAsync extends AsyncTask<Void, Integer, Void>
{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String mURL = "http://come.burguburu.free.fr/projet/json/log.php";
    private  Context context;
    private String message="";
    private ProgressBar mProgressBar;

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    public void setmProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    private void setContext(Context c){
        context = c;
    }

    private MyAsync(){

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public MyAsync(Context context,String message,ProgressBar progress) {
        super();
        setContext(context);
        setMessage(message);
        setmProgressBar(progress);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar
        //mProgressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        try {
            post(mURL, getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mProgressBar.setVisibility(View.GONE);

        Toast.makeText(context, "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
    }

    private String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}