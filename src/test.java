import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class test {

    public static void main(String[] args) {
        try(Socket socket = new Socket("203.162.10.109", 2206)){
            socket.setSoTimeout(5000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            out.write("B22DCCN533;YqMu9bek".getBytes(StandardCharsets.UTF_8));
            out.flush();

            byte [] buffer = new byte[2024];
            int size = in.read(buffer);

            String res = new String(buffer, 0,size,StandardCharsets.UTF_8);

            int sum = List.of(res.split("\\|")).stream()
                    .mapToInt(Integer::parseInt)
                    .sum();

            out.write((sum + "").getBytes());
            out.flush();

            System.out.println(res);

        }
        catch (Exception e){

        }
    }

}
