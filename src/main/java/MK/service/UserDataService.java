package MK.service;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import java.util.Scanner;

public class UserDataService {

    private Scanner sc = new Scanner(System.in);


    public String getString() {
        return sc.nextLine();

    }

    public int getInt(String message) {
        System.out.println(message);

        String input = sc.nextLine();
        if (!input.matches("\\d+")) {
            throw new MyException(ExceptionCode.USER_DATA,"INT VALUE IS NOT CORRECT: " + input);
        }

        return Integer.parseInt(input);
    }


    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }


}
