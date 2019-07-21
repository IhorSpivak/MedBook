package ua.profarma.medbook.types.requests;

public class ChangeTimeVisitRequest {
    public long    time_from;
    public long    time_to;

    public ChangeTimeVisitRequest(long time_from, long time_to) {
        this.time_from = time_from;
        this.time_to = time_to;
    }
}
