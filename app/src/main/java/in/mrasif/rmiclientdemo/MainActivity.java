package in.mrasif.rmiclientdemo;

import android.os.AsyncTask;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    Button btnConnect;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        btnConnect=findViewById(R.id.btnConnect);
        tvOutput=findViewById(R.id.tvOutput);
        btnConnect.setOnClickListener(this);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConnect: {
                connect();
            } break;
        }
    }

    private void connect() {
        try {
            CallHandler callHandler = new CallHandler();
            Client client = new Client("192.168.0.4", 7777, callHandler);
            RMIInterface rmiInterface = (RMIInterface) client.getGlobal(RMIInterface.class);
            String msg = rmiInterface.helloTo(etName.getText().toString());
            tvOutput.setText(msg);
            //Toast.makeText(MainActivity.this, testService.getResponse("abc"), Toast.LENGTH_SHORT).show();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
