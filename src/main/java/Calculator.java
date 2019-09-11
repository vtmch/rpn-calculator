import java.util.*;

/**
 * This class is used for calculating math expressions,
 * like 2+2.
 */
public class Calculator {

    private static final Map<String, Integer> prior = new HashMap<>(); // карта для приоритетности знаков

    static {
        prior.put("*", 5);
        prior.put("/", 5);
        prior.put("+", 2);
        prior.put("-", 2);
        prior.put("(", 0);
        prior.put(")", 0);
    }

    /**
     * @param expression math expression, e.g. "3 + (2 + 2)"
     * @return calculated result
     */
    public String calculate(String expression) {
        String line = rpnConvert(expression); // конвертируем выражение в обратную польскую запись
        String result = evaluate(line); // вычислеяем
        return result; // возвращаем ответ
    }

    /**
     * @param expression math expression
     * @return this expression in RPN
     */
    private String rpnConvert(String expression) {
        String[] split = expression.split(" "); // создаём массив из операторов, скобок и операндов

        LinkedList<String> stack = new LinkedList<>(); // стэк
        LinkedList<String> out = new LinkedList<>(); // выходная строка

        for (String s : split) {
            switch (s) {
                case "(":
                    stack.addLast(s); // добавили в стэк открывающую скобку
                    break;
                case ")":
                    while (!stack.getLast().equals("(")) { // пока последней в стеке не окажется открывающая скобка
                        out.addLast(stack.pollLast()); // выталкиваем из стэка всё в выходную строку
                    }
                    stack.pollLast(); // удалили открывающую скобку
                    break;
                case "*":
                case "/":
                case "-":
                case "+":
                    if (!stack.isEmpty() && prior.get(s) <= prior.get(stack.getLast())) { // проверяем последний элемент в стэке на приоритетность
                        out.addLast(stack.pollLast()); // и если приоритет такой же или выше, то выталкиваем его в выходную строку
                    }
                    stack.addLast(s); // добавляем знак в стэк
                    break;
                default:
                    out.addLast(s); // добавлем элемент (число) в выходную строку
            }
        }

        int stackSize = stack.size(); // узнаём размер стэка

        for (int i = 0; i < stackSize; i++) {
            out.addLast(stack.pollLast()); // выталкиваем всё, что есть в стэке, в выходную строку
        }

        return String.join(" ", out); // возвращаем переписанное в RPN выражение
    }

    private String evaluate(String line) {
        String[] lineArray = line.split(" "); // RPN-выражение представляем в виде массива

        LinkedList<String> stack = new LinkedList<>(); // создаём стэк, с помощью которого будем считать

        for (String s : lineArray) {
            switch (s) {
                case "+":
                    long b = Long.parseLong(stack.pollLast()); // берём последнее из стэка
                    long a = Long.parseLong(stack.pollLast()); // берём предпоследнее из стэка
                    stack.addLast(Long.toString(a+b)); // складываем их и добавляем в стэк
                    break;
                case "-":
                    long c = Long.parseLong(stack.pollLast()); // берём последнее из стэка
                    long d = Long.parseLong(stack.pollLast()); // берём предпоследнее из стэка
                    stack.addLast(Long.toString(d-c)); // вычитаем
                    break;
                case "*":
                    long e = Long.parseLong(stack.pollLast()); // берём последнее из стэка
                    long f = Long.parseLong(stack.pollLast()); // берём предпоследнее из стэка
                    stack.addLast(Long.toString(f*e)); // умножаем
                    break;
                case "/":
                    long g = Long.parseLong(stack.pollLast()); // берём последнее из стэка
                    long h = Long.parseLong(stack.pollLast()); // берём предпоследнее из стэка
                    stack.addLast(Long.toString(h/g)); // делим
                    break;
                default:
                    stack.addLast(s); // добавляем число в стэк
            }
        }

        return stack.pollLast(); // возвращаем последнее (и единственное) из стэка
    }
}
