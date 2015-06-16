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
	
	public float getxIni() {
		return xIni;
	}

	public void setxIni(float xIni) {
		this.xIni = xIni;
	}

	public float getyIni() {
		return yIni;
	}

	public void setyIni(float yIni) {
		this.yIni = yIni;
	}

	public float getzIni() {
		return zIni;
	}

	public void setzIni(float zIni) {
		this.zIni = zIni;
	}

	public float getxFim() {
		return xFim;
	}

	public void setxFim(float xFim) {
		this.xFim = xFim;
	}

	public float getYfim() {
		return yfim;
	}

	public void setYfim(float yfim) {
		this.yfim = yfim;
	}

	public float getzFim() {
		return zFim;
	}

	public void setzFim(float zFim) {
		this.zFim = zFim;
	}
}
