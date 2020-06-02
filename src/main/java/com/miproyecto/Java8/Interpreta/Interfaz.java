package com.miproyecto.Java8.Interpreta;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.io.*;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

//import org.antlr.v4.runtime.ANTLRFileStream;
//import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.*;
import javax.swing.JScrollBar;
import java.awt.TextArea;


public class Interfaz extends JFrame {
	
	JFileChooser seleccionar = new JFileChooser();
	File archivo;
	FileInputStream entrada;
	FileOutputStream salida;
	
	
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public String AbrirArchivo(File archivo) {
		String documento = "";
		try {
			entrada = new FileInputStream(archivo);
			int ascii;
			while((ascii=entrada.read())!=-1) {
				char caracter = (char) ascii;
				documento+=caracter;
			}
		}catch (Exception e) {
		}
		return documento;
	}
	
	public String GuardarArchivo(File archivo, String documento) {
		String mensaje=null;
		try {
			salida = new FileOutputStream(archivo);
			byte[] bytxt=documento.getBytes();
			salida.write(bytxt);
			mensaje="Archivo Guardado";
		}catch(Exception e){
		}
		return mensaje;
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 875);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 74, 625, 436);
		contentPane.add(textArea);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(seleccionar.showDialog(null, "Guardar")==JFileChooser.APPROVE_OPTION) {
					archivo=seleccionar.getSelectedFile();
					if(archivo.getName().endsWith("txt")) {
						String Documento = textArea.getText();
						String mensaje = GuardarArchivo(archivo, Documento);
						if(mensaje != null) {
							JOptionPane.showMessageDialog(null, mensaje);
						}else {
							JOptionPane.showMessageDialog(null, "Archivo no compatible");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Guardar Documento de Texto");
					}
				}
				
			}
		});
		btnGuardar.setBounds(10, 516, 299, 30);
		contentPane.add(btnGuardar);
		
		JButton btnModifica = new JButton("Modificar .jv");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Main principal = new Main();
				
				FileWriter flwriter = null;
				String sFichero = "C:\\\\Users\\\\Ritch\\\\eclipse-workspace\\\\Interpreta\\\\test\\\\test.jv";
				File f = new File(sFichero);
				f.delete();
				try {
				  f.createNewFile();
				} catch (IOException ioe) {
				  ioe.printStackTrace();
				}
				
				try {//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
					flwriter = new FileWriter("C:\\\\Users\\\\Ritch\\\\eclipse-workspace\\\\Interpreta\\\\test\\\\test.jv", true);
					BufferedWriter bfwriter = new BufferedWriter(flwriter);
					bfwriter.write("");
					bfwriter.write(textArea.getText());
					bfwriter.close();
					JOptionPane.showMessageDialog(null, "Archivo test.jv ha sido sobre escrito satisfactoriamente (?)");
					//principal.main(null);
					
				} catch (IOException o) {
					o.printStackTrace();
				} finally {
					if (flwriter != null) {
						try {
							flwriter.close();
						} catch (IOException o) {
							o.printStackTrace();
						}
					}
				}
				
			}
		});
		btnModifica.setBounds(336, 516, 299, 30);
		contentPane.add(btnModifica);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(seleccionar.showDialog(null, "Abrir")==JFileChooser.APPROVE_OPTION) {
					archivo=seleccionar.getSelectedFile();
					if(archivo.canRead()) {
						if(archivo.getName().endsWith("txt")) {
							String documento=AbrirArchivo(archivo);
							textArea.setText(documento);
						}else {
							JOptionPane.showMessageDialog(null, "Archivo No Compatible");
						}
					}
				}
			}
		});
		btnAbrir.setBounds(10, 559, 625, 30);
		contentPane.add(btnAbrir);
		
		TextArea textAreaTokens = new TextArea();
		textAreaTokens.setEditable(false);
		textAreaTokens.setBounds(675, 74, 547, 436);
		contentPane.add(textAreaTokens);
		
		TextArea textAreaConsola = new TextArea();
		textAreaConsola.setBounds(675, 595, 547, 214);
		contentPane.add(textAreaConsola);
		
		JButton btnLexico = new JButton("Analizar Lexico");
		btnLexico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Arg0) {
				
				String program = "test/test.jv";
				
				textAreaTokens.setText("");
				
				textAreaTokens.append("Interpreting file " + program + "\n\n");
				
				System.out.println();

				Java8Lexer lexer = null;
				
				try {
					lexer = new Java8Lexer(new ANTLRFileStream(program));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				Java8Parser parser = new Java8Parser(tokens);

				Java8Parser.CompilationUnitContext tree = parser.compilationUnit();
				
				Java8CustomVisitor visitor = new Java8CustomVisitor();
				
				visitor.visit(tree);
				
				List<Token> lista = tokens.getTokens();
				Iterator iter = lista.iterator();
				
				//textAreaTokens.selectAll();
			    
			    textAreaTokens.append("Numero de Tokens: " + tokens.getTokens().size() + "\n\n");
				
				for(int i = 0; i < tokens.getTokens().size(); i++) {
					
					textAreaTokens.append(iter.next().toString() + "\n");
					
				}
				
				textAreaTokens.append("\nInterpretation finished");
				textAreaConsola.setText("");
			}
		});
		btnLexico.setBounds(675, 516, 547, 30);
		contentPane.add(btnLexico);
		
		TextArea textAreaShowTokens = new TextArea();
		textAreaShowTokens.setBounds(10, 595, 625, 214);
		contentPane.add(textAreaShowTokens);
		textAreaShowTokens.append("");
		File documento = new File("C:\\Users\\Ritch\\eclipse-workspace\\Interpreta\\target\\generated-sources\\antlr4\\Java8.tokens");
		String doc = AbrirArchivo(documento);
		textAreaShowTokens.setText("Tokens: \n\n" + doc);


		
		
		
		
		JButton btnSintactico = new JButton("Analizar Sintactico");
		btnSintactico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textAreaConsola.setText("");
				
				PrintStream out = new PrintStream( new TextAreaOutputStream( textAreaConsola ) );
		        System.setOut( out );
		        System.setErr( out );
				
				String program = "test/test.jv";

				System.out.println("Interpretando archivo " + program);

				Java8Lexer lexer = null;
				try {
					lexer = new Java8Lexer(new ANTLRFileStream(program));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				
				Java8Parser parser = new Java8Parser(tokens);

				Java8Parser.CompilationUnitContext tree = parser.compilationUnit();
				
				Java8CustomVisitor visitor = new Java8CustomVisitor();
				
				visitor.visit(tree);
		
				System.out.println("Interpretacion finalizada");

			}
		});
		btnSintactico.setBounds(675, 559, 547, 30);
		contentPane.add(btnSintactico);
		
		JLabel lblTitulo = new JLabel("S Y M B O L");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Consolas", Font.BOLD, 68));
		lblTitulo.setBounds(113, 13, 437, 55);
		contentPane.add(lblTitulo);
		
		JLabel lblIcono = new JLabel("New label");
		lblIcono.setIcon(new ImageIcon("C:\\Users\\Ritch\\Pictures\\matrix.jpg"));
		lblIcono.setBounds(0, 0, 1244, 840);
		contentPane.add(lblIcono);
		
		
		
	}
}
