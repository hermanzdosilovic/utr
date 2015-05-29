/**
 * Represents a triplet of three generic types.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class Triplet<F, S, T> {

  /** First value in triplet. */
  private F first;

  /** Second value in triplet. */
  private S second;
  
  /** Third value in triplet. */
  private T third;
  
  /**
   * Empty default constructor.
   */
  public Triplet() {}

  /**
   * Creates <code>Triplet</code> with three members.
   * 
   * @param first - first member of triplet
   * @param second - second member of triplet
   * @param third - third memeber of triplet
   */
  public Triplet(F first, S second, T third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public F getFirst() {
    return first;
  }

  public void setFirst(F first) {
    this.first = first;
  }

  public S getSecond() {
    return second;
  }

  public void setSecond(S second) {
    this.second = second;
  }

  public T getThird() {
    return third;
  }

  public void setThird(T third) {
    this.third = third;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    result = prime * result + ((third == null) ? 0 : third.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Triplet))
      return false;
    Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
    if (first == null) {
      if (other.first != null)
        return false;
    } else if (!first.equals(other.first))
      return false;
    if (second == null) {
      if (other.second != null)
        return false;
    } else if (!second.equals(other.second))
      return false;
    if (third == null) {
      if (other.third != null)
        return false;
    } else if (!third.equals(other.third))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "(" + first + ", " + second + ", " + third + ")";
  }

}
