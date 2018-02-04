package in.mrasif.rmiclientdemo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    Button btnConnect, btnDisconnect, btnSend;
    TextView tvOutput;
    Client client;
    RMIInterface rmiInterface;
    boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        btnConnect=findViewById(R.id.btnConnect);
        btnDisconnect=findViewById(R.id.btnDisconnect);
        btnSend=findViewById(R.id.btnSend);
        tvOutput=findViewById(R.id.tvOutput);
        btnConnect.setOnClickListener(this);
        btnDisconnect.setOnClickListener(this);
        btnSend.setOnClickListener(this);

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
            case R.id.btnDisconnect: {
                disconnect();
            } break;
            case R.id.btnSend: {
                send();
            } break;
        }
    }


    private void connect() {
        try{
            CallHandler callHandler = new CallHandler();
            client = new Client("192.168.0.4", 7777, callHandler);
            rmiInterface = (RMIInterface) client.getGlobal(RMIInterface.class);
            isConnected=true;
            Toast.makeText(this, "RMI connected.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "RMI connection failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void disconnect() {
        try {
            client.close();
            isConnected=false;
            Toast.makeText(this, "RMI disconnected.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        if(isConnected) {
            try {
                String msg = rmiInterface.helloTo(etName.getText().toString());
                tvOutput.setText(msg);
                //Toast.makeText(MainActivity.this, testService.getResponse("abc"), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "Please connect RMI first.", Toast.LENGTH_SHORT).show();
        }
    }

}
