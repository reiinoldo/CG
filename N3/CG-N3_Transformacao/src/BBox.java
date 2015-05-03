import javax.media.opengl.GL;

public class BBox {
	
	private double xmin; /// valor Xmin.
	private double ymin; /// valor Ymin.
	private double xmax; /// valor Xmax.
	private double ymax; /// valor Ymax.
	private GL gl;
	
	 /// Cria o ponto (0,0,0,1).
	public BBox(GL gl) {
		this.gl = gl;
	}
		
	public void desenhaBB(){
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glLineWidth(1f);
		gl.glBegin(GL.GL_LINE_LOOP);
		{
			gl.glVertex2d(xmin, ymax);
			gl.glVertex2d(xmax, ymax);
			gl.glVertex2d(xmax, ymin);
			gl.glVertex2d(xmin, ymin);		
			
			System.out.println("xmin " + xmin);
			System.out.println("xmax " + xmax);
			System.out.println("ymin " + ymin);
			System.out.println("ymax " + ymax);
			
		}
		
		gl.glEnd();

		gl.glFlush();			
	}
	
	/// Obter valor X minimo.
	public double obterXmin() {
		return xmin;
	}
	
	/// Obter valor Y minimo
	public double obterYmin() {
		return ymin;
	}
	
	/// Obter valor X máximo.
	public double obterXmax() {
		return xmax;
	}
	
	/// Obter valor Y máximo.
	public double obterYmax() {
		return ymax;
	}

	
	/// Setar valor X minimo.
	public void setarXmin(double xmin) {
		this.xmin = xmin;
	}
	
	/// Setar valor Y minimo
	public void setarYmin(double ymin) {
		this.ymin = ymin;
	}
	
	/// Setar valor X máximo.
	public void setarXmax(double xmax) {
		this.xmax = xmax;
	}
	
	/// Setar valor Y máximo.
	public void setarYmax(double ymax) {
		this.ymax = ymax;
	}
	
	/// Verifica se está dentro da bound box
	public boolean dentroDoBbox(double x, double y) {
		return x > xmin && x < xmax && y > ymin && y < ymax;
	}	

}
