package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class bai3 {

    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            DataService dataService = (DataService) registry.lookup("RMIDataService");

            Integer amount = (Integer) dataService.requestData("B22DCCN533", "AYuW1XV8");
            System.out.println( amount);
            int [] a = {10, 5, 2, 1};
            List<Integer> arr = new ArrayList<>();
            for(int x : a){
                while(amount >= x){
                    amount = amount - x;
                    arr.add(x);
                }
            }
            System.out.println(amount);
            String s = arr.size() + "; "  +
                    arr.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
            System.out.println(s);

            if(amount > 0){
                s = "-1";
            }

            dataService.submitData( "B22DCCN533", "AYuW1XV8",  s);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
