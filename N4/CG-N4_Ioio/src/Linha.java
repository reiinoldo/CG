import javax.media.opengl.GL;


public class Linha extends ObjetoSolido{
	
	private float xIni;
	private float yIni;
	private float zIni;
	
	private float xFim;
	private float yfim;
	private float zFim;
	
	public Linha(float xIni, float yIni, float zInin, float xFim, float yFim, float zFim, GL gl){
		this.xIni = xIni;
		this.yIni = yIni;
		this.zIni = zInin;
		this.xFim = zFim;
		this.yfim = yFim;
		this.zFim = zFim;
		this.gl = gl;
	}
	
	@Override
	public void desenha(){
		gl.glPushMatrix();
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(xIni, yIni, zIni);
			gl.glVertex3f(xFim, yfim, zFim);
		gl.glEnd();
		gl.glPopMatrix();
	}
}
