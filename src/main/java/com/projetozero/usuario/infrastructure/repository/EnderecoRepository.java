package com.projetojava.projeto1.infrastructure.repository;

import com.projetojava.projeto1.infrastructure.entity.Endereco;
import com.projetojava.projeto1.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
