package com.practica.crud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practica.crud.entidades.Foto;


@Repository
public interface FotoRepository extends JpaRepository<Foto, String>{

}
