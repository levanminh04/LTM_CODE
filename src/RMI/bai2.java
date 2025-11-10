package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.stream.Collectors;

public class bai2 {

    public static void main(String[] args) {

        try{
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);

            CharacterService characterService = (CharacterService) registry.lookup("RMICharacterService");

            String receivedString = characterService.requestCharacter("B22DCCN533", "UYj1hRj8");
            System.out.println(receivedString);
            char [] a = receivedString.toCharArray();

            Set<Character> se = new LinkedHashSet<>();
            Map<Character, Integer> mp = new HashMap<>();

            for(char x : a){

                {
                    se.add(x);
                    mp.put(x, mp.getOrDefault(x, 0) + 1);
                }
            }
            for(char x: se){
                System.out.print(x + " ");
            }
            StringBuilder sb = new StringBuilder();



            for(char x : se){
                sb.append(x + "" + mp.get(x));
            }

            System.out.println(sb.toString());

            characterService.submitCharacter("B22DCCN533", "UYj1hRj8", sb.toString() );

        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
