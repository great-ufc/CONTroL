package great.ufc.br.control.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum Reason implements Serializable {
    PASS,
    DIVERGENT_FEATURES,
    TIMEOUT,
    CASTING_ERROR,
    UNDEFINED_CONTEXT,
    RETURN_CONTEXT_FEATURE;

    private static List<Reason> reasonsList = Arrays.asList(
            Reason.PASS,
            Reason.DIVERGENT_FEATURES,
            Reason.TIMEOUT,
            Reason.CASTING_ERROR,
            Reason.UNDEFINED_CONTEXT,
            Reason.RETURN_CONTEXT_FEATURE
    );

    public static List<Reason> getReasons() {
        return reasonsList;
    }

    @Override
    public String toString() {
        switch (this) {
            case PASS:
                return "The actual features are equals to the expected features";
            case DIVERGENT_FEATURES:
                return "The actual features diverge from the expected features.";
            case TIMEOUT:
                return "Timeout.";
            case CASTING_ERROR:
                return "Casting Error: ";
            case UNDEFINED_CONTEXT:
                return "Undefined context group in Context Domain: ";
            case RETURN_CONTEXT_FEATURE:
                return "The context feature method is not returning a boolean value: ";
            default:
                return "Unknown";
        }
    }
}