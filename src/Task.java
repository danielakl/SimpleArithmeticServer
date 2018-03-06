import java.io.Serializable;

@Deprecated
public final class Task implements Serializable {
    private double value = 0.0;

    public Task() {}

    public Task(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Task doAddition(int value) {
        this.value += value;
        return this;
    }

    public Task doSubstraction(int value) {
        this.value -= value;
        return this;
    }

    public Task doMultiplication(int value) {
        this.value *= value;
        return this;
    }

    public Task doDivision(int value) {
        this.value /= value;
        return this;
    }

}
