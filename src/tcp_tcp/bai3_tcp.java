package tcp_tcp;//
//[Mã câu hỏi (qCode): uKMFielo].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7"
//b.	Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
//c.	Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
//d.	Đóng kết nối và kết thúc chương trình.

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class bai3_tcp {


    static String decode(String str, int s){

        StringBuilder res = new StringBuilder("");
        int k = ((s % 26) + 26) % 26;
        for(char ch:str.toCharArray()){

            if(ch >= 'A' && ch <= 'Z'){
                char base = 'A';
                char r = (char) (base + ((ch - base - s + 26)) % 26);
                res.append(r);
            }
            else if(ch >= 'a' && ch <= 'z'){
                char base = 'a';
                char r = (char) (base + ((ch - base - s + 26)) % 26);
                res.append(r);
            }
            else{
                res.append(ch);
            }
        }
        return res.toString();
    }


    public static void main(String[] args) {

        try(Socket socket = new Socket("203.162.10.109",2207)){
            socket.setSoTimeout(5000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("B22DCCN533;uKMFielo");
            out.flush();

            String str = in.readUTF();
            int s = in.readInt();
//            System.out.println(str + "   " + s + "\n" + " " + decode(str, s));
            out.writeUTF(decode(str, s));
            out.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
