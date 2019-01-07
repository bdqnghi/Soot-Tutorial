package soot.tutorial.dotgraph;

import java.util.HashMap;
import java.util.Iterator;

import soot.MethodOrMethodContext;
import soot.Modifier;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.util.dot.DotGraph;

public class NodeVisitor {
	private static HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

	public static void visit(DotGraph dot, CallGraph callGraph, SootMethod currentSootMethod) {
		String identifier = getIdentifier(currentSootMethod);
		
		drawCurrentNode(dot, currentSootMethod, identifier);
		visitUnvisitedParentNodes(dot, callGraph, currentSootMethod);

		// iterate over unvisited children
		Iterator<MethodOrMethodContext> childrenTargets = new Targets(callGraph.edgesOutOf(currentSootMethod));
		if (childrenTargets != null) {
			while (childrenTargets.hasNext()) {
				SootMethod c = (SootMethod) childrenTargets.next();
				if (c == null)
					System.out.println("c is null");

				String cIdentifier = getIdentifier(c);
				dot.drawEdge(identifier, cIdentifier);

				if (!visited.containsKey(c.getSignature()))
					visit(dot, callGraph, c);
			}
		}
	}
	
	public static String getIdentifier(SootMethod sootMethod) {
		String identifier;
		
		if (0 == sootMethod.getDeclaringClass().getInterfaceCount()) {
			identifier = "<NoInterface>";
		} else {
			identifier = "<Interfaces:";
			for (SootClass sootClass: sootMethod.getDeclaringClass().getInterfaces()) {
				identifier += " " + sootClass;
			}
			identifier += ">";
		}
		
		if (false == sootMethod.getDeclaringClass().hasSuperclass()) {
			identifier += " <NoSuperClass>";
		} else {
			identifier += " <SuperClass: " + sootMethod.getDeclaringClass().getSuperclass().toString() + ">";
		}

		if (Modifier.toString(sootMethod.getModifiers()).equals("")) {
			identifier += " <NoModifier>";
		} else {
			identifier += " <Modifiers: " + Modifier.toString(sootMethod.getModifiers()) + ">";
		}
		
		identifier += " " + sootMethod.getSignature();		
		return identifier;
	}
	
	protected static void drawCurrentNode(DotGraph dot, SootMethod currentSootMethod, String identifier) {
		visited.put(currentSootMethod.getSignature(), true);
		dot.createSubGraph(identifier);
		dot.drawNode(identifier);
	}

	/** 
	 * iterate over unvisited parents
	 * @param dot
	 * @param callGraph
	 * @param currentSootMethod
	 */
	protected static void visitUnvisitedParentNodes(DotGraph dot, CallGraph callGraph, SootMethod currentSootMethod) {
		Iterator<MethodOrMethodContext> parrentTargets = new Targets(callGraph.edgesInto(currentSootMethod));
		if (parrentTargets != null) {
			while (parrentTargets.hasNext()) {
				SootMethod p = (SootMethod) parrentTargets.next();

				if (p == null)
					System.out.println("p is null");

				if (!visited.containsKey(p.getSignature()))
					visit(dot, callGraph, p);
			}
		}
	}
}
