package CSV;


public record CSVEntry(double x, double approxValue, double preciseValue) {
    public String toCsv() {
        return String.format(java.util.Locale.ROOT, "%f,%f,%f", x, approxValue, preciseValue);
    }
}
