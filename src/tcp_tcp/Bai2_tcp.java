package tcp_tcp;//import java.io.*;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//public class tcp.Bai2_tcp {
//    private static final String HOST = "203.162.10.109";
//    private static final int PORT = 2206;
//
//    public static void main(String[] args) {
//        String studenID = "B22DCCN533";
//        String qCode = "YqMu9bek";
//        Socket socket = null;
//
//        try {
//            socket = new Socket(HOST, PORT);
//            socket.setSoTimeout(5000);
//            InputStream in = socket.getInputStream();
//            OutputStream out = socket.getOutputStream();
//
//            String firstLine = studenID + ';' + qCode ;
//            out.write(firstLine.getBytes(StandardCharsets.UTF_8));
//            out.flush();
//
//            byte[] buffer = new byte[2048];
//            int bytesRead = in.read(buffer);
//
//            if (bytesRead == -1) {
//                throw new IOException("Server đã đóng kết nối, không nhận được dữ liệu.");
//            }
//
//            String res = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();

//            // Tính tổng
//            List<String> nums = List.of(res.split("\\|"));
//            int sum = nums.stream()
//                    .map(String::trim)
//                    .filter(s -> !s.isEmpty())
//                    .mapToInt(Integer::parseInt)
//                    .sum();
//
//            String result = sum + "";
//            out.write(result.getBytes(StandardCharsets.UTF_8));
//            out.flush();
//
//            socket.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (socket != null && !socket.isClosed()) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Bai2_tcp {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2206;

    public static void main(String[] args) {
        String studenID = "B22DCCN533";
        String qCode = "YqMu9bek";
        Socket socket = null;

        try {
            socket = new Socket(HOST, PORT);
            socket.setSoTimeout(5000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String firstLine = studenID + ';' + qCode ;
            out.write(firstLine.getBytes(StandardCharsets.UTF_8));
            out.flush();

            byte[] buffer = new byte[2048];
            int bytesRead = in.read(buffer); // buffer sẽ lưu dữ liệu chính, bytesRead lưu  độ dài của dữ liệu

            if (bytesRead == -1) {
                throw new IOException("Server đã đóng kết nối, không nhận được dữ liệu.");
            }

            String res = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();

            // Tính tổng
            List<String> nums = List.of(res.split("\\|"));
            int sum = nums.stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .sum();

            String result = sum + "";
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}