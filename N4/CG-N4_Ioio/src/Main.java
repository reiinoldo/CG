import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

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
	private Cubo cubo;
	
	private float view_rotx = 0.0f, view_roty = 0.0f, view_rotz = 0.0f;
	private int prevMouseX, prevMouseY;
	
	/** "render" feito logo apos a inicializacao do contexto OpenGL. **/
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));
		//gl.glEnable(GL.GL_DEPTH_TEST);
		//gl.glEnable(GL.GL_CULL_FACE);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		
		float pos[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
	    //gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_LIGHT0);
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    
		ioio = new Ioio(1f, 4f, 1f, 1f, gl);
		gl.glEnable(GL.GL_NORMALIZE);
		//ioio.atribuirGL(gl);
		
		cubo = new Cubo(100);
		cubo.atribuirGL(gl);
		cubo.translacaoXYZ(0, 180, 0);
		cubo.setR(0);
		cubo.setG(0);
		cubo.setB(0);
//		objeto.atribuirGL(gl);
	}

	/**  metodo definido na interface GLEventListener.
	     "render" feito pelo cliente OpenGL. **/	     
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

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
	    
		desenhaIoio();	
		
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
	
	public void desenhaIoio(){		
		ioio.desenha();
	}
	
	/** Dependendo da tecla pressionada fará as alterações nos objetos desenhados **/
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
			break;*/
		
		case KeyEvent.VK_SPACE:
			break;

		/*case KeyEvent.VK_RIGHT:
			if(objGrafico != null){
				objGrafico.translacaoXYZ(2.0,0.0,0.0);
				objGrafico.podeDesenharFilho = true;
			}
			break;
		case KeyEvent.VK_LEFT:
			if(objGrafico != null){
				objGrafico.translacaoXYZ(-2.0,0.0,0.0);
				objGrafico.podeDesenharFilho = true;
			}
			break;
		case KeyEvent.VK_UP:
			if(objGrafico != null){
				objGrafico.translacaoXYZ(0.0,2.0,0.0);
				objGrafico.podeDesenharFilho = true;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(objGrafico != null){
				objGrafico.translacaoXYZ(0.0,-2.0,0.0);
				objGrafico.podeDesenharFilho = true;
			}
			break;

		case KeyEvent.VK_PAGE_UP:
			if(objGrafico != null){
				Ponto4D centroBBox = new Ponto4D();
				centroBBox.atribuirX((objGrafico.obterBBox().obterXmax() + objGrafico.obterBBox().obterXmin()) / 2);
				centroBBox.atribuirY((objGrafico.obterBBox().obterYmax() + objGrafico.obterBBox().obterYmin()) / 2);
				objGrafico.escalaXYZPtoFixo(2.0,centroBBox);
				objGrafico.podeDesenharFilho = true;
			}
			break;
		case KeyEvent.VK_PAGE_DOWN:
			if(objGrafico != null){
				Ponto4D centroBBox = new Ponto4D();
				centroBBox.atribuirX((objGrafico.obterBBox().obterXmax() + objGrafico.obterBBox().obterXmin()) / 2);
				centroBBox.atribuirY((objGrafico.obterBBox().obterYmax() + objGrafico.obterBBox().obterYmin()) / 2);
				objGrafico.escalaXYZPtoFixo(0.5, centroBBox);
				objGrafico.podeDesenharFilho = true;
			}
			break;

		case KeyEvent.VK_HOME:
			if(objGrafico != null){
				Ponto4D centroBBox = new Ponto4D();
				centroBBox.atribuirX((objGrafico.obterBBox().obterXmax() + objGrafico.obterBBox().obterXmin()) / 2);
				centroBBox.atribuirY((objGrafico.obterBBox().obterYmax() + objGrafico.obterBBox().obterYmin()) / 2);
				objGrafico.rotacaoZPtoFixo(10f,centroBBox);
			}
			break;*/

		/*case KeyEvent.VK_1:
			objetos[0].escalaXYZPtoFixo(0.5, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
		case KeyEvent.VK_2:
			objetos[0].escalaXYZPtoFixo(2.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;
			
		case KeyEvent.VK_3:
			objetos[0].rotacaoZPtoFixo(10.0, new Ponto4D(-15.0,-15.0,0.0,0.0));
			break;*/
			//Desenhar poligno aberto
		/*case KeyEvent.VK_A:
			desenharPoligono(GL.GL_LINE_STRIP,'a');
			break;
			//Desenhar poligono fechado
		case KeyEvent.VK_F:
			desenharPoligono(GL.GL_LINE_LOOP,'f');
			break;
			//Desenhar os poligonos em vermelho
		case KeyEvent.VK_R:
			if(objGrafico != null){
				objGrafico.atribuirCor(1.0f, 0.0f, 0.0f);
			}
			break;
			//Desenhar os poligonos em verde
		case KeyEvent.VK_G:
			if(objGrafico != null){
				objGrafico.atribuirCor(0.0f, 1.0f, 0.0f);
			}
			break;
			//Desenhar os poligonos em azul
		case KeyEvent.VK_B:
			if(objGrafico != null){
				objGrafico.atribuirCor(0.0f, 0.0f, 1.0f);
			}
			break;
			//Desenhar os poligonos em preto
		case KeyEvent.VK_P:
			if(objGrafico != null){
				objGrafico.atribuirCor(0.0f, 0.0f, 0.0f);
			}
			break;
			//Deletara ponto selecionado
		case KeyEvent.VK_D:
			for (ObjetoGrafico objetoGrafico : objetos) {
				objetoGrafico.deletarSelecionado();
			}
			verticeSelecionado = null;
			break;
			//Ao apertar insert adicionará filhos ao polígono, e ao apertar novamente não fará relação ao poligono selecionado
		case KeyEvent.VK_INSERT:
			inserirFilhos = !inserirFilhos;			
			break;	
		case KeyEvent.VK_DELETE:
			if (objGrafico != null) // ta meio fail
				objGrafico.deletarObjeto();			
			break;*/			
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
