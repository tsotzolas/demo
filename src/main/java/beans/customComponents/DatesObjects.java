package beans.customComponents;


public class DatesObjects {
    private String min;
    private String max;

    public DatesObjects() {
    }

    public DatesObjects(String min, String max) {
        this.min = min;
        this.max = max;
    }


    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
