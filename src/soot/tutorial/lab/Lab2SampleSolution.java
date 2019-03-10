package soot.tutorial.lab;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PackManager;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.options.Options;


public class Lab2SampleSolution {

    public static void main(String[] args) {

        
        //prefer Android APK files// -src-prec apk
        Options.v().set_src_prec(Options.src_prec_apk);

        //output as APK, too//-f J
        Options.v().set_output_format(Options.output_format_dex);

        // resolve the PrintStream and System soot-classes
        Scene.v().addBasicClass("java.io.PrintStream",SootClass.SIGNATURES);
        Scene.v().addBasicClass("java.lang.System",SootClass.SIGNATURES);

        PackManager.v().getPack("jtp").add(new Transform("jtp.myInstrumenter", new BodyTransformer() {

            @Override
            protected void internalTransform(final Body b, String phaseName, @SuppressWarnings("rawtypes") Map options) {
                final PatchingChain<Unit> units = b.getUnits();

                //important to use snapshotIterator here
                List<Unit> saveUnit = new ArrayList<Unit>();
                for(Iterator<Unit> iter = units.snapshotIterator(); iter.hasNext();) {
                    final Unit u = iter.next();
                    saveUnit.add(u);
                    System.out.println(u.toString());
                    System.out.println("-----------------");
                    u.apply(new AbstractStmtSwitch() {

                        public void caseInvokeStmt(InvokeStmt stmt) {
                            InvokeExpr invokeExpr = stmt.getInvokeExpr();
                            if(invokeExpr.getMethod().getName().equals("loadUrl")) {
                                //stmt.getJavaSourceStartLineNumber();

                                Value argCheck = invokeExpr.getArg(0);
                                boolean exit = false;
                                while (!exit){
                                    for (int i = saveUnit.size() - 2; i >=0 ; i--) {
                                        Stmt s = (Stmt) saveUnit.get(i);

                                        if (s instanceof InvokeStmt) {
                                            InvokeStmt invS = (InvokeStmt)s;
                                            //int a = invS.getJavaSourceStartLineNumber();
                                            if (invS.getInvokeExpr().getArgs().contains(argCheck)) {
                                                argCheck = invS.getInvokeExpr();
                                            }
                                            else {

                                                if (checkContain(invS.getInvokeExpr().getUseBoxes(),argCheck)) {
                                                    Value url = checkAndRetrieveUrl(invS.getInvokeExpr().getArgs());
                                                    if (url != null) {
                                                        Local tmpRef = addTmpRef(b);
                                                        Local tmpString = addTmpString(b);
                                                        // insert "tmpRef = java.lang.System.out;"
                                                        units.insertBefore(Jimple.v().newAssignStmt(
                                                                tmpRef, Jimple.v().newStaticFieldRef(
                                                                        Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())), u);
                                                        // insert "tmpLong = 'HELLO';"
                                                        units.insertBefore(Jimple.v().newAssignStmt(tmpString,
                                                                StringConstant.v(url.toString() + "," +invokeExpr.getMethodRef().toString())), u);
                                                        // insert "tmpRef.println(tmpString);"
                                                        SootMethod toCall = Scene.v().getSootClass("java.io.PrintStream").getMethod("void println(java.lang.String)");
                                                        units.insertBefore(Jimple.v().newInvokeStmt(
                                                                Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), tmpString)), u);
                                                        //check that we did not mess up the Jimple
                                                        b.validate();
                                                        exit = true;
                                                    }
                                                }
                                            }
                                        }
                                        if (s instanceof AssignStmt) {
                                            if (argCheck.equals(((AssignStmt) s).getLeftOp())) {
                                                if (((AssignStmt) s).getRightOp() instanceof InvokeExpr) {
                                                    InvokeExpr rightOp = (InvokeExpr) ((AssignStmt) s).getRightOp();
                                                    argCheck = rightOp.getUseBoxes().get(0).getValue();
                                                }
                                            }
                                            if (argCheck.equals(((AssignStmt) s).getRightOp())){
                                                argCheck = ((AssignStmt) s).getLeftOp();
                                            }
                                        }
                                    }
                                    exit = true;
                                }

                                ;							}
                        }


                        private boolean checkContain(List<ValueBox> useBoxes, Value argCheck) {
                            for (int i = 0; i < useBoxes.size(); i++) {
                                if (useBoxes.get(i).getValue().equals(argCheck)) {
                                    return true;
                                }
                            }
                            return false;
                        }


                        public void caseAssignStmt(AssignStmt stmt) {


                        }

                    });
                }
            }


        }));

        soot.Main.main(args);
    }
    private static Value checkAndRetrieveUrl(List<Value> args) {
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).toString().contains("http://") || args.get(i).toString().contains("https://") || args.get(i).toString().contains("file://")) {
                return args.get(i);
            }
        }
        return null;
    }
    private static Local addTmpRef(Body body)
    {
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        return tmpRef;
    }

    private static Local addTmpString(Body body)
    {
        Local tmpString = Jimple.v().newLocal("tmpString", RefType.v("java.lang.String"));
        body.getLocals().add(tmpString);
        return tmpString;
    }
}