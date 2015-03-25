
package soh;

public class CPUOut {
	float presentCapacity;
	float initialCapacity;
	final static int day=1195; 
	public CPUOut(float presentCapacity, float initialCapacity) {
		// TODO Auto-generated constructor stub
		this.presentCapacity=presentCapacity;
		this.initialCapacity=initialCapacity;
	}

	public int getRUL() {
		// TODO Auto-generated method stub
		 int soh=this.getSOH();
		 int RUl=day*soh/100;
		return RUl;
	}

	public int getSOH() {
		// TODO Auto-generated method stub
		return  (int)((float)presentCapacity/(float)initialCapacity*100);
	}

}
