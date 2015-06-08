package com.eimis;

import java.util.Date;

//2014.08.31; 08:10:13; 444260; 21.0; 99; 0; 0; -; 65499; 16

public class CsvEntry {
	private int altitude;
	private int cadence;
	private Date day;
	
	private int distance;
	
	private float heartRate;
	
	private String power;
	
	private float speed;
	
	private int temperature;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CsvEntry other = (CsvEntry) obj;
		if (altitude != other.altitude) {
			return false;
		}
		if (cadence != other.cadence) {
			return false;
		}
		if (day == null) {
			if (other.day != null) {
				return false;
			}
		}
		else if (!day.equals(other.day)) {
			return false;
		}
		if (distance != other.distance) {
			return false;
		}
		if (heartRate != other.heartRate) {
			return false;
		}
		if (power == null) {
			if (other.power != null) {
				return false;
			}
		}
		else if (!power.equals(other.power)) {
			return false;
		}
		if (Float.floatToIntBits(speed) != Float.floatToIntBits(other.speed)) {
			return false;
		}
		if (temperature != other.temperature) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return the altitude
	 */
	public int getAltitude() {
		return altitude;
	}
	
	/**
	 * @return the cadence
	 */
	public int getCadence() {
		return cadence;
	}
	
	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}
	
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * @return the heartRate
	 */
	public float getHeartRate() {
		return heartRate;
	}
	
	/**
	 * @return the power
	 */
	public String getPower() {
		return power;
	}
	
	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + altitude;
		result = prime * result + cadence;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + distance;
		result = prime * result + Float.floatToIntBits(heartRate);
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + Float.floatToIntBits(speed);
		result = prime * result + temperature;
		return result;
	}
	
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	
	/**
	 * @param cadence the cadence to set
	 */
	public void setCadence(int cadence) {
		this.cadence = cadence;
	}
	
	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	/**
	 * @param heartRate the heartRate to set
	 */
	public void setHeartRate(float heartRate) {
		this.heartRate = heartRate;
	}
	
	/**
	 * @param power the power to set
	 */
	public void setPower(String power) {
		this.power = power;
	}
	
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CsvEntry [day=" + day + ", distance=" + distance + ", speed=" + speed + ", heartRate=" + heartRate
				+ ", cadence=" + cadence + ", power=" + power + ", altitude=" + altitude + ", temperature="
				+ temperature + "]";
	}
}
