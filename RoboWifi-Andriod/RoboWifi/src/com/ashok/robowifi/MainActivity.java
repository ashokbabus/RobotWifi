package com.ashok.robowifi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	EditText textOut;
	EditText textIP;
	EditText textPort;

	TextView textMsg;

	Socket socket = null;
	DataInputStream dataInputStream = null;

	DataOutputStream dataOutputStream = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textIP = (EditText)findViewById(R.id.text_ipadd);
		Button buttonConnect = (Button)findViewById(R.id.connect);
		Button buttonSend = (Button)findViewById(R.id.send);
		textOut = (EditText)findViewById(R.id.text_motor);
		textPort = (EditText)findViewById(R.id.text_port);
		textMsg = (TextView)findViewById(R.id.text_msg);
		buttonSend.setOnClickListener(buttonSendOnClickListener);
		buttonConnect.setOnClickListener(buttonConnectOnClickListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	Button.OnClickListener buttonSendOnClickListener
	= new Button.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				Log.d("Ashok",textOut.getText().toString());
				dataOutputStream.writeBytes(textOut.getText().toString());
				textMsg.setText("Sent");
				//textIn.setText(dataInputStream.readUTF());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				
			}
		}
	};

	Button.OnClickListener buttonConnectOnClickListener
	= new Button.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Socket socket = null;
			DataOutputStream dataOutputStream = null;
			DataInputStream dataInputStream = null;
			
			try {
				new RobotWifiConnect().execute(0);			
				//dataOutputStream.writeUTF(textOut.getText().toString());
				//textIn.setText(dataInputStream.readUTF());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if (socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (dataOutputStream != null){
					try {
						dataOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (dataInputStream != null){
					try {
						dataInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	};
	
	class RobotWifiConnect extends AsyncTask<Integer, Integer, Integer> {

	    protected Integer doInBackground(Integer... dummy) {
	    	try {	
	        	Log.d("ashok",textIP.getText().toString());
				Log.d("ashok","" + Integer.valueOf(textPort.getText().toString()));
				socket = new Socket(InetAddress.getByName(textIP.getText().toString()), Integer.valueOf(textPort.getText().toString()));
				//socket = new Socket("192.168.43.88",6000);
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataInputStream = new DataInputStream(socket.getInputStream());
				textMsg.setText("Connected");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return 0;
	    }

	    protected void onPostExecute(Integer result) {
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }
	 }

	 


}
