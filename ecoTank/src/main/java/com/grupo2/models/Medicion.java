package com.grupo2.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="mediciones")
public class Medicion {
	
	//Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Se le asigna como Primary Key
	private Long id;
	
	//Unique ID asociada a dispositivo
	private String uniqueId;
	
	//Variable Temperatura
	private Float temperatura;
	
	// Variable humedad
	private Float humedad;
	
	//Variable Fecha | @JsonFormat: para dar el formato de la fecha al json 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date fecha = new Date();
	
	//Columnas de Fecha/tiempo
	@Column(updatable = false) // esta columna especifica nunca se va a actualizar atravez del sistema
	private Date createdAt;// Para saber en que momento fue insertada en la base de datos
	private Date updatedAt;// Para cuando se actualizo
	
	//Many To One a Dispositivo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dispositivo_id")
	private Dispositivo dispositivo;

	//Constructores | Vacio
	public Medicion() {
		super();
	}

	//Constructor | Con parametros
	public Medicion(Float temperatura, String uniqueId, Float humedad, Date fecha, Dispositivo dispositivo) {
		super();
		this.temperatura = temperatura;
    	this.uniqueId = uniqueId;
		this.humedad = humedad;
		this.fecha = fecha;
		this.dispositivo = dispositivo;
	}

	//Getters and Setters
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
	
	public Float getTemperatura() {
		return temperatura;
	}


	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}


	public Float getHumedad() {
		return humedad;
	}


	public void setHumedad(Float humedad) {
		this.humedad = humedad;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Dispositivo getDispositivo() {
		return dispositivo;
	}


	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}
	
	// inserta en el atributo la fecha antes de registrar en la base de datos
		@PrePersist
		protected void onCreate() {
			this.createdAt = new Date();
		}

		@PreUpdate
		protected void onUpdate() {
			this.updatedAt = new Date();
		}
	
}
