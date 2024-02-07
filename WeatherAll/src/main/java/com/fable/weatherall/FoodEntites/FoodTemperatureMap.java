package com.fable.weatherall.FoodEntites;

import jakarta.persistence.*;

@Entity
@Table(name = "FoodTemperatureMap")
public class FoodTemperatureMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodTemperatureId")
    private Integer foodTemperatureId;

    
    @Column(name = "foodId", nullable = false)
    private Integer foodId;

    
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId;

	public Integer getFoodTemperatureId() {
		return foodTemperatureId;
	}

	public void setFoodTemperatureId(Integer foodTemperatureId) {
		this.foodTemperatureId = foodTemperatureId;
	}

	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	

    
}

