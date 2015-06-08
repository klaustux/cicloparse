/*
 * Copyright (c) 2009 Martin Jansen
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.divbyzero.gpx;

import java.util.Date;

/**
 * Way point for the GPS tracks.
 * 
 * <p>Way points represent the smallest unit of which GPS track are
 * composed.  They are made up of a coordinate, a time stamp, an
 * optional name and an elevation.</p>
 * 
 * @author Martin Jansen <martin@divbyzero.net>
 * @since 0.1
 */
public class Waypoint {
	private Date time;
	private Coordinate coordinate;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Waypoint [time=" + time + ", coordinate=" + coordinate + ", name=" + name + ", elevation=" + elevation
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(elevation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Waypoint other = (Waypoint) obj;
		if (coordinate == null) {
			if (other.coordinate != null) {
				return false;
			}
		}
		else if (!coordinate.equals(other.coordinate)) {
			return false;
		}
		if (Double.doubleToLongBits(elevation) != Double.doubleToLongBits(other.elevation)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		}
		else if (!time.equals(other.time)) {
			return false;
		}
		return true;
	}

	private String name = "";
	private double elevation = .0;
	
	/**
	 * Sets the time stamp of the way point.
	 * 
	 * <p>The time stamp denotes the point in time when a way point was
	 * recorded. The library does not assume anything regarding the use
	 * of time zones. Instead time stamps are accepted "as is".</p>
	 *  
	 * @param time the time stamp of the way point
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * Returns the time stamp of the way point.
	 * 
	 * @return the time stamp of the way point
	 */
	public Date getTime() {
		return time;
	}
	
	/**
	 * Sets the coordinate of the way point.
	 * 
	 * @param coordinate the coordinate of the way point
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Returns the coordinate of the way point
	 * 
	 * @return the coordinate of the way point
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	/**
	 * Sets the name of the way point.
	 * 
	 * <p>Optionally a way point can be label with a name in order to
	 * describe it further.</p>
	 * 
	 * @param name the name of the waypoint
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the way point.
	 * 
	 * @return the name of the way point
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the elevation of the way point in meters
	 * 
	 * <p>Internally the library expects the elevation to be in meters
	 * instead of feet or anything else. No conversion is applied to
	 * ensure this though.</p>
	 * 
	 * @param elevation the way point's elevation in meters
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	/**
	 * Returns the elevation of the way point
	 * 
	 * @return the way point's elevation in meters
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * Calculates the distance between this way point and another one
	 * 
	 * <p>In order to calculate the distance, the Spherical Law of Cosines
	 * is used. An equatorial radius of 6,378.137 kilometers is assumed.</p> 
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Spherical_law_of_cosines">Wikipedia on the Spherical Law Of Cosines</a>
	 * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">Implementation notes</a>
	 * @param otherPoint The other way point
	 * @return the distance in meters
	 */
	public double calculateDistanceTo(Waypoint otherPoint) {
		// According to http://en.wikipedia.org/wiki/Earth_radius#Mean_radii
		// earth has an equatorial radius of 6,378.137 kilometers. 
		final int R = 6378137;

		if (otherPoint.getCoordinate() == null || getCoordinate() == null) {
			return 0.0;
		}
		
		return Math.acos(
				Math.sin(Math.toRadians(getCoordinate().getLatitude())) * Math.sin(Math.toRadians(otherPoint.getCoordinate().getLatitude())) + 
                Math.cos(Math.toRadians(getCoordinate().getLatitude())) * Math.cos(Math.toRadians(otherPoint.getCoordinate().getLatitude())) *
                Math.cos(Math.toRadians(otherPoint.getCoordinate().getLongitude() - getCoordinate().getLongitude()))) * R;
	}
}
