package br.com.minerva.minerva.util;

import br.com.minerva.minerva.config.Ambiente;
import org.flywaydb.core.internal.util.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

public class Utils {

    public static Long DateTimeToMoodleDate(LocalDateTime localDate){
        return localDate.toInstant(ZoneOffset.ofHours(-3)).toEpochMilli()/1000;
    }

    public static Long DateToMoodleDate(LocalDate localDate){
        return Utils.DateTimeToMoodleDate(localDate.atTime(0,0));
    }

    public static boolean ImageToBase64(String imagem64, String local, UUID nomeArquivo ){

        if (imagem64.indexOf(',') > 0){
            imagem64 =  imagem64.substring(imagem64.indexOf(',')+1).trim();
        }

        byte[] data =  Base64.getDecoder().decode(imagem64);
        try {
            Path destinationFile = Paths.get(local, nomeArquivo.toString());
            Files.write(destinationFile, data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static String fileToBase64(String local, UUID nomeArquivo ){

        Path sourceFile = Paths.get(local, nomeArquivo.toString());
        if (! sourceFile.toFile().exists()) return "";

        byte[] data ;
        try {
            data = Files.readAllBytes(sourceFile.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(data);
    }

}
