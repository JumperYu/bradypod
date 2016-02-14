package com.bradypod.reflect.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassPrinter implements ClassVisitor{

	@Override
	public void visit(int version, int access, String name,
			String sign, String superName,
			String[] interfaces) {
		System.out.println(name + " extends " + superName + " {");
	}

	@Override
	public void visitSource(String paramString1, String paramString2) {
		
	}

	@Override
	public void visitOuterClass(String paramString1, String paramString2,
			String paramString3) {
		
	}

	@Override
	public AnnotationVisitor visitAnnotation(String paramString,
			boolean paramBoolean) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute paramAttribute) {
		
	}

	@Override
	public void visitInnerClass(String paramString1, String paramString2,
			String paramString3, int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FieldVisitor visitField(int access, String name,
			String desc, String sign, Object exceptions) {
		System.out.println("	" + name + desc);
		return null;
	}

	@Override
	public MethodVisitor visitMethod(int paramInt, String name,
			String desc, String sign,
			String[] exceptions) {
		System.out.println("	" + name + desc);
		return null;
	}

	@Override
	public void visitEnd() {
		System.out.println("}");
	}

}
