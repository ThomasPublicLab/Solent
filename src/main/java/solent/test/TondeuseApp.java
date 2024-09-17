package solent.test;

import java.util.List;
import java.util.Set;

public class TondeuseApp {
	private List<TondeuseCommandes> allCommandes;
	private Set<Tondeuse> allTondeuses;
	private int jardinX, jardinY;
	
	public TondeuseApp(List<TondeuseCommandes> allCommandes, Set<Tondeuse> allTondeuses, int jardinX, int jardinY) {
		this.allCommandes = allCommandes;
		this.allTondeuses = allTondeuses;
		this.jardinX = jardinX;
		this.jardinY = jardinY;
	}
	
	public void startApp() {
		IHubCommunication comm = new HubCommunication();
		boolean hasMoreCommand;
		
		do {
			hasMoreCommand = false;
			for(TondeuseCommandes c : allCommandes) {
				EnumCommandesTondeuse nextCommand = c.getNextCommand();
				if(nextCommand == null) 
					continue;
				hasMoreCommand = true;
				Tondeuse shallow = estimerProchainePositionTondeuse(c.getTondeuse(), nextCommand);
				EnumCoherencePositionTondeuse coherence = verifierCoherencePosition(shallow);

				switch(coherence) {
					case OK : deplacerTondeuse(c.getTondeuse(), nextCommand);
						break;
					case COLLISIONTONDEUSE:
						comm.alerter(c.getTondeuse(), nextCommand, coherence);
						deplacerTondeuse(c.getTondeuse(), EnumCommandesTondeuse.W);
						if(c.mettreCommandeEnAttente() > 3) {
							c.stopperTondeuse();
							comm.alerter(c.getTondeuse(), nextCommand, coherence);
						}
						break;
					case HORSJARDIN : 
						comm.alerter(c.getTondeuse(), nextCommand, coherence);
						c.stopperTondeuse();
						break;
				}
				comm.communicate(c.getTondeuse(), coherence);
			}
		} while(hasMoreCommand);
	}
	

	public Tondeuse estimerProchainePositionTondeuse(final Tondeuse t, EnumCommandesTondeuse action) {
		Tondeuse shallow = new Tondeuse(t.getId(), t.getPosX(), t.getPosY(), t.getOrientation());
		deplacerTondeuse(shallow, action);
		return shallow;
	}
	
	public void deplacerTondeuse(Tondeuse t, EnumCommandesTondeuse action) {
		switch(action) {
		case A : 
			switch(t.getOrientation()) {
				case N: t.setPosY(t.getPosY()+1);
				break;
				case S: t.setPosY(t.getPosY()-1);
				break;
				case E: t.setPosX(t.getPosX()+1);
				break;
				case W: t.setPosX(t.getPosX()-1);
				break;
			}
			break;
		case D:
			switch (t.getOrientation()) {
			case N: t.setOrientation(EnumOrientationTondeuse.E);
			break;
			case E: t.setOrientation(EnumOrientationTondeuse.S);
			break;
			case S: t.setOrientation(EnumOrientationTondeuse.W);
			break;
			case W: t.setOrientation(EnumOrientationTondeuse.N);
			break;
			}
			break;
		case G:
			switch (t.getOrientation()) {
			case N: t.setOrientation(EnumOrientationTondeuse.W);
			break;
			case W: t.setOrientation(EnumOrientationTondeuse.S);
			break;
			case S: t.setOrientation(EnumOrientationTondeuse.E);
			break;
			case E: t.setOrientation(EnumOrientationTondeuse.N);
			break;
			}
			break;
		case W : 
			//do nothing
			break;
		}
	}
	
	public EnumCoherencePositionTondeuse verifierCoherencePosition(Tondeuse t) {
		if(t.getPosX() < 0 || t.getPosX() > jardinX
			|| t.getPosY() < 0 || t.getPosY() > jardinY)
			return EnumCoherencePositionTondeuse.HORSJARDIN;

		
		for(Tondeuse tondeuse : allTondeuses) {
			if(tondeuse.getId() == t.getId())
				continue;
			if(t.getPosX() == tondeuse.getPosX()
					&& t.getPosY() == tondeuse.getPosY()) {
				return EnumCoherencePositionTondeuse.COLLISIONTONDEUSE;
			}
		}
		return EnumCoherencePositionTondeuse.OK;
	}
}
