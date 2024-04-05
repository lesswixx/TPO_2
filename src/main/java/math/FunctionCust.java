package math;



import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionCust {
    private static Function<Double, Double> provideLn(boolean isPrecise) {
        return isPrecise ? Logarithm::preciseValue : Logarithm::approximatedValue;
    }

    private static BiFunction<Double, Double, Double> provideLog(boolean isPrecise) {
        return isPrecise ? Logarithm::preciseValue : Logarithm::approximatedValue;
    }

    private static Function<Double, Double> provideSec(boolean isPrecise) {
        return isPrecise ? Trigonometry.Secant::preciseValue : Trigonometry.Secant::approximatedValue;
    }

    private static Function<Double, Double> provideCsc(boolean isPrecise) {
        return isPrecise ? Trigonometry.Cosecant::preciseValue : Trigonometry.Cosecant::approximatedValue;
    }

    public static double approximate(double x) {
        return calculate(x, false);
    }

    public static double calculatePrecise(double x) {
        return calculate(x, true);
    }

    private static double calculate(double x, boolean isPrecise) {
        if(x < 0){
            return Math.pow(Trigonometry.Secant.approximatedValue(x) + Trigonometry.Cosecant.approximatedValue(x) + Trigonometry.Secant.approximatedValue(x), 2);
        }
        else if(x > 0){
            double ln_x = Logarithm.preciseValue(x);
            double log2_x = Logarithm.preciseValue(x, 2);
            double log5_x = Logarithm.preciseValue(x, 5);
            double log10_x = Logarithm.preciseValue(x, 10);
            double numerator = ((ln_x / log2_x) + log2_x) * log5_x;
            double denominator = log2_x;
            double subtraction = log10_x * (log10_x / log2_x);

            return (numerator / denominator) - subtraction;
        }
        else{
            return Math.pow(Trigonometry.Secant.approximatedValue(x) + Trigonometry.Cosecant.approximatedValue(x) + Trigonometry.Secant.approximatedValue(x), 2);
        }
    }
}
