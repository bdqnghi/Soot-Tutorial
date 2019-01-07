package soot.tutorial.dotgraph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.*;

public class Util {
    private static String dumpCallGraph(CallGraph cg) {
        Iterator<Edge> itr = cg.iterator();
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        while (itr.hasNext()) {
            Edge e = itr.next();
            String srcSig = e.getSrc().toString();
            String destSig = e.getTgt().toString();
            Set<String> neighborSet;
            if (map.containsKey(srcSig)) {
                neighborSet = map.get(srcSig);
            } else {
                neighborSet = new HashSet<String>();
            }
            neighborSet.add(destSig);
            map.put(srcSig, neighborSet);

        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(map);
        return json;
    }
}
