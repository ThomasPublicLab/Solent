package solent.test;

public class Tondeuse {
	private int id;
	private int posX, posY;
	private EnumOrientationTondeuse orientation;
	
	public Tondeuse(int id, int posX, int posY, EnumOrientationTondeuse orientation) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public EnumOrientationTondeuse getOrientation() {
		return orientation;
	}
	public void setOrientation(EnumOrientationTondeuse orientation) {
		this.orientation = orientation;
	}
	
	@Override
	public String toString() {
		return "Tondeuse [id=" + id + ", posX=" + posX + ", posY=" + posY + ", orientation=" + orientation + "]";
	}
}
