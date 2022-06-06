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
//		Clothes cloth = getClothesById((long) 1);
		backEndInit();
//		rentClothes();
	}
	
	//método para adicionar roupas e lojas para utilização na demonstração do código
	private static void backEndInit() {
		postAddStore("CiA");
		postAddStore("Matisa");
		
		Store store = getStoreById((long) 1);
		System.out.println("loja de id "+store.getId()+" :");
		System.out.println(store.getName());
		
		postAddClothes("camisa azul", "esportiva", (float) 12, store.getId());
		postAddClothes("camisa preta", "esportiva", (float) 120, store.getId());
		postAddClothes("calça azul", "formal", (float) 12, store.getId());
		
		
	}

	//método para adicionar aluguel para utilização na demonstração do código
	private static void rentClothes() {
		getClothesByStyle("esportiva", "2020-02-12", "2020-12-01");
		
		postAddRent((long) 1,"2020-02-12", "2020-12-01");
		
		Clothes cloth = getClothesById((long) 1);
		
		System.out.print("roupa de id "+cloth.getId()+" : ");
		System.out.println(cloth.getName());
		

		getClothesByStyle("esportiva", "2020-02-12", "2020-12-01");
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
	
	private static Store getStoreById(Long id) {
		
		try {
			Store store = restTemplate.getForObject(STORE_URL+id, Store.class);
			return store;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//Clothes
	private static void postAddClothes(String name, String style, Float price, Long storeId) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Store store = new Store();
		store.setId(storeId);
		
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
	
	private static Clothes getClothesById(Long id) {
		try {
			Clothes cloth = restTemplate.getForObject(CLOTHES_URL+id, Clothes.class);
			return cloth;
		}catch(Exception e) {
			System.out.println(e);
			return null;
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
			System.out.print("preço total da  ");
			System.out.println(response.getPrice());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
