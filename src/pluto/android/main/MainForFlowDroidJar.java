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

public class MainForFlowDroidJar extends HashMutablePDG {

    public MainForFlowDroidJar(UnitGraph cfg) {
        super(cfg);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        String fileJar = Constants.JAR_FILE.toAbsolutePath().toString();

        soot.G.reset();

        Options.v().set_src_prec(Options.src_prec_java);
        Options.v().set_process_dir(Collections.singletonList(fileJar));
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().setPhaseOption("cg.spark", "verbose:true");

        Scene.v().loadNecessaryClasses();

        SootClass entryclass = Scene.v().getSootClass("org.mozilla.javascript.tools.shell.Main");
        SootMethod entryPoint = entryclass.getMethodByName("exec");

        Scene.v().setEntryPoints(Collections.singletonList(entryPoint));

        System.out.println(entryclass.getJavaPackageName());

        PackManager.v().runPacks();

        CallGraph cg = Scene.v().getCallGraph();

        DotGraph dot = new DotGraph("callgraph");

        NodeVisitor.visit(dot, cg, entryPoint);

        System.out.println("Dumping Call Graph.........");
        dot.plot(Constants.OUTPUT_FOLDER.resolve("CallGraph_mozilla_js" +
                DotGraph.DOT_EXTENSION).toAbsolutePath().toString());

        Iterator<Edge> Edges = cg.iterator();
        while (Edges.hasNext()) {

            Edge edge = Edges.next();
            SootMethod sourceMethod = edge.src();
            System.out.println(edge.getSrc());

            if (!sourceMethod.getDeclaringClass().toString().equals("org.mozilla.javascript.tools.shell.Main")) {
                continue;
            }

            if (!sourceMethod.getName().toString().equals("exec")) {
                continue;
            }

            System.out.println(sourceMethod.getDeclaringClass());
            System.out.println("Name: " + sourceMethod.getName());

            Body body = sourceMethod.retrieveActiveBody();
            UnitGraph cfg = new ExceptionalUnitGraph(body);

            CFGToDotGraph cfgtodot = new CFGToDotGraph();
            DotGraph dg = cfgtodot.drawCFG((ExceptionalUnitGraph) cfg);

            dg.plot(Constants.OUTPUT_FOLDER.resolve("CFGMethodJar" +
                    DotGraph.DOT_EXTENSION).toAbsolutePath().toString());

        }
    }

}
