/*
* File: RunnableWithReturn.java 
* Created: 2017-07-01
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.aspects;

public abstract class RunnableWithReturn implements Runnable {
    private Object oldValue;
    private Object returnValue;

    public Object getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public void changeReturnValue(String newValue, String type) throws IllegalArgumentException {
        switch (type) {
            case "int":
                returnValue = Integer.valueOf(newValue);
                break;
            case "double":
                returnValue = Double.valueOf(newValue);
                break;
        }
    }
}
