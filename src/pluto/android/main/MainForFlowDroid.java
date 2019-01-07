package pluto.android.main;

import org.xmlpull.v1.XmlPullParserException;
import pluto.android.common.Constants;
import pluto.android.dotgraph.NodeVisitor;
import soot.Body;
import soot.PackManager;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import pluto.android.dotgraph.Util;

public class MainForFlowDroid {

    public static void main(String[] args) {
        String fileAndroidJar = Constants.ANDROID_JAR.toAbsolutePath().toString();
        String fileApk = Constants.APK_FABRICA_WORDCARD.toAbsolutePath().toString();
        String fileSourcesAndSinks = Constants.CURRENT_PROJECT_FOLDER.resolve("SourcesAndSinks.txt").toAbsolutePath()
                .toString();

        soot.G.reset();

        SetupApplication app = new SetupApplication(fileAndroidJar, fileApk);
        try {
            app.calculateSourcesSinksEntrypoints(fileSourcesAndSinks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_process_dir(Collections.singletonList(fileApk));
        Options.v().set_force_android_jar(fileAndroidJar);
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().setPhaseOption("cg.spark", "verbose:true");

        Scene.v().loadNecessaryClasses();

        SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();

        Options.v().set_main_class(entryPoint.getSignature());

        Scene.v().setEntryPoints(Collections.singletonList(entryPoint));

        System.out.println("............" + entryPoint.getActiveBody());

        PackManager.v().runPacks();

        System.out.println(Scene.v().getCallGraph().size());


        CallGraph cg = Scene.v().getCallGraph();

        DotGraph dot = new DotGraph("callgraph");

        // boolean filterAndroidSystemCLass = false;
        NodeVisitor.visit(dot, cg, entryPoint);
        //
        dot.plot(Constants.OUTPUT_FOLDER.resolve("CallGraph_flappybird" +
                DotGraph.DOT_EXTENSION).toAbsolutePath().toString());


        // Test
        Iterator<Edge> Edges = cg.iterator();
        while (Edges.hasNext()) {

            Edge edge = Edges.next();
            SootMethod sourceMethod = edge.src();
            System.out.println(edge.getSrc());
            System.out.println("Name: " + sourceMethod.getName());
            System.out.println("Active Body: " + sourceMethod.getActiveBody());

            Body body = sourceMethod.retrieveActiveBody();
            UnitGraph cfg = new ExceptionalUnitGraph(body);

            CFGToDotGraph cfgtodot = new CFGToDotGraph();
            DotGraph dg = cfgtodot.drawCFG((ExceptionalUnitGraph) cfg);

            dg.plot(Constants.OUTPUT_FOLDER.resolve("CFGMethod" +
                    DotGraph.DOT_EXTENSION).toAbsolutePath().toString());

            System.exit(1);

        }
    }

}
