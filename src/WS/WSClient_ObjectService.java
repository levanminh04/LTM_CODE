package WS;

import webservice.ObjectService.ObjectService;
import webservice.ObjectService.ObjectService_Service;
import webservice.ObjectService.Student;

import java.util.ArrayList;
import java.util.List;

public class WSClient_ObjectService {

    public static void main(String[] args) {

        ObjectService_Service factory = new ObjectService_Service();
        ObjectService objectService = factory.getObjectServicePort();


        List<webservice.ObjectService.Student> students = (List<webservice.ObjectService.Student>) objectService.requestListStudent("B22DCCN533", "uMFrkRzC");

        List<webservice.ObjectService.Student> students1 = new ArrayList<>();
        for(Student x:students){
            if((x.getScore() >= 8.0) || (x.getScore() <= 5.0)){
                students1.add(x);
            }
        }
        objectService.submitListStudent("B22DCCN533", "uMFrkRzC", students1);
    }
}
