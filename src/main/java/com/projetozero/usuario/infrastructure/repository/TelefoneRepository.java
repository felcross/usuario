package com.projetojava.projeto1.infrastructure.repository;

import com.projetojava.projeto1.infrastructure.entity.Telefone;
import com.projetojava.projeto1.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
