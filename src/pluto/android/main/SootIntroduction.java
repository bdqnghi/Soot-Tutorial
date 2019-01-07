package pluto.android.main;

import pluto.android.common.Constants;
import pluto.android.dotgraph.NodeVisitor;
import soot.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.graph.pdg.HashMutablePDG;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;

import java.util.Collections;
import java.util.Iterator;

public class SootIntroduction extends HashMutablePDG {

    public SootIntroduction(UnitGraph cfg) {
        super(cfg);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {

        // Setting up Soot
        String fileJar = Constants.JAR_FILE.toAbsolutePath().toString();
        soot.G.reset();
        Options.v().set_src_prec(Options.src_prec_java);
        Options.v().set_process_dir(Collections.singletonList(fileJar));
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().setPhaseOption("cg.spark", "verbose:true");
        Scene.v().loadNecessaryClasses();

        // Get the entry class
        SootClass entryclass = Scene.v().getSootClass("org.mozilla.javascript.tools.shell.Main");
        // Get the entry point of the class
        SootMethod entryPoint = entryclass.getMethodByName("exec");

        Scene.v().setEntryPoints(Collections.singletonList(entryPoint));

        // Print out all of the methods in the class
        for(SootMethod method : entryclass.getMethods()){
            System.out.println(method.getName());
        }
    }
}