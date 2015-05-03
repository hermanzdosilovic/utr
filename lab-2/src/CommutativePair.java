/**
 * Represents a sortable commutative pair of two generic types. <br>
 * Example: <br>
 * <br>
 * <code>
 * CommutativePair&ltString, String> pairA = new CommutativePair<>("a", "b");<br>
 * CommutativePair&ltString, String> pairB = new CommutativePair<>("b", "a");<br><br>
 * pairA.equals(pairB); // true<br>
 * pairA.compareTo(pairB); // < 0
 * </code>
 * 
 * @author Herman Zvonimir Dosilovic
 */
public class CommutativePair<F extends Comparable<F>, S extends Comparable<S>> implements
    Comparable<CommutativePair<F, S>> {

  /** First value in pair. */
  private F first;

  /** Second value in pair. */
  private S second;

  /**
   * Empty default constructor.
   */
  public CommutativePair() {}

  /**
   * Creates <code>Pair</code> with two members.
   * 
   * @param first - first member of pair
   * @param second - second member of pair
   */
  public CommutativePair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns first member of pair.
   * 
   * @return first member of pair
   */
  public F getFirst() {
    return first;
  }

  /**
   * Sets first member of pair.
   * 
   * @param first - new value of first member
   */
  public void setFirst(F first) {
    this.first = first;
  }

  /**
   * Returns second member of pair.
   * 
   * @return second member of pair
   */
  public S getSecond() {
    return second;
  }

  /**
   * Sets second member of pair.
   * 
   * @param second - new value of second member
   */
  public void setSecond(S second) {
    this.second = second;
  }

  /**
   * Returns reversed pair of elements from given pair.
   * 
   * @param pair pair to reverse
   * @return reversed pair of elements from given pair
   */
  public static <F extends Comparable<F>, S extends Comparable<S>> CommutativePair<S, F> reverse(
      CommutativePair<F, S> pair) {
    return new CommutativePair<>(pair.getSecond(), pair.getFirst());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    int firstHashCode = first == null ? 0 : first.hashCode();
    int secondHashCode = second == null ? 0 : second.hashCode();
    if (firstHashCode < secondHashCode) {
      int tmp = firstHashCode;
      firstHashCode = secondHashCode;
      secondHashCode = tmp;
    }
    result = prime * result + firstHashCode;
    result = prime * result + secondHashCode;
    return result;
  }

  /**
   * Returns <code>true</code> if first value of this pair equals to first value of the given pair
   * and second value of this pair equals to second value of given pair.
   * 
   * @param other pair for comparison
   * @return <code>true</code> if first value of this pair equals to first value of the given pair
   *         and second value of this pair equals to second value of given pair
   */
  public <U extends Comparable<U>, V extends Comparable<V>> boolean matchesWith(
      CommutativePair<U, V> other) {
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
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;

    CommutativePair<?, ?> other = (CommutativePair<?, ?>) obj;

    return matchesWith(other) || matchesWith(CommutativePair.reverse(other));
  }

  @Override
  public String toString() {
    return "(" + first + ", " + second + ")";
  }

  @Override
  public int compareTo(CommutativePair<F, S> o) {
    if (first.compareTo(o.getFirst()) == 0)
      return second.compareTo(o.getSecond());
    return first.compareTo(o.getFirst());
  }

}
