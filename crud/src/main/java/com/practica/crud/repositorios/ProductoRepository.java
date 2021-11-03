package com.practica.crud.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practica.crud.entidades.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

	
}
