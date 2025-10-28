package tcp_tcp;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Bai1 {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2208;

        String studentCode = "B22DCCN533"; // không khoảng trắng
        String qCode = "g5lXUnud";        // không khoảng trắng

        try (Socket socket = new Socket(host, port)) {
            // timeout 5s theo đề
            socket.setSoTimeout(5000);

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // a) Gửi "studentCode;qCode" + newline
            String firstLine = studentCode + ";" + qCode;
            writer.write(firstLine); // write() chưa gửi dữ liệu đi ngay! Nó chỉ chép dữ liệu vào bộ nhớ tạm (RAM) bên trong BufferedWriter.
            writer.newLine();   // thêm newline \n vào cuối chuỗi trong buffer hả bạn
            writer.flush();  // “Xả” toàn bộ dữ liệu trong buffer, ép gửi ngay qua mạng.

            String line = reader.readLine();   // Ngồi chờ cho đến khi server gửi một dòng (kết thúc bằng newline \n hoặc \r\n) sau khi client (mình) gửi firstLine, server nhận đựợc và sẽ xử l => response lại (hoặc không reponsnse tùy TH, nhưng ở đây thì có vì đề bài đã nói rõ
            System.out.println("Server: " + line);

            String[] tokens = line.split(", ");

            List<String> edu = new ArrayList<>();
            for (String t : tokens) {
                String dom = t.trim();
                if (dom.toLowerCase().endsWith(".edu")) {
                    edu.add(dom);
                }
            }

            String result = String.join(", ", edu);
            writer.write(result);
            writer.newLine();
            writer.flush();

        } catch (SocketTimeoutException e) {
            System.err.println("Timeout 5s khi đọc từ server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
