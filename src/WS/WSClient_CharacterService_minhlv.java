package WS;

import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;


// wsimport -keep -s . -p vn.medianews http://203.162.10.109:8080/JNPWS/CharacterService?wsdl

// -keep: Yêu cầu giữ lại file mã nguồn .java
// -s .: Chỉ định lưu file mã nguồn .java vào thư mục hiện tại (tức là "src")
// -p vn.medianews: Chỉ định package cho các file được tạo ra



public class WSClient_CharacterService_minhlv {

    public static void main(String[] args) {
        String studentCode = "B22DCCN533";
        String qCode = "k9jgOFAZ";

        try {
            CharacterService_Service serviceFactory = new CharacterService_Service();
            CharacterService port = serviceFactory.getCharacterServicePort();
            String receivedString = port.requestString(studentCode, qCode);
            System.out.println(receivedString);
            List<String> words = Arrays.stream(receivedString.split("[ _]+"))
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            if (words.isEmpty()) {
                return;
            }

            StringBuilder pascalCaseBuilder = new StringBuilder();
            for (String word : words) {
                pascalCaseBuilder.append(capitalize(word));
            }
            String pascalCase = pascalCaseBuilder.toString();

            StringBuilder camelCaseBuilder = new StringBuilder();

            camelCaseBuilder.append(words.get(0));

            for (int i = 1; i < words.size(); i++) {
                camelCaseBuilder.append(capitalize(words.get(i)));
            }
            String camelCase = camelCaseBuilder.toString();


            String snakeCase = String.join("_", words);


            List<String> resultList = new ArrayList<>();
            resultList.add(pascalCase);
            resultList.add(camelCase);
            resultList.add(snakeCase);

            port.submitCharacterStringArray(studentCode, qCode, resultList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}