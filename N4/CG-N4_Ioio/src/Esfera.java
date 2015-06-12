
public class Esfera extends ObjetoSolido{
	
	private int raio;
	private int slice;
	private int stacks; 
	
	public Esfera(int raio, int slice, int stacks){
		this.raio = raio;
		this.slice = slice;
		this.stacks = stacks;
	}
	
	@Override
	public void desenha(){
		gl.glPushMatrix();
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
		glut.glutSolidSphere(raio, slice, stacks);
		gl.glPopMatrix();
	}
}
