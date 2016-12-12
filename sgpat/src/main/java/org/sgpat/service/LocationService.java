package org.sgpat.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Categorie;
import org.sgpat.entity.Client;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Location;
import org.sgpat.entity.Reservation;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.LocationForm;
import org.sgpat.form.ReservationForm;
import org.sgpat.repository.EmployeeRepository;
import org.sgpat.repository.LocationRepository;
import org.sgpat.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class LocationService {
	
	@Autowired
	LocationRepository locationRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	VehiculeService vehiculeService;
	
	public List<Location> getAll(){
		return locationRepository.getAll();
	}
	
	public List<Location> getAllCourses(){
		return locationRepository.getAllCourses();
	}
	
	public Long count(){
		return locationRepository.count();
	}
	
	public Long count(String statut){
		return locationRepository.countByStatut(statut);
	}
	
	public Long countByClient(String codeClient){
		return locationRepository.countByClient(codeClient);
	}
	
	public Long countReservation(String codeClient){
		return reservationRepository.countByClient(codeClient);
	}
	
	public Long countCourses(String codeClient) {
		// TODO Auto-generated method stub
		return locationRepository.countCourses(codeClient);
	}
	
	public List<Reservation> listeReservations(){
		return reservationRepository.getAllReservation();
	}
	
	@Transactional
	public Location creer(LocationForm locationForm){		
		Client client = clientService.findByCode(locationForm.getCodeClient());
		Assert.notNull(client, "Code client incorrect");
		
		Vehicule vehicule = vehiculeService.findByCode(locationForm.getCodeVehicule());
		Assert.notNull(vehicule, "Code vehicule incorrect");
		
		Categorie categorie = vehicule.getCategorie();
		Assert.notNull(categorie, "Veuillez créer la catégorie associée au vehicule");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		Location location = locationForm.createLocaton();
		location.setClient(client);
		location.setVehicule(vehicule);
		location.setEmployee(employee);
		location.setDateCreation(new Date());
		location.setStatut("AV");
		location.setStatutPaiement("LI");
		
		Long fromNow = getDateDiff(new Date(), location.getDateDebut(), TimeUnit.DAYS);
		Assert.isTrue(fromNow.intValue() > 0, "Date début incorrect");
		
		Long duree = getDateDiff(location.getDateDebut(), location.getDateRetour(), TimeUnit.DAYS);
		Assert.isTrue(duree.intValue() >= 0, "Période incorrect");
		location.setDuree(duree.intValue());
		
		Double coutLocation = 0.0;
		if(locationForm.getFacturation().equals("HEURE")){
			Long heure = getDateDiff(location.getDateDebut(), location.getDateRetour(), TimeUnit.HOURS);
			coutLocation = categorie.getPrixParHeurs() * heure;
		}else{
			coutLocation = categorie.getPrixParJours() * duree;
		}
		
		if(duree == 0) location.setStatut("EC");
		location.setCout(coutLocation);
		location = locationRepository.save(location);
		
		String code = location.getId()+"";
		int lenth = code.length();
		for(int i=4; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		
		LocalDate date = LocalDate.now();

		code = "LO/"+date.getYear()+"/"+code;
		locationRepository.updateCode(location.getId(), code);
		
		return location;
	}
	
	@Transactional
	public Reservation creerReservation(ReservationForm reservationForm){		
		Client client = clientService.findByCode(reservationForm.getCodeClient());
		Assert.notNull(client, "Code client incorrect");
		
		Vehicule vehicule = vehiculeService.findByCode(reservationForm.getCodeVehicule());
		Assert.notNull(vehicule, "Code vehicule incorrect");
		
		Categorie categorie = vehicule.getCategorie();
		Assert.notNull(categorie, "Veuillez créer la catégorie associée au vehicule");
		
		Reservation location = reservationForm.createReservation();
		
		Long fromNow = getDateDiff(new Date(), location.getDateRetour(), TimeUnit.DAYS);
		Assert.isTrue(fromNow.intValue() > 0, "Date début incorrect");
		
		Long duree = getDateDiff(location.getDateDebut(), location.getDateRetour(), TimeUnit.DAYS);
		Assert.isTrue(duree.intValue() >= 0, "Période incorrect");
		
		location.setVehicule(vehicule);
		location.setClient(client);
		location = reservationRepository.save(location);
		
		String code = location.getId()+"";
		int lenth = code.length();
		for(int i=6; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		
		code = "RE"+code;
		reservationRepository.updateCode(location.getId(), code);
		
		return location;
	}
	
	@Transactional
	public Location creerCourse(ReservationForm reservationForm){		
		Client client = clientService.findByCode(reservationForm.getCodeClient());
		Assert.notNull(client, "Code client incorrect");
		
		Vehicule vehicule = vehiculeService.findByCode(reservationForm.getCodeVehicule());
		Assert.notNull(vehicule, "Code vehicule incorrect");
		
		Categorie categorie = vehicule.getCategorie();
		Assert.notNull(categorie, "Veuillez créer la catégorie associée au vehicule");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		Location location = reservationForm.createLocaton();
		location.setClient(client);
		location.setVehicule(vehicule);
		location.setEmployee(employee);
		location.setDateCreation(new Date());
		location.setStatut("AV");
		location.setStatutPaiement("CI");
		
		Long fromNow = getDateDiff(new Date(), location.getDateRetour(), TimeUnit.DAYS);
		Assert.isTrue(fromNow.intValue() > 0, "Date début incorrect");
		
		Long duree = getDateDiff(location.getDateDebut(), location.getDateRetour(), TimeUnit.DAYS);
		Assert.isTrue(duree.intValue() >= 0, "Période incorrect");
		
		location = locationRepository.save(location);
		
		String code = location.getId()+"";
		int lenth = code.length();
		for(int i=6; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		
		code = "RE"+code;
		locationRepository.updateCode(location.getId(), code);
		
		return location;
	}
	
	public List<Location> findByClient(String codeClient, String statut){
		return locationRepository.findByClientCodeClientAndStatut(codeClient, statut);
	}
	public Double gains(Date date){
		LocalDateTime dt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		dt = dt.minusMonths(1);
		Instant instant = dt.toInstant(ZoneOffset.UTC);
		
		return locationRepository.somme(Date.from(instant), date);
	}

	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
