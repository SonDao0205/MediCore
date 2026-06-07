package com.btvn.medicore.config;

import com.btvn.medicore.entity.*;
import com.btvn.medicore.repository.*;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if(roleRepository.count() > 0){
            return;
        }

        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");

        Role doctorRole = new Role();
        doctorRole.setRoleName("ROLE_DOCTOR");

        Role patientRole = new Role();
        patientRole.setRoleName("ROLE_PATIENT");

        roleRepository.save(adminRole);
        roleRepository.save(doctorRole);
        roleRepository.save(patientRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setEmail("admin@medicore.com");
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);

        User doctorUser = new User();
        doctorUser.setUsername("doctor1");
        doctorUser.setPassword(passwordEncoder.encode("123456"));
        doctorUser.setEmail("doctor@medicore.com");
        doctorUser.setEnabled(true);
        doctorUser.setRoles(Set.of(doctorRole));

        userRepository.save(doctorUser);

        Doctor doctor = new Doctor();
        doctor.setFullName("Nguyen Van Doctor");
        doctor.setSpecialization("Cardiology");
        doctor.setPhone("0901234567");
        doctor.setUser(doctorUser);

        doctorRepository.save(doctor);

        User patientUser = new User();
        patientUser.setUsername("patient1");
        patientUser.setPassword(passwordEncoder.encode("123456"));
        patientUser.setEmail("patient@medicore.com");
        patientUser.setEnabled(true);
        patientUser.setRoles(Set.of(patientRole));

        userRepository.save(patientUser);

        Patient patient = new Patient();
        patient.setFullName("Tran Van Patient");
        patient.setPhone("0911111111");
        patient.setUser(patientUser);

        patientRepository.save(patient);

        Medicine m1 = new Medicine();
        m1.setName("Paracetamol");
        m1.setDescription("Pain relief");
        m1.setPrice(5000.0);

        Medicine m2 = new Medicine();
        m2.setName("Amoxicillin");
        m2.setDescription("Antibiotic");
        m2.setPrice(15000.0);

        medicineRepository.save(m1);
        medicineRepository.save(m2);

        System.out.println("=== SAMPLE DATA CREATED ===");
    }
}