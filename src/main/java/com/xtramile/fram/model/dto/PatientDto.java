package com.xtramile.fram.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDto {
    private Long id;
    @NotBlank(message = "Patient Id must be provided")
    private String patientId;
    @NotBlank(message = "First name must be provided")
    private String firstName;
    private String lastName;
    private String fullName;
    @NotBlank(message = "Date of birth must be provided")
    private String dateOfBirth;
    @NotBlank(message = "Gender must be provided")
    @Size(min = 1, max = 1, message = "Gender character is not valid")
    private String gender;
    @NotBlank(message = "Address must be provided")
    private String address;
    private Integer suburbId;
    private String suburbName;
    private Integer stateId;
    private String stateName;
    private String postcode;
    @NotBlank(message = "Phone no must be provided")
    private String phoneNumber;
}
