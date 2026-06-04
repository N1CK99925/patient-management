package com.hopital.patient_service.service;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hopital.patient_service.DTO.PatientRequestDTO;
import com.hopital.patient_service.DTO.PatientResponseDTO;
import com.hopital.patient_service.exception.EmailAlreadyExistsException;
import com.hopital.patient_service.exception.PatientNotFoundException;
import com.hopital.patient_service.mapper.PatientMapper;
import com.hopital.patient_service.model.Patient;
import com.hopital.patient_service.repository.PatientRepository;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientDTOs = patients.stream()
                .map(PatientMapper::toPatientResponseDTO)
                .toList();
        return patientDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toPatientResponseDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id:" + id));

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDTO(updatedPatient);
    }
    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}

