package RMI;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLOutput;

public class bai4 {

    public static void main(String[] args) {
        try{

            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);

            ObjectService objectService = (ObjectService) registry.lookup("RMIObjectService");

            Product product = (Product) objectService.requestObject("B22DCCN533", "efiFAh4c");

            Product product1 = new Product(product.id, product.code.toUpperCase(), product.importPrice, product.importPrice * 0.2 + product.importPrice);
            System.out.println(product);
            System.out.println(product1);

            objectService.submitObject("B22DCCN533", "efiFAh4c", product1);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
