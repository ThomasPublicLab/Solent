package solent.test;

public class HubCommunication implements IHubCommunication{

	@Override
	public void communicate(Tondeuse tondeuse, EnumCoherencePositionTondeuse coherence) {
		System.out.println(tondeuse.toString() +" "+ coherence);
	}

	@Override
	public void alerter(Tondeuse tondeuse, EnumCommandesTondeuse nextCommand, EnumCoherencePositionTondeuse coherence) {
		System.err.println("Une erreur est survenue avec la programmation de la tondeuse !");
		System.err.println(tondeuse.toString());
		System.err.println("command :" + nextCommand);
		System.err.println(coherence);
	}
	
}
