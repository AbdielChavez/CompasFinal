import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Compas_Principal{
		
private static	JPanel principal;
private static	JPanel contenedor;
private static	JTextField nombres2;
private static JTextField nombres3;
private static JTextField radio;
private static panelCircular panel;
private static JButton aceptar;
private static JButton cancelar;
private static ActionBoton oyenteBotonesGenerales;
private static Conexion conec;
private static Connection con;
private static int numCirculo = 0;

	public static void main(String [] args){
		
		oyenteBotonesGenerales = new ActionBoton();
		conec = new Conexion();
		con = conec.getConexion();
		
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		
		
		//panel superior de dibujado
		principal = new JPanel();
		principal.setBounds(0, 0, frame.getWidth(), frame.getHeight() -200);
		principal.setBackground(Color.CYAN);
		principal.setLayout(null);
		;
		frame.add(principal);
		
		
		
		
		//panel inferior de datos
		contenedor = new JPanel();
		contenedor.setBounds(0, principal.getHeight(), frame.getWidth(), 200);
		contenedor.setBackground(Color.gray);
		contenedor.setLayout(null);
		//boton de pedir centro
		//label
		JLabel nombre1 = new JLabel("Punto centro: ");
        nombre1.setBounds(5, 10, 100, 40);
        contenedor.add(nombre1);
        //textfield 
        nombres2 = new JTextField();
        nombres2.setBounds(90,10,100,30);
        contenedor.add(nombres2);
        
        nombres3 = new JTextField();
        nombres3.setBounds(220, 10, 100, 30);
        contenedor.add(nombres3);
		//final del boton pedir centro
        
        JLabel radio1 = new JLabel("Radio para el circulo: ");
        radio1.setBounds(5, 60, 150, 40);
        contenedor.add(radio1);
        //textfield
        radio = new JTextField();
        radio.setBounds(150, 60, 100, 30);
        contenedor.add(radio);
        
        //botones
        
        aceptar = new JButton("Aceptar");
        aceptar.setBounds(150,120,120,40);
        aceptar.addActionListener(oyenteBotonesGenerales);
        contenedor.add(aceptar);
        
        cancelar = new JButton("Cerrar");
        cancelar.setBounds(290,120,120,40);
        cancelar.addActionListener(oyenteBotonesGenerales);
        contenedor.add(cancelar);
        
        
		frame.add(contenedor);
		
		
		
		frame.setVisible(true);
		
	}
	//insertar los datos en la base de datos
	public static void insertarDatos(int numCirculo,int posX,int posY,int radioF) {
		try {
			
			String sql = "INSERT INTO datos (CodCirculo,posx,posy,radio) VALUES (?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, numCirculo );
			pst.setInt(2, posX);
			pst.setInt(3, posY);
			pst.setInt(4, radioF);
			
			pst.executeUpdate();
			
			//mensaje de los datos que se introdujeron 
			JOptionPane.showMessageDialog(null, "Los datos son: "+numCirculo+" posicion x: "+posX+" posicion y:"+posY
					+" radio: "+radioF, "Agregado",JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	//listener de los boton aceptar y cancelar 
	//ActionBoton como clase interna a Compas_Principal
	static class ActionBoton implements ActionListener{
		    
	        @Override
	        public void actionPerformed (ActionEvent e){
	       
	            if (aceptar == e.getSource()) {
	            	
	            	numCirculo++;
	            	
	            	
	            	int posX =Integer.parseInt( nombres2.getText());
	            	int posY = Integer.parseInt(nombres3.getText());
	            	int radioF = Integer.parseInt(radio.getText());
	            	
	            	panelCircular panel = new panelCircular();
	            	panel.setPosiciones(posX, posY);
	            	panel.setRadio(radioF);
	            	
	            	principal.add(panel);
	            	
	            	
	            	panel.dibujar();
	            	
	            	insertarDatos(numCirculo,posX,posY,radioF);
	            	
	            	
	            }else if(cancelar == e.getSource()) {
	            	
	            System.exit(0);
	            }
	            
	            
	        }
	      }
}
