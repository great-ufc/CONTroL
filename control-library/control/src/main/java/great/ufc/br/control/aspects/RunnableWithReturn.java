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
