package com.fable.weatherall.TravelEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "TravelNames")
public class TravelNames {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelid")
    private Integer travelid;
    
    @Column(name = "Travelname", nullable = false)
    private String Travelname;

	

	public Integer getTravelid() {
		return travelid;
	}

	public void setTravelid(Integer travelid) {
		this.travelid = travelid;
	}

	public String getTravelname() {
		return Travelname;
	}

	public void setTravelname(String travelname) {
		Travelname = travelname;
	}
	
}
