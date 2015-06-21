import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import object.OBJModel;

import com.sun.opengl.util.GLUT;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private GLUT glut;
	boolean criandoObjeto;
	char ultimaTecla;
	private Ioio ioio;
	private Linha linha;
	private OBJModel mao;
	int framebufferID;
	
	private float view_rotx = 0.0f, view_roty = 0.0f, view_rotz = 0.0f;
	private int prevMouseX, prevMouseY;
	
	/** "render" feito logo apos a inicializacao do contexto OpenGL. **/
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		float pos[] = {0.0f, 20.0f, 20.0f, 0.5f };
		float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
		
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
	    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
	    //gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_LIGHT1);
	    gl.glEnable(GL.GL_LIGHT0);
	    gl.glEnable(GL.GL_DEPTH_TEST);	    
		
		gl.glEnable(GL.GL_NORMALIZE);
		
		//Criando linha
		linha = new Linha(-1.5f, 14f, 0f, 0f, 10.5f, 0f, gl);
		
		//Criando Ioio
		ioio = new Ioio(0.5f, 2f, 1f, 1f, gl, linha);
		
		//Criando mao
		mao = new OBJModel("data/hand", 10f, gl, true);		

	}

	/**  metodo definido na interface GLEventListener.
	     "render" feito pelo cliente OpenGL. **/	     
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glShadeModel( GL.GL_SMOOTH );

		// configurar window
		//glu.gluOrtho2D(-240.0f, 240.0f, -230.0f, 230.0f);
		glu.gluPerspective(45, 1, 1, 1000);
		glu.gluLookAt(0, 0, -50, 0, 0, 0, 0, 1, 0);

		//desenhaSRU();
		
		//rotacionando os objetos da cena
	    gl.glPushMatrix();
	    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
	    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
	    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);
	    
	    gl.glPushMatrix();
	    gl.glTranslatef(0f, 15f, 0f);
	    mao.draw(gl);
	    gl.glPopMatrix();
	    
		ioio.desenha();
		
		linha.desenha();
		
		gl.glPopMatrix();
		gl.glFlush();
	}

	/** desenha a cruz (X -->, Y î ) **/
	public void desenhaSRU() {	
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2f( -120.0f, 0.0f );
			gl.glVertex2f(  120.0f, 0.0f );
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2f(  0.0f, -115.0f);
			gl.glVertex2f(  0.0f, 115.0f );
		gl.glEnd();
	}
	
	/** Dependendo da tecla pressionada fará as alterações nos objetos desenhados **/
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_R:
			glDrawable.display();
			break;
			
		case KeyEvent.VK_B:
			ioio.frente();
			while(ioio.getQtdFrente() > 0){
				glDrawable.display();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			break;
		
		case KeyEvent.VK_G:
			ioio.girar();
			while(ioio.getQtdDes() > 0){
				glDrawable.display();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			break;
		}

		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// System.out.println(" --- reshape ---");
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}

	public void mouseDragged(MouseEvent e) {		
	    int x = e.getX();
	    int y = e.getY();
	    Dimension size = e.getComponent().getSize();

	    float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)size.width);
	    float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)size.height);
	    
	    prevMouseX = x;
	    prevMouseY = y;

	    view_rotx += thetaX;
	    view_roty += thetaY;
	    
	    glDrawable.display();
	}

	public void mouseMoved(MouseEvent arg0) {
		//glDrawable.display();
	}

	public void mouseClicked(MouseEvent arg0) {		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}	
}
