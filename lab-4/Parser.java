import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple solution of lab-4.
 * 
 * @author Herman Zvonimir Dosilovic
 */
public final class Parser {

  private static String string;
  private static int index = -1;
  private static boolean prihvati = true;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    string = reader.readLine();
    S();
    System.out.println();
    if (prihvati && index + 1 >= string.length()) {
      System.out.println("DA");
    } else {
      System.out.println("NE");
    }
    reader.close();
  }

  private static void S() {
    if (!prihvati)
      return;
    System.out.print("S");
    index++;
    if (index < string.length() && string.charAt(index) == 'a') {
      A();
      B();
    } else if (index < string.length() && string.charAt(index) == 'b') {
      B();
      A();
    } else {
      prihvati = false;
    }
  }

  private static void B() {
    if (!prihvati)
      return;
    System.out.print("B");
    index++;
    if (index < string.length() && string.charAt(index) == 'c') {
      index++;
      if (index < string.length() && string.charAt(index) == 'c') {
        S();
        index++;
        if (index < string.length() && string.charAt(index) == 'b') {
          index++;
          if (index < string.length() && string.charAt(index) == 'c') {
            ;
          } else {
            prihvati = false;
          }
        } else {
          prihvati = false;
        }
      } else {
        prihvati = false;
      }
    } else {
      index--;
    }
  }

  private static void A() {
    if (!prihvati)
      return;
    System.out.print("A");
    index++;
    if (index < string.length() && string.charAt(index) == 'b') {
      C();
    } else if (index < string.length() && string.charAt(index) == 'a') {
      ;
    } else {
      prihvati = false;
    }
  }

  private static void C() {
    if (!prihvati)
      return;
    System.out.print("C");
    A();
    A();
  }

}
