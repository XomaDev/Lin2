package xyz.kumaraswamy.lin2.structs;

public class Result {

  public final double accuracy;
  public final int leastDistance;
  public final String group;
  public final String matchContentName;
  public final char[] bestBits;

  public Result(double accuracy, int leastDistance, String group, String matchContentName, char[] bestBits) {
    this.accuracy = accuracy;
    this.leastDistance = leastDistance;
    this.group = group;
    this.matchContentName = matchContentName;
    this.bestBits = bestBits;
  }


  @Override
  public String toString() {
    return "Result{" +
        "accuracy=" + accuracy +
        ", leastDistance=" + leastDistance +
        ", group='" + group + '\'' +
        ", matchContentName='" + matchContentName + '\'' +
        '}';
  }
}