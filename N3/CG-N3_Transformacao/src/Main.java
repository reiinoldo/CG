import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private ArrayList<ObjetoGrafico> objetos = new ArrayList<ObjetoGrafico>();
	private ObjetoGrafico objGrafico;
	boolean criandoObjeto;
	char ultimaTecla;
	private static final int ORIGEM_X = 240;
	private static final int ORIGEM_Y = 230;
	private double ultimoX;
	private double ultimoY;
	private double atualX;
	private double atualY;
	private boolean desenharRastro;
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		criandoObjeto = false;

		for (ObjetoGrafico objetoGrafico : objetos) {
			objetoGrafico.atribuirGL(gl);
		}
//		objeto.atribuirGL(gl);
	}

	// metodo definido na interface GLEventListener.
	// "render" feito pelo cliente OpenGL.
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		// configurar window
		glu.gluOrtho2D(-240.0f, 240.0f, -230.0f, 230.0f);

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);

		desenhaSRU();
		
		for (ObjetoGrafico objetoGrafico : objetos) {
			objetoGrafico.desenha();
		}
		
		gl.glColor3f(0.3f, 0.6f, 0.0f);
		gl.glLineWidth(2);
		gl.glPointSize(2);		
		desenhaRastro();
		
		gl.glFlush();
	}

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
	
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		/*case KeyEvent.VK_P:
			objetos[0].exibeVertices();
			break;
		case KeyEvent.VK_M:
			objetos[0].exibeMatriz();
			break;

		case KeyEvent.VK_R:
			objetos[0].atribuirIdentidade();
			break;

		case KeyEvent.VK_RIGHT:
			objetos[0].translacaoXYZ(2.0,0.0,0.0);
			break;
		case KeyEvent.VK_LEFT:
			objetos[0].translacaoXYZ(-2.0,0.0,0.0);
			break;
		case KeyEvent.VK_UP:
			objetos[0].translacaoXYZ(0.0,2.0,0.0);
			break;
		case KeyEvent.VK_DOWN:
			objetos[0].translacaoXYZ(0.0,-2.0,0.0);
			break;

		case KeyEvent.VK_PAGE_UP:
			objetos[0].escalaXYZ(2.0,2.0);
			break;
		case KeyEvent.VK_PAGE_DOWN:
			objetos[0].escalaXYZ(0.5,0.5);
			break;

		case KeyEvent.VK_HOME:
//			objetos[0].RoracaoZ();
			break;

		case KeyEvent.VK_1:
			objetos[0].escalaXYZPtoFixo(0.5, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
		case KeyEvent.VK_2:
			objetos[0].escalaXYZPtoFixo(2.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
		case KeyEvent.VK_3:
			objetos[0].rotacaoZPtoFixo(10.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;*/
			//Desenhar poligno aberto
		case KeyEvent.VK_A:
			desenharPoligono(GL.GL_LINE_STRIP,'a');
			break;
			//Desenhar poligono fechado
		case KeyEvent.VK_F:
			desenharPoligono(GL.GL_LINE_LOOP,'f');
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

	public void mouseDragged(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent arg0) {
		atualX = arg0.getX() - ORIGEM_X;
		atualY = (arg0.getY() - ORIGEM_Y) * -1;
		glDrawable.display();
	}

	public void mouseClicked(MouseEvent arg0) {	
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		if(criandoObjeto == true){
			Ponto4D ponto = new Ponto4D();
			ponto.atribuirX(arg0.getX() - ORIGEM_X);
			ponto.atribuirY((arg0.getY() - ORIGEM_Y) * -1);
			ponto.atribuirZ(0);
			
			objGrafico.addPonto4D(ponto);
			System.out.println("ponto adicionado! X: " + ponto.obterX() + " Y: " + ponto.obterY());
			ultimoX = ponto.obterX();
			ultimoY = ponto.obterY();
			desenharRastro = true;
			
			glDrawable.display();
		}	
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void desenharPoligono(int primitiva, char tecla){
		System.out.println("Tecla pressionada!");
		if(criandoObjeto == false){
			objGrafico = new ObjetoGrafico(primitiva, gl);
			objetos.add(objGrafico);
			criandoObjeto = true;
			ultimaTecla = tecla;
		} else if(ultimaTecla == tecla){
			System.out.println("Encerrando poligono");
			criandoObjeto = false;
			desenharRastro = false;
		}	
	}
	
	private void desenhaRastro() {
		if(desenharRastro == true){			
			gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(ultimoX, ultimoY);
				gl.glVertex2d(atualX, atualY);
			gl.glEnd();
		}
	}
}
