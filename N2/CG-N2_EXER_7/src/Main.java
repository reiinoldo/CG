/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private float ortho2D_minX = -400.0f, ortho2D_maxX =  400.0f, ortho2D_minY = -400.0f, ortho2D_maxY =  400.0f;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private int antigoX, antigoY = 0;
	private double xCircMaior = 200;
	private double yCircMaior = 200;
	private double xCircMenor = 200;
	private double yCircMenor = 200;
	private double raioCircMaior = 150;
	private double raioCircMenor = 50;
	private double xMinBbox = RetornaX(135, raioCircMaior) + xCircMaior;
	private double xMaxBbox = RetornaX( 45, raioCircMaior) + xCircMaior;
	private double yMinBbox = RetornaY(225, raioCircMaior) + yCircMaior;
	private double yMaxBbox = RetornaY(135, raioCircMaior) + yCircMaior;
	//Cores do bbox num vetor, onde 0 = R, 1 = G, 2 = B
	private float[] corBbox = {0f, 1f, 1f};

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
		 
		//Desenhando os círculos
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(1f);
		
		//Círculo maior
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i <= 360; i += 5) {
			gl.glVertex2d(RetornaX(i, raioCircMaior) + xCircMaior, RetornaY(i, raioCircMaior) + yCircMaior);
		}
		gl.glEnd();
		
		//Círculo menor
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i <= 360; i += 5) {
			gl.glVertex2d(RetornaX(i, raioCircMenor) + xCircMenor, RetornaY(i, raioCircMenor) + yCircMenor);
		}
		gl.glEnd();
		
		//Ponto no centro do círculo menor
		gl.glPointSize(6f);
		gl.glBegin(GL.GL_POINTS);
		{
			gl.glVertex2d(xCircMenor, yCircMenor);
		}
		gl.glEnd();
		 
		//Desenhando o Bbox
		gl.glColor3f(corBbox[0], corBbox[1], corBbox[2]);
		gl.glLineWidth(1f);
		gl.glBegin(GL.GL_LINE_LOOP);
		{
			gl.glVertex2d(yMaxBbox, xMinBbox);
			gl.glVertex2d(yMaxBbox, xMaxBbox);
			gl.glVertex2d(yMinBbox, xMaxBbox);
			gl.glVertex2d(yMinBbox, xMinBbox);
		}
		gl.glEnd();

		gl.glFlush();		 
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
		}
		
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
	
	@Override
	public void mouseDragged(MouseEvent e) {
		double xMov = e.getX() - antigoX;
		double yMov = e.getY() - antigoY;
		double xNovo = xCircMenor + (xMov + (xMov * 1));
		double yNovo = yCircMenor - (yMov + (yMov * 1));

		if (dentroDoBbox(xNovo, yNovo)) {
			xCircMenor = xNovo;
			yCircMenor = yNovo;
			corBbox[0] = 0f;
			corBbox[1] = 1f;
			corBbox[2] = 1f;
		} else {
			if (dentroDoCirculoMaior(xNovo, yNovo)) {
				xCircMenor = xNovo;
				yCircMenor = yNovo;
				corBbox[0] = 1f;
				corBbox[1] = 0f;
				corBbox[2] = 1f;
			} else {
				corBbox[0] = 1f;
				corBbox[1] = 1f;
				corBbox[2] = 0f;
			}
		}
		antigoX = e.getX();
		antigoY = e.getY();
		glDrawable.display();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		antigoX = arg0.getX();
        antigoY = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}
	
	public double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}
	
	public boolean dentroDoCirculoMaior(double x, double y) {
		return Math.pow(raioCircMaior, 2) >= Math.pow(x - xCircMaior, 2) + Math.pow(y - yCircMaior, 2);
	}

	public boolean dentroDoBbox(double x, double y) {
		return x > xMinBbox && x < xMaxBbox && y > yMinBbox && y < yMaxBbox;
	}
	
}