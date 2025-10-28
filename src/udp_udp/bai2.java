package udp_udp;

//[Mã câu hỏi (qCode): BArutyEj].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN000;XbYdNZ3”.
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;b1,b2”, trong đó:
//requestId là chuỗi ngẫu nhiên duy nhất.
//b1 là số nhị phân thứ nhất
//b2 là số nhị phân thứ hai.
//Ví dụ: requestId;0100011111001101,1101000111110101
//c. Thực hiện tính tổng hai số nhị phân nhận được, chuyển về dạng thập phân và gửi lên server theo định dạng “requestId;sum”
//Kết quả: requestId;72130
//d. Đóng socket và kết thúc chương trình.

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class bai2 {

    public static String HOST = "203.162.10.109";
    public static int PORT = 2208;

    public static void main(String[] args) {


        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(HOST);
            socket.setSoTimeout(5000);

            String msv = ";B22DCCN533;BArutyEj";
            byte[] b = msv.getBytes(StandardCharsets.UTF_8);

            DatagramPacket send1 = new DatagramPacket(b, b.length, address, PORT);

            socket.send(send1);

            byte[] res_byte = new byte[2048];

            DatagramPacket resultReceived = new DatagramPacket(res_byte, res_byte.length);

            socket.receive(resultReceived);
            String res_string = new String(resultReceived.getData(), 0, resultReceived.getLength(), StandardCharsets.UTF_8);

            System.out.println(res_string);

            String [] arr1 = res_string.split(";");
            String requestId = arr1[0];

            String bin1 = arr1[1].split(",")[0];
            String bin2 = arr1[1].split(",")[1];

            System.out.println(bin1 + " " + bin2);

            int num1 = Integer.parseInt(bin1, 2);
            int num2 = Integer.parseInt(bin2,2);

            String res = requestId + ";" + (num1 + num2) + "";
            byte[] by = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket send2 = new  DatagramPacket(by, by.length, address, PORT);

            socket.send(send2);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
