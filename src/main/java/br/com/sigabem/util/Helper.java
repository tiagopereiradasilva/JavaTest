package br.com.sigabem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Helper {
    public static float porcentagem(float total, float percentual){
        return total * (percentual/100);
    }
    public static String formatarData(LocalDate data, String formato){
        return data.format(DateTimeFormatter.ofPattern(formato));
    }
    public static LocalDate incrementarDiasEmData(LocalDate data, long quantidadeDias){
        return data.plusDays(quantidadeDias);
    }
}
