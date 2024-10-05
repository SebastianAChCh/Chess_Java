package typesChess;

public class Positions {
	public int axisX, axisY;
	public TypeCell typeCell;
	
	public void setTypeCell(TypeCell typeCell) {
		this.typeCell = typeCell;
	}

	public Positions(int axisX, int axisY, TypeCell typeCell) {
		this.axisX = axisX;
		this.axisY = axisY;
		this.typeCell = typeCell;
	}
	
	
}
