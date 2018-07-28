package com.example.thien.project_32;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class my_javaclass extends AppCompatActivity {

    // These are the global variables
    EditText editName, editPassword;
    TextView result;
    Button buttonSubmit, buttonReset;

    // EditText etGitHubUser; // This will be a reference to our GitHub username input.
    //Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvRepoList, hienthi;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    String response_server="";
    String input_user;
    public static String name = "";
    String password;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        editName  = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        result = (TextView) findViewById(R.id.tvResult);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        /*-------------*/
        //this.etGitHubUser =  (EditText) findViewById(R.id.et_github_user);
        // this.btnGetRepos = (Button) findViewById(R.id.btn_get_repos);
        this.tvRepoList = (TextView) findViewById(R.id.tv_repo_list);
        this.tvRepoList.setMovementMethod(new ScrollingMovementMethod());
        this.hienthi = (TextView) findViewById(R.id.hienthi);
        requestQueue = Volley.newRequestQueue(this);
        /*-----------*/

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                password = editPassword.getText().toString();
                url = "http://starglobal.xyz/demo/iot/rest/application.php/post_login";
                //Toast.makeText(getApplicationContext(),global_variable.name,Toast.LENGTH_SHORT).show();
                request_post_for_login();
            }
        });


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clearing out all the values
                editName.setText("");
                editPassword.setText("");
                result.setText("");
                editName.requestFocus();
            }
        });
    }

    private void request_post_for_login() {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        //this.url = this.baseUrl+ username + "/repos";
        StringRequest arrReq = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //tvRepoList.setText(response.toString());
                //điều kiện login
                if((response.toString()).equals("\"OK\""))
                {
                    Intent intent = new Intent(my_javaclass.this, MainActivity.class);
                    //intent.putExtra(name, name);
                    startActivity(intent);
                }
                else
                {
                    tvRepoList.setText(response.toString());
                }
                //
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvRepoList.setText("error");
                // câu lệnh khi có lỗi
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("user",name);
                params.put("password",password);
                return params;
            }
        };
        requestQueue.add(arrReq);
    }
}

