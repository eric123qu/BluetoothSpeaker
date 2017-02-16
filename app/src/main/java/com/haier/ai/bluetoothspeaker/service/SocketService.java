package com.haier.ai.bluetoothspeaker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.haier.ai.bluetoothspeaker.manager.ProtocolManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class
SocketService extends Service {
    // 声明一个服务器端socket
    ServerSocket serverSocket = null;
    private ExecutorService pool;

    public SocketService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //socket server thread
        Log.i("TCPReceive","onStartCommand");
        if(pool == null){
            pool = Executors.newCachedThreadPool();
        }
        Thread socketServerThread = new Thread(new TCPSocketServer());
        socketServerThread.start();

        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* Log.d("server_service", "onDestroy");
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public class TCPSocketServer implements Runnable {

        //public static final String SERVERIP = "127.0.0.1";
        public static final int SERVERPORT = 9770;

        public void run() {
            try {
                Log.i("socketService", "thread start");

                // 声明一个socket来接受客户端连接
                Socket socket = null;
                while(true) {
                    try {
                        Log.i("socketservice", "running");
                        // 定义服务器端socket并指定监听端口
                        //serverSocket = new ServerSocket(SERVERPORT);
                        if(serverSocket == null)
                        {
                            serverSocket = new ServerSocket();
                            serverSocket.setReuseAddress(true);
                            serverSocket.bind(new InetSocketAddress(SERVERPORT));
                            Log.d("socketserver", "new ServerSocket");
                        }
                        // 调用阻塞式方法来获取客户端连接的socket
                        if(serverSocket != null) {
                            socket = serverSocket.accept();
                            //client连接，读取data
                            if (socket != null) {
                                AcceptClientThread clientThread = new AcceptClientThread(socket);
                                pool.execute(clientThread);
                                //clientThread.start();
                            }
                        }

                        Thread.sleep(100);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
//                        try {
//                            //socket.close();
//                            if(serverSocket != null) {
//                                serverSocket.close();
//                            }
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("S: Error");
                e.printStackTrace();
            }

        }
    }

    public class AcceptClientThread extends Thread {
        private Socket mSocket;

        public AcceptClientThread(Socket socket){
            mSocket = socket;
        }

        @Override
        public void run() {
            super.run();
            int temp;
            // 获取客户端socket的输入流
            InputStream inputStream = null;
            try {
                inputStream = mSocket.getInputStream();
                // 读取客户端socket的输入流的内容并输出
                byte[] buffer = new byte[512];
                while ((temp = inputStream.read(buffer)) != -1) {
                    //Log.e("socketserver", "recv data:" + temp);
                    //收到保温解析
//                    Protocol protocol = new Protocol(SocketService.this);
//                    protocol.parseProtocol(buffer, temp);
                    ProtocolManager.getInstance().parseProtocol(buffer, temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(mSocket != null) {
                        mSocket.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
