import javax.media.opengl.GL;


public class Ioio extends ObjetoSolido{
	
	private float raioInterno;
	private float raioExterno;
	private float comprimento;
	private float profundidade; 
	private int qtdDes;
	private boolean descer;	
	private int qtdFrente;
	private Linha linha;
	
	
	public Ioio(float raioInterno, float raioExterno, float comprimento, float profundidade, GL gl, Linha linha){
		this.raioInterno = raioInterno;
		this.raioExterno = raioExterno;
		this.comprimento = comprimento;
		this.profundidade = profundidade;
		this.gl = gl;
		this.qtdDes = 0;
		this.linha = linha;
		this.translacaoXYZ(0f, 10f, 0f);		
	}
	
	@Override
	public void desenha(){
		gl.glPushMatrix();
		movGiro();
		movFrente();
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
		this.montaPartes();
		gl.glPopMatrix();
	}
	
	public void girar(){
		this.descer = true;
		this.qtdDes = 1;
		System.out.println("Girou");
	}
	
	private void movGiro(){
		if(qtdDes > 0){
			if(descer == true){
				qtdDes++;
				this.translacaoXYZ(0, -1, 0);
				//this.yfim = this.yfim - 1;
				linha.setYfim(linha.getYfim() - 1);
			}
			else{
				qtdDes--;
				if(qtdDes > 0){
				  this.translacaoXYZ(0, 1, 0);
				  linha.setYfim(linha.getYfim() + 1);
				}
				
			}
			
			if(qtdDes == 15){
				descer = false;
			}
		}		
	}
	
	public void frente(){
		this.descer = true;
		this.qtdFrente = 1;
		System.out.println("frente");
	}
	
	private void movFrente(){
		if(qtdFrente > 0){
			if(descer == true){
				qtdFrente++;
				if(qtdFrente <= 10){
				  this.translacaoXYZ(-0.5, -1, 0);
				  linha.setxFim(linha.getxFim() - 0.5f);
				  linha.setYfim(linha.getYfim() - 1.0f);
				}
				else{
					this.translacaoXYZ(0.5, -1, 0);
					linha.setxFim(linha.getxFim() + 0.5f);
					linha.setYfim(linha.getYfim() - 1.0f);
				}
				if(qtdFrente == 20)
					descer = false;
			} else{
				qtdFrente--;
				if(qtdFrente <= 10){
					if(qtdFrente > 0){
						this.translacaoXYZ(-0.5, 1, 0);
						linha.setxFim(linha.getxFim() - 0.5f);
					    linha.setYfim(linha.getYfim() + 1.0f);
					}
				} else{
					this.translacaoXYZ(0.5, 1, 0);
					linha.setxFim(linha.getxFim() + 0.5f);
					linha.setYfim(linha.getYfim() + 1.0f);
				}
			}
		}
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
		//gl.glShadeModel(GL.GL_SMOOTH);

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

	public int getQtdDes() {
		return qtdDes;
	}
	
	public int getQtdFrente() {
		return qtdFrente;
	}
}
