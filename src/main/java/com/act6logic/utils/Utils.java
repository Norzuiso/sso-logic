package com.act6logic.utils;

import com.act6logic.obj.ProcessTime;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {

       public static final int MIN_PROCESS_TIME = 5;
    //   public static final int MAX_PROCESS_TIME = 19;

    //public static final int MIN_PROCESS_TIME = 1;
    public static final int MAX_PROCESS_TIME = 5;

    public String getResult(String operacion) {
        String result;
        String[] elementos = operacion.split(" ");

        if (elementos.length != 3) {
            return "Operación inválida";
        }

        try {
            int num1 = Integer.parseInt(elementos[0]);
            String operador = elementos[1];
            int num2 = Integer.parseInt(elementos[2]);
            result = switch (operador) {
                case "+" -> String.valueOf(num1 + num2);
                case "-" -> String.valueOf(num1 - num2);
                case "*" -> String.valueOf(num1 * num2);
                case "/" -> {
                    float div = (float) num1/num2;
                    yield String.valueOf(div);
                }
                case "%" -> String.valueOf(num1 % num2);
                default -> throw new IllegalArgumentException("Operador desconocido: " + operador);
            };

        } catch (NumberFormatException e) {
            result = "Error al evaluar la operación: los operandos no son números válidos.";
        } catch (ArithmeticException e) {

            result = "Error al evaluar la operación: división por cero.";
        } catch (IllegalArgumentException e) {

            result = "Error al evaluar la operación: " + e.getMessage();
        }

        return result;
    }

    public String generateOperation() {
        String[] operands = new String[]{"+", "-", "*", "/", "%"};

        Integer randomIntA = getRandomInteger(1, 99);
        Integer randomOperandID = getRandomInteger(0, operands.length - 1);
        String randomOperandStr = operands[randomOperandID];
        Integer randomIntB = getRandomInteger(1, 99);

        return randomIntA + " " + randomOperandStr + " " + randomIntB;
    }

    public ProcessTime generateEstimatedTime() {
        Integer seconds = getRandomInteger(MIN_PROCESS_TIME, MAX_PROCESS_TIME);
        return new ProcessTime(0, seconds);
    }

    public Integer getRandomInteger(Integer min, Integer max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
