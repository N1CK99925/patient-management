package com.hopital.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hopital.patient_service.DTO.PatientRequestDTO;
import com.hopital.patient_service.DTO.PatientResponseDTO;
import com.hopital.patient_service.mapper.PatientMapper;
import com.hopital.patient_service.model.Patient;
import com.hopital.patient_service.repository.PatientRepository;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public List<PatientResponseDTO> getAllPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientDTOs = patients.stream()
                .map(PatientMapper::toPatientResponseDTO)
            .toList();
        return patientDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toPatientResponseDTO(newPatient);
    }
}
