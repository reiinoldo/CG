/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaço de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1f, 1f, 1f, 0f);
	}
	
	public void SRU() {
//		gl.glDisable(gl.GL_TEXTURE_2D);
//		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and texture is enabled

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2f( -200.0f, 0.0f );
			gl.glVertex2f(  200.0f, 0.0f );
			gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2f(  0.0f, -200.0f);
			gl.glVertex2f(  0.0f, 200.0f );
		gl.glEnd();
	}

	//exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_PROJECTION);
		 gl.glLoadIdentity();
		 glu.gluOrtho2D( ortho2D_minX,  ortho2D_maxX,  ortho2D_minY,  ortho2D_maxY);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();

		 SRU();
		 
		 // seu desenho ...
		 gl.glColor3f(0.0f, 0.0f, 1.0f);		 		 
		 gl.glPointSize(2.0f);
		 gl.glBegin(GL.GL_POINTS);
		 	for (int i = 0; i < 72; i++) {
		 		gl.glVertex2d(RetornaX(i * 10, 90), RetornaY(i * 10, 90));
			}
		    
		 gl.glEnd();

		 gl.glFlush();
	}	

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---" + e.getKeyChar());
		
		switch(e.getKeyChar()){
		case 'i':
			updateOrtho(50f, -50f, 50f, -50f);
			break;
		case 'o':
			updateOrtho(-50f, 50f, -50f, 50f);
			break;
		case 'e':
			break;
		case 'd':
			break;
		case 'c':
			break;
		case 'b':
			break;
		}
		
		System.out.println(this.ortho2D_maxX);
		System.out.println(this.ortho2D_maxY);
		
		System.out.println(this.ortho2D_minX);
		System.out.println(this.ortho2D_minY);		
		glDrawable.display();
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		System.out.println(" --- reshape ---");
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}
	
	public double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}
	
	public double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}
	
	private void updateOrtho(float minX, float maxX, float minY, float maxY){
		this.ortho2D_minX += minX;
		this.ortho2D_maxX += maxX;
		
		this.ortho2D_minY += minY;
		this.ortho2D_maxY += maxY;
		
		if(this.ortho2D_minX > -100)
			this.ortho2D_minX = -100f;
		if(this.ortho2D_minY > -100)
			this.ortho2D_minY = -100f;
		
		if(this.ortho2D_minX < -500)
			this.ortho2D_minX = -500f;
		if(this.ortho2D_minY < -500)
			this.ortho2D_minY = -500f;
		
		if(this.ortho2D_maxX < 100)
			this.ortho2D_maxX = 100f;
		if(this.ortho2D_maxY < 100)
			this.ortho2D_maxY = 100f;
		
		if(this.ortho2D_maxX > 500)
			this.ortho2D_maxX = 500f;
		if(this.ortho2D_maxY > 500)
			this.ortho2D_maxY = 500f;		
	}
	
}
