package udp_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

//
//[Mã câu hỏi (qCode): GO0GcbA9].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với:
//        --- requestId là chuỗi ngẫu nhiên duy nhất.
//--- string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
//
//c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ chuỗi đã được xử lý: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
//
//d. Đóng socket và kết thúc chương trình.
public class bai1 {

    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2207;

    public static void main(String[] args) {
        String studentCode = "B22DCCN533";
        String qCode = "GO0GcbA9";

        try (DatagramSocket clientSocket = new DatagramSocket()) {

            // Lấy địa chỉ IP của server
            InetAddress serverAddress = InetAddress.getByName(HOST);
            clientSocket.setSoTimeout(5000);

            String sendMsg1 = ";" + studentCode + ";" + qCode;
            byte[] sendData1 = sendMsg1.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, serverAddress, PORT);

            // Gửi đi
            clientSocket.send(sendPacket1);

            // Tạo một "xô rỗng" để hứng dữ liệu nhận về
            byte[] receiveBuffer = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // Chờ nhận dữ liệu (lệnh này sẽ "treo" lại)
            clientSocket.receive(receivePacket);

            // Lấy dữ liệu từ phong bì và chuyển thành String
            String receivedMsg = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

            String [] str = receivedMsg.split(";");
            TreeMap<Integer,String> mp = new TreeMap<>();
            String requestId = str[0];
            String[] pairs = str[1].split(",");

            for(String x : pairs){
                String [] tmp = x.split(":");
                mp.put(Integer.parseInt(tmp[1]), tmp[0]);
            }


            for(Map.Entry<Integer, String> x : mp.entrySet()){
                System.out.println(x.getValue() + " + " + x.getKey());
            }

            String res = String.join(",", mp.values());
            res = requestId + ";" + res;

            byte[] b = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket send2 = new DatagramPacket(b,b.length, serverAddress, PORT);

            clientSocket.send(send2);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
