package io.weather.app.dto.weather;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;
import lombok.Data;

@Data
public class TimePeriodRequest {
    @NotNull
    @Past(message = "\"from\" should be a date in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date from;

    @NotNull
    @PastOrPresent(message = "\"to\" should be a date in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date to;
}
