package com.miproyecto.Java8.Interpreta;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	private static final String EXTENSION = "jv";

	public static void main(String[] args) throws IOException {
		String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

		System.out.println("Interpreting file " + program);

		Java8Lexer lexer = new Java8Lexer(new ANTLRFileStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);

		Java8Parser.TypeDeclarationContext tree = parser.typeDeclaration();
		
		Java8CustomVisitor visitor = new Java8CustomVisitor();
		visitor.visit(tree);

		System.out.println("Interpretation finished");

	}

}
