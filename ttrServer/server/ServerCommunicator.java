package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.Enumeration;

import com.google.gson.Gson;

import com.example.tyudy.ticket2rideclient.common.commands.Command;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;


/**
     * Created by colefox on 1/19/17.
     */
    public class ServerCommunicator
    {

        private static final int MAX_WAITING_CONNECTION = 10;
        private HttpServer server;
        private static int SERVER_PORT_NUMBER = 8080;
        private Gson gson = new Gson();

        public ServerCommunicator(){}

        public void run()
        {
            try
            {
                server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER), MAX_WAITING_CONNECTION);
                String probableAddress = "Could not find a likely IP choice";
                Enumeration e = NetworkInterface.getNetworkInterfaces();
                System.out.println("This machine is attached to the following IP addresses:");
                while(e.hasMoreElements())
                {
                    NetworkInterface n = (NetworkInterface) e.nextElement();
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements())
                    {
                        InetAddress i = (InetAddress) ee.nextElement();
                        String prefix = i.getHostAddress().substring(0, 3);
                        if(prefix.contains("128") || prefix.contains("192") || prefix.contains("10"))
                            probableAddress = i.getHostAddress();

                        System.out.println("\t" + i.getHostAddress());
                    }
                }

                if(probableAddress.equals("Could not find a likely IP choice"))
                {
                    System.out.println(probableAddress);
                    //System.out.println("Server not started");
                    //return;
                }
                else
                {
                    System.out.println("\n" + probableAddress + ":" + String.valueOf(SERVER_PORT_NUMBER) +
                            " <---------- Most likely choice to use from android device");
                    System.out.println("Remember to make sure this machine and the android device are on the same wifi network");
                }
                //System.out.print("Server created at " + InetAddress.getLocalHost());
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.print(e.getMessage());
                return;
            }
            server.setExecutor(null);
            server.createContext("/command", commandHandler);
            server.start();
        }


        private HttpHandler commandHandler = new HttpHandler()
        {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException
            {
                InputStream input = httpExchange.getRequestBody();
                try
                {
                    String body = streamToString(input);
                    Command command = (Command) Serializer.deserialize(body);
                    DataTransferObject response = command.execute();
                    if (response != null)
                    {
                        sendOutData(response, httpExchange);
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                    sendOutData(new DataTransferObject("","","Failed. Connection error",null), httpExchange);
                } catch (ClassCastException e)
                {
                    e.printStackTrace();
                    sendOutData(new DataTransferObject("","","Failed. No request body was found",null), httpExchange);
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                    sendOutData(new DataTransferObject("","","Failed. Class not found",null), httpExchange);

                }

            }
        };

        private void sendOutData(Object data, HttpExchange exchange)
        {
            try
            {
                if (data != null)
                {

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    OutputStreamWriter sendBack = new OutputStreamWriter(exchange.getResponseBody(), Charset.forName("UTF-8"));
                    String json = gson.toJson(data);
                    sendBack.write(json);
                    sendBack.close();
                }
                else
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                System.out.println("\nError sending data " + e.getMessage());
            }
        }

        private String streamToString(InputStream in) throws IOException
        {
            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            for(String line = br.readLine(); line != null; line = br.readLine())
                out.append(line);
            br.close();
            return out.toString();
        }

        public void stop()
        {
            Runtime.getRuntime().exit(0);
        }

    }


