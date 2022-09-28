

package com.Tomi.Biblioteca.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Tomi
 */
@Entity

public class Libro {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String titulo;
    @ManyToOne
    private Editorial editorial;
    @ManyToOne
    private Autor autor;
    
    private Boolean alta;
    private Integer anio;
    private Integer unidadesTotales;
    private Integer unidadesPrestadas;
    private Integer unidadesDisponibles;

    public Libro() {
    }

    public Libro(String titulo, Editorial editorial, Autor autor, Integer anio, Integer unidadesTotales) {
        this.id = id;
        this.titulo = titulo;
        this.editorial = editorial;
        this.autor = autor;
        this.alta = true;
        this.anio = anio;
        this.unidadesTotales = unidadesTotales;
        this.unidadesPrestadas = 0;
        this.unidadesDisponibles = unidadesTotales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getUnidadesTotales() {
        return unidadesTotales;
    }
 

    public void setUnidadesTotales(Integer unidadesTotales) {
        this.unidadesTotales = unidadesTotales;
    }

    public Integer getUnidadesPrestadas() {
        return unidadesPrestadas;
    }

    public void setUnidadesPrestadas(Integer unidadesPrestadas) {
        this.unidadesPrestadas = unidadesPrestadas;
    }

    public Integer getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(Integer unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }
    
    
            

}
