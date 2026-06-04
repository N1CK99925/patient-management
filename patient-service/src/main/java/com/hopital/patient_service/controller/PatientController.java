package com.hopital.patient_service.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hopital.patient_service.DTO.PatientRequestDTO;
import com.hopital.patient_service.DTO.PatientResponseDTO;
import com.hopital.patient_service.service.PatientService;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

@Controller
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "Operations for managing patient records")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "List all patients", description = "Retrieve all patient records.")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    @Operation(summary = "Create patient", description = "Create a new patient. Required fields: firstName, lastName, email.")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Patient data to create. Required: firstName, lastName, email.", required = true)
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO createdPatient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(createdPatient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient", description = "Update an existing patient by ID. Required fields: firstName, lastName, email.")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Patient data to update. Required: firstName, lastName, email.", required = true)
            @RequestBody @Validated({Default.class}) PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(updatedPatient);
    }

    // TODO: change the register to different dto class so it doesnt interfere with the validation here

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient", description = "Delete a patient by ID.")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}