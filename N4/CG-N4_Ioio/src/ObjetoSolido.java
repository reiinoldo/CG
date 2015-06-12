import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public abstract class ObjetoSolido {
	protected static Transformacao4D matrizGlobal = new Transformacao4D();
	protected static Transformacao4D matrizTmpTranslacao = new Transformacao4D();
	protected static Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
	protected static Transformacao4D matrizTmpEscala = new Transformacao4D();
	protected Transformacao4D matrizObjeto = new Transformacao4D();
	protected GL gl;
	protected GLUT glut;
	protected float R;
	protected float G;
	protected float B;
	
	public ObjetoSolido(){
		glut = new GLUT();
	}
	
	public void atribuirGL(GL gl) {
		this.gl = gl;
	}
	
	public void desenha(){};
	
	public void translacaoXYZ(double tx, double ty, double tz) {
		Transformacao4D matrizTranslate = new Transformacao4D();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);		
	}	

	public void escalaXYZ(double Sx,double Sy) {
		Transformacao4D matrizScale = new Transformacao4D();		
		matrizScale.atribuirEscala(Sx,Sy,1.0);
		matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
	}

	///TODO: erro na rotacao
	public void rotacaoZ(double angulo) {
//		anguloGlobal += 10.0; // rotacao em 10 graus
//		Transformacao4D matrizRotacaoZ = new Transformacao4D();		
//		matrizRotacaoZ.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
//		matrizObjeto = matrizRotacaoZ.transformMatrix(matrizObjeto);
	}
	
	public void atribuirIdentidade() {
//		anguloGlobal = 0.0;
		matrizObjeto.atribuirIdentidade();
	}	

	public void escalaXYZPtoFixo(double escala, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}
	
	public void rotacaoZPtoFixo(double angulo, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}

	public void exibeMatriz() {
		matrizObjeto.exibeMatriz();
	}

	public float getR() {
		return R;
	}

	public void setR(float r) {
		R = r;
	}

	public float getG() {
		return G;
	}

	public void setG(float g) {
		G = g;
	}

	public float getB() {
		return B;
	}

	public void setB(float b) {
		B = b;
	}
}
