package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class bai1 {

    public static void main(String[] args) {

        String studentCode = "B22DCCN533";
        String qCode = "BvVroXRf";

        String RMI_HOST = "203.162.10.109";
        int RMI_PORT = 1099; // Cổng RMI Registry mặc định
        String RMI_NAME = "RMIByteService";

        try {
            // Lấy RMI Registry
            Registry registry = LocateRegistry.getRegistry(RMI_HOST, RMI_PORT);

            // Tra cứu đối tượng từ xa
            ByteService byteService = (ByteService) registry.lookup(RMI_NAME);

            byte[] receivedData = byteService.requestData(studentCode, qCode);

            System.out.println("receivedData: (dạng chuỗi) \"" + new String(receivedData) + "\"");
            System.out.println("receivedData (dạng mảng): " + Arrays.toString(receivedData));
            int shift = receivedData.length; // kích thước mảng byte

            byte[] encryptedData = new byte[shift];

            for (int i = 0; i < receivedData.length; i++) {
                // Chỉ cần thực hiện phép cộng byte.
                // Java sẽ tự động xử lý tràn số (overflow) (bù 2)
                // ví dụ: (byte) (127 + 5) = -124
                encryptedData[i] = (byte) (receivedData[i] + shift);
            }

            byteService.submitData(studentCode, qCode, encryptedData);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}