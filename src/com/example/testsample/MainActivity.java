package com.example.testsample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
    private Socket socket;
    private static final int SERVERPORT = 5000;
	private static final String SERVER_IP = "192.168.1.71";
	private static ObjectOutputStream msgOutput;
	private static ObjectInputStream msgInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//Starting the client thread so that it will send ip address and port to the server
		new Thread(new ClientThread()).start();
		
		//setupConnection();
		
        final Button upButton = (Button) findViewById(R.id.go_up);
        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendSocketData("1000");
            	upButton.setText("I am Up");
            }
        });
        
        final Button downButton = (Button) findViewById(R.id.go_down);
        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendSocketData("0010");
            	downButton.setText("I am down");
            }
        });
        
        final Button leftButton = (Button) findViewById(R.id.go_left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendSocketData("0100");
            	leftButton.setText("I am left");
            }
        });
        
        final Button rightButton = (Button) findViewById(R.id.go_right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendSocketData("0001");
            	rightButton.setText("I am right");
            }
        });
	}
	
	void sendSocketData(String msg){
		
		try {
			msgOutput.writeObject(msg.getBytes("UTF-8"));
			//msg sent waiting for reply
			byte[] temp1 = (byte[]) msgInput.readObject();
            String message1 = new String(temp1,"UTF-8");
            //add a text box
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class ClientThread implements Runnable {
		
		public void run() {
	        try {
	            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
	            socket = new Socket(serverAddr, SERVERPORT);
	            
	            Context context = getApplicationContext();
	            CharSequence text = "Connection Made";
	            int duration = Toast.LENGTH_SHORT;

	            Toast toast = Toast.makeText(context, text, duration);
	            toast.show();
	            
				msgOutput = new ObjectOutputStream(socket.getOutputStream());
				msgOutput.flush();
				msgInput = new ObjectInputStream(socket.getInputStream());
				//streams and socket created
	        } catch (UnknownHostException e1) {
	            e1.printStackTrace();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
		}
    }
	
	public void setupConnection()
	{
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);
            
            Context context = getApplicationContext();
            CharSequence text = "Connection Made";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            
			msgOutput = new ObjectOutputStream(socket.getOutputStream());
			msgOutput.flush();
			msgInput = new ObjectInputStream(socket.getInputStream());
			//streams and socket created
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
	}

}
