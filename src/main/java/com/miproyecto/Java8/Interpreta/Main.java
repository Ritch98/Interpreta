package com.miproyecto.Java8.Interpreta;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//import org.antlr.v4.runtime.ANTLRFileStream;
//import org.antlr.v4.runtime.CommonTokenStream;

import org.antlr.v4.runtime.*;


public class Main {

	public static void main(String[] args) throws IOException {
		
		String program = "test/test.jv";

		System.out.println("Interpreting file " + program);

		Java8Lexer lexer = new Java8Lexer(new ANTLRFileStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);

		Java8Parser.TypeDeclarationContext tree = parser.typeDeclaration();
		
		Java8CustomVisitor visitor = new Java8CustomVisitor();
		
		visitor.visit(tree);
		
		List<Token> lista = tokens.getTokens();
		Iterator iter = lista.iterator();
		
		System.out.println("\nNumero de Tokens: " + tokens.getTokens().size() + "\n\n");
		
		for(int i = 0; i < tokens.getTokens().size(); i++) {
			
			System.out.println(iter.next());
			
		}
		
		System.out.println("Interpretation finished");

	}

}
