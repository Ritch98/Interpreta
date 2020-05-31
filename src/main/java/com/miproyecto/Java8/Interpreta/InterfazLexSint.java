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

public class InterfazLexSint extends JFrame {
	
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
					InterfazLexSint frame = new InterfazLexSint();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazLexSint() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTituloPrincipal = new JLabel("S Y M B O L");
		lblTituloPrincipal.setFont(new Font("Broadway", Font.BOLD, 40));
		lblTituloPrincipal.setForeground(Color.BLACK);
		lblTituloPrincipal.setBounds(510, 13, 254, 52);
		contentPane.add(lblTituloPrincipal);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(44, 96, 720, 420);
		contentPane.add(textArea);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnAbrir.setBounds(44, 572, 720, 30);
		contentPane.add(btnAbrir);
		
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
		btnGuardar.setBounds(44, 529, 351, 30);
		contentPane.add(btnGuardar);
		
		JButton btnAnalizar = new JButton("Analizar");
		btnAnalizar.setBounds(413, 529, 351, 30);
		contentPane.add(btnAnalizar);
	}
}
