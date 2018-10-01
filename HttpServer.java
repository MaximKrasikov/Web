/**
 * Created by Admin on 29.09.2018.
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Created by yar 09.09.2009
 */
public class HttpServer {

    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();// ожидаем подключения
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();// запуск сервер-сокета
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            // настройка сокета на чтение запись
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }
// запуск сокета
        public void run() {
            try {
                readInputHeaders();
                writeResponse("<html><body><h1>Hello from KarasyBih</h1></body></html>");// ответ
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            // формирование запроса
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();// сбрасываем буфер
        }

        private void readInputHeaders() throws Throwable {// чтение из потока
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}