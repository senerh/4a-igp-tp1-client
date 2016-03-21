package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Classe représentant la base de données des messages et des mots de passe
 * 
 * @author GERLAND - LETOURNEUR
 */
public class GestionFichiers {

	/**
	 * Lecture des messages dans la boite aux lettres associée à l'identifiant
	 * @param identifiant
	 * @return ListeMessages
	 */
	public static ListeMessages LireMessages(String identifiant) {
		
		ListeMessages messages = new ListeMessages();
		String filePath = new File("").getAbsolutePath();
		filePath += "/Fichiers/boites/" + identifiant + ".txt";
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
		 
			try {
				String line;
				int i = 0;
				while ((line = buff.readLine()) != null) {
					Message nouveauMessage = new Message();
					nouveauMessage.setNumero(i+1);
					nouveauMessage.setTailleOctets(line.length());
					nouveauMessage.setMarque(false);
					nouveauMessage.setCorps(line);
					messages.add(nouveauMessage);

					i++;
				}
			} finally {
				buff.close();
			}
		} catch (FileNotFoundException fnfe) { System.out.println("Fichier de messages introuvable");
		} catch (IOException e) { System.out.println("Erreur IO --" + e.toString()); }
		
		return messages;
	}
	
	/**
	 * Suppression des messages dans la boite aux lettres associée à l'identifiant
	 * @param identifiant
	 * @param listeMessages
	 */
	public static void SupprimerMessages(String identifiantClient, ListeMessages listeMessages) {
		
		String filePath = new File("").getAbsolutePath();
		filePath += "/Fichiers/boites/" + identifiantClient + ".txt";
		String tempFile = new File("").getAbsolutePath();
		tempFile += "/Fichiers/tmp.txt";
		File f = new File(filePath);
        File tmp = new File(tempFile);
        
		try {
	        BufferedReader reader = new BufferedReader(new FileReader(filePath));
	        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	
	        String line;
	        int i = 0;
	
	        while((line = reader.readLine()) != null) {
	            if(!listeMessages.get(i).getMarque())
	            {
	            	writer.write(line + "\n");
	            }
	            i++;
	        }
	        
	        writer.close(); 
	        reader.close();
	        
	        Files.copy(tmp.toPath(), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
		} catch (FileNotFoundException fnfe) { System.out.println("Fichier de messages introuvable");
		} catch (IOException e) { System.out.println("Erreur IO --" + e.toString()); }
	}
	
	/**
	 * Authentification d'un utilisateur par son identifiant et son mot de passe
	 * @param identifiant
	 * @param motDePasse
	 * @return boolean Succès de l'authentification
	 */
	public static boolean LireAuthentification(String identifiant, String motDePasse) {
		
		String filePath = new File("").getAbsolutePath();
		filePath += "/Fichiers/authentifications.txt";
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
		 
			try {
				String line;
				String[] parts;
				while ((line = buff.readLine()) != null) {
					parts = line.split(";");
					if(motDePasse == null) {
						if(parts[0].equals(identifiant))
							return true;
					} else {
						if(parts[0].equals(identifiant)
						&& parts[1].equals(motDePasse))
							return true;
					}
				}
			} finally {
				buff.close();
			}
		} catch (FileNotFoundException fnfe) { System.out.println("Fichier d'authentification introuvable");
		} catch (IOException e) { System.out.println("Erreur IO --" + e.toString()); }
		
		return false;
	}
}
