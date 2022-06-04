package com.ufrn.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rent")
public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date_start;
	
	private LocalDate date_finish;
	
	private float price;
	
	@ManyToOne
	@JoinColumn(name="clothes_id", nullable=false)
    private Clothes clothes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate_start() {
		return date_start;
	}
	public void setDate_start(LocalDate date_start) {
		this.date_start = date_start;
	}
	public LocalDate getDate_finish() {
		return date_finish;
	}
	public void setDate_finish(LocalDate date_finish) {
		this.date_finish = date_finish;
	}
	public Clothes getClothes() {
		return clothes;
	}
	public void setClothes(Clothes clothes) {
		this.clothes = clothes;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	
}
