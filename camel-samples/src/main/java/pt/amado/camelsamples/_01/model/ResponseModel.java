package pt.amado.camelsamples._01.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseModel {

    private List<User> users;
    private int statusCode;
    private long timeRunning;

}
