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

import java.io.IOException;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 10, 625, 500);
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
		textAreaTokens.setBounds(675, 10, 547, 500);
		contentPane.add(textAreaTokens);
		
		JButton btnLexico = new JButton("Analizar Lexico");
		btnLexico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Arg0) {
				
				String program = "test/test.jv";

				System.out.println("Interpreting file " + program);

				Java8Lexer lexer = null;
				
				try {
					lexer = new Java8Lexer(new ANTLRFileStream(program));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				Java8Parser parser = new Java8Parser(tokens);

				Java8Parser.TypeDeclarationContext tree = parser.typeDeclaration();
				
				Java8CustomVisitor visitor = new Java8CustomVisitor();
				
				visitor.visit(tree);
				
				List<Token> lista = tokens.getTokens();
				Iterator iter = lista.iterator();
				
				System.out.println("\nNumero de Tokens: " + tokens.getTokens().size() + "\n\n");
				
				//textAreaTokens.selectAll();
			    
			    textAreaTokens.setText("");
				
				for(int i = 0; i < tokens.getTokens().size(); i++) {
					
					System.out.println(iter.next());
					textAreaTokens.append(iter.next().toString() + "\n");
					
				}
				
				System.out.println("Interpretation finished");
				
			}
		});
		btnLexico.setBounds(675, 516, 547, 30);
		contentPane.add(btnLexico);
	}
}
