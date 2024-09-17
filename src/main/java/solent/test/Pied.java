package solent.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pied {

	public static void main(String[] args) {
//		runParam();
		runViaReadFile();
	}

	private static void runViaReadFile(){
		String rootPath = "./";
		String fileName = "confFile.txt";
		String regexCommandes = "^[DGA]+$";

		List<TondeuseCommandes> allCommandes = new ArrayList<>();
		Set<Tondeuse> allTondeuses = new HashSet<>();
		int jardinX = 0, jardinY = 0;
		
		/*
		 format du fichier en entrée 
		 Dimension Jardin 
		 T1 Position 
		 T1 Commandes 
		 T2 Position 
		 T2 Commandes
	
		 */

        int numeroTondeuse = 1;
		try (BufferedReader reader = new BufferedReader(new FileReader(rootPath+fileName))) {
            String line = reader.readLine();
            if(line != null && line.length() == 2) {
            	jardinX = Integer.valueOf(line.substring(0, 1));
            	jardinY = Integer.valueOf(line.substring(1, 2));
            } else {
            	throw new TondeuseAppException("Définition des dimensions du jardin incorrecte");
            }
            
            while ((line = reader.readLine()) != null) {
            	if(line.length() != 3)
            		throw new TondeuseAppException("Définition de la position de la tondeuse "+numeroTondeuse);
            	Tondeuse t = new Tondeuse(numeroTondeuse, Integer.valueOf(line.substring(0, 1)), Integer.valueOf(line.substring(1, 2)), EnumOrientationTondeuse.valueOf(line.substring(2, 3)));
        		allTondeuses.add(t);
        		
        		line = reader.readLine();
        		if(line != null) {
        			if(!line.matches(regexCommandes)) 
        				throw new TondeuseAppException("Définition de la commande de la tondeuse "+numeroTondeuse);
        			TondeuseCommandes c = new TondeuseCommandes(t, line);
        			allCommandes.add(c);
        		}
        		numeroTondeuse++;
            }

		
		if(allTondeuses.isEmpty())
			throw new TondeuseAppException("Aucune tondeuse !");
		
        } catch (IOException | IllegalArgumentException | TondeuseAppException e) {
        	System.err.println("Problème sur la tondeuse "+numeroTondeuse);
			System.err.println(e.getMessage());
		}
		
		new TondeuseApp(allCommandes, allTondeuses, jardinX, jardinY).startApp();
	}

	private static void runParam() {
		List<TondeuseCommandes> allCommandes = new ArrayList<>();
		Set<Tondeuse> allTondeuses = new HashSet<>();
		int jardinX = 5;
		int jardinY = 5;

		Tondeuse t1 = new Tondeuse(1, 1, 2, EnumOrientationTondeuse.N);
		allTondeuses.add(t1);
		TondeuseCommandes c1 = new TondeuseCommandes(t1, "GAGAGAGAA");
		allCommandes.add(c1);

		Tondeuse t2 = new Tondeuse(2, 3, 3, EnumOrientationTondeuse.E);
		allTondeuses.add(t2);
		TondeuseCommandes c2 = new TondeuseCommandes(t2, "AADAADADDA");
		allCommandes.add(c2);

		new TondeuseApp(allCommandes, allTondeuses, jardinX, jardinY).startApp();
	}
}
