package kz.sdu.project.sauapbackend.dto;

public enum PaymentStatus {
    CREATED(0),
    IN_PROGRESS(1),
    DONE(2),
    FAILED(3);

    private int status;

    PaymentStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
