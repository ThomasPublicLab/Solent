package solent.test;

public interface IHubCommunication {
	public void communicate(Tondeuse tondeuse, EnumCoherencePositionTondeuse coherence);
	public void alerter(Tondeuse tondeuse, EnumCommandesTondeuse nextCommand, EnumCoherencePositionTondeuse coherence);
}
