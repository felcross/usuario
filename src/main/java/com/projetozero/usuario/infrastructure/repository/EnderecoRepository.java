package com.projetozero.usuario.infrastructure.repository;

import com.projetozero.usuario.infrastructure.entity.Endereco;
import com.projetozero.usuario.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
