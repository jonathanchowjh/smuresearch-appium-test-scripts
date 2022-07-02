package apps;

import java.util.ArrayList;
import java.util.function.Function;

public class FunctionGraph {

  public String name;
  public Function<Void, Integer> func;
  public ArrayList<FunctionGraph> nodes;

  public FunctionGraph(String name, Function func) {
    this.name = name;
    this.func = func;
    this.nodes = new ArrayList<>();
  }

  public FunctionGraph(
    String name,
    Function func,
    ArrayList<FunctionGraph> nodes
  ) {
    this.name = name;
    this.func = func;
    this.nodes = nodes;
  }

  public FunctionGraph run() {
    Integer id = func.apply(null);
    if (id >= nodes.size()) return null;
    return nodes.get(id);
  }
}
