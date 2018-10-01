package com.javarush.task.task40.task4006;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/* 
Отправка GET-запроса через сокет
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://javarush.ru/social.html"));
    }

    /*должен явно создавать и использовать сокетное соединение Socket с сервером.*/

    public static void getSite(URL url) {
        try {
            /*Адрес сервера и параметры для GET-запроса получи из параметра url.*/
            Socket socket= new Socket(url.getHost(), url.getDefaultPort());

            //получаем OutputStream, чтобы писать в него данные запроса
            OutputStream outputStream = socket.getOutputStream();// запись

            String request = "GET "+url.getFile()+" HTTP/1.1\r\n";
            request +="Host: "+url.getHost()+"\r\n\r\n";

            outputStream.write(request.getBytes());
            outputStream.flush();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}