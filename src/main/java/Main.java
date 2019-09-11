import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * The main class where program begins.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator(); // создаём калькулятор

        System.out.println("Enter your expression. Enter \"exit\" to finish the program."); // вывод описания его работы

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); // поток ввода
        String expression; // начальное значение выражения

        while (true) {

            expression = bufferedReader.readLine(); // считываем выражения

            if (expression.equalsIgnoreCase("exit")) break; // условие выхода

            String result = calculator.calculate(expression); // результат вычисления

            System.out.println(result); // вывод результата
        }

        bufferedReader.close();
    }
}
