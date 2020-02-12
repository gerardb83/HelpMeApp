/**
 * Copyright (c) 2019.
 * This program and the accompanying materials are made available
 * under my granted permission provided that this note is kept intact, unmodified and unchanged.
 * @ Author: Baraa Ali -  API and implementation.
 * All rights reserved.
*/
/**
 * 
 */
package co.grandcircus.HelpMeApp.placedetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlacesDetailsService {

	@Value("${Geocoding.API_KEY}")
	private String key;

}