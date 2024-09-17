package solent.test;

public class TondeuseCommandes {
	private Tondeuse tondeuse;
	private String commandes;
	private int nbCommandesRealisees;
	private int compteurCollision = 0;
	
	public TondeuseCommandes(Tondeuse tondeuse, String commandes) {
		this.tondeuse = tondeuse;
		this.commandes = commandes;
		this.nbCommandesRealisees = 0;
	}
	
	public Tondeuse getTondeuse() {
		return tondeuse;
	}

	public void setTondeuse(Tondeuse tondeuse) {
		this.tondeuse = tondeuse;
	}

	public String getCommandes() {
		return commandes;
	}

	public void setCommandes(String commandes) {
		this.commandes = commandes;
	}

	public int getNbCommandesRealisees() {
		return nbCommandesRealisees;
	}

	public void setNbCommandesRealisees(int nbCommandesRealisees) {
		this.nbCommandesRealisees = nbCommandesRealisees;
	}
	
	public EnumCommandesTondeuse getNextCommand() {
		if(nbCommandesRealisees >= commandes.length()) {
			return null;
		}
			
		String nextCommand = commandes.substring(nbCommandesRealisees, nbCommandesRealisees+1);
		nbCommandesRealisees++;
		return EnumCommandesTondeuse.valueOf(nextCommand);
	}
	
	public void stopperTondeuse() {
		nbCommandesRealisees = commandes.length();
	}
	
	public int mettreCommandeEnAttente() {
		nbCommandesRealisees--;
		return ++compteurCollision;
	}
}
