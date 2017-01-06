package net;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * author: qu
 * date: 15-8-14
 * introduce:
 */
public class SocketClient {
    private final int serverPort = 9769;
    private final String serverIP = "127.0.0.1";
    public SocketClient(){

    }

    public void socketSend(byte [] data){
        Socket socket = null;
        try {
            String msg = null;
            // 产生socket对象，制定服务器地址和服务器监听的端口号
            socket = new Socket(serverIP, serverPort);

            OutputStream outputStream = socket.getOutputStream();
            //while (!(msg = reader.readLine()).equalsIgnoreCase("exit"))
            {
                outputStream.write(data);
                Log.d("socket_client", "sending socket");
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            try {
                if(socket != null) {
                    socket.close();
                }
                Log.d("socket_client", "has send socket");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
