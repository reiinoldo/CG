import javax.media.opengl.GL;


public class Ioio extends ObjetoSolido{
	
	private float raioInterno;
	private float raioExterno;
	private float comprimento;
	private float profundidade; 
	
	public Ioio(float raioInterno, float raioExterno, float comprimento, float profundidade, GL gl){
		this.raioInterno = raioInterno;
		this.raioExterno = raioExterno;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.gl = gl;		
	}
	
	@Override
	public void desenha(){
		gl.glPushMatrix();
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
		//glut.glutSolidSphere(raio, comprimento, profundidade);
		this.montaPartes();
		gl.glPopMatrix();
	}
	
	//public void gear(GL gl,float inner_radius,float outer_radius,float width,int teeth,float tooth_depth){
    public void montaPartes(){
		int i;
		float r0, r1;
		float angle, da;
		int pontos = 20;

		r0 = raioInterno;
		r1 = raioExterno - profundidade / 2.0f;

		da = 2.0f * (float) Math.PI / pontos / 4.0f;

		//gl.glShadeModel(GL.GL_FLAT);

		gl.glNormal3f(0.0f, 0.0f, 1.0f);

		/* frente */
		float red[] = { 0.8f, 0.1f, 0.0f, 1.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
		
		gl.glBegin(GL.GL_QUAD_STRIP);
		for (i = 0; i <= pontos; i++)
		{
			angle = i * 2.0f * (float) Math.PI / pontos;
			gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (comprimento + 2) * 0.5f);
			gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), comprimento * 0.5f);
			if(i < pontos)
			{
				gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (comprimento + 2) * 0.5f);
				gl.glVertex3f(r1 * (float)Math.cos(angle + 3.0f * da), r1 * (float)Math.sin(angle + 3.0f * da), comprimento * 0.5f);
			}
		}
		gl.glEnd();

		/* tras */
		gl.glBegin(GL.GL_QUAD_STRIP);
		for (i = 0; i <= pontos; i++)
		{
			angle = i * 2.0f * (float) Math.PI / pontos;
			gl.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -comprimento * 0.5f);
			gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (-comprimento - 2) * 0.5f);
			gl.glVertex3f(r1 * (float)Math.cos(angle + 3 * da), r1 * (float)Math.sin(angle + 3 * da), -comprimento * 0.5f);
			gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (-comprimento - 2) * 0.5f);
		}
		gl.glEnd();

		//gl.glShadeModel(GL.GL_SMOOTH);
		
		float grey[] = { 0.8f, 0.8f, 0.8f, 1.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, grey, 0);

		/* cilindro interno*/
		gl.glColor3f(0.7f, 0.7f, 0.7f);
		gl.glBegin(GL.GL_QUAD_STRIP);
		for (i = 0; i <= pontos; i++)
		{
			angle = i * 2.0f * (float) Math.PI / pontos;
			gl.glNormal3f(-(float)Math.cos(angle), -(float)Math.sin(angle), 0.0f);
			gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (-comprimento - 2) * 0.5f);
			gl.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), (comprimento + 2) * 0.5f);
		}
		gl.glEnd();
	}
}
