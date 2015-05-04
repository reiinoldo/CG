import java.util.ArrayList;

import javax.media.opengl.GL;
public class ObjetoGrafico {
	GL gl;
	private BBox bb;
	private float tamanho;
	private float R;
	private float G;
	private float B;

	private int primitiva; 
	private ArrayList<Ponto4D> vertices; 
	private ArrayList<ObjetoGrafico> filhos;

	private Transformacao4D matrizObjeto = new Transformacao4D();

	/// Matrizes temporarias que sempre sao inicializadas com matriz Identidade entao podem ser "static".
	private static Transformacao4D matrizTmpTranslacao = new Transformacao4D();
	private static Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
	private static Transformacao4D matrizTmpEscala = new Transformacao4D();		
//	private static Transformacao4D matrizTmpRotacaoZ = new Transformacao4D();
	private static Transformacao4D matrizGlobal = new Transformacao4D();
//	private double anguloGlobal = 0.0;
	
	public ObjetoGrafico(int primitiva, GL gl) {
		this.primitiva = primitiva;
		this.vertices = new ArrayList<Ponto4D>();
		this.tamanho = 2.0f;
		this.gl = gl;
		this.bb = new BBox(gl);
		this.filhos = new ArrayList<ObjetoGrafico>();
	}
	
	public Transformacao4D obterT4D(){
		return this.matrizObjeto;
	}
	
	public void setFilho(ObjetoGrafico filho){
		this.filhos.add(filho);
	}
	
	public boolean temFilho(){
		return filhos.size() > 0;
	}
	
	public BBox obterBB(){
		return bb;
	}
	
	public void addPonto4D(Ponto4D ponto){
		this.vertices.add(ponto);
	}
	
	public void removePonto4D(Ponto4D ponto){
		this.vertices.remove(ponto);
	}

	public void atribuirGL(GL gl) {
		this.gl = gl;
	}

	public double obterTamanho() {
		return tamanho;
	}

	public double obterPrimitava() {
		return primitiva;
	}
	
	public void desenha() {
		gl.glLineWidth(tamanho);
		gl.glPointSize(tamanho);
		
		double pontoX = 0, pontoY = 0;

		gl.glPushMatrix();
			gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
			gl.glBegin(primitiva);
			    for (Ponto4D ponto4D : vertices) {
					gl.glColor3f(R,G,B);
			    	if(ponto4D.obterSelcionado() == true){
			    		gl.glColor3f(1.0f,1.0f,0.0f);
			    	}
			    	pontoX = ponto4D.obterX();
			    	pontoY = ponto4D.obterY();
			    	gl.glVertex2d(pontoX, pontoY);
			    	
				}
			gl.glEnd();

			//////////// ATENCAO: chamar desenho dos filhos... 
			
			if (temFilho()){
				for (ObjetoGrafico objetoGrafico : filhos) {
					gl.glBegin(primitiva);
				    for (Ponto4D ponto4D : objetoGrafico.vertices) {
						gl.glColor3f(R,G,B);
				    	if(ponto4D.obterSelcionado() == true){
				    		gl.glColor3f(1.0f,1.0f,0.0f);
				    	}
				    	gl.glVertex2d(pontoX - ponto4D.obterX(), pontoY - ponto4D.obterY());
					}
				    gl.glEnd();
				}
			}

		gl.glPopMatrix();		
		
	}
	
	public void setarBBox(){	
		double minX = 1000;
		double minY = 1000;
		double maxX = 0;
		double maxY = 0;
		System.out.println("desenhaBBox");
								
		for (Ponto4D ponto4d : vertices) {
			if (ponto4d.obterX() <= minX){
				minX = ponto4d.obterX();
			}
			if (ponto4d.obterY() <= minY){
				minY = ponto4d.obterY();
			}
			if (ponto4d.obterX() >= maxX){
				maxX = ponto4d.obterX();
			}				
			if (ponto4d.obterY() >= maxY){
				maxY = ponto4d.obterY();
			}		
							
		}
		
		bb.setarXmin(minX);
		bb.setarYmin(minY);
		bb.setarXmax(maxX);
		bb.setarYmax(maxY);		
	}

	public boolean intersecao(double yI){
		double ti = -1; 
		
		for (int i = 0; i < vertices.size() -1; i++) {	
			if (vertices.get(i + 1)!=null){
				ti = (yI - vertices.get(i).obterY()) / (vertices.get(i+1).obterY() - vertices.get(i).obterY());
				if (ti >= 0 && ti <= 1)
					return true;
			}			
		}	
		
		return false;		
		
	}
	
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

	public void exibeVertices() {
		/*System.out.println("P0[" + vertices[0].obterX() + "," + vertices[0].obterY() + "," + vertices[0].obterZ() + "," + vertices[0].obterW() + "]");
		System.out.println("P1[" + vertices[1].obterX() + "," + vertices[1].obterY() + "," + vertices[1].obterZ() + "," + vertices[1].obterW() + "]");
		System.out.println("P2[" + vertices[2].obterX() + "," + vertices[2].obterY() + "," + vertices[2].obterZ() + "," + vertices[2].obterW() + "]");
		System.out.println("P3[" + vertices[3].obterX() + "," + vertices[3].obterY() + "," + vertices[3].obterZ() + "," + vertices[3].obterW() + "]");*/
//		System.out.println("anguloGlobal:" + anguloGlobal);
	}
	
	//Marcar um ponto como selecionado
	public Ponto4D selecionarPonto(Ponto4D pontoExterno){
		Ponto4D retorno = null;
		Ponto4D pontoObjTranformado;

		for (Ponto4D pontoObjeto : vertices) {

			pontoObjeto.atribuiSelecionado(false);
			
			pontoObjTranformado = new Ponto4D();
			pontoObjTranformado.atribuirX(pontoObjeto.obterX());
			pontoObjTranformado.atribuirY(pontoObjeto.obterY());
			pontoObjTranformado.atribuirZ(pontoObjeto.obterZ());
			pontoObjTranformado = matrizObjeto.transformPoint(pontoObjTranformado);
			
			if((pontoObjTranformado.obterX() <= pontoExterno.obterX() + 5 &&
				pontoObjTranformado.obterX() >= pontoExterno.obterX() - 5) &&
					
			   (pontoObjTranformado.obterY() <= pontoExterno.obterY() + 5 &&
			    pontoObjTranformado.obterY() >= pontoExterno.obterY() - 5) &&
				
			   (pontoObjTranformado.obterZ() <= pontoExterno.obterZ() + 5 &&
				pontoObjTranformado.obterZ() >= pontoExterno.obterZ() - 5)				
				
				){
				pontoObjeto.atribuiSelecionado(true);
				retorno = pontoObjeto;
			}
		}
		return retorno;
	}
	
	//Remover o ponto selecionado do objeto grafico
	public void deletarSelecionado(){
		for (Ponto4D ponto4d : vertices) {
			if(ponto4d.obterSelcionado() == true){
				vertices.remove(ponto4d);
				return;
			}
		}
	}
	
	public void atribuirCor(float R, float G, float B){
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public BBox obterBBox(){
		return this.bb;
	}
	
}

