package com.hashir.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashir.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
