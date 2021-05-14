package org.covid.vaccineman.dto.district;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DistrictCreateRequest {

    @JsonProperty("district_id")
    @NotBlank(message = "district_id cannot be blank")
    private Integer districtId;

    @JsonProperty("district_name")
    @NotBlank(message = "district_name cannot be blank")
    private String districtName;

}
