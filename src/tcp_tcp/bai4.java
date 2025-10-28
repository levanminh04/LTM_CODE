package tcp_tcp;

import TCP.Customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//[Mã câu hỏi (qCode): f9WbDjuC].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
//a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
//b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
//c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
//
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
//a.	Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
//      •	Tên đầy đủ của lớp: TCP.Customer
//      •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
//      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
//      •	Trường dữ liệu: private static final long serialVersionUID = 20170711L;
//b.	Tương tác với server theo kịch bản dưới đây:
//        1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
//        2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
//	3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
//	4) Đóng socket và kết thúc chương trình.
public class bai4 {


    public static String normalizeName(String name){
        String []  arr = name.split(" ");
        StringBuilder sb = new StringBuilder("");

        sb.append(arr[arr.length - 1].toUpperCase() + ",");
        for(int i = 0 ; i < arr.length - 1; i++){
            String s = String.valueOf(Character.toUpperCase(arr[i].toCharArray()[0])) + arr[i].substring(1).toLowerCase();
            sb.append(" " + s);
        }
        return sb.toString();

    }

    public static String normalizeDate(String date){
        String [] arr = date.split("-");
        return arr[1] + "/" + arr[0] + "/" + arr[2];
    }

    public static String normalizeUsername(String username){
        username = username.toLowerCase();

        String []  arr = username.split(" ");
        StringBuilder sb = new StringBuilder("");

        for(int i = 0 ; i < arr.length - 1; i++){
            String s = String.valueOf(Character.toLowerCase(arr[i].toCharArray()[0]));
            sb.append(s);
        }
        sb.append(arr[arr.length - 1].toLowerCase());

        return sb.toString();
    }

    public static void main(String[] args) {
        try(Socket socket = new Socket("203.162.10.109", 2209)){
            socket.setSoTimeout(5000);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("B22DCCN533;f9WbDjuC");
            out.flush();

            Object obj = in.readObject();
            Customer c = (Customer) obj;
            System.out.println(c.getId() + " " + c.getName() + " " + c.getCode() + " " + c.getUserName() + " " + c.getDayOfBirth());

            Customer res = new Customer(c.getId(), c.getCode(), normalizeName(c.getName()), normalizeDate(c.getDayOfBirth()), normalizeUsername(c.getName()));

            out.writeObject(res);
            out.flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
