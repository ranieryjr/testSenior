package testeSenior;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesafioJava {
	
	public static void main(String[] args) throws IOException {
		
		FileInputStream f = new FileInputStream("file.csv");//csv input file
		BufferedReader file = feature1(f);
		System.out.println("Requisito 1: arquivo lido\n");
		
		List<String> list2 = feature2(f);
		System.out.println("Requisito 2: lista de capitais ordenadas por nome");
		for(String capital : list2)
			System.out.println(capital);
		System.out.println();

		String[] array3 = feature3(f);
		System.out.println("Requisito 3: estado com maior e menor quantidade de cidades");		
		System.out.println(array3[0]+" possui a maior quantidade de cidades: "+array3[1]);
		System.out.println(array3[2]+" possui a menor quantidade de cidades: "+array3[3]+"\n");
		
		List<State> list4 = feature4(f);
		System.out.println("Requisito 4: quantidade de cidades por estado");
		for(State state : list4)
			System.out.println(state.getAccronym() +": "+state.getNcity());
		System.out.println();
		
		City city = feature5(f, 2516409);
		System.out.println("Requisito 5: obter os dados da cidade informando o id do IBGE (ex: 2516409)");
		System.out.println(city.getIbge_id()+","+city.getNo_accents()+","+city.getAlternative_names()+","+city.getUf()+","+
				city.getLon()+","+city.getLat()+","+city.getMicroregion()+","+city.getMesoregion()+"\n");
		
		List<String> list6 = feature6(f, "AL");
		System.out.println("Requisito 6: retornar o nome das cidades baseado em um estado selecionado (ex: AL)");
		for(String nome : list6)
			System.out.println(nome);
		System.out.println();
		
		feature7(new City(1,"RO","Alta Floresta D'Oeste2","",0,0,"Alta Floresta D'Oeste2","","Cacoal","Leste Rondoniense"));
		System.out.println("Requisito 7: permitir adicionar uma nova cidade (ex: 1,RO,Alta Floresta D'Oeste2,,0,0,Alta Floresta D'Oeste2,,Cacoal,Leste Rondoniense)\n");
		
		feature8(f, 1100072);
		System.out.println("Requisito 8: permitir deletar uma cidade (ex: 1100072)\n");
		
		List<City> list9 = feature9(f, 6, "sao");
		System.out.println("Requisito 9: permitir selecionar uma coluna e entrar com uma string para filtrar (ex: no_accents, coluna 7, id 6, string \"sao\")");
		for(City c : list9)
			System.out.println(c.getIbge_id()+","+c.getUf()+","+c.getNo_accents());
		System.out.println();
		
		int quant = feature10(f,8).size();
		System.out.println("Requisito 10: retornar a quantidade de registros baseado em uma coluna sem itens iguais (ex: microregion, coluna 9, id 8)");
		System.out.println(quant+"\n");
		
		quant = feature11(f,7).size();
		System.out.println("Requisito 11: retornar a quantidade de registros total (ex: alternative_names, coluna 8, id 7)");
		System.out.println(quant+"\n");
		
		System.out.println("Requisito 12: obter as duas cidades mais distantes uma da outra com base na localização");
		String[] array12 = feature12(f);
		System.out.println(array12[0]+" e "+array12[1]+" com aproximadamente "+array12[2]+" km de distancia");
		
		file.close();
		f.close();
	}
	
	public static BufferedReader feature1(FileInputStream f) throws FileNotFoundException{
		return new BufferedReader(new InputStreamReader(f));
	}
	
	public static List<String> feature2(FileInputStream f) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<String> list = new ArrayList<String>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			if(row[3].equals("true"))
				list.add(row[6]);
		}
		Collections.sort(list);
		return list;
	}
	
	public static String[] feature3(FileInputStream f) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));
		file.readLine();
		String row[] = file.readLine().split(",");
		List<State> list = new ArrayList<State>();
		list.add(new State(row[1], 1));
		
		while(file.ready()){
			row = file.readLine().split(",");
			if(row[1].equals(list.get(list.size()-1).getAccronym()))
				list.get(list.size()-1).increaseNcity();
			else
				list.add(new State(row[1], 1));			
		}
		
		f.getChannel().position(0);
		file = new BufferedReader(new InputStreamReader(f));
		file.readLine();
		String[] cities = new String[4];
		cities[1] = 1+"";
		cities[3] = (int) file.lines().count()+"";		
		for(State state : list){
			if(state.getNcity() > Integer.parseInt(cities[1])){
				cities[0] = state.getAccronym();
				cities[1] = state.getNcity()+"";
			}
			if(state.getNcity() < Integer.parseInt(cities[3])){
				cities[2] = state.getAccronym();
				cities[3] = state.getNcity()+"";
			}
		}
		return cities;
	}
	
	public static List<State> feature4(FileInputStream f) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));
		file.readLine();
		String row[] = file.readLine().split(",");
		List<State> list = new ArrayList<State>();
		list.add(new State(row[1], 1));
		
		while(file.ready()){
			row = file.readLine().split(",");
			if(row[1].equals(list.get(list.size()-1).getAccronym()))
				list.get(list.size()-1).increaseNcity();
			else
				list.add(new State(row[1], 1));			
		}
		return list;
	}
	
	public static City feature5(FileInputStream f, int id) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<City> list = new ArrayList<City>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			list.add(new City(Integer.parseInt(row[0]), row[1], row[2], row[3], Double.parseDouble(row[4]), 
					Double.parseDouble(row[5]), row[6], row[7], row[8], row[9]));
		}
		for(City city : list){
			if(city.getIbge_id() == id) 
				return city;
		}
		return null;
	}
	
	public static List<String> feature6(FileInputStream f, String state) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<String> list = new ArrayList<String>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			if(row[1].equals(state))
				list.add(row[6]);
		}
		return list;
	}
	
	public static void feature7(City city) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("file2.csv"),true));
		writer.append(city.getIbge_id()+","+city.getUf()+","+city.getName()+","+city.getCapital()+","+city.getLon()+","+
				city.getLat()+","+city.getNo_accents()+","+city.getAlternative_names()+","+city.getMicroregion()+","+city.getMesoregion()+"\n");
		writer.close();
	}
	
	public static void feature8(FileInputStream f, int id) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("file3.csv")));
		String row[];
		String line;
		while(file.ready()){
			line = file.readLine();
			row = line.split(",");
			if(!row[0].equals(id+""))
				writer.append(line+"\n");
		}		
		writer.close();		
	}

	public static List<City> feature9(FileInputStream f, int column, String filter) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<City> list = new ArrayList<City>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			if(row[column].toLowerCase().contains(filter.toLowerCase()))
				list.add(new City(Integer.parseInt(row[0]), row[1], row[2], row[3], Double.parseDouble(row[4]), 
						Double.parseDouble(row[5]), row[6], row[7], row[8], row[9]));
		}
		return list;
	}
	
	public static List<String> feature10(FileInputStream f, int column) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<String> list = new ArrayList<String>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			if(!list.contains(row[column]))
				list.add(row[column]);
		}
		return list;
	}
	
	public static List<String> feature11(FileInputStream f, int column) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<String> list = new ArrayList<String>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			if(!row[column].equals(""))
				list.add(row[column]);
		}
		return list;
	}
	
	public static String[] feature12(FileInputStream f) throws IOException{
		f.getChannel().position(0);
		BufferedReader file = new BufferedReader(new InputStreamReader(f));		
		file.readLine();
		List<City> cities = new ArrayList<City>();
		String row[];
		while(file.ready()){
			row = file.readLine().split(",");
			cities.add(new City(Integer.parseInt(row[0]), row[1], row[2], row[3], Double.parseDouble(row[4]), 
					Double.parseDouble(row[5]), row[6], row[7], row[8], row[9]));
		}
		
		double result;
		String[] d = {"", "", "0"};
		City city1, city2;
		System.out.println("Calculando distancias... (este processo pode demorar alguns segundos)");
		for(int i = 0; i < cities.size()-1; i++){
			city1 = cities.get(i);
			for(int j = i+1; j < cities.size(); j++){
				city2 = cities.get(j);
				result =(Math.sqrt(Math.pow(city2.getLon() - city1.getLon(), 2) + Math.pow(city2.getLat() - city1.getLat(), 2))) * 111.12;
				if (result > Double.parseDouble(d[2])) {
					d[0] = city1.getNo_accents();
					d[1] = city2.getNo_accents();
					d[2] = Math.round(result)+"";
				}				
			}
		}
		return d;
	}
	
	public static class State{
		private String accronym;
		private int ncity;
		
		public State(String accronym, int ncity){
			this.accronym = accronym;
			this.ncity = ncity;
		}
		
		public String getAccronym() {
			return accronym;
		}
		public void setAccronym(String accronym) {
			this.accronym = accronym;
		}
		public int getNcity() {
			return ncity;
		}
		public void setNcity(int ncity) {
			this.ncity = ncity;
		}
		public void increaseNcity() {
			this.ncity++;
		}
	}
	
	public static class City{
		private int ibge_id;
		private String uf;
		private String name;
		private String capital;
		private double lon;
		private double lat;
		private String no_accents;
		private String alternative_names;
		private String microregion;
		private String mesoregion;
		
		public City(int ibge_id, String uf, String name, String capital,
				double lon, double lat, String no_accents,
				String alternative_names, String microregion, String mesoregion) {
			this.ibge_id = ibge_id;
			this.uf = uf;
			this.name = name;
			this.capital = capital;
			this.lon = lon;
			this.lat = lat;
			this.no_accents = no_accents;
			this.alternative_names = alternative_names;
			this.microregion = microregion;
			this.mesoregion = mesoregion;
		}
		
		public int getIbge_id() {
			return ibge_id;
		}
		public void setIbge_id(int ibge_id) {
			this.ibge_id = ibge_id;
		}
		public String getUf() {
			return uf;
		}
		public void setUf(String uf) {
			this.uf = uf;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCapital() {
			return capital;
		}
		public void setCapital(String capital) {
			this.capital = capital;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public String getNo_accents() {
			return no_accents;
		}
		public void setNo_accents(String no_accents) {
			this.no_accents = no_accents;
		}
		public String getAlternative_names() {
			return alternative_names;
		}
		public void setAlternative_names(String alternative_names) {
			this.alternative_names = alternative_names;
		}
		public String getMicroregion() {
			return microregion;
		}
		public void setMicroregion(String microregion) {
			this.microregion = microregion;
		}
		public String getMesoregion() {
			return mesoregion;
		}
		public void setMesoregion(String mesoregion) {
			this.mesoregion = mesoregion;
		}
	}

}
