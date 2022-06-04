package Client;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ufrn.entity.Clothes;
import com.ufrn.entity.Rent;
import com.ufrn.entity.Store;

public class Client {
	private static final String STORE_URL="http://localhost:8080/store/";
	private static final String CLOTHES_URL="http://localhost:8080/clothes/";
	private static final String RENT_URL="http://localhost:8080/rent/";
	
	static RestTemplate restTemplate = new RestTemplate();
	public static void main(String args[]) {
		postAddRent((long) 1, "2020-12-09", "2020-12-22");
	}
	//stores
	private static void postAddStore(String name) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, String> param = new HashMap<>(); 
		param.put("name", name);
		
		HttpEntity<Map<String, String>> request = new HttpEntity<>(param,header);
		
		try {
			Store store = restTemplate.postForObject(STORE_URL+"add", request, Store.class);
			System.out.print("loja ");
			System.out.print(store.getName());
			System.out.print(" id: "+store.getName());
			System.out.println(" adcionada");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void deleteStore(Long id) {
		

		HttpEntity<Map<String, String>> request = new HttpEntity<>(null);
		try {
			ResponseEntity<String> store = restTemplate.exchange(STORE_URL+"delete"+id, HttpMethod.DELETE, request, String.class);
			System.out.println("loja deletada");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void getStoreById(Long id) {
//		Map<String, Long> param = new HashMap<>(); 
//		param.put("id", id);
		
		try {
			Store store = restTemplate.getForObject(STORE_URL+id, Store.class);
			System.out.println("loja de id "+id+" :");
			System.out.println(store.getName());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//Clothes
	private static void postAddClothes(String name, String style, Float price) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Store store = new Store();
		store.setId((long) 1);
		
		Clothes cloth = new Clothes();
		cloth.setName(name);
		cloth.setStyle(style);
		cloth.setPrice(price);
		cloth.setStore(store);
		
		HttpEntity<Clothes> request = new HttpEntity<>(cloth,header);
//		System.out.print(request);
		
		try {
			Clothes response = restTemplate.postForObject(CLOTHES_URL+"add", request, Clothes.class);
			System.out.print("roupa ");
			System.out.print(response.getName());
			System.out.println(" adcionada");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
		
	private static void deleteClothes(Long id) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		

		HttpEntity<Map<String, String>> request = new HttpEntity<>(header);
		try {
			ResponseEntity<String> response = restTemplate.exchange(CLOTHES_URL+"delete/"+id, HttpMethod.DELETE, request, String.class);
			System.out.println("roupa deletada");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void getClothesById(Long id) {
//			Map<String, Long> param = new HashMap<>(); 
//			param.put("id", id);
		
		try {
			Clothes cloth = restTemplate.getForObject(CLOTHES_URL+id, Clothes.class);
			System.out.println("loja de id "+id+" :");
			System.out.println(cloth.getName());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private static void getClothesByStyle(String style, String start, String finish) {
		String uriComplement = "forStyleAll?";
		uriComplement += "style=";
		uriComplement += style;
		uriComplement += "&start=";
		uriComplement += start;
		uriComplement += "&finish=";
		uriComplement += finish;
		try {
			ResponseEntity<String> clothes = restTemplate.getForEntity(CLOTHES_URL+uriComplement, String.class);
			System.out.println(clothes.getBody());
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//Rents
	private static void postAddRent(Long id,  String start, String finish) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Clothes cloth = new Clothes();
		cloth.setId(id);
		
		Rent rent = new Rent();
		rent.setDate_start(LocalDate.parse(start));
		rent.setDate_finish(LocalDate.parse(finish));
		rent.setClothes(cloth);
		
		HttpEntity<Rent> request = new HttpEntity<>(rent,header);
		
		try {
			Rent response = restTemplate.postForObject(RENT_URL+"add", request, Rent.class);
			System.out.print("preço ");
			System.out.print(response.getPrice());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
