package online.botika.risetwebviewtonotif.service;

public class StatusService {
    String status;

    public StatusService(){
    }

    public StatusService(String status){
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
