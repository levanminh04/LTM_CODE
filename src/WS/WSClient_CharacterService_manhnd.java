package WS;


// bài này của mạnh
// wsimport -keep -s . -p vn.medianews http://203.162.10.109:8080/JNPWS/CharacterService?wsdl

// -keep: Yêu cầu giữ lại file mã nguồn .java
// -s .: Chỉ định lưu file mã nguồn .java vào thư mục hiện tại (tức là "src")
// -p vn.medianews: Chỉ định package cho các file được tạo ra


import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WSClient_CharacterService_manhnd {

    public static void main(String[] args) {
        String studentCode = "B22DCCN533"; // Thay bằng mã của bạn
        String qCode = "k9jgOFAZ";

        try {
            // 1. Khởi tạo dịch vụ
            // Tên lớp này (CharacterServiceService) được tạo bởi wsimport
            CharacterService_Service serviceFactory = new CharacterService_Service();

            // 2. Lấy "port"
            CharacterService port = serviceFactory.getCharacterServicePort();

            // =========================================================
            // a. Triệu gọi phương thức requestStringArray
            // =========================================================
            System.out.println("Đang gọi requestStringArray...");
            // Hàm này trả về List<String> là ĐÚNG
            List<String> receivedList = port.requestStringArray(studentCode, qCode);
            System.out.println("Đã nhận danh sách: " + receivedList);

            // =========================================================
            // b. Phân loại và xử lý chuỗi
            // =========================================================
            Map<Integer, List<String>> groups = new HashMap<>();

            for (String word : receivedList) {
                int vowelCount = countVowels(word);
                groups.computeIfAbsent(vowelCount, k -> new ArrayList<>()).add(word);
            }

            List<String> resultList = new ArrayList<>();
            for (List<String> wordGroup : groups.values()) {
                Collections.sort(wordGroup);
                String groupString = String.join(", ", wordGroup);
                resultList.add(groupString);
            }

            System.out.println("Các nhóm đã xử lý: " + resultList);

            // =========================================================
            // c. Triệu gọi phương thức submitCharacterStringArray
            // =========================================================
            System.out.println("Đang gửi kết quả lên server...");

            // *** SỬA LỖI Ở ĐÂY ***
            // Chỉ cần gọi hàm. Không gán nó cho bất kỳ biến nào
            // vì kiểu trả về của nó là void.
            port.submitCharacterStringArray(studentCode, qCode, resultList);

            // Vì hàm không trả về gì, chúng ta chỉ có thể giả định
            // là nó đã thành công nếu không có Exception nào bị ném ra.
            System.out.println("Đã gửi kết quả thành công (hàm không trả về giá trị).");

            // =========================================================
            // d. Kết thúc
            // =========================================================
            System.out.println("Client hoàn thành.");


        } catch (Exception e) {
            System.err.println("Lỗi khi gọi Web Service:");
            e.printStackTrace();
        }
    }

    // Hàm phụ để đếm nguyên âm (a, e, i, o, u)
    private static int countVowels(String str) {
        int count = 0;
        String lowerCaseStr = str.toLowerCase();
        for (int i = 0; i < lowerCaseStr.length(); i++) {
            char ch = lowerCaseStr.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }
}