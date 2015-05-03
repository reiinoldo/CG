import java.util.ArrayList;
import javax.media.opengl.GL;
public class ObjetoGrafico {
	GL gl;
	BBox bb;
	private float tamanho;

	private int primitiva; 
	private ArrayList<Ponto4D> vertices; 

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
	
	public void desenha(float R, float G, float B) {
		gl.glLineWidth(tamanho);
		gl.glPointSize(tamanho);

		gl.glPushMatrix();
			gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
			gl.glBegin(primitiva);
			    for (Ponto4D ponto4D : vertices) {
					gl.glColor3f(R,G,B);
			    	if(ponto4D.obterSelcionado() == true){
			    		gl.glColor3f(1.0f,1.0f,0.0f);
			    	}
			    	gl.glVertex2d(ponto4D.obterX(), ponto4D.obterY());
				}
			gl.glEnd();

			//////////// ATENCAO: chamar desenho dos filhos... 

		gl.glPopMatrix();		
		
	}
	
	public void desenhaBBox(){	
		if (!vertices.isEmpty()){
			System.out.println("desenhaBBox");
			bb.setarXmin(vertices.get(0).obterX());
			bb.setarYmin(vertices.get(0).obterY());
			bb.setarXmax(vertices.get(vertices.size()-1).obterX());
			bb.setarYmax(vertices.get(vertices.size()-1).obterY());
			bb.desenhaBB();
		}
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
		for (Ponto4D pontoObjeto : vertices) {
			System.out.println("ptO x: " + pontoObjeto.obterX() + "ptE x: " + pontoExterno.obterX());
			System.out.println("ptO y: " + pontoObjeto.obterY() + "ptE y: " + pontoExterno.obterY()) ;
			System.out.println("ptO z: " + pontoObjeto.obterZ() + "ptE z: " + pontoExterno.obterZ()) ;
			pontoObjeto.atribuiSelecionado(false);
			
			if((pontoObjeto.obterX() <= pontoExterno.obterX() + 5 &&
				pontoObjeto.obterX() >= pontoExterno.obterX() - 5) &&
					
			   (pontoObjeto.obterY() <= pontoExterno.obterY() + 5 &&
				pontoObjeto.obterY() >= pontoExterno.obterY() - 5) &&
				
			   (pontoObjeto.obterZ() <= pontoExterno.obterZ() + 5 &&
				pontoObjeto.obterZ() >= pontoExterno.obterZ() - 5)				
				
				){
				pontoObjeto.atribuiSelecionado(true);
				System.out.println("é igual");
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
	
}

