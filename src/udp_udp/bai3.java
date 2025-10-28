package udp_udp;

import UDP.Product;

import java.io.*;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//[Mã câu hỏi (qCode): 60jDblCY].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
//a.	Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
//b.	Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
//
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
//a.	Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
//•	Tên đầy đủ của lớp: UDP.Product
//•	Các thuộc tính: id String, code String, name String, quantity int
//•	Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
//•	Trường dữ liệu: private static final long serialVersionUID = 20161107;
//b.	Giao tiếp với server theo kịch bản
//•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//
//        •	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
//•	Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc:
//        08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
//•	Đóng socket và kết thúc chương trình.
public class bai3 {

    public static String reverseName(String name) {
        String[] words = name.trim().split("\\s+");

        // Nếu tên chỉ có 0 hoặc 1 từ, không cần làm gì
        if (words.length < 2) {
            return name;
        }

        // Hoán đổi vị trí từ đầu tiên (index 0) và từ cuối cùng
        String firstWord = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = firstWord;

        // Nối các từ lại, các từ ở giữa vẫn giữ nguyên vị trí
        return String.join(" ", words);
    }


    public static int reverseQuantity(int num){
        String n = num + "";
        return Integer.parseInt(new StringBuilder(n).reverse().toString());
    }


    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName("203.162.10.109");
            socket.setSoTimeout(5000);
            byte[] b1 = ";B22DCCN533;60jDblCY".getBytes(StandardCharsets.UTF_8);
            DatagramPacket send1 = new DatagramPacket(b1, b1.length, address, 2209);

            socket.send(send1);

            byte[] b2 = new byte[2048];
            DatagramPacket rec = new DatagramPacket(b2, b2.length);
            socket.receive(rec);

            int totalLength = rec.getLength();

            String requestId = new String(rec.getData(), 0 , 8, StandardCharsets.UTF_8);

            // Lớp ByteArrayInputStream này cho phép bạn biến một mảng byte[] (trong bộ nhớ) thành một InputStream (luồng). (kiểu nhu giả lập 1 luồng inputStream)
            ByteArrayInputStream t = new ByteArrayInputStream(rec.getData(), 8, totalLength - 8); // totalLength - 8: số lượng byte tối đa được đọc kể từ offset.

            ObjectInputStream in = new ObjectInputStream(t);
            Product p = (Product) in.readObject();
            Product res = new Product(p.getId(), p.getCode(), reverseQuantity(p.getQuantity()), reverseName(p.getName()));
            System.out.println(p.getId() + "/ " + p.getCode() + "/ " + p.getName() + "/ " + p.getQuantity());
            System.out.println(p.getId() + "/ " + p.getCode() + "/ " + reverseQuantity(p.getQuantity()) + "/ " + reverseName(p.getName()));


            // giả lập 1 luồng inputStream
            ByteArrayOutputStream b3 = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(b3);

            oos.writeObject(res);
            oos.flush();

            byte[] objByte = b3.toByteArray();
            // ByteArrayOutputStream sẽ ghi vào một vùng nhớ tạm (buffer)
            ByteArrayOutputStream b4 = new ByteArrayOutputStream();
            b4.write(requestId.getBytes(StandardCharsets.UTF_8));
            b4.write(objByte);

            byte[] finalObj = b4.toByteArray();

            DatagramPacket send = new DatagramPacket(finalObj, finalObj.length, address, 2209);
            socket.send(send);




        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
