package com.ozeksi.demogdg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.ozeksi.demogdg.network.ResponseHandler;
import com.ozeksi.demogdg.network.ServiceProvider;
import com.ozeksi.demogdg.network.api.ApiService;
import com.ozeksi.demogdg.network.model.User;

import java.io.IOException;
import java.util.Date;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        final ApiService apiService = new ServiceProvider().provideApiService();

        final TextView textView = (TextView) this.findViewById(R.id.text);
        final Button buttonTest = (Button) this.findViewById(R.id.button_test);
        final Button buttonUser = (Button) this.findViewById(R.id.button_user);
        final Button buttonStorage = (Button) this.findViewById(R.id.button_storage);

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.test(new ResponseHandler<ResponseBody>() {
                    @Override
                    public void success(ResponseBody data) {
                        try {
                            textView.setText(data.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail(String code, String description) {
                        textView.setText(description);
                    }
                });
            }
        });
        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.getUser(new ResponseHandler<User>() {
                    @Override
                    public void success(User data) {
                        textView.setText(data.getUsername());
                    }

                    @Override
                    public void fail(String code, String description) {
                        textView.setText(description);
                    }
                });
            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        buttonStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String value = "gdg-ankara";
                final String key = "key_" + (new Date().getTime());
                textView.setText(key + ": " + value);
                editor.putString(key, value);
                editor.commit();
            }
        });


    }
}
