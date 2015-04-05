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
	private float origemX = 0f;
	private float origemY = 0f;
	private double raio = 100;
	private double angulo = 45;
	private int priGeometrica = 0;

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
		 gl.glPointSize(4.0f);
		 gl.glLineWidth(4.0f);
		 gl.glBegin(priGeometrica);
		 	gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glVertex2f(-200f, 200f);
			 	
			gl.glColor3f(1.0f, .0f, 1.0f);
			gl.glVertex2f(-200f, -200f);
			
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glVertex2f(200f, -200f);	
			 	
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex2f(200f, 200f);
			 	
		 	
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
			updateOrtho(50f, 50f, 0f, 0f);
			break;
		case 'd':
			updateOrtho(-50f, -50f, 0f, 0f);
			break;
		case 'c':
			updateOrtho(0f, 0f, -50f, -50f);
			break;
		case 'b':
			updateOrtho(0f, 0f, 50f, 50f);
			break;
		case 'q':
			updateOrigem(-10f);
			break;
		case 'w':
			updateOrigem(10f);
			break;
		case 'a':
			updateRaio(-10d);
			break;
		case 's':
			updateRaio(10d);
			break;
		case 'z':
			updateAngulo(-10d);
			break;
		case 'x':
			updateAngulo(10d);
			break;
		case ' ':
			nextPriGeometrica();
			break;
		}
		
		System.out.println("Max X: " + this.ortho2D_maxX);
		System.out.println("Max Y: " + this.ortho2D_maxY);
		
		System.out.println("Min X: " + this.ortho2D_minX);
		System.out.println("Min Y: " + this.ortho2D_minY);		
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
		if(this.ortho2D_minX + minX > -100)
			return;		
		if(this.ortho2D_minY + minY > -100)
			return;
		
		if(this.ortho2D_minX + minX < -700)
			return;
		if(this.ortho2D_minY + minY < -700)
			return;
		
		if(this.ortho2D_maxX + maxX < 100)
			return;
		if(this.ortho2D_maxY + maxY < 100)
			return;
		
		if(this.ortho2D_maxX + maxX > 700)
			return;
		if(this.ortho2D_maxY + maxY > 700)
			return;		
		
		this.ortho2D_minX += minX;
		this.ortho2D_maxX += maxX;
		
		this.ortho2D_minY += minY;
		this.ortho2D_maxY += maxY;
		
	}
	
	private void updateOrigem(float mov) {
		if(this.origemX + mov > 200 || this.origemX + mov < -200)
			return;
		
		this.origemX += mov;		
	}
	
	private void updateRaio(double mov){
		this.raio += mov;
	}
	
	private void updateAngulo(double mov){
		this.angulo += mov;
	}
	
	private void nextPriGeometrica(){
		this.priGeometrica++;
		if(this.priGeometrica >= 10)
			this.priGeometrica = 0;
	}
	
}
