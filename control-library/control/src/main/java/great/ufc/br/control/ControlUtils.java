package great.ufc.br.control;

import android.os.Environment;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import great.ufc.br.control.models.TestSequences;

public final class ControlUtils {
    private static final String dummyTestSeqJson = "{\"testSequences\":[[{\"id\":0}]],\"finished\":true}";

    private static boolean messageIsPrinted;

    public static void setMessageIsPrinted(boolean value) {
        messageIsPrinted = value;
    }

    public static boolean messageIsPrinted() {
        return messageIsPrinted;
    }

    public static Object readObjectFromJsonFile(String filePath, Class clazz) {
        Object objectFromJson = null;
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder logMessageBuilder = new StringBuilder();

        try {
            // TODO - Adicionar forma desse caminho ser buscado a partir do gradle.
            File file = new File(Environment.getExternalStorageDirectory(), filePath);

            if (file == null) {
                Log.d("TESTE","Arquivo vazio");
            }

            objectFromJson = mapper.readValue(file, clazz);
        } catch (JsonParseException e) {
            logMessageBuilder.append("-------------------------------------------------------------------------------------------------------\n");
            logMessageBuilder.append("OPERATIONS FROM CCONTRol WERE ABORTED.");
            logMessageBuilder.append("\nREASON: There was a problem during the json parsing. Please check if the json file for the object of type " + clazz.getName() + " follows the correct formatting described in the documentation.");
            logMessageBuilder.append("\n\nSTACK TRACE:");
            logMessageBuilder.append(e.getMessage());
            logMessageBuilder.append("---------------------------------------------------------------------------------------------------------");

            String logMessage = logMessageBuilder.toString();
            printLog(logMessage);
            saveLogFile(logMessage);
        } catch (JsonMappingException e) {
            logMessageBuilder.append("---------------------------------------------------------------------------\n");
            logMessageBuilder.append("OPERATIONS FROM CCONTRol WERE ABORTED.");
            logMessageBuilder.append("\nREASON: There was a problem during the json parsing. Please check if the file " + filePath + " contains a serialized instance of the class " + clazz.getName());
            logMessageBuilder.append("\n\nSTACK TRACE:");
            logMessageBuilder.append(e.getMessage());
            logMessageBuilder.append("---------------------------------------------------------------------------");

            String logMessage = logMessageBuilder.toString();
            printLog(logMessage);
            saveLogFile(logMessage);
        } catch (IOException e) {
            logMessageBuilder.append("---------------------------------------------------------------------------\n");
            logMessageBuilder.append("OPERATIONS FROM CCONTRol WERE ABORTED.");
            logMessageBuilder.append("\nREASON: There was a problem during the json parsing. Please check if the file" + filePath + " exists in the intended location.");
            logMessageBuilder.append("\nREMINDER: The configuration files must be placed in the \\control folder in the SD Card of the device.");
            logMessageBuilder.append("\n\nSTACK TRACE:");
            logMessageBuilder.append(e.getMessage());
            logMessageBuilder.append("---------------------------------------------------------------------------");

            String logMessage = logMessageBuilder.toString();
            printLog(logMessage);
            saveLogFile(logMessage);
        }

        return objectFromJson;
    }

    public static void writeObjectToJsonFile(String filePath, Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File jsonFile = new File(filePath);
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
            }
            mapper.writeValue(jsonFile, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ControlSetup initializeControlSetup() {
        ControlSetup controlSetup = (ControlSetup) readObjectFromJsonFile(
                "/control/control_setup.json", ControlSetup.class);

        return controlSetup;
    }

    public static TestSequences initializeTestSequences() {
        ControlSetup controlSetup = ControlSetup.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        TestSequences testSequences = null;

        if (controlSetup != null) {
            testSequences = (TestSequences) readObjectFromJsonFile(
                    controlSetup.getTestSequencePath(),
                    TestSequences.class);

            if (testSequences != null) {
                return testSequences;
            }
        }

        try {
            testSequences = mapper.readValue(dummyTestSeqJson, TestSequences.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testSequences;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static File saveLogFile(String logMessage) {
        File logFile = null;
        if (isExternalStorageWritable()) {
            logFile = new File(String.valueOf(Environment.getExternalStorageDirectory()), logMessage);
        }

        return logFile;
    }

    public static void printLog(String msg) {
        Log.d("CONTROL", msg);
    }

}
