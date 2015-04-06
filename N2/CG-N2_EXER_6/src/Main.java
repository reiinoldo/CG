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
	
	// posição de qual vertice será movimentado
	private int opcao = 0;
	
	// posição nos vetores
	static final int X = 0;
	static final int Y = 1;
	
	// qtd de pontos da spline
	private int qtdPontos = 10;
	
	// salva os 4 pontos
	private float[][] pontos = new float[4][2];	

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaço de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1f, 1f, 1f, 0f);
		
		// Inicia o desenho padrão
		pontos[0][X] = -100;
		pontos[0][Y] = -100;
		pontos[1][X] = -100;
		pontos[1][Y] =  100;
		pontos[2][X] =  100;
		pontos[2][Y] =  100;
		pontos[3][X] =  100;
		pontos[3][Y] = -100;
					
		
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
		 
		 // Poliedro
		 // |
		 paintLine(pontos[0][X], pontos[0][Y],
				   pontos[1][X], pontos[1][Y]);
		 // -
		 paintLine(pontos[1][X], pontos[1][Y],
				   pontos[2][X], pontos[2][Y]);		 
		 // |
		 paintLine(pontos[2][X], pontos[2][Y],
				   pontos[3][X], pontos[3][Y]);
		 
		 // Spline
		 desenhaSpline();
		 
		 // Ponto em algum dos vértices do poliedro
		 desenhaPonto();
		 
		 gl.glFlush();		 
	}	
	
	public void desenhaPonto(){		 
		 gl.glColor3f(1.0f, 0.0f, 0.0f);
		 gl.glPointSize(4.0f);
		 gl.glBegin( GL.GL_POINTS);
		 	 gl.glVertex2f( pontos[opcao][X], pontos[opcao][Y] );				
		 gl.glEnd();
	}
	public void paintLine(float line1PositionX, float line1PositionY,
			  			  float line2PositionX, float line2PositionY){
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glBegin( GL.GL_LINE_STRIP);
			gl.glVertex2f( line1PositionX, line1PositionY );
			gl.glVertex2f( line2PositionX, line2PositionY );
		gl.glEnd();
	}
	
	public float[] splineInterpolacao(float[] P1, float[] P2, int t){
		float [] posicao = new float[2];
		posicao[X] = P1[X] + (P2[X] - P1[X]) * t/qtdPontos;
		posicao[Y] = P1[Y] + (P2[Y] - P1[Y]) * t/qtdPontos;
		return posicao;
	}
	
	public void desenhaSpline(){		
		float [] P1P2, P2P3, P3P4, P1P2P3, P2P3P4, P1P2P3P4;		
		
			gl.glColor3f(1.0f, 1.0f, 0.0f);		 		 
			gl.glLineWidth(1.0f);
			gl.glBegin(GL.GL_LINE_STRIP);
			
			for (int i = 0; i <= qtdPontos; i++) {
				
				P1P2 = splineInterpolacao(pontos[0], pontos[1], i);			
				P2P3 = splineInterpolacao(pontos[1], pontos[2], i);
				P3P4 = splineInterpolacao(pontos[2], pontos[3], i);
				P1P2P3 = splineInterpolacao(P1P2, P2P3, i);
				P2P3P4 = splineInterpolacao(P2P3, P3P4, i);
				P1P2P3P4 = splineInterpolacao(P1P2P3, P2P3P4, i);
				
			 	gl.glVertex2d(P1P2P3P4[X],P1P2P3P4[Y]);
			 	
			}		
			
			gl.glEnd(); 		    
							
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
		case '1':
			opcao = 0;
			break;
		case '2':
			opcao = 1;
			break;
		case '3':
			opcao = 2;
			break;
		case '4':
			opcao = 3;
			break;
		case '-':
			if (qtdPontos > 1) 
				qtdPontos -= 1;			 
			break;
		case '+':
			qtdPontos += 1;
			break;
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
		// TODO Auto-generated method stub
		int movtoX = e.getX() - antigoX;
	    int movtoY = e.getY() - antigoY;
	    pontos[opcao][X] += movtoX;
	    pontos[opcao][Y] -= movtoY;
	    
	    //Dump ...
	    System.out.println("posMouse: "+movtoX+" / "+movtoY);
	    
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
	
}