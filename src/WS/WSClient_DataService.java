package WS;

import webservice.DataService.DataService;
import webservice.DataService.DataService_Service;

import java.util.ArrayList;
import java.util.List;

public class WSClient_DataService {
    public static void main(String[] args) {

        DataService_Service factory = new DataService_Service();
        DataService dataService = factory.getDataServicePort();

        List<Integer> nums = dataService.getData("B22DCCN533", "gTlLQWtx");
        System.out.println(nums.toString());
        List<String> strs = new ArrayList<>();
        for(Integer x : nums){
            strs.add(Integer.toOctalString(x).toUpperCase() + "|" + Integer.toHexString(x).toUpperCase());
        }

        dataService.submitDataStringArray("B22DCCN533", "gTlLQWtx", strs);
    }
}
