
public class Cubo extends ObjetoSolido{
	
	private int tamanho;	
	
	public Cubo(int tamanho){
		this.tamanho = tamanho;		
	}
	
	@Override
	public void desenha(){
		gl.glPushMatrix();
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
		glut.glutSolidCube(tamanho);
		gl.glPopMatrix();
	}
}
