package neu.wangnong.kaona.bookshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private static final String urlJSON = "http://swiftcodingthai.com//neu/get_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binf Widget
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);


    }   // Main Method

    //Creade Inner Class
    private class MySynchronize extends AsyncTask<Void, Void, String> {

        private Context context;
        private String urlString;
        private boolean statusABoolean = true;
        private String truePasswordString;
        private String nameLoginString;

        public MySynchronize(Context context, String urlString) {
            this.context = context;
            this.urlString = urlString;

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlString).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }


        } //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("BookShopV1", "JSON ==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString("User"))) {
                        statusABoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                        nameLoginString = jsonObject.getString("Name");

                    }   //if

                } // for

                //checkUser
                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context,"ไม่มี User นี้",
                            " ไม่มี " + userString +" ในฐานข้อมูล");
                } else if (passwordString.equals(truePasswordString)) {
                    // password True
                    Intent intent = new Intent(context, BookActivity.class);
                    intent.putExtra("Name", nameLoginString);
                    startActivity(intent);

                    Toast.makeText(context,"Welcome User", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // password False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context,"Password False",
                            "Wrong Password");
                }   // if

            } catch (Exception e) {
                e.printStackTrace();
            }

        } //onPost

    }   //Class

    public void clickSinghIn(View view){

        //Get value form EditText
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check space
        if (userString.equals("") || passwordString.equals("") ) {
            //Have space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง","กรุณากรอกให้ครบทุกช่อง");

        } else {

            searchUserAnPassword();

        }

    } //Click SignIn

    private void searchUserAnPassword() {

        MySynchronize mySynchronize = new MySynchronize(this, urlJSON);
        mySynchronize.execute();

    }   //searchUser

    public void clickSignUpMain(View view){
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
}   // Main Class
