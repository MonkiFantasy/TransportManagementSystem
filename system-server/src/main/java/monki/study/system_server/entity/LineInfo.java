package monki.study.system_server.entity;

public class LineInfo {
    private int lineNumber;
    private String startPoint;
    private String destination;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "LineInfo{" +
                "lineNumber=" + lineNumber +
                ", startPoint='" + startPoint + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
