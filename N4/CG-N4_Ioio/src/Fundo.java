import javax.media.opengl.GL;


public class Fundo extends ObjetoSolido{
	
	public Fundo(GL gl) {
		
		this.gl = gl;
		
		loadImage("data/parque2.jpg");
		
		// Gera identificador de textura
		idTexture = new int[10];
		gl.glGenTextures(1, idTexture, 1);
		
		// Especifica qual é a textura corrente pelo identificador 
		gl.glBindTexture(GL.GL_TEXTURE_2D, idTexture[0]);	
		
		// Envio da textura para OpenGL
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, larguraImagem, 
				alturaImagem, 0, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, buffer);

		// Define os filtros de magnificação e minificação 
		gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
		gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
		gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR);	
		gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
	}

	@Override
	public void desenha(){
		// Desenha um cubo no qual a textura é aplicada
		gl.glEnable(GL.GL_TEXTURE_2D);	// Primeiro habilita uso de textura
				
		gl.glPushMatrix();
		gl.glScalef(100f, 100f, 0);		
		gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
		gl.glColor3f(this.R, this.G, this.B);
			  	
		gl.glBegin (GL.GL_QUADS );
		
		
		// Especifica a coordenada de textura para cada vértice
		// Face frontal
		gl.glNormal3f(0.0f,0.0f,-1.0f);
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
		
		gl.glEnd();
		
		gl.glPopMatrix();
		
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
	
}
