import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;

class panelCircular extends javax.swing.JPanel implements Runnable,Serializable{

    private int progreso = 0; //se crea para animacion de circulo
    private Thread hiloPrincipal = null; //creacion del hilo 
    private int radio = 0; // declaracion del radio
    private int posicionX; //las posiciones que se va colocar el punto medio del compas
    private int posicionY;//las posiciones que se va colocar el punto medio del compas
    private ArrayList<Integer> posiciones = new ArrayList<>();  //se crea el arrayList para guardar las posicione que el usuario a dado
    private Color color = Color.BLACK; //el color del circulo

    // tamaño del panel de arriba donde se dibuja el circulo
    public panelCircular(int ancho, int alto) {
        this.setSize(ancho, alto);
        this.setPosiciones(this.getWidth()/2, this.getHeight()/2);
        this.setBackground(new Color(255,0,0));
    }
    //las posiciones que el usuario tecleo
    public panelCircular(){
    	this.setSize(200, 200); //tamaño de la ventana
    	this.posicionX = 0; 
    	this.posicionY = 0;
    	this.setBackground(new Color(255,0,0));
    }
    
    public Color getColor(){
    	return this.color;
    }
    
    public int getRadioCirculo(){
    	return this.radio;
    }
    
    public int getPosicionX(){
    	return this.posicionX;
    }
    public int getPosicionY(){
    	return this.posicionY;
    }
    //se redibuja el circulo de compas
    public ArrayList<Integer> getCentro(){
    	this.posiciones.clear();
    	this.posiciones.add(this.posicionX);
    	this.posiciones.add(this.posicionY);
    	return this.posiciones;
    }
    
    
    public void setPosiciones(int posicionX,int posicionY){
    	this.posicionX = posicionX;
    	this.posicionY  = posicionY;
    }
    public void setPosicionX(int posicionX){
    	this.posicionX = posicionX;
    }
    
    public void setPosicionY(int posicionY){
    	this.posicionY = posicionY;
    }
    public void setRadio(int radio){
    	this.radio = radio;
    }
    public void setColor(Color color){
    	this.color = color;
    }
    

    // metodo de pintado de la pantalla donde se hace el circulo
    public void paint(Graphics g) {
    	this.setSize(this.getWidth()+this.radio,this.getHeight()+this.radio);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(this.posicionX, this.posicionY);
        g2.rotate(Math.toRadians(270));

        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
        Ellipse2D circulo = new Ellipse2D.Float(0, 0, 110, 110);
        Ellipse2D circulo1 = new Ellipse2D.Float(0, 0, 110, 110);

        arc.setFrameFromCenter(new Point(0, 0), new Point(this.radio, this.radio));
        circulo.setFrameFromCenter(new Point(0, 0), new Point(this.radio-5, this.radio-5));
        circulo1.setFrameFromCenter(new Point(0, 0), new Point(this.radio, this.radio));

        arc.setAngleStart(1);
        arc.setAngleExtent(-this.progreso * 3.6); //este this se pone negativo ya que si se pone positivo se empieza la animacion del otro lado
        
        g2.setColor(this.color);
        g2.draw(arc);
        g2.fill(arc);

        g2.setColor(Color.CYAN); //color del circulo central
        g2.draw(circulo);
        g2.fill(circulo);
        
        this.setOpaque(false);
    }
    
    //Hilo 
    public void dibujar(){
    	if(this.hiloPrincipal == null){
    		this.hiloPrincipal = new Thread(this);
    		//this.progreso = 0;
    		this.hiloPrincipal.start(); //se inicia el hilo principal
    	}
    	
    }
    

    @Override
    // metodo de la funcion en la cual tiene la funcion del hilo
    public void run() {
        try {
            //int tiempo = (int) (Math.random() * 100) + 1;
            for (int i = 1; i <= 100; i++) {
                this.progreso = i; //este progreso es la animacion de dibujado como de compas
                this.repaint(); //cada vez que se vuelva a poner parametros se va a redibujar en otra posicion sin borrar el anterior
                Thread.sleep(30);  // el hilo duerme 
            }
           
            
            this.hiloPrincipal = null;  // se manda como null
            


            
        } catch (Exception e) {
        	
        }
    }
}
