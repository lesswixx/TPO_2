

import math.FunctionCust;
import math.Logarithm;
import math.Trigonometry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import CSV.CSVEntry;
import CSV.CSVWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MathTest {
    private static double precision;
    private static int iterationCnt;
    private static double step;
    private static double initialValue;

    @BeforeAll
    static void loadConfig() {
        Configuration.load();
        precision = Configuration.getPrecision();
        iterationCnt = Configuration.getIterationCnt();
        step = Configuration.getStep();
        initialValue = Configuration.getInitialValue();
    }

    private List<CSVEntry> doTest(double step,
                                  Function<Double, Double> approximatingFunction,
                                  Function<Double, Double> preciseFunction) {
        double x = initialValue;

        double approxValue;
        double preciseValue;

        List<CSVEntry> csvEntries = new ArrayList<>();

        for (int i = 0; i < iterationCnt; i++, x += step) {
            if (Trigonometry.Sine.preciseValue(x) == 0) {
                continue;
            }
            approxValue = approximatingFunction.apply(x);
            preciseValue = preciseFunction.apply(x);
            csvEntries.add(new CSVEntry(x, approxValue, preciseValue));
            Assertions.assertEquals(approxValue, preciseValue, precision);
        }

        return csvEntries;
    }

    @Test
    void logarithmTest() {
        var csvEntries = doTest(step, Logarithm::preciseValue, Logarithm::approximatedValue);
        CSVWriter.write("Logarithm", csvEntries);
    }

    @Test
    void SecantTest() {
        var csvEntries = doTest(-step, Trigonometry.Secant::preciseValue, Trigonometry.Secant::approximatedValue);
        csvEntries.addAll(doTest(step, Trigonometry.Secant::preciseValue, Trigonometry.Secant::approximatedValue));
        CSVWriter.write("Secant", csvEntries);
    }

    @Test
    void CosecantTest() {
        var csvEntries = doTest(-step, Trigonometry.Cosecant::preciseValue, Trigonometry.Cosecant::approximatedValue);
        csvEntries.addAll(doTest(step, Trigonometry.Cosecant::preciseValue, Trigonometry.Cosecant::approximatedValue));
        CSVWriter.write("Cosecant", csvEntries);
    }

    @Test
    void FunctionCustTest() {
        var csvEntries = doTest(-step, FunctionCust::calculatePrecise, FunctionCust::approximate);
        csvEntries.addAll(doTest(step, FunctionCust::calculatePrecise, FunctionCust::approximate));
        CSVWriter.write("FunctionCust", csvEntries);
    }
}