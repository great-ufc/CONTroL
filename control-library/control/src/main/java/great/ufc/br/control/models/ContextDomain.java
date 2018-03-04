/*
* File: ContextDomain.java 
* Created: 2017-07-01
* Authors:
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import great.ufc.br.control.ControlUtils;

public class ContextDomain {
    private Map<String, Map<String, String>> keyValues;
    private Map<String, Map<String, ArrayList<String>>> keyValuesList;

    public ContextDomain() {

    }

    public ContextDomain(Map<String, Map<String, String>> keyValues, Map<String, Map<String, ArrayList<String>>> keyValuesList) {
        this.keyValues = keyValues;
        this.keyValuesList = keyValuesList;
    }

    public Map<String, Map<String, String>> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, Map<String, String>> keyValues) {
        this.keyValues = keyValues;
    }

    public Map<String, Map<String, ArrayList<String>>> getKeyValuesList() {
        return keyValuesList;
    }

    public void setKeyValuesList(Map<String, Map<String, ArrayList<String>>> keyValuesList) {
        this.keyValuesList = keyValuesList;
    }

    public String getValueUniqueKey(String groupName, String key) {
        if (keyValues.containsKey(groupName)) {
            if (keyValues.get(groupName).containsKey(key)) {
                return keyValues.get(groupName).get(key);
            }
        }
        return null;
    }

    public ArrayList<String> getValuesFromList(String groupName, String key) {
        return keyValuesList.get(groupName).get(key);
    }

    public String getRandomValueFromList(String groupName, String key) {
        if (keyValuesList.containsKey(groupName)) {
            if (keyValuesList.get(groupName).containsKey(key)) {
                return keyValuesList.get(groupName).get(key).get(new Random().nextInt(keyValuesList.get(groupName).get(key).size()));
            }
        }
        return null;
    }

    public String getValue(String groupName, String key) {
        String value = getValueUniqueKey(groupName, key);
        String valueFromList = getRandomValueFromList(groupName, key);

        if (value != null) {
            return value;
        } else if (valueFromList != null) {
            return valueFromList;
        } else {
            return Reason.UNDEFINED_CONTEXT.toString();
        }
    }
}